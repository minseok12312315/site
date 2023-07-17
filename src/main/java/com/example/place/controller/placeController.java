package com.example.place.controller;

import com.example.place.model.CommonDto;
import com.example.place.service.PlaceService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
public class placeController {

    private final PlaceService placeService;

    public placeController(PlaceService placeService) {
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
}
