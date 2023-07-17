package com.example.place.jpa.repository;

import com.example.place.jpa.entity.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KeywordRepository extends JpaRepository<Keyword, Integer> {

    Keyword findByKeyColumn(String key);

    List<Keyword> findTop10ByOrderByValueColumnDesc();
}
