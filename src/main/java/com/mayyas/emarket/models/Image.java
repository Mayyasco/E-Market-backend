package com.mayyas.emarket.models;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Setter
@Getter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Image {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @NonNull
    String url;
}
