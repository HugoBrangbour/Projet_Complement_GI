package com.tt.back.repository;

import com.tt.back.entity.FileType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FileTypeRepository extends JpaRepository<FileType, Integer> {
    Optional<FileType> findByNom(String nom);
    @Override
    Optional<FileType> findById(Integer integer);
}