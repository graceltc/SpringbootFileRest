package com.grace.springboot.service;

import java.io.IOException;


import org.springframework.web.multipart.MultipartFile;

import com.grace.springboot.entity.FileMetaData;

public interface SaveUploadFileService {

	void saveUploadedFiles(MultipartFile file) throws IOException;
	
	void saveFileMetaData(FileMetaData file);
	
}
