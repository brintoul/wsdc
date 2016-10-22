package com.controlledthinking.wsdc;

import java.io.IOException;
import java.util.concurrent.Callable;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DelayServiceCallable implements Callable<String> {

	private String url;
	private OkHttpClient client = null;
	
	private OkHttpClient getClient() {
		return client;
	}

	private void setClient(OkHttpClient client) {
		this.client = client;
	}
 
	public DelayServiceCallable(String url) {
		super();
		this.url = url;
	}

	public DelayServiceCallable(String url, OkHttpClient client) {
		super();
		this.url = url;
		this.client = client;
	}

	@Override
	public String call() throws Exception {
		if( getClient() == null ) {
			setClient(new OkHttpClient());
		}
		Request request = new Request.Builder()
			      .url(url)
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
		return responseString;
	}


}
