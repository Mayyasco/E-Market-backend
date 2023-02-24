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
@Table(name="house")
public class House {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;
	String bn;
	String state;
	String city;
	String street;
	String zip;
	int area;
	int bed;
	int cost;
	int bath;
	String other;
	String fo;
	@ManyToOne
    User user;
	@ManyToMany(mappedBy="houses")
	List<User> users = new  LinkedList<User>();
	
	@Override
    public boolean equals(Object o) {
         House h=(House)o;
        if(this.id==h.id)
        return true;
        return false;
    }
 
}

