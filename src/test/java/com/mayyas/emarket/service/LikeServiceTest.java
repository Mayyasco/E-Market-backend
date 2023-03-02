package com.mayyas.emarket.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
@SpringBootTest
class LikeServiceTest {
	@Autowired
	private LikeService likeService;
	@Test
	void testLikeCar() {
		//car 27 set to like by user 1
				likeService.likeCar(27,1,"add");
				int actual = likeService.chechLikeCar(27, 1);
				 assertThat(actual).isEqualTo(1);
				 /*
				  likeService.likeCar(27,1,"del");
				int actual = likeService.chechLikeCar(27, 1);
				 assertThat(actual).isEqualTo(0);
				  */
	}

	@Test
	void testLikeHouse() {
		//house 1 set to like by user 1
		likeService.likeHouse(1,1,"add");
		int actual = likeService.chechLikeHouse(1, 1);
		 assertThat(actual).isEqualTo(1);
		 /*
		  likeService.likeHouse(1,1,"del");
		int actual = likeService.chechLikeHouse(1, 1);
		 assertThat(actual).isEqualTo(0);
		  */
	}

	@Test
	void testChechLikeCar() {
		//user 2 like car 27
		 int actual = likeService.chechLikeCar(27, 2);
		 assertThat(actual).isEqualTo(1);
	}

	@Test
	void testChechLikeHouse() {
		//user 1 like house 2
		 int actual = likeService.chechLikeHouse(2, 1);
		 assertThat(actual).isEqualTo(1);
	}

}
