package com.api.save.csv.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {

    public void saveFile(MultipartFile file) throws IOException;

}
