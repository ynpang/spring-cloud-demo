package cc.mrbird.personclient;

import cc.mrbird.personapi.service.PersonService;
import cc.mrbird.personclient.ribbon.FirstServerForeverRule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(clients = PersonService.class)
@EnableHystrix
public class PersonClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(PersonClientApplication.class, args);
	}

	@Bean
	public FirstServerForeverRule firstServerForeverRule(){
		return new FirstServerForeverRule();
	}

}
