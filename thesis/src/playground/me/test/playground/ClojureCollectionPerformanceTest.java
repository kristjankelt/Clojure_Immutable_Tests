package me.test.playground;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.test.util.debug.CountTime;
import me.test.util.debug.Out;

import org.pcollections.PVector;
import org.pcollections.TreePVector;

import clojure.lang.IPersistentCollection;
import clojure.lang.ITransientVector;
import clojure.lang.PersistentVector;

import com.google.common.collect.ImmutableList;

public class ClojureCollectionPerformanceTest {
	
	private static final Integer PAD_SIZE = Integer.valueOf(50);
	
	private static final int HARD_TEST_LIMIT = 1000;
	
	private static final int TEST_SIZE = 1000;

	private static final int WARM_UP_PERIOD = 5;

	public static void main(String[] args) {
		
		System.out.println("Warming up ...");

		for (int i=0; i < WARM_UP_PERIOD; i++) addTest1(TEST_SIZE, false);
		Out.separator();
		
		System.out.println("Test run");
		Out.separator();
		for (int i=0; i < 1; i++) addTest1(TEST_SIZE, true);
		Out.separator();
		
	}
	
	private static void addTest1(final int runCount, boolean showElapsedTime) {
		CountTime.run(PAD_SIZE, "int[]", new Runnable() {

			public void run() {
				int[] list = new int[runCount]; 
				

				for (int i = 0; i < runCount; i++) {
					list[i] = 0;
				}
			}
			
		}, showElapsedTime);
		
		CountTime.run(PAD_SIZE, "Integer[]", new Runnable() {

			public void run() {
				Integer[] list = new Integer[runCount]; 
				

				for (int i = 0; i < runCount; i++) {
					list[i] = Integer.valueOf(0);
				}
			}
			
		}, showElapsedTime);
		
		CountTime.run(PAD_SIZE, "ArrayList<Integer>(initialCapacity)", new Runnable() {
			
			public void run() {
				List<Integer> list = new ArrayList<Integer>(runCount);
				

				for (int i = 0; i < runCount; i++) {
					list.add(
							Integer.valueOf(0));
				}
			}
			
		}, showElapsedTime);
		
		CountTime.run(PAD_SIZE, "ArrayList<Integer>()", new Runnable() {

			public void run() {
				List<Integer> list = new ArrayList<Integer>();
				

				for (int i = 0; i < runCount; i++) {
					list.add(
							Integer.valueOf(0));
				}
			}
			
		}, showElapsedTime);
		

		final PersistentVector persistentList = PersistentVector.create();
		
		CountTime.run(PAD_SIZE, "PersistentVector.create()", new Runnable() {

			public void run() {
				IPersistentCollection list = persistentList;
				
				for (int i = 0; i < runCount; i++) {
					list = list.cons(
							Integer.valueOf(0));
				}
			}
			
		}, showElapsedTime);
		
		
		CountTime.run(PAD_SIZE, "ArrayList<Integer>(initialCapacity) -> PersistentVector", new Runnable() {

			public void run() {
			
				List<Integer> list = new ArrayList<Integer>(runCount); 
				

				for (int i = 0; i < runCount; i++) {
					list.add(Integer.valueOf(0));
				}
				
				IPersistentCollection persistentList = PersistentVector.create(list);
				
				persistentList.hashCode();
			}
			
		}, showElapsedTime);
		
		CountTime.run(PAD_SIZE, "PersistentVector.EMPTY.asTransient() -> PersistentVector", new Runnable() {

			public void run() {
			
				ITransientVector list = PersistentVector.EMPTY.asTransient();
				

				for (int i = 0; i < runCount; i++) {
					list = (ITransientVector) list.conj(Integer.valueOf(0));
				}
				
				IPersistentCollection persistentList = list.persistent();
				
				persistentList.hashCode();
			}
			
		}, showElapsedTime);
		
		CountTime.run(PAD_SIZE, "Integer[] -> PersistentVector", new Runnable() {

			public void run() {
				Integer[] list = new Integer[runCount]; 
				

				for (int i = 0; i < runCount; i++) {
					list[i] = Integer.valueOf(0);
				}
				
				IPersistentCollection persistentList = PersistentVector.create(Arrays.asList(list));
				
				persistentList.hashCode();
			}
			
		}, showElapsedTime);	
		
		CountTime.run(PAD_SIZE, "TreePVector", new Runnable() {
			
			public void run() {
				 PVector<Integer> list = TreePVector.empty();
				 
				for (int i = 0; i < runCount; i++) {
				
					list = list.plus(Integer.valueOf(0));
				}
		
			}
		}, showElapsedTime);
		
		CountTime.run(PAD_SIZE, "fj.data.List<Integer>", new Runnable() {
			
			public void run() {
				fj.data.List<Integer> list = fj.data.List.list();
				 
				for (int i = 0; i < runCount; i++) {
				
					list = list.cons(Integer.valueOf(0));
				}
				
				//System.out.println(list.length());
		
				
			}
		}, showElapsedTime);
		
		fj.data.List<Integer> list = fj.data.List.list();
		 
		for (int i = 0; i < runCount; i++) {
		
			list = list.cons(Integer.valueOf(0));
		}
		
		final fj.data.List<Integer> immutableList = list;
		
		CountTime.run(PAD_SIZE, "fj.data.List<Integer> access 1", new Runnable() {
			
			public void run() {
				
				immutableList.index(0);
				
			}
		}, showElapsedTime);

		CountTime.run(PAD_SIZE, "fj.data.List<Integer> access 2", new Runnable() {
			
			public void run() {
				
				immutableList.index(runCount - 1);
				
			}
		}, showElapsedTime);

		CountTime.run(PAD_SIZE, "copy of ArrayList", new Runnable() {
			
			public void run() {
				List<Integer> list = new ArrayList<Integer>(runCount);

				for (int i = 0; i < runCount; i++) {
					list.add(
							Integer.valueOf(0));
				}
				
				@SuppressWarnings("unused")
				List<Integer> immutableList = new ArrayList<Integer>(list);
		
				//list.set(0, Integer.valueOf(1));
				//System.out.println(immutableList.get(0));
			}
			
		}, showElapsedTime);
		
		
		CountTime.run(PAD_SIZE, "ImmutableList.copyOf", new Runnable() {
			
			public void run() {
				List<Integer> list = new ArrayList<Integer>(runCount);
				

				for (int i = 0; i < runCount; i++) {
					list.add(
							Integer.valueOf(0));
				}
				
				@SuppressWarnings("unused")
				ImmutableList<Integer> immutableList = ImmutableList.copyOf(list);
				
				//list.set(0, Integer.valueOf(1));
				//System.out.println(immutableList.get(0));
			}
			
		}, showElapsedTime);
		
		if (runCount <= HARD_TEST_LIMIT) {
		
			CountTime.run(PAD_SIZE, "ImmutableList", new Runnable() {
	
				public void run() {
					ImmutableList<Integer> list = ImmutableList.of();
					
					for (int i = 0; i < runCount; i++) {
					
						list = ImmutableList.<Integer>builder().addAll(list).add(Integer.valueOf(0)).build();
					}
				}
			}, showElapsedTime);
		
		
		
			CountTime.run(PAD_SIZE, "ArrayList<Integer>() -> ArrayList<Integer>()", new Runnable() {
	
				public void run() {
				
					List<Integer> list = new ArrayList<Integer>();
					
	
					for (int i = 0; i < runCount; i++) {
						list =  new ArrayList<Integer>(list);
						list.add(Integer.valueOf(0));
					}
					
					
				}
				
			}, showElapsedTime);
		}
		
	}
	
}
