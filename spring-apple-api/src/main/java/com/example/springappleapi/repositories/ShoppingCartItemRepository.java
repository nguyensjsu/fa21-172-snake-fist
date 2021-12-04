package com.example.springappleapi.repositories;

import java.util.Optional;

import com.example.springappleapi.models.ShoppingCartItem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingCartItemRepository extends JpaRepository<ShoppingCartItem, Long> {
    Optional<ShoppingCartItem> findByShoppingCartIdAndItemId(long shoppingCartId, long itemId);
}
