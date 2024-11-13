package com.jetty.ssafficebe.mattermost.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import jakarta.annotation.PostConstruct;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@RequiredArgsConstructor
public class MattermostUtil {

	private static final String VERSION = "v4";
	@Value("${ssafyproject.openapi.key}")
	private String openApiKey;
	@Value("${ssafyproject.openapi.url}")
	private String openApiUrl;
	@Value("${mattermost.url}")
	private String mattermostUrl;
	private RestTemplate restTemplate;
	private String authToken;

	@PostConstruct
	private void initializeRestTemplate() {
		ObjectMapper objectMapper =
			new ObjectMapper()
				.setPropertyNamingStrategy(new PropertyNamingStrategies.SnakeCaseStrategy())
				.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
//		// StringConverter 테스트코드 시작
//		StringHttpMessageConverter stringConverter = new StringHttpMessageConverter(
//			StandardCharsets.UTF_8);
//		messageConverters.add(stringConverter);
//		// StringConverter 테스트코드 끝
		MappingJackson2HttpMessageConverter converter =
			new MappingJackson2HttpMessageConverter(objectMapper);
		messageConverters.add(converter);

		RestTemplate template = new RestTemplate();
		template.setMessageConverters(messageConverters);
		this.restTemplate = template;
	}

	public <T, U> ResponseEntity<T> callApi(
		String path,
		MultiValueMap<String, String> queryParameters,
		HttpMethod method,
		U body,
		Class<T> responseType) {
		ResponseEntity<T> response;
		try {
			RequestEntity<U> requestEntity =
				RequestEntity.method(method, this.generateMMUri(path, queryParameters))
					.contentType(MediaType.APPLICATION_JSON)
					.body(body);
//			// responseTest Code 시작
//			ResponseEntity<String> responseEntity = this.restTemplate.exchange(requestEntity, String.class);
//			System.out.println(responseEntity.getBody());
//			// responseTest Code 끝
			response = this.restTemplate.exchange(requestEntity, responseType);
		} catch (HttpStatusCodeException exception) {
			response = new ResponseEntity<>(exception.getStatusCode());
		}
		return response;
	}

	private URI generateMMUri(String path, MultiValueMap<String, String> queryParameters) {
		UriComponentsBuilder mmUriCompBuilder =
			UriComponentsBuilder.fromUriString(openApiUrl)
				.pathSegment(VERSION)
				.pathSegment("mm")
				.pathSegment(path)
				.queryParam("apiKey", openApiKey);

		if (queryParameters != null) {
			mmUriCompBuilder.queryParams(queryParameters);
		}

		return mmUriCompBuilder.build().toUri();
	}
}
