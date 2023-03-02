package com.mayyas.emarket.controllers;

import java.io.*;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.mayyas.emarket.models.Car;
import com.mayyas.emarket.models.House;
import com.mayyas.emarket.models.Image;
import com.mayyas.emarket.models.User;
import com.mayyas.emarket.service.ImageService;
import com.mayyas.emarket.service.LikeService;

import org.springframework.web.multipart.MultipartFile;
import jakarta.servlet.ServletContext;
import jakarta.validation.Valid;
import com.mayyas.emarket.dao.CarRepos;
import com.mayyas.emarket.dao.HouseRepos;
import com.mayyas.emarket.dao.ImageRepos;
import com.mayyas.emarket.dao.UserRepos;
import com.mayyas.emarket.dto.SearchCar;
import com.mayyas.emarket.dto.SearchHouse;
import com.mayyas.emarket.dto.SignIn;
import com.mayyas.emarket.dto.UpdatePassword;

@RestController
@RequestMapping("/emarket")
public class EmarketRest {

	@Autowired
	private CarRepos carRepos;
	@Autowired
	private ImageService getImageService;
	@Autowired
	private LikeService likeService;
	@Autowired
	private UserRepos userRepos;
	@Autowired
	private HouseRepos houseRepos;
	@Autowired
	ServletContext context;
	@Autowired
	private ImageRepos imageRepos;

	@ResponseBody
	@GetMapping(value = "/getimage/{id}/{ty}")
	public ResponseEntity<byte[]> getImage(@PathVariable("id") int id, @PathVariable("ty") String ty)
			throws IOException {

		return getImageService.getImage(id, ty);
	}

	@PostMapping(value = "/addimage")
	public ResponseEntity<String> addImage(@RequestParam("file") MultipartFile multipartFile,
			@RequestParam("id") int id, @RequestParam("ty") String ty) throws IOException {

		return getImageService.addImage(multipartFile, id, ty);
	}

	@PostMapping(value = "/addcar/{id}")
	public ResponseEntity<Car> addCar(@PathVariable("id") int id, @Valid @RequestBody Car car) {

		User u = userRepos.findById(id).get();
		Image i=new Image();
		i.setUrl("x");
		Image savedImage = imageRepos.save(i);
		car.setImage(savedImage);
		car.setUser(u);
		return new ResponseEntity<Car>(carRepos.save(car), HttpStatus.CREATED);
	}

	@PostMapping(value = "/addhouse/{id}")
	public ResponseEntity<House> addHouse(@PathVariable("id") int id, @Valid @RequestBody House house) {
		User u = userRepos.findById(id).get();
		Image i=new Image();
		i.setUrl("x");
		Image savedImage = imageRepos.save(i);
		house.setImage(savedImage);
		house.setUser(u);
		return new ResponseEntity<House>(houseRepos.save(house), HttpStatus.CREATED);
	}

	@PostMapping(value = "/adduser")
	public ResponseEntity<User> addUser(@Valid @RequestBody User user) {
		return new ResponseEntity<User>(userRepos.save(user), HttpStatus.CREATED);
	}

	@PostMapping(value = "/likecar")
	public void likeCar(@RequestParam(name = "id_car") int id_car, @RequestParam(name = "id_user") int id_user,
			@RequestParam(name = "act") String act) {
		likeService.likeCar(id_car, id_user, act);
	}
	
	@PostMapping(value = "/likehouse")
	public void likeHouse(@RequestParam(name = "id_house") int id_house, @RequestParam(name = "id_user") int id_user,
			@RequestParam(name = "act") String act) {
		likeService.likeHouse(id_house, id_user, act);
	}
	
	@GetMapping(value = "/chechlikecar")
	public int chechLikeCar(@RequestParam(name = "id_car") int id_car, @RequestParam(name = "id_user") int id_user) {
		return likeService.chechLikeCar(id_car, id_user);
	}

	@GetMapping(value = "/chechlikehouse")
	public int chechLikeHouse(@RequestParam(name = "id_house") int id_house,
			@RequestParam(name = "id_user") int id_user) {
		return likeService.chechLikeHouse(id_house, id_user);
	}

	@PostMapping(value = "/searchcar")
	public List<Car> searchCar(@RequestBody SearchCar si) {

		return carRepos.search("%" + si.getMake() + "%", "%" + si.getCond() + "%", "%" + si.getBody_type() + "%",
				si.getCost_min(), si.getCost_max(), si.getMileage_min(), si.getMileage_max(), si.getYear_min(),
				si.getYear_max());

	}

