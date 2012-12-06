package me.test.playground;

import me.test.util.concurrent.ParallelRunner;

public class ComposeLocks {
	
	private static int TEST_SIZE = 10000000;

	private static class Account {
		
		private int amount;
		
		private Account(int amount) {
			this.amount = amount;
		}
		
		private  synchronized void deposit(int amount) {
			this.amount += amount;
		}
		
		private synchronized void withdraw(int amount) {
			deposit(-amount);
		}
		
		private synchronized int getAmount() {
			return amount;
		}
		
	}
	
	public static void main(String[] args) {
		
		final Account a = new Account(10);
		final Account b = new Account(10);
		
		ParallelRunner.run(new Runnable() {

			public void run() {
				for (int i = 0; i < TEST_SIZE; i++) {
					synchronized (a) {
						synchronized (b) {
							if (a.getAmount() >= 10) {
								a.withdraw(10);
								b.deposit(10);
							}
							else if (b.getAmount() >= 10) {
								b.withdraw(10);
								a.deposit(10);
							}
						}
					}
				}
			}
			
		}, new Runnable() {

			public void run() {
				for (int i = 0; i < TEST_SIZE; i++) {
					synchronized (a) {
						synchronized (b) {
							if (a.getAmount() >= 10) {
								a.withdraw(10);
								b.deposit(10);
							}
							else if (b.getAmount() >= 10) {
								b.withdraw(10);
								a.deposit(10);
							}
						}
					}
				}
			}
			
		}, new Runnable() {

			public void run() {
				for (int i = 0; i < TEST_SIZE; i++) {
//					synchronized (a) 
					{
//						synchronized (b) 
						{
							int aAmount = a.getAmount();
							int bAmount = b.getAmount();
							if (aAmount + bAmount != 20) {
							//	throw new IllegalStateException();
								System.out.println(aAmount + " - " + bAmount);
							}
							else {
//							  System.out.println(a.getAmount() + " - " + b.getAmount());
							}
							
						}
					}
				}
			}
			
		});
	}
}
