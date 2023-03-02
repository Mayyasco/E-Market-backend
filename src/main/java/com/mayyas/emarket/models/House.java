package com.mayyas.emarket.models;
import java.util.LinkedList;
import java.util.List;
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
@Table(name="house")
public class House {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;
	@NotBlank(message="please enter at least two letters")
    @Size(min = 2,message="please enter at least two letters")
	String bn;
	@NotBlank(message="please enter at least two letters")
    @Size(min = 2,message="please enter at least two letters")
	String state;
	@NotBlank(message="please enter at least two letters")
    @Size(min = 2,message="please enter at least two letters")
	String city;
	String street;
	String zip;
	@NotNull
	@Min(value=1,message="The value must be greater than 0")
	int area;
	@NotNull
	@Min(value=1,message="The value must be greater than 0 and less than 20")
	@Max(value=20,message="The value must be greater than 0 and less than 20")
	int bed;
	@NotNull
	@Min(value=1,message="The value must be greater than 0")
	int cost;
	@NotNull
	@Min(value=1,message="The value must be greater than 0 and less than 20")
	@Max(value=20,message="The value must be greater than 0 and less than 20")
	int bath;
	String other;
	String fo;
	@ManyToOne
    User user;
	@OneToOne
    Image image;
	@ManyToMany(fetch = FetchType.EAGER,mappedBy="houses")
	List<User> users = new  LinkedList<User>();
	
	@Override
    public boolean equals(Object o) {
         House h=(House)o;
        if(this.id==h.id)
        return true;
        return false;
    }
 
}

