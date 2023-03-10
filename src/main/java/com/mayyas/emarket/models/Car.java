package com.mayyas.emarket.models;
import java.util.HashSet;
import java.util.Set;

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

@Setter
@Getter
@NoArgsConstructor
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
	String bodyType;
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
	@ManyToMany(mappedBy="cars")
	Set<User> users = new  HashSet<User>();
	
	@Override
    public boolean equals(Object o) {
         Car c=(Car)o;
        if(this.id==c.id)
        return true;
        return false;
    }
 
}
