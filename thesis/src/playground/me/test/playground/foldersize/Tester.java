package me.test.playground.foldersize;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Tester {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		System.out.println((folderSize("/opt") / 1000000.0) + "MB");
	}
	
	private static long folderSize(String path) {
		File file = new File(path);
		
		//System.out.println(path);
		
		if (!file.exists()) {
			throw new IllegalArgumentException("Parameter path does not reference to an existing file");
		}
		
		if (file.isDirectory()) {
			long folderSize = 0L;
			
			File[] subFiles  = file.listFiles();
			
			if (subFiles == null) {
				return 0L;
			}
			
			for (File subFile : file.listFiles()) {
				if (subFile.isDirectory()) {
					folderSize += folderSize(subFile.getPath());
				}
				else {
					folderSize += file.length();
				}
			}
			
			return folderSize;
		}
		else {
			throw new IllegalArgumentException("Parameter path does not reference to a folder.");
		}
		
		
	}
	
	private static long folderSize2(String path) {
		File file = new File(path);
		
		//System.out.println(path);
		
		ExecutorService service = Executors.newFixedThreadPool(4);
		
		
		
		
		if (!file.exists()) {
			throw new IllegalArgumentException("Parameter path does not reference to an existing file");
		}
		
		if (file.isDirectory()) {
			long folderSize = 0L;
			
			File[] subFiles  = file.listFiles();
			
			if (subFiles == null) {
				return 0L;
			}
			
			for (File subFile : file.listFiles()) {
				if (subFile.isDirectory()) {
					folderSize += folderSize(subFile.getPath());
				}
				else {
					folderSize += file.length();
				}
			}
			
			return folderSize;
		}
		else {
			throw new IllegalArgumentException("Parameter path does not reference to a folder.");
		}
		
		
	}

}
