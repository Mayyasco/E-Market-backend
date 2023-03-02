package com.mayyas.emarket.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.mayyas.emarket.dao.CarRepos;
import com.mayyas.emarket.models.Car;

import java.nio.file.Path;

@SpringBootTest
class ImageServiceTest {
	@Autowired
	private ImageService getImageService;
	@Autowired
	private CarRepos carRepos;
	
	@Test
	void testAddImage() throws IOException{
		File file = new File("D:/car.jpg");
		FileInputStream input = new FileInputStream(file);
		MultipartFile multipartFile = new MockMultipartFile("file",
		            file.getName(), "image/jpeg", IOUtils.toByteArray(input));
		
		ResponseEntity<String> RE=getImageService.addImage(multipartFile, 27, "car");
		Car c=carRepos.findById(27).get();
		String actual=c.getImage().getUrl();
		assertThat(actual).isEqualTo("D:\\images\\cars\\27\\car.jpg");
	}

}
