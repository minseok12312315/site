package com.example.place.service;

import com.example.place.mapper.DtoMapper;
import com.example.place.model.CommonDto;
import com.example.place.model.KakaoPlaceDto;
import com.example.place.model.NaverPlaceDto;
import com.example.place.placeInterface.PlaceSearchService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlaceService {

    private final PlaceSearchService kakaoPlaceSearchService;
    private final PlaceSearchService naverPlaceSearchService;
    private final DtoMapper dtoMapper;

    public PlaceService(@Qualifier("kakao") PlaceSearchService kakaoPlaceSearchService,
                        @Qualifier("naver") PlaceSearchService naverPlaceSearchService,
                        DtoMapper dtoMapper) {
        this.kakaoPlaceSearchService = kakaoPlaceSearchService;
        this.naverPlaceSearchService = naverPlaceSearchService;
        this.dtoMapper = dtoMapper;
    }

    public List<CommonDto> searchPlace(String query) {
        List<Object> kakaoPlace = kakaoPlaceSearchService.searchPlace(query);
        List<KakaoPlaceDto.Document> kakaoResult = dtoMapper.mapList(kakaoPlace, KakaoPlaceDto.Document.class);
        List<Object> naverPlace = naverPlaceSearchService.searchPlace(query);
        List<NaverPlaceDto.PlaceDto> naverResult = dtoMapper.mapList(naverPlace, NaverPlaceDto.PlaceDto.class);

        List<CommonDto> kakaoCommonList = kakaoResult.stream().map(dtoMapper::kakaoPlaceDtoToCommonDto).collect(Collectors.toList());
        List<CommonDto> naverCommonList = naverResult.stream().map(dtoMapper::naverPlaceDtoToCommonDto).collect(Collectors.toList());

        List<CommonDto> result = new ArrayList<>();
        List<CommonDto> duplication = kakaoCommonList.stream()
                .filter(kakao -> naverCommonList.stream()
                        .anyMatch(naver -> isPlacePolicy(kakao, naver)))
                .collect(Collectors.toList());
        kakaoCommonList.removeAll(duplication);
        List<CommonDto> newNaverCommonList = naverCommonList.stream()
                .filter(naver -> duplication.stream()
                        .noneMatch(dup -> isPlacePolicy(dup, naver)))
                .collect(Collectors.toList());

        // 1. 카카오와 네이버 검색결과 모두 중복인 부분 추가
        // 2. 나머지 중복되지 않은 카카오 검색
        // 3. 네이버 검색결과 converting 해서 리턴
        result.addAll(duplication);
        result.addAll(kakaoCommonList);
        result.addAll(newNaverCommonList);

        return result;
    }

    private boolean isPlacePolicy(CommonDto kakao, CommonDto naver) {
        String overlapTitle = getOverlappingTitle(kakao.getTitle(), naver.getTitle());
        // "SK플래닛 판교사옥" 과 "SK플래닛" 비교
        if(kakao.getTitle().equals("SK플래닛 판교사옥") && naver.getTitle().equals("SK플래닛")) {
            System.out.println();
        }
        if(isSimilar(overlapTitle, kakao.getTitle()) &&
                isSimilar(overlapTitle, naver.getTitle()) &&
                (kakao.getRoadAddress().contains(naver.getRoadAddress()) || naver.getRoadAddress().contains(kakao.getRoadAddress()))) {
            kakao.setTitle(overlapTitle);
            naver.setTitle(overlapTitle);
            kakao.setRoadAddress(kakao.getRoadAddress().replace(naver.getTitle(), "").replace(kakao.getTitle(), ""));
            naver.setRoadAddress(kakao.getRoadAddress().replace(naver.getTitle(), "").replace(kakao.getTitle(), ""));
        }
        if (naver.getRoadAddress().length() > 0 && kakao.getRoadAddress().length() > 0) {
            return naver.getRoadAddress().contains(kakao.getRoadAddress()) &&
                    kakao.getTitle().equals(naver.getTitle());
        } else if (naver.getAddress().length() > 0 && kakao.getAddress().length() > 0) {
            return naver.getAddress().contains(kakao.getAddress()) &&
                    kakao.getTitle().equals(naver.getTitle());
        } else {
            return false;
        }
    }

    private String getOverlappingTitle(String str1, String str2) {
        String baseString = (str1.length() <= str2.length()) ? str1 : str2;
        String targetString = (str1.length() <= str2.length()) ? str2 : str1;

        StringBuilder overlappingPart = new StringBuilder();

        // baseString을 기준으로 하나씩 문자를 추출하여 targetString에 포함되는지 확인
        for (int i = 0; i < baseString.length(); i++) {
            char ch = baseString.charAt(i);
            if (targetString.indexOf(ch) != -1) {
                overlappingPart.append(ch);
            }
        }

        return overlappingPart.toString();
    }

    private boolean isSimilar(String str1, String str2) {
        int maxLength = Math.max(str1.length(), str2.length());
        double minSimilarity = maxLength * 0.5;

        int commonLength = 0;
        for (int i = 0; i < maxLength; i++) {
            if (i < str1.length() && i < str2.length() && str1.charAt(i) == str2.charAt(i)) {
                commonLength++;
            }
        }

        return commonLength >= minSimilarity;
    }
}
