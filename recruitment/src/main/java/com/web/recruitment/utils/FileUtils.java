package com.web.recruitment.utils;

import lombok.extern.slf4j.Slf4j;
import java.util.*;
@Slf4j
public class FileUtils {
    public static boolean checkFileExtension(String fileExtension) {
        List<String> extensions = new ArrayList<>(List.of("png", "jpg", "jpeg", "pdf"));
        return extensions.contains(fileExtension.toLowerCase());
    }
    public static String getFileExtension(String fileName) {
        int lastIndexOf = fileName.lastIndexOf(".");
        return fileName.substring(lastIndexOf + 1);
    }
}
