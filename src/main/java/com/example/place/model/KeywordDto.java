package com.example.place.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KeywordDto {
    private int id;
    private String keyColumn;
    private int valueColumn;

    public KeywordDto(int id, String keyColumn, int valueColumn) {
        this.id = id;
        this.keyColumn = keyColumn;
        this.valueColumn = valueColumn;
    }
}
