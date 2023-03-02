package com.mayyas.emarket.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mayyas.emarket.dao.CarRepos;
import com.mayyas.emarket.dao.HouseRepos;
import com.mayyas.emarket.dao.UserRepos;
import com.mayyas.emarket.models.Car;
import com.mayyas.emarket.models.House;
import com.mayyas.emarket.models.User;
@Service
public class LikeService {
	@Autowired
	private CarRepos carRepos;
	@Autowired
	private UserRepos userRepos;
	@Autowired
	private HouseRepos houseRepos;
	
	public void likeCar(int id_car, int id_user,String act) {
		Car c = carRepos.findById(id_car).get();
		User u = userRepos.findById(id_user).get();
		//check if like or unlike
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
	//------------------------------------------------------
	public void likeHouse(int id_house,int id_user,String act) {
		House h = houseRepos.findById(id_house).get();
		User u = userRepos.findById(id_user).get();
		//check if like or unlike
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
	//------------------------------------------------------
	public int chechLikeCar(int id_car,int id_user) {
		Car c = carRepos.findById(id_car).get();
		User u = userRepos.findById(id_user).get();
		//check if liked or not
		if (!u.getCars().contains(c))
			return 0;
		return 1;
	}
	//---------------------------------------------------
	public int chechLikeHouse(int id_house,int id_user) {
		House h = houseRepos.findById(id_house).get();
		User u = userRepos.findById(id_user).get();
		//check if liked or not
		if (!u.getHouses().contains(h))
			return 0;
		return 1;
	}
}
