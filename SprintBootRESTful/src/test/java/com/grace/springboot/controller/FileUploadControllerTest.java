package com.grace.springboot.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import com.grace.springboot.config.JPAConfig;
import com.grace.springboot.service.SaveUploadFileService;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class FileUploadControllerTest {

	@Autowired
    private MockMvc mvc;
	
	@MockBean
	SaveUploadFileService saveUploadFileService;
	
	@Test
	public void testUploadFile() throws Exception {
		MockMultipartFile multipartFile =
                new MockMultipartFile("file", "/SprintBootRESTful/src/main/resources/fileFolder/pom.xml", "text/plain", "Spring Framework".getBytes());
        this.mvc.perform(fileUpload("/upload").file(multipartFile))
                .andExpect(status().isOk())
                .andReturn().equals("file uploaded success");

        then(this.saveUploadFileService).should().saveUploadedFiles(multipartFile);
	}
}
