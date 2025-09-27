package com.ecom.productCatalog.repository;

import com.ecom.productCatalog.model.Cart;

import com.ecom.productCatalog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUser(User user);
}
