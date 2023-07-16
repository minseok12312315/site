package com.example.place.placeInterface;

import com.example.place.model.NaverPlaceDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service("naver")
public class NaverPlaceSearchService implements PlaceSearchService {

    @Value("${url.naver.place}")
    private String url;

    @Value("${spring.security.oauth2.client.registration.naver.client-id}")
    private String naverClientId;

    @Value("${spring.security.oauth2.client.registration.naver.client-secret}")
    private String naverClientSecret;
    @Override
    public List<Object> searchPlace(String query) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.set("Connection", "keep-alive");
            headers.set("Accept-Encoding", "*/*");
            headers.set("Accept", "*/*");
            headers.set("X-Naver-Client-Id", naverClientId);
            headers.set("X-Naver-Client-Secret", naverClientSecret);

            URI uri = UriComponentsBuilder.fromUriString(url)
                    .queryParam("query", URLEncoder.encode(query, StandardCharsets.UTF_8))
                    .queryParam("display", 5)
                    .build(true)
                    .toUri();

            RequestEntity<Void> requestEntity = new RequestEntity<>(headers, HttpMethod.GET, uri);
            ResponseEntity<NaverPlaceDto> responseEntity = restTemplate.exchange(requestEntity, NaverPlaceDto.class);

            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                List<NaverPlaceDto.PlaceDto> placeList = Objects.requireNonNull(responseEntity.getBody()).getItems();
                return new ArrayList<>(placeList);
            } else {
                return Collections.emptyList();
            }
        } catch (Exception e) {
            System.out.println(e);
            return Collections.emptyList();
        }
    }
}
