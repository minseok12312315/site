package com.example.place.placeInterface;

import com.example.place.model.KakaoPlaceDto;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Component
public interface PlaceSearchService {
    List<Object> searchPlace(String query);
}
