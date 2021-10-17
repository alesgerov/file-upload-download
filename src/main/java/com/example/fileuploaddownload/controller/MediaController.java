package com.example.fileuploaddownload.controller;

import com.example.fileuploaddownload.exception.FileNotFoundEXC;
import com.example.fileuploaddownload.model.Media;
import com.example.fileuploaddownload.model.ResponseForm;
import com.example.fileuploaddownload.service.MediaService;
import lombok.Getter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@RestController
@RequestMapping(value = {"/api"})
public class MediaController {

    private final MediaService mediaService;

    public MediaController(MediaService service) {
        this.mediaService = service;
    }

    @PostMapping("/add")
    public ResponseEntity<?> saveFile(@RequestParam("file")MultipartFile file){
        return ResponseEntity.ok(mediaService.saveMedia(file));
    }

    @PostMapping("/add/list")
    public ResponseEntity<?> saveFileList(@RequestParam("file") List<MultipartFile> file){
        return ResponseEntity.ok(mediaService.saveMediaWithList(file));
    }

    @GetMapping("/file/{id}")
    public ResponseEntity<?> getFile(@PathVariable("id") long id){
        try {
            Media media=mediaService.getMediaById(id);
            return ResponseEntity.ok().body(media);
        }catch (FileNotFoundEXC e){
            e.printStackTrace();
            return ResponseEntity.status(404).body(new ResponseForm(409,"File not found",id));
        }
    }


    @GetMapping("/file/{id}/show")
    public ResponseEntity<?> getFileShow(@PathVariable("id") long id){
        try {
            Media media=mediaService.getMediaById(id);
            HttpHeaders headers=new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_TYPE,media.getContentType());
            return ResponseEntity.ok().headers(headers).body(media.getResource());
        }catch (FileNotFoundEXC e){
            e.printStackTrace();
            return ResponseEntity.status(404).body(new ResponseForm(409,"File not found",id));
        }
    }


    @GetMapping("/file/{id}/download")
    public ResponseEntity<?> getFileDownload(@PathVariable("id") long id){
        try {
            Media media=mediaService.getMediaById(id);
            HttpHeaders headers=new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_TYPE,media.getContentType());
            headers.add(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\""+media.getName()+"\"");
            return ResponseEntity.ok().headers(headers).body(media.getResource());
        }catch (FileNotFoundEXC e){
            e.printStackTrace();
            return ResponseEntity.status(404).body(new ResponseForm(409,"File not found",id));
        }
    }


}
