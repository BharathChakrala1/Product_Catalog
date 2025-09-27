package com.ecom.productCatalog.repository;

import com.ecom.productCatalog.model.WishList;
import com.ecom.productCatalog.model.WishListProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WishListProductRepository extends JpaRepository<WishListProduct, Long> {
    List<WishListProduct> findByWishlistId(Long wishlistId);
    List<WishListProduct> findByWishlist(WishList wishlist);
    void deleteByWishlistIdAndProductId(Long wishlistId, Long productId);
    boolean existsByWishlistIdAndProductId(Long wishlistId, Long productId);
}
