package com.example.place.controller;

import com.example.place.jpa.entity.Keyword;
import com.example.place.model.CommonDto;
import com.example.place.model.KeywordDto;
import com.example.place.service.PlaceService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class PlaceController {

    private final PlaceService placeService;

    public PlaceController(PlaceService placeService) {
        this.placeService = placeService;
    }

    @GetMapping("/")
    public String hello() {
        return "hello-world";
    }

    @GetMapping("/v1/place")
    public List<CommonDto> getPlace(@PathParam("query") String query) {
        return placeService.searchPlace(query);
    }

    @GetMapping("/v1/keyword")
    public List<KeywordDto> getKeyword() {
        List<Keyword> keywordList = placeService.getKeyword();
        return keywordList.stream()
                .map(keyword -> new KeywordDto(keyword.getId(), keyword.getKeyColumn(), keyword.getValueColumn()))
                .collect(Collectors.toList());
    }
}
