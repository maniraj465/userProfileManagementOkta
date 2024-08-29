package com.example.demo.exception;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.server.ResponseStatusException;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@ControllerAdvice
public class WebClientException {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ResponseBody
	public InputValidationError handleMethodArgumentNotValidException(MethodArgumentNotValidException ex,
                                                                      WebRequest request) {
		BindingResult result = ex.getBindingResult();

		List<String> errorList = new ArrayList<>();
		result.getFieldErrors().forEach((fieldError) -> {
			errorList.add(fieldError.getObjectName() + "." + fieldError.getField() + " : "
					+ fieldError.getDefaultMessage() + " : rejected value [" + fieldError.getRejectedValue() + "]");
		});
		result.getGlobalErrors().forEach((fieldError) -> {
			errorList.add(fieldError.getObjectName() + " : " + fieldError.getDefaultMessage());
		});
		return new InputValidationError(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name(), errorList);
	}

	@ExceptionHandler(ServiceException.class)
	public static ExchangeFilterFunction handleError() {
		return ExchangeFilterFunction.ofResponseProcessor(response -> {
			if (response.statusCode() != null
					&& (response.statusCode().is4xxClientError() || response.statusCode().is5xxServerError())) {
				return response.bodyToMono(String.class).defaultIfEmpty(response.statusCode().getReasonPhrase())
						.flatMap(body -> {
							log.debug("Body is {}", body);
							JSONObject jsonResult = new JSONObject(body.toString());
							String errorSummary = jsonResult.getString("errorSummary").toString();
							return Mono.error(new ResponseStatusException(response.statusCode(), errorSummary));
						});
			} else {
				return Mono.just(response);
			}
		});
	}
}