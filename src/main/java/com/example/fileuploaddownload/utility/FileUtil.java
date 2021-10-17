package com.example.fileuploaddownload.utility;

import java.util.UUID;

public class FileUtil {

    public static String generateName(){
        return UUID.randomUUID().toString();
    }

    public static String getExtension(String filename){
        int index=filename.lastIndexOf(".");
        if (index>0){
            return filename.substring(index);
        }
        return "";
    }

    public static String getFileName(String fullname){
        int index=fullname.lastIndexOf("/");
        if (index>0){
            return fullname.substring(index);
        }
        return "";
    }
}
