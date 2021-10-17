package com.example.fileuploaddownload.service;

import com.example.fileuploaddownload.config.FileConfig;
import com.example.fileuploaddownload.entity.FileTable;
import com.example.fileuploaddownload.exception.FileNotFoundEXC;
import com.example.fileuploaddownload.model.Media;
import com.example.fileuploaddownload.repository.MediaRepository;
import com.example.fileuploaddownload.utility.FileUtil;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MediaService implements MediaRepository {

    private final FileConfig fileConfig;
    private final FileService service;

    public MediaService(FileConfig fileConfig, FileService service) {
        this.fileConfig = fileConfig;
        this.service = service;
    }


    @Override
    public Media saveMedia(MultipartFile file) {
        try {
            String extension = FileUtil.getExtension(file.getOriginalFilename());
            String name = FileUtil.generateName() + extension;
            String newName = fileConfig.getLocalUrl() + name;
            Files.copy(file.getInputStream(), Paths.get(newName), StandardCopyOption.REPLACE_EXISTING);
            Media media = new Media();
            media.setSize(file.getSize());
            media.setName(name);
            media.setContentType(file.getContentType());
            return service.saveMedia(media);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Media> saveMediaWithList(List<MultipartFile> files) {
        List<Media> media=new ArrayList<>();
        for (int i = 0; i < files.size(); i++) {
            media.add(saveMedia(files.get(i)));
        }
        return media;
    }


    public Media getMediaById(long id) throws FileNotFoundEXC {
        Media media = new Media();
        Optional<FileTable> optional = service.getFileById(id);
        if (optional.isEmpty()){
            throw new FileNotFoundEXC("File not found");
        }
        FileTable file =optional.get();
        if (file.getCdnUrl()!=null){
            media.setCdnUrl(file.getCdnUrl());
        }
        System.out.println(file.getLocation());
        media.setName(file.getLocation());
        media.setSize(file.getSize());
        media.setContentType(file.getContentType());
        Resource resource= new FileSystemResource(fileConfig.getLocalUrl()+file.getLocation());
        media.setResource(resource);
        return  media;
    }

}
