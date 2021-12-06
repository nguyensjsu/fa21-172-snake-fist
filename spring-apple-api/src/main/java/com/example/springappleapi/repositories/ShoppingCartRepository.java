package com.example.springappleapi.repositories;

import java.util.List;
import java.util.Optional;

import com.example.springappleapi.models.ShoppingCart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    Optional<ShoppingCart> findByUserIdAndActive(long id, boolean active);
    List<ShoppingCart> findAllByUserIdAndActive(long id, boolean active);
}
