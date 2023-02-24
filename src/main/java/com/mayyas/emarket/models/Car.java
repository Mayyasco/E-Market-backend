package com.mayyas.emarket.models;
import java.util.LinkedList;
import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name="car")
public class Car {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;
	String make;
	String model;
	int year;
	String trim;
	String cond;
	String body_type;
	String trans;
	String address;
	int cost;
	int mileage;
	String other;
	@ManyToOne
    User user;
	@ManyToMany(mappedBy="cars")
	List<User> users = new  LinkedList<User>();
	
	@Override
    public boolean equals(Object o) {
         Car c=(Car)o;
        if(this.id==c.id)
        return true;
        return false;
    }
 
}
