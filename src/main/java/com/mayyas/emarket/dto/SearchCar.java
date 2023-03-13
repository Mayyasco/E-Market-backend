package com.mayyas.emarket.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SearchCar {
	
	String make;
	String cond;
	String bodyType;
	int yearMin;
	int costMin;
	int mileageMin;
	int yearMax;
	int costMax;
	int mileageMax;
}