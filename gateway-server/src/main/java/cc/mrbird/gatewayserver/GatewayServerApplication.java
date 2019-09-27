package cc.mrbird.gatewayserver;

import cc.mrbird.gatewayserver.filter.RequestTimeFilter;
import cc.mrbird.gatewayserver.filter.RequestTimeGatewayFilterFactory;
import cc.mrbird.gatewayserver.filter.TokenFilter;
import cc.mrbird.gatewayserver.resolve.HostAddrKeyResolver;
import cc.mrbird.gatewayserver.resolve.UriKeyResolver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@SpringBootApplication
@EnableDiscoveryClient
@RestController
public class GatewayServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayServerApplication.class, args);
	}

	@Bean
	public RouteLocator customerRouteLocator(RouteLocatorBuilder builder) {
		// @formatter:off
		return builder.routes()
				.route(r -> r.path("/customer/**")
						.filters(f -> f.filter(new RequestTimeFilter())
								.addResponseHeader("X-Response-Default-Foo", "Default-Bar"))
						.uri("http://httpbin.org:80/get")
						.order(0)
						.id("customer_filter_router")
				)
				.build();
		// @formatter:on
	}

	@Bean
	public RequestTimeGatewayFilterFactory elapsedGatewayFilterFactory() {
		return new RequestTimeGatewayFilterFactory();
	}

	@Bean
	public TokenFilter tokenFilter(){
		return new TokenFilter();
	}

	@Bean
	public RouteLocator myRoutes(RouteLocatorBuilder builder){
		String httpUri = "http://httpbin.org:80";
		return builder.routes()
				.route(p -> p
					.path("/get")
					.filters(f -> f.addRequestHeader("Hello","World"))
					.uri(httpUri))
				.route(p -> p
						.host("*.hystrix.com")
						.filters(f -> f
								.hystrix(config -> config
										.setName("mycmd")
										.setFallbackUri("forward:/fallback")))
						.uri(httpUri))
				.build();
	}

	@RequestMapping("/fallback")
	public Mono<String> fallback() {
		return Mono.just("fallback");
	}


	@Bean
	public HostAddrKeyResolver hostAddrKeyResolver() {
		return new HostAddrKeyResolver();
	}

	@Bean
	public UriKeyResolver uriKeyResolver() {
		return new UriKeyResolver();
	}

	@Bean
	KeyResolver userKeyResolver() {
		return exchange -> Mono.just(exchange.getRequest().getQueryParams().getFirst("user"));
	}

}
