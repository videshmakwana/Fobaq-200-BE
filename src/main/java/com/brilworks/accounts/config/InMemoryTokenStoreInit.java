package com.brilworks.accounts.config;

import com.brilworks.accounts.config.tokenstore.InMemoryTokenStoreService;
import com.brilworks.accounts.config.tokenstore.TokenStoreService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "use.redis.session.store", havingValue = "false", matchIfMissing = true)
public class InMemoryTokenStoreInit {

	@Bean(name = "tokenStoreService")
	public TokenStoreService geTokenStoreService() {
		return new InMemoryTokenStoreService();
	}
}
