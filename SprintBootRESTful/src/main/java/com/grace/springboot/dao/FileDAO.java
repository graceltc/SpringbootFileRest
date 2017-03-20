package com.grace.springboot.dao;

import java.util.List;

import com.grace.springboot.entity.FileMetaData;

public interface FileDAO {
	void save(FileMetaData file);
	FileMetaData getFileMetaData(int fileId);
	List<Integer> getIds(String fileNameSubString);
}
