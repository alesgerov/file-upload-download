package com.example.fileuploaddownload.service;

import com.example.fileuploaddownload.entity.FileTable;
import com.example.fileuploaddownload.model.Media;
import com.example.fileuploaddownload.repository.FileRepository;
import com.example.fileuploaddownload.utility.FileUtil;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
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
        FileTable table = repository.save(fileTable);
        if (table != null) {
            return media;
        }
        return null;
    }

    public Optional<FileTable> getFileById(long id){
        return repository.findById(id);
    }
}
