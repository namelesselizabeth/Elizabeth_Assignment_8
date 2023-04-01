package com.coderscampus.assignment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Assignment8Application {

	public static void main(String[] args) {
		ExecutorService service = Executors.newCachedThreadPool();
		
		List<Integer> outputList = Collections.synchronizedList( new ArrayList<>() );
		
		List<CompletableFuture<Void>> tasks = new ArrayList<>();
		Assignment8 assignment = new Assignment8();
		
		synchronized(outputList) {
			
		}
		
		for(int i = 0; i < 1000; i++) {
		CompletableFuture<Void> task = CompletableFuture.supplyAsync(() -> assignment, service)
						 .thenApply(Assignment8::getNumbers)
						 .thenAccept(newList -> outputList.addAll(newList));
		
		tasks.add(task);
		}

		while(tasks.stream()
			.filter(CompletableFuture::isDone)
			.count() < 1000) {

		}
		
		String message = "Done. List size: ";
		System.out.println(message + outputList.size());
		
		Map<Integer, Integer> outputMap = new HashMap<>();
		
		for(Integer output : outputList) {
			
			if (outputMap.containsKey(output)) {
				outputMap.put(output, outputMap.get(output) + 1);
			}
			
			else if (!outputMap.containsKey(output)) {
				outputMap.put(output, 1);
			}
		}
		
		System.out.println("\nNumber of Times Each Unique Number Appears\n-------------------------------\n");
		printToConsole(outputMap);
	}

	private static void printToConsole(Map<Integer, Integer> outputMap) {
		
		for (Map.Entry<Integer, Integer> entry : outputMap.entrySet())
			System.out.println(entry.getKey() + " -> " + entry.getValue());
	}
}
