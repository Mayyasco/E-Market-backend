package com.mayyas.emarket.dao;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mayyas.emarket.models.Car;
import com.mayyas.emarket.models.User;



public interface CarRepos extends JpaRepository<Car, Integer> {
	
	@Query("from Car where "
			+ " make like :make and"
			+ " cond like :cond and"
			+ " bodyType like :bodyType and"
			+ " cost between :costMin and :costMax and"
			+ " mileage between :mileageMin and :mileageMax and"
			+ " year between :yearMin and :yearMax")
	 List<Car> search(@Param("make")String make,@Param("cond")String cond,
			          @Param("bodyType")String bodyType,@Param("costMin")Integer costMin,
			          @Param("costMax")Integer costMax,@Param("mileageMin")Integer mileageMin,
			          @Param("mileageMax")Integer mileageMax,@Param("yearMin")Integer yearMin,
			          @Param("yearMax")Integer yearMax);
	
	
	List<Car> findByUser(User u);
}
