package com.example.fileuploaddownload.utility;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.fileuploaddownload.config.FileConfig;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class FileUtil {

    private final FileConfig config;

    public FileUtil(FileConfig config) {
        this.config = config;
    }

    public static String generateName() {
        return UUID.randomUUID().toString();
    }

    public static String getExtension(String filename) {
        int index = filename.lastIndexOf(".");
        if (index > 0) {
            return filename.substring(index);
        }
        return "";
    }

    public static String getFileName(String fullname) {
        int index = fullname.lastIndexOf("/");
        if (index > 0) {
            return fullname.substring(index);
        }
        return "";
    }


    public Cloudinary generateCloudinary() {
        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", config.getCloudName(),
                "api_key", config.getApiKey(),
                "api_secret", config.getApiSecret()));
        return cloudinary;
    }
}
