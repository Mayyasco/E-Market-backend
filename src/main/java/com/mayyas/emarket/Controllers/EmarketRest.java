package com.mayyas.emarket.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.mayyas.emarket.Repos.CarRepos;
import com.mayyas.emarket.Repos.HouseRepos;
import com.mayyas.emarket.Repos.UserRepos;
import com.mayyas.emarket.models.Car;
import com.mayyas.emarket.models.House;
import com.mayyas.emarket.models.User;
import com.mayyas.emarket.dto.SearchCar;
import com.mayyas.emarket.dto.SearchHouse;
import com.mayyas.emarket.dto.SignIn;

@RestController
@RequestMapping("/emarket")
public class EmarketRest {

	@Autowired
	private CarRepos carRepos;
	@Autowired
	private UserRepos userRepos;
	@Autowired
	private HouseRepos houseRepos;

	@PostMapping(value = "/addcar/{id}")
	public Car addCar(@PathVariable("id") int id, @RequestBody Car car) {
		User u = userRepos.findById(id).get();
		car.setUser(u);
		return carRepos.save(car);
	}

	@PostMapping(value = "/addhouse/{id}")
	public House addHouse(@PathVariable("id") int id, @RequestBody House house) {
		User u = userRepos.findById(id).get();
		house.setUser(u);
		return houseRepos.save(house);
	}

	@PostMapping(value = "/adduser")
	public User addUser(@RequestBody User user) {
		return userRepos.save(user);
	}

	@GetMapping(value = "/likecar")
	public void likeCar(@RequestParam(name = "id_car") int id_car, @RequestParam(name = "id_user") int id_user,
			@RequestParam(name = "act") String act) {
		Car c = carRepos.findById(id_car).get();
		User u = userRepos.findById(id_user).get();
		if (act.equals("add")) {
			u.getCars().add(c);
			c.getUsers().add(u);
		} else {
			u.getCars().remove(c);
			c.getUsers().remove(u);
		}
		carRepos.save(c);
		userRepos.save(u);

	}

	@GetMapping(value = "/chechlikecar")
	public int chechLikeCar(@RequestParam(name = "id_car") int id_car, @RequestParam(name = "id_user") int id_user) {
		Car c = carRepos.findById(id_car).get();
		User u = userRepos.findById(id_user).get();
		if (!u.getCars().contains(c))
			return 0;
		return 1;
	}

	@GetMapping(value = "/chechlikehouse")
	public int chechLikeHouse(@RequestParam(name = "id_house") int id_house,
			@RequestParam(name = "id_user") int id_user) {
		House h = houseRepos.findById(id_house).get();
		User u = userRepos.findById(id_user).get();
		if (!u.getHouses().contains(h))
			return 0;
		return 1;
	}

	@GetMapping(value = "/likehouse")
	public void likeHouse(@RequestParam(name = "id_house") int id_house, @RequestParam(name = "id_user") int id_user,
			@RequestParam(name = "act") String act) {
		House h = houseRepos.findById(id_house).get();
		User u = userRepos.findById(id_user).get();
		if (act.equals("add")) {
			u.getHouses().add(h);
			h.getUsers().add(u);
		} else {
			u.getHouses().remove(h);
			h.getUsers().remove(u);
		}
		houseRepos.save(h);
		userRepos.save(u);

	}

	@PostMapping(value = "/searchcar")
	public List<Car> searchCar(@RequestBody SearchCar si) {

		return carRepos.search("%" + si.getMake() + "%", "%" + si.getCond() + "%", "%" + si.getBody_type() + "%",
				si.getCost_min(), si.getCost_max(), si.getMileage_min(), si.getMileage_max(), si.getYear_min(),
				si.getYear_max());

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

	@PostMapping(value = "/searchhouse")
	public List<House> searchHouse(@RequestBody SearchHouse si) {

		return houseRepos.search("%" + si.getState() + "%", "%" + si.getCity() + "%", si.getFo(), si.getBaths(),
				si.getBeds(), si.getArea(), si.getCost_min(), si.getCost_max());

	}

	@DeleteMapping(value = "/deletecar/{id}")
	public void deleteCar(@PathVariable("id") int id) {

		carRepos.deleteById(id);
	}

	@DeleteMapping(value = "/deletehouse/{id}")
	public void deleteHouse(@PathVariable("id") int id) {

		houseRepos.deleteById(id);
	}

	@GetMapping(value = "/getuser/{id}")
	public User getUser(@PathVariable("id") int id) {
		return userRepos.findById(id).get();
	}

	@PutMapping(value = "/updateuser/{id}")
	public User updateUser(@PathVariable("id") int id, @RequestBody User user) {
		User u = userRepos.findById(id).get();
		u.setName(user.getName());
		u.setEmail(user.getEmail());
		u.setPassword(user.getPassword());
		u.setAddress(user.getAddress());
		u.setPhone(user.getPhone());
		return userRepos.save(u);

	}

	@PutMapping(value = "/updatecar/{id}")
	public Car updateCar(@PathVariable("id") int id, @RequestBody Car car) {
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
		return carRepos.save(c);

	}

	@PutMapping(value = "/updatehouse/{id}")
	public House updateHouse(@PathVariable("id") int id, @RequestBody House house) {
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
		return houseRepos.save(h);

	}

	@PostMapping(value = "/signin")
	public Integer signin(@RequestBody SignIn si) {

		Integer x = userRepos.signin(si.getEmail(), si.getPassword());
		if (x != null)
			return x;
		else
			return 0;
	}

}