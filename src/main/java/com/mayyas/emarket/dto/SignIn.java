package com.mayyas.emarket.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SignIn {
	
	String email;
	String password;
	
}