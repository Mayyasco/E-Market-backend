package com.mayyas.emarket.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mayyas.emarket.models.House;
import com.mayyas.emarket.models.User;

public interface HouseRepos extends JpaRepository<House, Integer> {

	@Query("from House where state like :state and city like :city and fo =:fo"
			+ "  and bath >= :bath and bed >= :bed and area >= :area and cost between :costMin and :costMax")
	List<House> search(@Param("state") String state, @Param("city") String city, @Param("fo") String fo,
			@Param("bath") Integer bath,@Param("bed") Integer bed,@Param("area") Integer area,
			@Param("costMin") Integer costMin, @Param("costMax") Integer costMax);
	
	List<House> findByUser(User u);

}
