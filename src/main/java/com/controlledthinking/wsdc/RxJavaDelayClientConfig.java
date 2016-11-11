package com.controlledthinking.wsdc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class RxJavaDelayClientConfig {
	@Bean
	@Profile("rxjava")
	public DelayClient makeRxJavaClient() {
		return new RxJavaDelayClient();
	}
}
