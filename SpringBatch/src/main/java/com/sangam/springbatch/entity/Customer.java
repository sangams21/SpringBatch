package com.sangam.springbatch.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="CUSTOMERS_INFO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
	

	@javax.persistence.Id
	private int id;
	
	private String firstName;
	
	private String lastName;
	
	private String email;
	
	private String gender;
	
	private String contactNo;
	
	private String country;
	
	private String dob;
	
}
