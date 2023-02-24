package com.mayyas.emarket.models;
import java.util.LinkedList;
import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name="user")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;
	String name;
	String password;
	String phone;
	String address;
	String email;
	@JsonIgnore
	@ManyToMany
    List<House> houses = new  LinkedList<House>();
	@JsonIgnore
	@ManyToMany
    List<Car> cars = new  LinkedList<Car>();
}
