package me.test.test;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public final class CommandLineExecutor {
	
	private static final int TEST_SIZE_FROM_DEFAULT = 1;
	private static final int TEST_SIZE_TO_DEFAULT = 10000;
	private static final int TEST_SIZE_STEP_DEFAULT = 10;
	
	private static final int TEST_SIZE_DEFAULT = 100;
	private static final int TEST_REPEAT_COUNT_DEFAULT = 5;
	
	private static enum COMMAND {
		RUN,
		GENERATE;
		
		public static boolean isCommand(String value) {
			try {
				COMMAND.valueOf(value);
				return true;
			}
			catch (Exception e) {
				return false;
			}

		}
	}

	@SuppressWarnings("static-access")
	private static Options buildOptions() {
		
		// Looks like Apache Commons CLI is using a hack here
		// http://commons.apache.org/cli/usage.html
		// see @SuppressWarnings("static-access")
		return new Options()
			.addOption(OptionBuilder
					.withDescription("Prints this help.")
					.create("help"))
			.addOption(OptionBuilder
					.withDescription("Prints system info.")
					.create("info"))
			.addOption(OptionBuilder
					.withDescription("Prints list of all available tests.")
					.create("list"))
			.addOption(OptionBuilder
					.withDescription("Waits for user input before and after test running " +
										"to allow easier profiling.")
					.create("profiling"))
			.addOption(OptionBuilder
					.withDescription("Used in colloboration with RUN and GENERATE command to turn on optional csv " + 
										"(comma separated values) file format output.")
					.create("csv"))
			
			.addOption(OptionBuilder
					.withArgName("testSizeFrom")
					.hasArg()
					.withDescription("Used in colloboration with GENERATE command to set minimum test size. " +
										"Optional, default value is " + TEST_SIZE_FROM_DEFAULT + ".")
					.create("sizeFrom"))
			.addOption(OptionBuilder
					.withArgName("testSizeTo")
					.hasArg()
					.withDescription("Used in colloboration with GENERATE command to set maximum test size. " + 
										"Optional, default value is " + TEST_SIZE_TO_DEFAULT + ".")
					.create("sizeTo"))
			.addOption(OptionBuilder
					.withArgName("testSizeStep")
					.hasArg()
					.withDescription("Used in colloboration with GENERATE command to set test size step " + 
										"increment for inbetween tests. " + 
										"Optional, default value is " + TEST_SIZE_STEP_DEFAULT + ".")
					.create("sizeStep"))
			.addOption(OptionBuilder
					.withDescription("Used in colloboration with GENERATE command to set test size step " + 
										"increment operator for inbetween tests. Default operator is addition." + 
										"Optional, default value is " + TEST_SIZE_STEP_DEFAULT + ".")
					.create("multiply"))
			.addOption(OptionBuilder
					.withArgName("testId")
					.hasArg()
					.withDescription("Used in colloboration with RUN command to specify the id of the test to execute. " + 
										"Is required when using RUN command.")
					.create("testId"))
			.addOption(OptionBuilder
					.withArgName("testSize")
					.hasArg()
					.withDescription("Used in colloboration with RUN command to specify test size. " + 
										"Optional, default value is " + TEST_SIZE_DEFAULT + ".")
					.create("size"))
			.addOption(OptionBuilder
					.withArgName("repeatCount")
					.hasArg()
					.withDescription("Used in colloboration with RUN and GENERATE command to specify the count of test executions. " + 
										"Optional, default value is " + TEST_REPEAT_COUNT_DEFAULT + ".")
					.create("count"))
			.addOption(OptionBuilder
					.withArgName("token")
					.hasArg()
					.withDescription("Addes token to the end of test id in the output.")
					.create("token"));
		
	}
	
	private static void printUsage() {
		HelpFormatter formatter = new HelpFormatter();
	
		formatter.printHelp( 
				"java -jar stmtest.jar [OPTIONS] [COMMAND]", 
				"Generates test executions with GENERATE command." + "\n" +
				"Tests are executed with RUN command." + "\n" +
				"" + "\n" +
				"Example usages: " + "\n" +
				"  java -jar stmtest.jar -sizeFrom 1 -sizeTo 10000 -sizeStep 10 GENERATE" + "\n" +
				"  java -jar stmtest.jar -testId com.unique.test.Id -size 100 -count 10 RUN" + "\n" + 
				"\n",
				buildOptions(), 
				"");
	}
	
	public static void execute(String[] args, 
									GenerateTests generateTests, 
									RunTest runTest,
									ListTests listTests) {
	
		try {

	        CommandLine commandLine = new GnuParser().parse( buildOptions(), args );
	        
	        if (commandLine.hasOption("help")) {
	        	printUsage();
	        }
	        else if (commandLine.hasOption("list")) {
	        	listTests.call();
	        }
	        else if (commandLine.hasOption("info")) {
	        	System.out.println("Processor core count: " + Runtime.getRuntime().availableProcessors());
	        }
	        else if (commandLine.getArgList().size() < 1) {
	        	printUsage();
	        }
	        else if (commandLine.getArgList().size() > 1) {
	        	printMessageWithUsage("Only one command can be executed.");
	        } 
	        else if (command(commandLine) == null) {
	        	printMessageWithUsage("Unknown command.");
	        }
	        else if (!COMMAND.isCommand(commandStr(commandLine))) {
	        	printMessageWithUsage("Unknown command: " + commandStr(commandLine));
	        }
	        else {
	        	switch (COMMAND.valueOf(commandStr(commandLine))) {
	        	case RUN:
	        		
	        		if (!commandLine.hasOption("testId")) {
	        			printMessageWithUsage("Option testId is required when executing command RUN.");
	        		}
	        		else if (commandLine.hasOption("size") && !isInteger(commandLine.getOptionValue("size"))) {
	        			printMessageWithUsage("Please specify valid numeric value for option size.");
	        		}
	        		else if (commandLine.hasOption("count") && !isInteger(commandLine.getOptionValue("count"))) {
	        			printMessageWithUsage("Please specify valid numeric value for option count.");
	        		}
	        		else {
		        		runTest.call(
		        				commandLine.getOptionValue("testId"),
		        				commandLine.hasOption("size") ? Integer.valueOf(commandLine.getOptionValue("size")).intValue() : TEST_SIZE_DEFAULT,
		        				commandLine.hasOption("count") ? Integer.valueOf(commandLine.getOptionValue("count")).intValue() : TEST_REPEAT_COUNT_DEFAULT,
		        				commandLine.hasOption("csv"),
		        				commandLine.hasOption("profiling"),
		        				commandLine.hasOption("token") ? commandLine.getOptionValue("token") : null
		        		);
	        		}
	        		break;
	        	case GENERATE:
	        		if (commandLine.hasOption("sizeFrom") && !isInteger(commandLine.getOptionValue("sizeFrom"))) {
	        			printMessageWithUsage("Please specify valid numeric value for option sizeFrom.");
	        		}
	        		else if (commandLine.hasOption("sizeTo") && !isInteger(commandLine.getOptionValue("sizeTo"))) {
	        			printMessageWithUsage("Please specify valid numeric value for option sizeTo.");
	        		}
	        		else if (commandLine.hasOption("sizeStep") && !isDouble(commandLine.getOptionValue("sizeStep"))) {
	        			printMessageWithUsage("Please specify valid numeric value for option sizeStep.");
	        		}
	        		else if (commandLine.hasOption("count") && !isInteger(commandLine.getOptionValue("count"))) {
	        			printMessageWithUsage("Please specify valid numeric value for option count.");
	        		}
	        		else {
	        			generateTests.call(
	        					commandLine.hasOption("sizeFrom") ? Integer.valueOf(commandLine.getOptionValue("sizeFrom")).intValue() : TEST_SIZE_FROM_DEFAULT,
	        					commandLine.hasOption("sizeTo") ? Integer.valueOf(commandLine.getOptionValue("sizeTo")).intValue() : TEST_SIZE_TO_DEFAULT,
       							commandLine.hasOption("sizeStep") ? Double.valueOf(commandLine.getOptionValue("sizeStep")).doubleValue() : TEST_SIZE_STEP_DEFAULT,
       							commandLine.hasOption("count") ? Integer.valueOf(commandLine.getOptionValue("count")).intValue() : TEST_REPEAT_COUNT_DEFAULT,
       							commandLine.hasOption("csv"),
       							commandLine.hasOption("multiply"),
       							commandLine.hasOption("token") ? commandLine.getOptionValue("token") : null
	        			);
		        		
	        		}
	        		break;
	        	}
	        }
	        
	    }
	    catch( ParseException exp ) {
	        // oops, something went wrong
	    	printMessageWithUsage(exp.getMessage());
	    }
		
		
	}

	private static String commandStr(CommandLine commandLine) {
		return command(commandLine).toString().toUpperCase();
	}

	private static Object command(CommandLine commandLine) {
		return commandLine.getArgList().get(0);
	}

	private static void printMessageWithUsage(String message) {
		System.out.println(message);
		System.out.println();
		printUsage();
	}
	
	private static boolean isInteger(String value) {
		
		try {
			Integer.valueOf(value);
			return true;
		}
		catch (NumberFormatException e) {
			return false;
		}
	}
	
	private static boolean isDouble(String value) {
		
		try {
			Double.valueOf(value);
			return true;
		}
		catch (NumberFormatException e) {
			return false;
		}
	}
	
}
