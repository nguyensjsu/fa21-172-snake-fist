package com.example.springappleapi.repositories;

import com.example.springappleapi.models.PaymentsCommand;

import org.springframework.data.repository.CrudRepository;

public interface PaymentsCommandRepository extends CrudRepository<PaymentsCommand, Integer> {
    
}