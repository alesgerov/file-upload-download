package com.example.fileuploaddownload.repository;

import com.example.fileuploaddownload.entity.FileTable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FileRepository extends JpaRepository<FileTable, Long> {
    @Override
    Optional<FileTable> findById(Long aLong);

    Optional<FileTable> findByLocation(String location);
}
