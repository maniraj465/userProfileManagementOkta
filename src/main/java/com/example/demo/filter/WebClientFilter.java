package com.example.demo.filter;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
public class WebClientFilter {

	private static final Logger LOG = LoggerFactory.getLogger(WebClientFilter.class);

	public static ExchangeFilterFunction logRequest() {
		return ExchangeFilterFunction.ofRequestProcessor(request -> {
			logMethodAndUrl(request);
			logHeaders(request);

			return Mono.just(request);
		});
	}

	public static ExchangeFilterFunction logResponse() {
		return ExchangeFilterFunction.ofResponseProcessor(response -> {
			logStatus(response);
			logHeaders(response);
			return Mono.just(response);
		});
	}

	private static void logStatus(ClientResponse response) {
		HttpStatus status = response.statusCode();
		log.debug("Returned staus code {} ({})", status.value(), status.getReasonPhrase());
	}

	private static void logMethodAndUrl(ClientRequest request) {
		StringBuilder sb = new StringBuilder();
		sb.append(request.method().name());
		sb.append(" to ");
		sb.append(request.url());

		log.debug(sb.toString());
	}

	private static void logHeaders(ClientRequest request) {
		request.headers().forEach((name, values) -> {
			values.forEach(value -> {
				logNameAndValuePair(name, value);
			});
		});
	}

	private static void logNameAndValuePair(String name, String value) {
		log.debug("{}={}", name, value);
	}

	private static void logHeaders(ClientResponse response) {
		response.headers().asHttpHeaders().forEach((name, values) -> {
			values.forEach(value -> {
				logNameAndValuePair(name, value);
			});
		});
	}
}
