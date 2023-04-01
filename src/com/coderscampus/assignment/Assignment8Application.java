package com.coderscampus.assignment;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Assignment8Application {

	public static void main(String[] args) {
		ExecutorService service = Executors.newCachedThreadPool();
		List<Integer> outputList = new ArrayList<>();
		List<CompletableFuture<Void>> tasks = new ArrayList<>();
		Assignment8 assignment = new Assignment8();

		for(int i=0; i< 1000; i++) {
		CompletableFuture<Void> task = CompletableFuture.supplyAsync(() -> assignment, service)
						 .thenApply(Assignment8 -> Assignment8.getNumbers())
						 .thenAccept(newList -> outputList.addAll(newList));
		
		tasks.add(task);
		}

		while(tasks.stream()
			.filter(CompletableFuture::isDone)
			.count() < 1000) {
		
		}
		
		String message = "Done. List size: ";
		System.out.println(message + outputList.size());
	}
		

}
