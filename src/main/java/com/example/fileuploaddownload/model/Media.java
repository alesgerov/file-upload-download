package com.example.fileuploaddownload.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.core.io.Resource;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Media {
    private String contentType;
    private String name;
    private UploadStatus status=UploadStatus.PENDING;
    private String cdnUrl;
    private long size;
    @JsonIgnore
    private Resource resource;
}
