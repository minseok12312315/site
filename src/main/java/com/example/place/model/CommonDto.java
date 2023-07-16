package com.example.place.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CommonDto {
    private String title;
    private String address;
    private String category;
    private String phone;
    private String roadAddress;
    private String x;
    private String y;
}
