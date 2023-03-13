package com.mayyas.emarket.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SearchHouse {
	
	String state;
	String city;
	String fo;
	int area;
	int baths;
	int beds;
	int costMin;
	int costMax;
}