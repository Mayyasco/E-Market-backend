package com.mayyas.emarket.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SearchCar {
	
	String make;
	String cond;
	String body_type;
	int year_min;
	int cost_min;
	int mileage_min;
	int year_max;
	int cost_max;
	int mileage_max;
}