package com.sangam.springbatch.config;

import org.springframework.batch.item.ItemProcessor;

import com.sangam.springbatch.entity.Customer;

public class CustomerProcceser implements ItemProcessor<Customer, Customer>{

	@Override
	public Customer process(Customer customer) throws Exception {
		// if you want to savw some data based on filter from csv to db
		
	/*	if(customer.getCountry().equals("")) {
			return customer;
		}else{
			return null;
			
		}*/
		return customer;
	}

}
