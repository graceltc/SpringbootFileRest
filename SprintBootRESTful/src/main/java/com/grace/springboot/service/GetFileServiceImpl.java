package com.grace.springboot.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grace.springboot.dao.FileDAO;
import com.grace.springboot.entity.FileMetaData;

@Service
public class GetFileServiceImpl implements GetFileService {

	@Autowired
	FileDAO fileDAO;

	@Transactional
	public FileMetaData getMetaData(int fileId) {

		return fileDAO.getFileMetaData(fileId);

	}

	@Transactional
	public List<Integer> getFileIdsByName(String fileName) {

		return fileDAO.getIds(fileName);
	}

}
