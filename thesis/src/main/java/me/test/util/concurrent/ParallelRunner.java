package me.test.util.concurrent;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import me.test.transactions.IdRunnable;
import me.test.transactions.ParameterRunnable;

public class ParallelRunner {
	
	public static void run(final int parallelism, final IdRunnable runnable) {
		
		if (runnable == null) {
			throw new IllegalArgumentException("Runnable can not be null.");
		}
		
		ExecutorService service = Executors.newFixedThreadPool(parallelism);
		
		final CountDownLatch latch = new CountDownLatch(parallelism);
		

		for (int i=0; i< parallelism; i++) {
			
			final int id = i;
			
			service.execute(new Runnable() {

				public void run() {
					try {
						runnable.run(id);
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

	public static void run(final int parallelism, final Runnable runnable) {
		
		if (runnable == null) {
			throw new IllegalArgumentException("Runnable can not be null.");
		}
		
		ExecutorService service = Executors.newFixedThreadPool(parallelism);
		
		final CountDownLatch latch = new CountDownLatch(parallelism);
		

		for (int i=0; i< parallelism; i++) {
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
	

	public static <E> void run(final int parallelism, final ParameterRunnable<E> runnable, final E ... parameters) {
		
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
