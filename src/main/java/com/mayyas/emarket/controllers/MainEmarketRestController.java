package com.mayyas.emarket.controllers;

import java.io.*;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mayyas.emarket.models.AuthGroup;
import com.mayyas.emarket.models.Car;
import com.mayyas.emarket.models.House;
import com.mayyas.emarket.models.Image;
import com.mayyas.emarket.models.User;
import com.mayyas.emarket.service.ImageService;
import com.mayyas.emarket.service.LikeService;
import com.mayyas.emarket.service.UpdatePasswordService;

import org.springframework.web.multipart.MultipartFile;
import jakarta.servlet.ServletContext;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import com.mayyas.emarket.dao.AuthGroupRepos;
import com.mayyas.emarket.dao.CarRepos;
import com.mayyas.emarket.dao.HouseRepos;
import com.mayyas.emarket.dao.ImageRepos;
import com.mayyas.emarket.dao.UserRepos;
import com.mayyas.emarket.dto.SearchCar;
import com.mayyas.emarket.dto.SearchHouse;
import com.mayyas.emarket.dto.UpdatePassword;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/emarket")
public class MainEmarketRestController {
	final int MAX=3;
	@Autowired
	private CarRepos carRepos;
	@Autowired
	private ImageService getImageService;
	@Autowired
	private LikeService likeService;
	@Autowired
	private UpdatePasswordService updatePasswordService;
	@Autowired
	private UserRepos userRepos;
	@Autowired
	private HouseRepos houseRepos;
	@Autowired
	ServletContext context;
	@Autowired
	private ImageRepos imageRepos;
	@Autowired
	private AuthGroupRepos authGroupRepos;

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
		//add user t0 the car
		car.setUser(u);
		Car cs=carRepos.save(car);
		//check the count to update auth
		checkCount(u.getEmail());
		log.info("add car");
		return new ResponseEntity<Car>(cs, HttpStatus.CREATED);
	}

	
	private void checkCount(String email) {
		User u = userRepos.findByEmail(email).get();
		List<House> houses = houseRepos.findByUser(u);
		List<Car> cars = carRepos.findByUser(u);
		List<AuthGroup> authG =authGroupRepos.findByEmail( email);
		//check previous auth
		if(houses.size()>=MAX && cars.size()>=MAX) authG.get(0).setRole("FULL");
		else if (houses.size()<MAX && cars.size()<MAX) authG.get(0).setRole("USER");
		else if ( houses.size()>=MAX && cars.size()<MAX) authG.get(0).setRole("FULL_HOUSES");
		else if ( houses.size()<MAX && cars.size()>=MAX) authG.get(0).setRole("FULL_CARS");
		authGroupRepos.save(authG.get(0));
	}
	

	@PostMapping(value = "/addhouse/{id}")
	public ResponseEntity<House> addHouse(@PathVariable("id") int id, @Valid @RequestBody House house) {
		User u = userRepos.findById(id).get();
		Image i=new Image();
		i.setUrl("x");
		Image savedImage = imageRepos.save(i);
		house.setImage(savedImage);
		//add user to the house
		house.setUser(u);
		House hs=houseRepos.save(house);
		//check the count to update auth
		checkCount(u.getEmail());
		log.info("add house");
		return new ResponseEntity<House>(hs, HttpStatus.CREATED);
	}

	@PostMapping(value = "/adduser")
	public ResponseEntity<User> addUser(@Valid @RequestBody User user) {
		Optional<User> ou=userRepos.findByEmail(user.getEmail());
		User u = new User();
		//check if email exists
		if(!ou.isPresent()) {
		u.setName(user.getName());
		u.setEmail(user.getEmail());
		u.setAddress(user.getAddress());
		u.setPhone(user.getPhone());
		//encrypt the password
		u.setPassword(new BCryptPasswordEncoder(4).encode(user.getPassword()));
		log.debug(u.getPassword());
		//add default role USER
		AuthGroup au=new AuthGroup();
		au.setEmail(user.getEmail());
		au.setRole("USER");
		authGroupRepos.save(au);
		return new ResponseEntity<User>(userRepos.save(u), HttpStatus.CREATED);}
		else 
		{
			log.warn("the email exist");
			u.setId(-2);
			return new ResponseEntity<User>(u, HttpStatus.CREATED);
		}
	}

	@PostMapping(value = "/likecar")
	public void likeCar(@RequestParam(name = "id_car") int id_car, @RequestParam(name = "id_user") int id_user,
			@RequestParam(name = "act") String act) {
		log.info("like car");
		likeService.likeCar(id_car, id_user, act);
	}
	
	@PostMapping(value = "/likehouse")
	public void likeHouse(@RequestParam(name = "id_house") int id_house, @RequestParam(name = "id_user") int id_user,
			@RequestParam(name = "act") String act) {
		log.info("like house");
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
		log.info("search car");
		return carRepos.search("%" + si.getMake() + "%", "%" + si.getCond() + "%", "%" + si.getBody_type() + "%",
				si.getCost_min(), si.getCost_max(), si.getMileage_min(), si.getMileage_max(), si.getYear_min(),
				si.getYear_max());

	}

	@PostMapping(value = "/searchhouse")
	public List<House> searchHouse(@RequestBody SearchHouse si) {
		log.info("search house");
		return houseRepos.search("%" + si.getState() + "%", "%" + si.getCity() + "%", si.getFo(), si.getBaths(),
				si.getBeds(), si.getArea(), si.getCost_min(), si.getCost_max());

	}
	
	@GetMapping(value = "/mine/{id}")
	public List[] mine(@PathVariable("id") int id) {
		User u = userRepos.findById(id).get();
		//get the two lists
		List<Car> cars = carRepos.findByUser(u);
		List<House> houses = houseRepos.findByUser(u);
		log.info("show mine list");
		return new List[] { cars, houses };

	}

	@GetMapping(value = "/fav/{id}")
	public Set[] fav(@PathVariable("id") int id) {
		User u = userRepos.findById(id).get();
		//get the two sets
		Set<Car> cars = u.getCars();
		Set<House> houses = u.getHouses();
		log.info("show fav list");
		return new Set[] { cars, houses };

	}
	//rollback if can't delete every related thing
	@Transactional(rollbackOn = Exception.class)
	@DeleteMapping(value = "/deletecar/{id}/{id_user}")
	public void deleteCar(@PathVariable("id") int id,@PathVariable("id_user") int id_user) {
		Car c = carRepos.findById(id).get();
		//delete all like
		Set<User> listUser=c.getUsers();
		for ( User u : listUser ) 
			{
			u.getCars().remove(c);
			userRepos.save(u);
			}
		c.getUsers().clear();
		carRepos.save(c);
		carRepos.deleteById(id);
		User u=userRepos.findById(id_user).get();
		checkCount(u.getEmail());
		log.info("delete car");
	}
	//rollback if can't delete every related thing
	@Transactional(rollbackOn = Exception.class)
	@DeleteMapping(value = "/deletehouse/{id}/{id_user}")
	public void deleteHouse(@PathVariable("id") int id,@PathVariable("id_user") int id_user) {

		House h = houseRepos.findById(id).get();
		Set<User> listUser=h.getUsers();
		//delete all like
		for ( User u : listUser ) 
			{
			u.getHouses().remove(h);
			userRepos.save(u);
			}
		h.getUsers().clear();
		houseRepos.save(h);
		houseRepos.deleteById(id);
		User u=userRepos.findById(id_user).get();
		checkCount(u.getEmail());
		log.info("delete house");
	}

	@GetMapping(value = "/getuser/{id}")
	public User getUser(@PathVariable("id") int id) {
		return userRepos.findById(id).get();
	}

	@PostMapping(value = "/updateuserpass/{id}")
	public Map<String, String> updateUserPass(@PathVariable("id") int id, @RequestBody UpdatePassword p) {
		
		return updatePasswordService.updateUserPass(id, p.getOldP(),p.getNewP());
	}

	@PostMapping(value = "/updateuser/{id}")
	public ResponseEntity<User> updateUser(@PathVariable("id") int id, @Valid @RequestBody User user) {
		User u = userRepos.findById(id).get();
		u.setName(user.getName());
		u.setEmail(user.getEmail());
		u.setAddress(user.getAddress());
		u.setPhone(user.getPhone());
		log.info("update user");
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
		log.info("update car");
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
		log.info("update house");
		return new ResponseEntity<House>(houseRepos.save(h), HttpStatus.CREATED);

	}

	@GetMapping(value = "/checkaddc")
	public void checkaddc() {
		log.debug("check user auth to add car");
        
	}
	@GetMapping(value = "/checkaddh")
	public void checkaddh() {
		log.debug("check user auth to add house");
	}
	@GetMapping(value = "/getcarowner/{id}")
	public User getCarOwner(@PathVariable("id") int id) {
		Car c=carRepos.findById(id).get();
		return c.getUser();
	}
	@GetMapping(value = "/gethouseowner/{id}")
	public User getHouseOwner(@PathVariable("id") int id) {
		House h=houseRepos.findById(id).get();
		return h.getUser();
	}
	
}