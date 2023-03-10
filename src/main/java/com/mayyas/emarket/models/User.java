package com.mayyas.emarket.models;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Setter
@Getter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name="user")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;
	@NotBlank(message="please enter at least two letters")
    @Size(min = 2,message="please enter at least two letters")
	String name;
	@NotBlank(message="the length of password must be at least 4 and the two pw fields must be identical" )
    @Size(min = 4,message="the length of password must be at least 4 and the two pw fields must be identical" )
	String password;
	String phone;
	String address;
	@NotNull
	@Email(regexp = "^\\w+([.-]?\\w+)*@\\w+([.-]?\\w+)*(\\.\\w{2,3})+$",message="please enter valid email")
	String email;
	@JsonIgnore
	@ManyToMany(fetch = FetchType.EAGER)
    Set<House> houses = new  HashSet<House>();
	@JsonIgnore
	@ManyToMany(fetch = FetchType.EAGER)
	Set<Car> cars = new  HashSet<Car>();
	@Override
    public boolean equals(Object o) {
         User u=(User)o;
        if(this.id==u.id)
        return true;
        return false;
    }
}
