package com.api.save.csv.service.impl;

import com.api.save.csv.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@RequiredArgsConstructor
@Service
public class FileServiceImpl implements FileService {

    @Value("${spring.path.save.file}")
    private String path;

    @Override
    public void saveFile(MultipartFile file) throws IOException {

        File newFile = new File(path + file.getOriginalFilename());
        newFile.getParentFile().mkdirs();

        try (OutputStream os = new FileOutputStream(newFile)) {
            InputStream is = file.getInputStream();
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = is.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
        }

    }
}
