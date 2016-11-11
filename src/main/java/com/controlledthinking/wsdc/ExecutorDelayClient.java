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

public class ExecutorDelayClient implements DelayClient {
	
	private List<Future<String>> futures;
	private List<String> urls = new ArrayList<String>();
	private OkHttpClient client = null;
	private ExecutorService executorService = null;
	private static int numThreads = 5;

	public ExecutorDelayClient() {		
		client = new OkHttpClient();
		executorService = Executors.newFixedThreadPool(numThreads);
	}
	
	public void fetchUrls(String[] urls) throws InterruptedException, ExecutionException, TimeoutException {
		this.urls.addAll(Arrays.asList(urls));
		futures = new ArrayList<>();
		for( String url : urls ) {
			Callable<String> delayCall = new DelayServiceCallable(url, client);
			futures.add(executorService.submit(delayCall));
		}
		for( Future<String> future : futures ) {
			String result = future.get(10, TimeUnit.SECONDS);
			System.out.println("The result is: " + result);
		}
	}
	
	public void stop() throws InterruptedException {
		executorService.shutdownNow();
		executorService.awaitTermination(2000, TimeUnit.MILLISECONDS);
	}

}
