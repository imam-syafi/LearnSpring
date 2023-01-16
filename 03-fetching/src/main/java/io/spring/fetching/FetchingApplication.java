package io.spring.fetching;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class FetchingApplication {

    private static final Logger log = LoggerFactory.getLogger(FetchingApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(FetchingApplication.class, args);
	}

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    public CommandLineRunner run(RestTemplate restTemplate) throws Exception {
        return args -> {
            Quote quote = restTemplate.getForObject("http://localhost:14045/api/random", Quote.class);

            if (quote == null) {
                throw new Exception();
            }

            log.info(quote.toString());
        };
    }

}
