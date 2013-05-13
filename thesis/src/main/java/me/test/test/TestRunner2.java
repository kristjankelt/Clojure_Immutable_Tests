package me.test.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import me.test.util.debug.CountTime;
import me.test.util.debug.Out;

public class TestRunner2 {
	
	public static void main(String[] args) {
			
		CommandLineExecutor.execute(args, 
				
			new GenerateTests() {
				
				public void call(int sizeFrom, int sizeTo, double sizeStep, int repeatCount, 
									boolean csv, boolean multiply, String token) {
					
					printAllTests(TestCollection.getAllTests(), 
									sizeFrom, sizeTo, sizeStep, repeatCount, 
									csv, multiply, token);
				
				}
				
			},
			
			new RunTest() {
				
				public void call(String testId, 
								int size, int repeatCount, 
								boolean csv,
								boolean profiling, String token) {
					
					if (profiling) { waitForYes("Start profiling?"); }
					
					runTestById(TestCollection.getAllTests(), 
								testId, 
								size, repeatCount, 
								csv,
								token);
					
					if (profiling) { waitForYes("End profiling?"); }
				}
				
			},
			
			new ListTests() {
				
				public void call() {
					
					printTestList(TestCollection.getAllTests());
				
				}

				
			}
		
		);
		
	}
	
	private static void printAllTests(
								final Map<String, TestContainer> tests, 
								int testSizeFrom, int testSizeTo, double testSizeStep, int repeatCount,
								boolean csv, boolean multiply, String token) {
		for (String testId : tests.keySet()) {

			for (int testSize = testSizeFrom; 
					testSize <= testSizeTo; 
					testSize = stepOperation(testSize,  testSizeStep, multiply)) {
				
				StringBuilder options = new StringBuilder("");
				if (csv) {
					options.append(" -csv");
				}
				
				if (token != null) {
					options.append(" -token " + token);
				}
				
				// -Xss256m 
				
				Out.println("java -jar target/stmtest.jar RUN -size " + testSize + " -count " + repeatCount + " -testId " + testId + options.toString());
			}
		}
	}
	
	private static int stepOperation(int oldValue, double step, boolean multiply) {
		if (multiply) {
			System.out.println("STEP "+ oldValue + " " + step);
			return (int)(oldValue * step);
		}
		else {
			return (int)(oldValue + step);
		}
	}

	private static void runTestById(
								final Map<String, TestContainer> tests, String testId, 
								int size, int repeatCount, 
								boolean csv,
								String token) {
		
		if (tests.containsKey(testId)) {

			final TestContainer testContainer = tests.get(testId);
			final Test test = testContainer.test();
			
			if (token != null) {
				testId = testId + "#" + token;
			}
			
			try {
				
				
				long time = runTest(test, size, repeatCount);
			
				if (csv) {
					Out.csv(
							testId, 
							testContainer.getSetName(), testContainer.getGroupName(), testContainer.getTestName(),
							size, repeatCount, 
							time);
				}
				else {
					Out.padded(testId, 80, 10, size, repeatCount, Double.valueOf(time / 1000000.0));
				}
			}
			catch (Exception e) {
				if (csv) {
					Out.csv(
							testId, 
							testContainer.getSetName(), testContainer.getGroupName(), testContainer.getTestName(),
							size, repeatCount, 
							"", e.getMessage(), e.getClass());
				}
				else {
					Out.padded(testId, 80, 10, size, repeatCount, "-", e.getMessage(), e.getClass());
				}
			}
		}
		else {
			if (csv) {
				Out.csv(
						testId, 
						"", "", "",
						size, repeatCount, 
						"", "TEST NOT FOUND");
			}
			else {
				Out.padded(testId, 80, 10, size, repeatCount, "-", "TEST NOT FOUND");

			}
		}
	}
	
	private static long runTest(final Test test, final int testSize, final int runCount) {
		
		long totalRunTime = 0;
		
		for (int i = 0; i < runCount - 1; i++) { // WARM UP
			
			test.prepare(testSize);
			
			CountTime.run(new Runnable() {
	
				public void run() {
					test.run(testSize);
				}
				
			}, false);
		}
		
		test.prepare(testSize);
		
		totalRunTime += CountTime.run(new Runnable() {
			
			public void run() {
				test.run(testSize);
			}
			
		}, false);
		
		return totalRunTime;// / runCount;
	}
	
	private static void printTestList(Map<String, TestContainer> allTests) {
		
		List<String> sorted = new ArrayList<String>(allTests.keySet());
		
		Collections.sort(sorted);
		
		for (String testId : sorted) {
			Out.println(testId);
		}
	}
	
	private static boolean waitForYes(final String message) {
		
		while (true) {
			System.out.println(message + " [Answer y]");
			
		    Scanner answer = new Scanner(System.in);
		    
		    if (answer.nextLine().equals("y")) {
		    	break;
		    }
		       
		    answer.close();            
		  
		}
	       
		return false;
	}

}
