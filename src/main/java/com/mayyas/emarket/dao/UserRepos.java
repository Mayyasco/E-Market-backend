package com.mayyas.emarket.dao;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.mayyas.emarket.models.User;


public interface UserRepos extends JpaRepository<User, Integer> {
	@Query("select id from User where "
			+ " email= :email and"
			+ " password =:password")
	 Integer signin(@Param("email")String email,@Param("password")String password);
			         
}
