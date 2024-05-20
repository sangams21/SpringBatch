package com.sangam.springbatch.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sangam.springbatch.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer>{

}
