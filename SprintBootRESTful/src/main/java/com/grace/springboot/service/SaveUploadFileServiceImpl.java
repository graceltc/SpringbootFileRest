package com.grace.springboot.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.grace.springboot.dao.FileDAO;
import com.grace.springboot.entity.FileMetaData;

@Service
public class SaveUploadFileServiceImpl implements SaveUploadFileService {

	private static String UPLOADED_FOLDER = "./src/main/resources/fileFolder/";

	@Autowired
	FileDAO fileDAO;

	public void saveUploadedFiles(MultipartFile file) throws IOException {
		
		if (file.isEmpty()) {
            
        }
		byte[] bytes = file.getBytes();
        Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
        Files.write(path, bytes);
		
	}

	@Transactional
	public void saveFileMetaData(FileMetaData file) {
		fileDAO.save(file);
		System.out.println("save successfully");
	}

}
