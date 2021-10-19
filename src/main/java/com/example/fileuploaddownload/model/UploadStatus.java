package com.example.fileuploaddownload.model;


import java.util.Arrays;

public enum UploadStatus {
    IGNORE(0),
    PENDING(1),
    IN_PROGRESS(2),
    SUCCESS(3),
    ERROR(4);

    private int value;

    UploadStatus(int value) {
        this.value = value;
    }

    public static UploadStatus from(int value) {
        return Arrays.stream(values())
                .filter(uploadStatus -> uploadStatus.value == value)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Invalid upload status value " + value));
    }

    public int getValue() {
        return value;
    }
}
