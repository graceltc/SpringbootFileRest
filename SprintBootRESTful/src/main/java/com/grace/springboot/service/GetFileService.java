package com.grace.springboot.service;

import java.util.List;

import com.grace.springboot.entity.FileMetaData;

public interface GetFileService {

	public FileMetaData getMetaData(int fileId);
	
	public List<Integer> getFileIdsByName(String fileName);
}