	@PostMapping(value = "/searchhouse")
	public List<House> searchHouse(@RequestBody SearchHouse si) {

		return houseRepos.search("%" + si.getState() + "%", "%" + si.getCity() + "%", si.getFo(), si.getBaths(),
				si.getBeds(), si.getArea(), si.getCost_min(), si.getCost_max());

	}
	
	@GetMapping(value = "/mine/{id}")
	public List[] mine(@PathVariable("id") int id) {
		User u = userRepos.findById(id).get();
		List<Car> cars = carRepos.findByUser(u);
		List<House> houses = houseRepos.findByUser(u);
		return new List[] { cars, houses };

	}

	@GetMapping(value = "/fav/{id}")
	public List[] fav(@PathVariable("id") int id) {
		User u = userRepos.findById(id).get();
		List<Car> cars = u.getCars();
		List<House> houses = u.getHouses();
		return new List[] { cars, houses };

	}

	@DeleteMapping(value = "/deletecar/{id}")
	public void deleteCar(@PathVariable("id") int id) {
		Car c = carRepos.findById(id).get();
		List<User> listUser=c.getUsers();
		for ( User u : listUser ) 
			{
			u.getCars().remove(c);
			userRepos.save(u);
			}
		c.getUsers().clear();
		carRepos.save(c);
		carRepos.deleteById(id);
	}

	@DeleteMapping(value = "/deletehouse/{id}")
	public void deleteHouse(@PathVariable("id") int id) {

		House h = houseRepos.findById(id).get();
		List<User> listUser=h.getUsers();
		for ( User u : listUser ) 
			{
			u.getHouses().remove(h);
			userRepos.save(u);
			}
		h.getUsers().clear();
		houseRepos.save(h);
		houseRepos.deleteById(id);
	}

	@GetMapping(value = "/getuser/{id}")
	public User getUser(@PathVariable("id") int id) {
		return userRepos.findById(id).get();
	}

	@PostMapping(value = "/updateuserpass/{id}")
	public Map<String, String> updateUserPass(@PathVariable("id") int id, @RequestBody UpdatePassword p) {
		User u = userRepos.findById(id).get();
		//check old password same as new password
		if (u.getPassword().equals(p.getOldP())) {
			u.setPassword(p.getNewP());
			userRepos.save(u);
			return Collections.singletonMap("ms", "ok");
		} else
			return Collections.singletonMap("ms", "the old password not correct");

	}

	@PostMapping(value = "/updateuser/{id}")
	public ResponseEntity<User> updateUser(@PathVariable("id") int id, @Valid @RequestBody User user) {
		User u = userRepos.findById(id).get();
		u.setName(user.getName());
		u.setEmail(user.getEmail());
		u.setAddress(user.getAddress());
		u.setPhone(user.getPhone());

		return new ResponseEntity<User>(userRepos.save(u), HttpStatus.CREATED);

	}

	@PostMapping(value = "/updatecar/{id}")
	public ResponseEntity<Car> updateCar(@PathVariable("id") int id, @Valid @RequestBody Car car) {
		Car c = carRepos.findById(id).get();
		c.setMake(car.getMake());
		c.setModel(car.getModel());
		c.setYear(car.getYear());
		c.setTrim(car.getTrim());
		c.setCond(car.getCond());
		c.setBody_type(car.getBody_type());
		c.setTrans(car.getTrans());
		c.setAddress(car.getAddress());
		c.setCost(car.getCost());
		c.setMileage(car.getMileage());
		c.setOther(car.getOther());
		return new ResponseEntity<Car>(carRepos.save(c), HttpStatus.CREATED);

	}

	@PostMapping(value = "/updatehouse/{id}")
	public ResponseEntity<House> updateHouse(@PathVariable("id") int id, @Valid @RequestBody House house) {
		House h = houseRepos.findById(id).get();
		h.setBn(house.getBn());
		h.setState(house.getState());
		h.setCity(house.getCity());
		h.setStreet(house.getStreet());
		h.setZip(house.getZip());
		h.setArea(house.getArea());
		h.setBed(house.getBed());
		h.setCost(house.getCost());
		h.setBath(house.getBath());
		h.setOther(house.getOther());
		h.setFo(house.getFo());
		return new ResponseEntity<House>(houseRepos.save(h), HttpStatus.CREATED);

	}

	@PostMapping(value = "/signin")
	public Integer signin(@RequestBody SignIn si) {
        //exists or not
		Integer x = userRepos.signin(si.getEmail(), si.getPassword());
		if (x != null)return x;	
		else return 0;
			
	}

}