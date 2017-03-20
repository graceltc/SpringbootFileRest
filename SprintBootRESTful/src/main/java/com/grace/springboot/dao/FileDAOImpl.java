package com.grace.springboot.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.grace.springboot.entity.FileMetaData;

@Repository
public class FileDAOImpl implements FileDAO {
	@PersistenceContext
	EntityManager em;

	public void save(FileMetaData file) {
		em.persist(file);
	}

	public FileMetaData getFileMetaData(int fileId) {

		return em.find(FileMetaData.class, fileId);
	}

	public List<Integer> getIds(String fileNameSubString) {
		Query query = em.createQuery("select f.id from FileMetaData f where f.name like :str");
		query.setParameter("str", "%" + fileNameSubString + "%");
		List<Integer> idList = query.getResultList();
		return idList;
	}

}
