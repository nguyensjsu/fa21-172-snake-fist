package com.example.springappleapi.repositories;

import java.util.Optional;

import com.example.springappleapi.models.Product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // Optional<Product> findByCardNumber(String cardNumber);
}
