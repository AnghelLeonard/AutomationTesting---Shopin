package org.shopin.config;

import javax.annotation.PostConstruct;
import org.shopin.util.Utils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PingExternalResourcesConfig {

    @Value("${spring.mail.host}")
    private String host;

    @Value("${spring.mail.port}")
    private int port;

    @PostConstruct
    public void init() {
        boolean pingSMTP = Utils.pingSMTPHost(host, port, port);

        if (!pingSMTP) {
            throw new RuntimeException("The SMTP server is not running ...");
        }
    }
}
