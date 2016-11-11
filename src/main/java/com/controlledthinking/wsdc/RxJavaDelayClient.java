package com.controlledthinking.wsdc;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.schedulers.Schedulers;

public class RxJavaDelayClient implements DelayClient {

	private Scheduler theScheduler = null;
	private static final int numThreads = 5;
	private Observable<String> theObservable = null;
	private OkHttpClient client = null;

	private OkHttpClient getClient() {
		if( client == null ) {
			setClient(new OkHttpClient());
		}
		return client;
	}
	private void setClient(OkHttpClient client) {
		this.client = client;
	}

	public RxJavaDelayClient() {
		
	}

	@Override
	public void fetchUrls(String[] urls) throws InterruptedException, ExecutionException, TimeoutException {

		theScheduler = Schedulers.from(Executors.newFixedThreadPool(numThreads));
		
		class InnerSubscriber extends Subscriber<String> {
			public String url;
		    @Override
		    public void onNext(String s) { System.out.println(s); }

		    @Override
		    public void onCompleted() { }

		    @Override
		    public void onError(Throwable e) { }				
		};

		theObservable = Observable.create(
				new Observable.OnSubscribe<String>() {
			        @Override
			        public void call(Subscriber<? super String> sub) {
			    		Request request = new Request.Builder()
			  			      .url("http://localhost:8080/delay/3")
			  			      .build();
			    		Response response = null;
			    		String responseString = null;
				  		try {
				  			response = getClient().newCall(request).execute();
				  			responseString = response.body().string();
				  		} catch (IOException e) {
				  			// TODO Auto-generated catch block
				  			e.printStackTrace();
				  		}
			            sub.onNext(responseString);
			            sub.onCompleted();
			        }
				} ).subscribeOn(Schedulers.io());

		InnerSubscriber theInnerSubscriber = new InnerSubscriber();
		for( int i = 0; i < urls.length; i++ ) {
			theInnerSubscriber.url = urls[i];
			theObservable.subscribe(theInnerSubscriber);
		}
		Thread.sleep(14000);
		System.out.println("Done sleeping.");			
	}

	@Override
	public void stop() throws InterruptedException {
		//theObservable.unsubscribeOn(theScheduler);
	}

}
