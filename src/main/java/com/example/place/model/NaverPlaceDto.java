package com.example.place.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
public class NaverPlaceDto {
    private String lastBuildDate;
    private int total;
    private int start;
    private int display;
    private List<PlaceDto> items;

    @Getter
    @Setter
    public static class PlaceDto {
        @JsonProperty("title")
        private String title;
        @JsonProperty("link")
        private String link;
        @JsonProperty("category")
        private String category;
        @JsonProperty("description")
        private String description;
        @JsonProperty("telephone")
        private String telephone;
        @JsonProperty("address")
        private String address;
        @JsonProperty("roadAddress")
        private String roadAddress;
        @JsonProperty("mapx")
        private String mapx;
        @JsonProperty("mapy")
        private String mapy;
    }
}