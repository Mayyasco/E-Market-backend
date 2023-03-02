package com.mayyas.emarket.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.repository.query.Param;

import com.mayyas.emarket.models.Car;

import com.mayyas.emarket.models.User;

@SpringBootTest
public class CarReposTest {

	@Autowired
	private CarRepos carRepos;
	@Autowired
	private UserRepos userRepos;
	
	@ParameterizedTest
	@CsvSource({ "bmw,used,Hatchback,1, 1000000, 1, 1000000, 1900, 2023,1","mazda,used,Van,1, 1000000, 1, 1000000, 1900, 2023,2"})
	void testSearch(String make, String cond, String body_type, int cost_min,
			int cost_max,int mileage_min,int mileage_max,int year_min,int year_max,int caseN) {
		List<Car> expected ;
		if(caseN==2) {
			// the cars with id 33 only match these criteria
			expected = new ArrayList<>(Arrays.asList(carRepos.findById(33).get()));
		}
		else{
			// the cars with id 31 and 32 match these criteria
			 expected = new ArrayList<>(Arrays.asList(carRepos.findById(31).get(), carRepos.findById(32).get()));
			}
		List<Car> actual = carRepos.search("%"+make+"%", "%"+cond+"%", "%"+body_type+"%", cost_min, cost_max,
				mileage_min, mileage_max, year_min, year_max);
		assertThat(actual).isEqualTo(expected);
		

	}

	@Test
	void testFindByUser() {
		User u = userRepos.findById(1).get();
		List<Car> actual = carRepos.findByUser(u);
		// user 1 has two cars with these ids 27 and 32
		Car expected1 = new Car();expected1.setId(27);
		Car expected2 = new Car();expected2.setId(32);
		assertThat(actual).containsOnly(expected1,expected2);

	}

	/*@Test
	void testSave() {
		Car c = new Car();
		c.setMake("toyota");
		c.setModel("corolla");
		c.setYear(2023);
		c.setTrim("qw1");
		c.setCond("new");
		c.setBody_type("SUV");
		c.setTrans("auto");
		c.setAddress("MI");
		c.setCost(100000);
		c.setMileage(100);
		c.setOther("good car");
		Car c2 = carRepos.save(c);
		assertThat(c2).isNotNull();

	}
*/
	@Test
	void testFindById() {
		Car expected = new Car();expected.setId(31);
		Car actual = carRepos.findById(31).get();
		assertThat(actual).isEqualTo(expected);
	}

}
