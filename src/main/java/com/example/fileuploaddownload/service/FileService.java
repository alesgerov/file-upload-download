package com.example.fileuploaddownload.service;

import com.example.fileuploaddownload.entity.FileTable;
import com.example.fileuploaddownload.model.Media;
import com.example.fileuploaddownload.repository.FileRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class FileService {
    private final FileRepository repository;

    public FileService(FileRepository repository) {
        this.repository = repository;
    }

    public Media saveMedia(Media media) {
        FileTable fileTable = new FileTable();
        if (media.getCdnUrl() != null) {
            fileTable.setCdnUrl(media.getCdnUrl());
        }
        fileTable.setLocation(media.getName());
        fileTable.setSize(media.getSize());
        fileTable.setContentType(media.getContentType());
        fileTable.setStatus(media.getStatus());
        FileTable table = repository.save(fileTable);
        if (table != null) {
            return media;
        }
        return null;
    }


    public Media updateMedia(Media media, String name) {
        Optional<FileTable> optional = getFileByName(name);
        if (optional.isEmpty()) return null;
        FileTable fileTable = optional.get();
        if (media.getCdnUrl() != null) {
            fileTable.setCdnUrl(media.getCdnUrl());
        }
        fileTable.setLocation(media.getName());
        fileTable.setSize(media.getSize());
        fileTable.setContentType(media.getContentType());
        fileTable.setStatus(media.getStatus());
        FileTable table = repository.save(fileTable);
        if (table != null) {
            return media;
        }
        return null;
    }

    public Optional<FileTable> getFileById(long id) {
        return repository.findById(id);
    }

    public Optional<FileTable> getFileByName(String name) {
        return repository.findByLocation(name);
    }
}
