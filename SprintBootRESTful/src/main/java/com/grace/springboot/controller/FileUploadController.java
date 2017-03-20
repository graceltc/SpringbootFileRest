package com.grace.springboot.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.grace.springboot.entity.FileMetaData;
import com.grace.springboot.exception.FileContentException;
import com.grace.springboot.service.GetFileService;
import com.grace.springboot.service.SaveUploadFileService;


@RestController
public class FileUploadController {
	private final static Logger LOGGER = Logger.getLogger(FileUploadController.class);

	@Autowired
	SaveUploadFileService saveUploadFileService;
	
	@Autowired
	GetFileService getFileService;
	
	@PostMapping("/upload")
	public String uploadFile(
            @RequestParam("file") MultipartFile uploadfile) throws FileContentException {

		LOGGER.debug("File uploading ......");

        if (!uploadfile.isEmpty()) {
        	try {
            	saveUploadFileService.saveUploadedFiles(uploadfile);
            	
            	String fileName = uploadfile.getOriginalFilename();
            	Long fileSize = uploadfile.getSize();
            	FileMetaData file = new FileMetaData();
            	file.setName(fileName);
            	file.setSize(fileSize);
            	saveUploadFileService.saveFileMetaData(file);
            	LOGGER.info(fileName + "  " + fileSize);
            	
            	return "file uploaded success";
            } catch (Exception e) {
            	LOGGER.error("error in uploading .. " + e.getLocalizedMessage());
            	throw new FileContentException("Failed to upload the file.");
            }
        } else {
        	throw new FileContentException( "You failed to upload because the file was empty.");
        }

    }
	
	//@GetMapping("find?I")
	@GetMapping("/find/id/{id}")
	public FileMetaData getFileInfo(@PathVariable("id") String id) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.info("GET file request ID :  " + id);
		}
		FileMetaData fileGet = getFileService.getMetaData(Integer.parseInt(id));
		LOGGER.info(fileGet.getId() + " " + fileGet.getName());
		return fileGet;
	}
	
	@GetMapping("/findBy/name/{subname}")
	public List<Integer> getFileIds(@PathVariable("subname") String subFileName) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.info("Find file id by " + subFileName);
		}
		List<Integer> idList = getFileService.getFileIdsByName(subFileName);
		return idList;
	}
	
	@GetMapping("/download/{file_name}.{ext}")
	public void getFile(@PathVariable("file_name") String fileName, @PathVariable("ext") String ext, HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		String fileLocation = "./src/main/resources/fileFolder/";
		File downloadFile = new File(fileLocation + fileName + "." + ext);
		System.out.println(downloadFile.getCanonicalPath());
		// Approach 1:
//		FileInputStream inputStream = new FileInputStream(downloadFile);
//		String mimeType = request.getServletContext().getMimeType(fileLocation + fileName);
//		if (mimeType == null) {
//			mimeType = "application/octet-stream";
//		}
//		response.setContentType(mimeType);
//		response.setContentLength((int) downloadFile.length());
//		String headerKey = "Content-Disposition";
//        String headerValue = String.format("attachment; filename=\"%s\"",
//                fileName);
//        response.setHeader(headerKey, headerValue);
//        OutputStream outStream = response.getOutputStream();
//        byte[] buffer = new byte[4096];
//        int bytesRead = -1;
//        while ((bytesRead = inputStream.read(buffer)) != -1) {
//            outStream.write(buffer, 0, bytesRead);
//        }
//        inputStream.close();
//        outStream.close();
//        LOGGER.info("Download " + fileName + " Success!" );
		// Approach 2:
		if (downloadFile.exists()) {
			try {
				InputStream inputStream = new FileInputStream(downloadFile);
				String type = downloadFile.toURL().openConnection().guessContentTypeFromName(fileName);
				response.setContentType(type);
				response.setContentLengthLong(downloadFile.length());
				IOUtils.copy(inputStream, response.getOutputStream());
				response.flushBuffer();
				LOGGER.info("Download " + fileName + " Success!" );
			} catch (IOException ex){
				LOGGER.info("Error in downloading file" + fileName);
				throw new RuntimeException("Error writing file to outputstream");
			}
		} else {
			LOGGER.info("ERROR file does not exist");
		}
		
	}
	
	@ExceptionHandler
	public String handleException(Exception e) {
		LOGGER.error(e);
		return e.getLocalizedMessage();
	}
	
}
