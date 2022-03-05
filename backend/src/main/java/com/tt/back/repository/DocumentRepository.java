package com.tt.back.repository;

import com.tt.back.entity.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface DocumentRepository extends PagingAndSortingRepository<Document, Integer> {
    Page<Document> findByNomContainsIgnoreCase(String nom, Pageable pageable);
    @Override
    Optional<Document> findById(Integer integer);
}