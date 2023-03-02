package com.mayyas.emarket.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.mayyas.emarket.dao.CarRepos;
import com.mayyas.emarket.dao.HouseRepos;
import com.mayyas.emarket.dao.ImageRepos;
import com.mayyas.emarket.models.Car;
import com.mayyas.emarket.models.House;
import com.mayyas.emarket.models.Image;

@Service
public class ImageService {

	@Autowired
	private CarRepos carRepos;
	@Autowired
	private ImageRepos imageRepos;
	@Autowired
	private HouseRepos houseRepos;

	public ResponseEntity<byte[]> getImage(int id, String ty) throws IOException {
		int i = 0;
		// check if car or house
		if (ty.equals("car")) {
			Car c = carRepos.findById(id).get();
			i = c.getImage().getId();
		} else {
			House h = houseRepos.findById(id).get();
			i = h.getImage().getId();
		}
		try {
			Image image = imageRepos.findById(i).get();
			// create image object by ToByteArray
			byte[] imageBytes = new byte[0];
			imageBytes = FileUtils.readFileToByteArray(new File(image.getUrl()));

			return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageBytes);

		} catch (IOException ioe) {
			throw new IOException(ioe);
		}
	}

	// ---------------------------------------------------------------
	public ResponseEntity<String> addImage(MultipartFile multipartFile, int id, String ty) throws IOException {

		String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

		try (InputStream inputStream = multipartFile.getInputStream()) {

			// save the image on server
			Path path;
			if (ty.equals("car"))
				path = Files.createDirectories(Paths.get("/images/cars/" + id + "/"));
			else
				path = Files.createDirectories(Paths.get("/images/houses/" + id + "/"));
			Path filePath = path.resolve(fileName);
			Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);

			if (ty.equals("car")) {
				Car c = carRepos.findById(id).get();

				// delete old one if exists
				if (!c.getImage().getUrl().equals("x")) {
					Files.deleteIfExists(Paths.get(c.getImage().getUrl()));
				}
				// --------------
				c.getImage().setUrl(filePath.toAbsolutePath().toString());
				carRepos.save(c);

			} else {
				House h = houseRepos.findById(id).get();
				// delete old one if exists
				if (!h.getImage().getUrl().equals("x")) {
					Files.deleteIfExists(Paths.get(h.getImage().getUrl()));
				}
				h.getImage().setUrl(filePath.toAbsolutePath().toString());
				houseRepos.save(h);
			}
			return new ResponseEntity<String>("done", HttpStatus.OK);
		} catch (IOException ioe) {
			throw new IOException(ioe);
		}

	}
}
