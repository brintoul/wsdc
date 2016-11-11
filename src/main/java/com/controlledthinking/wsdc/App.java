package com.controlledthinking.wsdc;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


/**
 * Main App Class
 *
 */
@Configuration
@Import({ ExecutorDelayClientConfig.class, RxJavaDelayClientConfig.class })
public class App 
{
	private static ApplicationContext ctx;

    public static void main( String[] args )
    {
    	ctx = new AnnotationConfigApplicationContext(App.class);    			   
    	
    	long startTime = System.currentTimeMillis();
        try {
    		String[] localUrls = {"http://localhost:8080/delay/8","http://localhost:8080/delay/3","http://localhost:8080/delay/4","http://localhost:8080/delay/5","http://localhost:8080/delay/1","http://localhost:8080/delay/1"};
			DelayClient c = ctx.getBean(DelayClient.class);
			c.fetchUrls(localUrls);
			c.stop();
		} catch (InterruptedException | ExecutionException | TimeoutException e) {
			e.printStackTrace();
		}
        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        System.out.println("Total time with DelayClient: " + elapsedTime);
    }
}
