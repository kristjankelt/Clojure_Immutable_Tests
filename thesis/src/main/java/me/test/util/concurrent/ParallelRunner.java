package me.test.util.concurrent;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import me.test.transactions.IdRunnable;
import me.test.transactions.ParameterRunnable;

public class ParallelRunner {
	
	private static final ThreadLocal<ExecutorService> service = new ThreadLocal<ExecutorService>();
	
	// Experimental
	public static void run(final Runnable runnable) {

		if (runnable == null) {
			throw new IllegalArgumentException("Runnable can not be null.");
		}
		
		if (service.get() == null) {
			service.set(Executors.newFixedThreadPool(
					Runtime.getRuntime().availableProcessors()));
		}
		
		final CountDownLatch latch = new CountDownLatch(1);
		
		service.get().execute(new Runnable() {

			public void run() {
				try {
					runnable.run();
				}
				finally {
					latch.countDown();
				}
			}
			
		});
		
		
		//service.get().shutdown();
		
		try {
			latch.await();
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static void run(final int parallelism, final IdRunnable runnable) {
		
		if (runnable == null) {
			throw new IllegalArgumentException("Runnable can not be null.");
		}
		
		ExecutorService service = Executors.newFixedThreadPool(parallelism);

		for (int i=0; i< parallelism; i++) {
			
			final int id = i;
			
			service.execute(new Runnable() {

				public void run() {
					runnable.run(id);
				}
				
			});
		}
		
		service.shutdown();
		
		while (!service.isShutdown()) {
			try {
				service.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
			} catch (InterruptedException e) {
				throw new IllegalStateException(e);
			}
		}	
		
	}
	
	public static void run2(final int parallelism, final IdRunnable runnable) {
		
		if (runnable == null) {
			throw new IllegalArgumentException("Runnable can not be null.");
		}
		
		ExecutorService service = Executors.newFixedThreadPool(2);

		for (int i=0; i< parallelism; i++) {
			
			final int id = i;
			
			service.execute(new Runnable() {

				public void run() {
					runnable.run(id);
				}
				
			});
		}
		
		service.shutdown();
		
		while (!service.isShutdown()) {
			try {
				service.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
			} catch (InterruptedException e) {
				throw new IllegalStateException(e);
			}
		}	
		
	
		
	}
	
	public static void run3(final int parallelism, final IdRunnable runnable) {
		
		final int CPU_COUNT = 2;
		
		if (runnable == null) {
			throw new IllegalArgumentException("Runnable can not be null.");
		}
		
		ExecutorService service = Executors.newFixedThreadPool(CPU_COUNT);
		
		final int[] splitsLower = new int[CPU_COUNT];
		final int[] splitsUpper = new int[CPU_COUNT];
		
		for (int i=0; i < CPU_COUNT; i++) {
			splitsLower[i] = i * parallelism / CPU_COUNT;
			splitsUpper[i] = (i * parallelism / CPU_COUNT) + (parallelism / CPU_COUNT);
		}

		for (int i=0; i < CPU_COUNT; i++) {
			
			final int cpuId = i;
			
			service.execute(new Runnable() {

				public void run() {
					for (int i=splitsLower[cpuId]; i< splitsUpper[cpuId]; i++) {

						final int id = i;
						
						runnable.run(id);
					}
					
					if (splitsUpper[cpuId] + 1 == parallelism) {
						runnable.run(parallelism - 1);
					}
				}
				
			});
		}
		
		service.shutdown();
		
		while (!service.isShutdown()) {
			try {
				service.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
			} catch (InterruptedException e) {
				throw new IllegalStateException(e);
			}
		}	
		
	
		
	}

	public static void run(final int parallelism, final Runnable runnable) {
		
		if (runnable == null) {
			throw new IllegalArgumentException("Runnable can not be null.");
		}
		
		
		ExecutorService service = Executors.newFixedThreadPool(parallelism);
		
		final CountDownLatch finishLatch = new CountDownLatch(parallelism);
		
		for (int i=0; i< parallelism; i++) {
			service.execute(new Runnable() {

				public void run() {
					try {

						runnable.run();
					}
					finally {
						finishLatch.countDown();
					}
				}
				
			});
		}
		
		service.shutdown();
		
		try {
			finishLatch.await();
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		
	}
	

	public static <E> void run(final int parallelism, final ParameterRunnable<E> runnable, @SuppressWarnings("unchecked") final E ... parameters) {
		
		if (runnable == null) {
			throw new IllegalArgumentException("Runnable can not be null.");
		}
		
		ExecutorService service = Executors.newFixedThreadPool(parallelism);
		
		final CountDownLatch latch = new CountDownLatch(parallelism);
		

		for (int i=0; i< parallelism; i++) {
			service.execute(new Runnable() {

				public void run() {
					try {
						runnable.run(parameters);
					}
					finally {
						latch.countDown();
					}
				}
				
			});
		}
		
		service.shutdown();
		
		try {
			latch.await();
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		
	}
	
	public static void run(final Runnable ... runnables) {
		
		if (runnables == null || runnables.length == 0) {
			throw new IllegalArgumentException("Runnables can not be null nor empty.");
		}
		
		for (final Runnable runnable : runnables) {
			if (runnable == null) {
				throw new IllegalArgumentException("Runnable can not be null.");
			}
				
		}
		
		ExecutorService service = Executors.newFixedThreadPool(runnables.length);
		
		final CountDownLatch latch = new CountDownLatch(runnables.length);
		

		for (final Runnable runnable : runnables) {
			service.execute(new Runnable() {

				public void run() {
					try {
						runnable.run();
					}
					finally {
						latch.countDown();
					}
				}
				
			});
		}
		
		service.shutdown();
		
		try {
			latch.await();
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		
	}
}
