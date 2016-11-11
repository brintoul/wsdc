package com.controlledthinking.wsdc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class ExecutorDelayClientConfig {
	@Bean
	@Profile("executor")
	public DelayClient makeExecutorClient() {
		return new ExecutorDelayClient();
	}
}
