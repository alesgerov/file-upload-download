package com.example.fileuploaddownload.exception;

import org.springframework.stereotype.Component;

public class FileNotFoundEXC extends Exception{
    public FileNotFoundEXC(String message) {
        super(message);
    }
}
