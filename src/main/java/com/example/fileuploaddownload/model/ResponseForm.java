package com.example.fileuploaddownload.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseForm {
    private int status;
    private String message;
    private Object content;
}
