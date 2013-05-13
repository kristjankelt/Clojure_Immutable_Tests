package me.test.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class CsvTransformer {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		
		List<String[]> csvFile = loadCsvFile();
		
		Map<String, int[]> partitions = findPartitions(csvFile);
		
		int[] firstPartion = findFirstPartition(partitions);
		
		String[] fistLine = new String[partitions.size() + 1];
		fistLine[0] = "";
		int firstLinelineIndex = 1;
		
		for (String partitionId : partitions.keySet()) {
			int[] partition = partitions.get(partitionId);
			
			
			fistLine[firstLinelineIndex] = csvFile.get(partition[0])[2];
					
			firstLinelineIndex++;
		}
		
		System.out.println(Arrays.toString(fistLine).substring(1, Arrays.toString(fistLine).length()-1));
		
		for (int i=0; i <= firstPartion[1]; i ++) {
			//System.out.println(Arrays.toString(csvFile.get(i)));
			
			String[] newLine = new String[partitions.size() + 1];
			
			int lineIndex = 1;
			
			newLine[0] = csvFile.get(i)[4];
			
			for (String partitionId : partitions.keySet()) {
				
				int[] partition = partitions.get(partitionId);
				
				newLine[lineIndex] = csvFile.get(i+partition[0])[6];
						
				lineIndex++;
			}
			
			System.out.println(Arrays.toString(newLine).substring(1, Arrays.toString(newLine).length()-1));
		}
		
	}
	
	private static int[] findFirstPartition(Map<String, int[]> partitions) {
		
		for (int[] partition : partitions.values()) {
			if (partition[0] == 0) {
				return partition;
			}
		}
		
		throw new IllegalStateException("Can not find first partition.");
	}

	private static Map<String, int[]> findPartitions(List<String[]> csvFile) {
		Map<String, int[]> partitions = new TreeMap<String, int[]>();
		
		if (csvFile.size() > 1) {
			int previousLineIndex = 0;
			String previousLineId = csvFile.get(0)[0];

			for (int i=1; i < csvFile.size(); i++) {
				String[] line = csvFile.get(i);
				
				if (line.length < 7) {
					throw new IllegalStateException("Invalid row size.");
				}
				
				if (!previousLineId.equals(line[0])) {
					partitions.put(previousLineId, 
									new int[] {  
										previousLineIndex,
										i-1
									}
								);
					
					previousLineId = line[0];
					previousLineIndex = i;
				}
			}
			
			partitions.put(previousLineId, 
					new int[] {  
						previousLineIndex,
						csvFile.size()-1
					}
				);
			
		}
		else {
			throw new IllegalStateException("Did not find any partitions in input.");
		}
		
		return partitions;
	}

	private static List<String[]> loadCsvFile() throws IOException {
		List<String[]> csvFile = new ArrayList<String[]>();
		
		BufferedReader in = null;
	    try {
	        in = new BufferedReader(new InputStreamReader(System.in));
	        String line;
	        while ((line = in.readLine()) != null) {
	        	
	        	csvFile.add(line.split(","));
	        	
	        }
	    }
	    catch (IOException e) {
	        System.err.println("IOException reading System.in " + e);
	        throw e;
	    }
	    finally {
	        if (in != null) {
	            in.close();
	        }
	    }
	    
	    return csvFile;
	}

}
