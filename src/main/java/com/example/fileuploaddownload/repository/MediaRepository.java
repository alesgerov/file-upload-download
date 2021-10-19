package com.example.fileuploaddownload.repository;

import com.example.fileuploaddownload.model.Media;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MediaRepository {
    Media saveMedia(MultipartFile media);

    Media updateMedia(MultipartFile file, Media media);

    List<Media> saveMediaWithList(List<MultipartFile> files);

}
