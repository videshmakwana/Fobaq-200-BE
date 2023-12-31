package com.brilworks.accounts.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AppProperties {
    @Value("${domain-name}")
    String domainName;

    public String getDomainName() {
        return domainName;
    }
}
