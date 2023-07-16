package com.example.place.utils;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

@Component
public class ConvertUtil {
    public String toAddress(String address) {
        String[] parts = address.split(" ");

        StringBuilder builder = new StringBuilder();

        for (int i = 1; i < parts.length; i++) {
            builder.append(parts[i]);
            if (i < parts.length - 1) {
                builder.append(" ");
            }
        }
        String[] addressArray = builder.toString().split(" ");
        List<String> addressList = new ArrayList<>();

        for (String part : addressArray) {
            if (part != null) {
                addressList.add(part);
            }
        }

        String[] finalAddress = addressList.stream().limit(4).toArray(String[]::new);
        return String.join(" ", finalAddress);
    }

    public String toName(String name) {
        Pattern pattern = Pattern.compile("<[^>]*>");
        return pattern.matcher(name).replaceAll("");
    }
}
