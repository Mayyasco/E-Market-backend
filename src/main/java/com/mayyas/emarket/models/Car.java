package com.mayyas.emarket.models;
import java.util.LinkedList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;
import jakarta.validation.constraints.*;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name="car")
public class Car {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;
	@NotBlank(message="please enter at least two letters")
    @Size(min = 2,message="please enter at least two letters")
	String make;
	@NotBlank(message="please enter at least two letters")
    @Size(min = 2,message="please enter at least two letters")
	String model;
	@NotNull
	@Min(value=1900,message="The year must be 4 digits and greater than 1900")
	int year;
	String trim;
	String cond;
	String body_type;
	String trans;
	String address;
	@NotNull
	@Min(value=1,message="The value must be integer and greater than 0")
	int cost;
	@NotNull
	@Min(value=1,message="The value must be integer and greater than 0")
	int mileage;
	String other;
	@ManyToOne
    User user;
	@OneToOne
    Image image;
	@ManyToMany(fetch = FetchType.EAGER,mappedBy="cars")
	List<User> users = new  LinkedList<User>();
	
	@Override
    public boolean equals(Object o) {
         Car c=(Car)o;
        if(this.id==c.id)
        return true;
        return false;
    }
 
}
