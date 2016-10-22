package com.controlledthinking.wsdc;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import okhttp3.OkHttpClient;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	long startTime = System.currentTimeMillis();
        try {
			DelayClient c = new DelayClient();
		} catch (InterruptedException | ExecutionException | TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        System.out.println("Total time with DelayClient: " + elapsedTime);
    }
}
