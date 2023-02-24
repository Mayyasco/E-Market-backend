package com.mayyas.emarket.Repos;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.mayyas.emarket.models.Car;
import com.mayyas.emarket.models.User;



public interface CarRepos extends JpaRepository<Car, Integer> {
	
	@Query("from Car where "
			+ " make like :make and"
			+ " cond like :cond and"
			+ " body_type like :body_type and"
			+ " cost between :cost_min and :cost_max and"
			+ " mileage between :mileage_min and :mileage_max and"
			+ " year between :year_min and :year_max")
	 List<Car> search(@Param("make")String make,@Param("cond")String cond,
			          @Param("body_type")String body_type,@Param("cost_min")Integer min_cost,
			          @Param("cost_max")Integer max_cost,@Param("mileage_min")Integer min_mileage,
			          @Param("mileage_max")Integer max_mileage,@Param("year_min")Integer min_year,
			          @Param("year_max")Integer max_year);
	
	
	List<Car> findByUser(User u);
}
