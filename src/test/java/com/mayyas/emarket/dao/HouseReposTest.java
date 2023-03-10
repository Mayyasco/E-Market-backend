package com.mayyas.emarket.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mayyas.emarket.models.House;
import com.mayyas.emarket.models.User;

@SpringBootTest
class HouseReposTest {

	@Autowired
	private HouseRepos houseRepos;
	@Autowired
	private UserRepos userRepos;

	@Test
	void testSearch() {

		List<House> expected = new ArrayList<>(Arrays.asList(houseRepos.findById(1).get(), houseRepos.findById(2).get()));
		// the houses with id 1 and 2 match these criteria
		List<House> actual = houseRepos.search("%TX%", "%da%", "sale", 1,1, 500, 1, 1000000);
		assertThat(actual).isEqualTo(expected);
	}

	@Test
	void testFindByUser() {
		User u = userRepos.findById(2).get();
		List<House> actual = houseRepos.findByUser(u);
		// user 2 has two cars with these ids 2 and 5
		House expected1 = new House();expected1.setId(2);
		House expected2 = new House();expected2.setId(5);
		assertThat(actual).containsOnly(expected1,expected2);

	}

	/*@Test
	void testSave() {
	House h = new House();
		h.setBn("123");
		h.setState("MI");
		h.setCity("dearborn");
		h.setStreet("btr");
		h.setZip("1234");
		h.setArea(1200);
		h.setBed(2);
		h.setCost(200000);
		h.setBath(2);
		h.setOther("nice house");
		h.setFo("sale");
		House h2 = houseRepos.save(h);
		assertThat(h2).isNotNull();

	}
*/
	@Test
	void testFindById() {
		House expected = new House();expected.setId(2);
		House actual = houseRepos.findById(2).get();
		assertThat(actual).isEqualTo(expected);
	}

}
