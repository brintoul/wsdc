package com.controlledthinking.wsdc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import okhttp3.OkHttpClient;

public class DelayClient {
	
	private List<Future<String>> futures;
	private List<String> urls = new ArrayList<String>();
	private OkHttpClient client = null;
	private static int numThreads = 5;

	public DelayClient() throws InterruptedException, ExecutionException, TimeoutException {
		
		String[] localUrls = {"http://localhost:8080/delay/5","http://localhost:8080/delay/1","http://localhost:8080/delay/1"};
		urls.addAll(Arrays.asList(localUrls));
		client = new OkHttpClient();
		futures = new ArrayList<>();
		ExecutorService service = Executors.newFixedThreadPool(numThreads);
		for( String url : urls ) {
			Callable<String> delayCall = new DelayServiceCallable(url, client);
			futures.add(service.submit(delayCall));
		}
		for( Future<String> future : futures ) {
			String result = future.get(10, TimeUnit.SECONDS);
			System.out.println("The result is: " + result);
		}
	}

}
