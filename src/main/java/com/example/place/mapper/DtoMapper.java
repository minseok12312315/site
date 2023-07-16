package com.example.place.mapper;

import com.example.place.model.CommonDto;
import com.example.place.model.KakaoPlaceDto;
import com.example.place.model.NaverPlaceDto;
import com.example.place.utils.ConvertUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DtoMapper {
    private final ObjectMapper objectMapper;
    private final ConvertUtil convertUtil;

    public DtoMapper(ObjectMapper objectMapper, ConvertUtil convertUtil) {
        this.objectMapper = objectMapper;
        this.convertUtil = convertUtil;
    }

    public <T> T map(Object inputObject, Class<T> targetClass) {
        return objectMapper.convertValue(inputObject, targetClass);
    }

    public <T> List<T> mapList(Object inputList, Class<T> targetClass) {
        return objectMapper.convertValue(inputList, objectMapper.getTypeFactory().constructCollectionType(List.class, targetClass));
    }

    public CommonDto kakaoPlaceDtoToCommonDto(KakaoPlaceDto.Document dto) {
        return CommonDto.builder()
                .title(dto.getPlaceName())
                .address(convertUtil.toAddress(dto.getAddressName()))
                .category(dto.getCategoryName())
                .phone(dto.getPhone())
                .roadAddress(convertUtil.toAddress(dto.getRoadAddressName()))
                .x(dto.getX())
                .y(dto.getY())
                .build();
    }

    public CommonDto naverPlaceDtoToCommonDto(NaverPlaceDto.PlaceDto dto) {
        return CommonDto.builder()
                .title(convertUtil.toName(dto.getTitle()))
                .address(convertUtil.toAddress(dto.getAddress()))
                .category(dto.getCategory())
                .phone(dto.getTelephone())
                .roadAddress(convertUtil.toAddress(dto.getRoadAddress()))
                .x(dto.getMapx())
                .y(dto.getMapy())
                .build();
    }
}
