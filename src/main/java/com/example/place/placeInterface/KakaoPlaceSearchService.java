package com.example.place.placeInterface;

import com.example.place.model.KakaoPlaceDto;
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

@Service("kakao")
public class KakaoPlaceSearchService implements PlaceSearchService {
    @Value("${url.kakao.place}")
    private String url;

    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String kakaoClientId;
    @Override
    public List<Object> searchPlace(String query) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "KakaoAK "+kakaoClientId);
            headers.set("Connection", "keep-alive");
            headers.set("Accept-Encoding", "*/*");
            headers.set("Accept", "*/*");

            List<Object> allDocuments = new ArrayList<>();
            boolean isEnd = false;
            int page = 1;

            while (!isEnd) {
                URI uri = UriComponentsBuilder.fromUriString(url)
                        .queryParam("query", URLEncoder.encode(query, StandardCharsets.UTF_8))
                        .queryParam("page", page)
                        .build(true)
                        .toUri();

                RequestEntity<Void> requestEntity = new RequestEntity<>(headers, HttpMethod.GET, uri);
                ResponseEntity<KakaoPlaceDto> responseEntity = restTemplate.exchange(requestEntity, KakaoPlaceDto.class);

                if (responseEntity.getStatusCode() == HttpStatus.OK) {
                    List<KakaoPlaceDto.Document> documents = Objects.requireNonNull(responseEntity.getBody()).getDocuments();
                    allDocuments.addAll(documents);
                    isEnd = responseEntity.getBody().getMeta().isEnd();
                    page++;
                } else {
                    return Collections.emptyList();
                }
            }

            return allDocuments;
        } catch (Exception e) {
            System.out.println(e);
            return Collections.emptyList();
        }
    }
}
