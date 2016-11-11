package com.controlledthinking.wsdc;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public interface DelayClient {
	public void fetchUrls(String[] urls) throws InterruptedException, ExecutionException, TimeoutException;
	public void stop() throws InterruptedException;
}
