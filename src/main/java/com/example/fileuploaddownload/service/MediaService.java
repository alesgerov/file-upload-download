package com.example.fileuploaddownload.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.fileuploaddownload.config.FileConfig;
import com.example.fileuploaddownload.entity.FileTable;
import com.example.fileuploaddownload.exception.FileNotFoundEXC;
import com.example.fileuploaddownload.exception.StatusException;
import com.example.fileuploaddownload.model.Media;
import com.example.fileuploaddownload.model.UploadStatus;
import com.example.fileuploaddownload.repository.MediaRepository;
import com.example.fileuploaddownload.utility.FileUtil;
import lombok.SneakyThrows;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.FileUrlResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class MediaService implements MediaRepository {

    private final FileConfig fileConfig;
    private final FileService service;
    private final FileUtil fileUtil;

    public MediaService(FileConfig fileConfig, FileService service, FileUtil fileUtil) {
        this.fileConfig = fileConfig;
        this.service = service;
        this.fileUtil = fileUtil;
    }

    private Media saveUtils(MultipartFile file, Media media) {
        try {
            String extension = FileUtil.getExtension(file.getOriginalFilename());
            String name = FileUtil.generateName() + extension;
            String newName = fileConfig.getLocalUrl() + name;
            Files.copy(file.getInputStream(), Paths.get(newName), StandardCopyOption.REPLACE_EXISTING);
            media.setSize(file.getSize());
            media.setName(name);
            media.setContentType(file.getContentType());
            Media media1 = service.saveMedia(media);
            if (media1 != null) {
                return saveMediaCloudinary(media);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Media saveMedia(MultipartFile file) {
        Media media = new Media();
        return saveUtils(file, media);
    }


    @Override
    public Media updateMedia(MultipartFile file, Media media) {
        return null;
    }

    @Override
    public List<Media> saveMediaWithList(List<MultipartFile> files) {
        List<Media> media = new ArrayList<>();
        for (int i = 0; i < files.size(); i++) {
            media.add(saveMedia(files.get(i)));
        }
        return media;
    }


    @SneakyThrows
    public Media getMediaById(long id) throws FileNotFoundEXC {
        Media media = new Media();
        Optional<FileTable> optional = service.getFileById(id);
        if (optional.isEmpty()) {
            throw new FileNotFoundEXC("File not found");
        }
        FileTable file = optional.get();
        Resource resource = new FileSystemResource(fileConfig.getLocalUrl() + file.getLocation());
        if (file.getCdnUrl() != null) {
            media.setCdnUrl(file.getCdnUrl());
            URL url = new URL(media.getCdnUrl());
            resource = new FileUrlResource(url);
        }
        System.out.println(file.getLocation());
        media.setName(file.getLocation());
        media.setSize(file.getSize());
        media.setContentType(file.getContentType());
        media.setResource(resource);
        return media;
    }

    @SneakyThrows
    public Media saveMediaCloudinary(Media media) {
        Cloudinary cloudinary = fileUtil.generateCloudinary();
        try {
            if (media.getStatus() == UploadStatus.PENDING) {
                Map map = cloudinary.uploader().upload(new File(fileConfig.getLocalUrl() + media.getName()),
                        ObjectUtils.asMap("public_id", FileUtil.getFileName(media.getName()), "folder", "testfiles"));
                media.setCdnUrl(map.get("secure_url").toString());
                media.setStatus(UploadStatus.SUCCESS);
                return service.updateMedia(media, media.getName());
            }
        } catch (Exception e) {
            media.setStatus(UploadStatus.ERROR);
            e.printStackTrace();
            return service.updateMedia(media, media.getName());
        }
        throw new StatusException("Status is " + media.getStatus() + " you cannot upload.");
    }


}
