package com.example.fileuploaddownload.repository;

import com.example.fileuploaddownload.model.Media;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface MediaRepository {
    Media saveMedia(MultipartFile media);
    List<Media> saveMediaWithList(List<MultipartFile> files);
}
