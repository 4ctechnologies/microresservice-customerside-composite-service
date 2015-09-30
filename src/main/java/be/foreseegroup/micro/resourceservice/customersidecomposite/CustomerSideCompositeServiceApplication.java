package be.foreseegroup.micro.resourceservice.customersidecomposite;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Created by Kaj on 29/09/15.
 */

@SpringBootApplication
@EnableCircuitBreaker
@EnableDiscoveryClient
public class CustomerSideCompositeServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(CustomerSideCompositeServiceApplication.class, args);
    }
}
