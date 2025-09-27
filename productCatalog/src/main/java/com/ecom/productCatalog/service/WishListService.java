package com.ecom.productCatalog.service;

import com.ecom.productCatalog.model.*;
import com.ecom.productCatalog.repository.*;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WishListService {

	@Autowired
    private WishListRepository wishlistRepository;
	
	@Autowired
    private WishListProductRepository wishlistProductRepository;
	
	@Autowired
    private ProductRepository productRepository;

    // Get or create wishlist for the user
    public WishList getOrCreateWishlist(User user) {
        return wishlistRepository.findByUser(user)
                .orElseGet(() -> {
                    WishList wishlist = new WishList();
                    wishlist.setUser(user);
                    return wishlistRepository.save(wishlist);
                });
    }

    // Add product to wishlist
    public void addProductToWishlist(User user, Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        WishList wishlist = getOrCreateWishlist(user);

        // Optional: check if product already in wishlist
        boolean alreadyExists = wishlistProductRepository
                .existsByWishlistIdAndProductId(wishlist.getId(), productId);

        if (!alreadyExists) {
            WishListProduct wishlistProduct = new WishListProduct();
            wishlistProduct.setWishlist(wishlist);
            wishlistProduct.setProduct(product);
            wishlistProductRepository.save(wishlistProduct);
        }
    }

    // Remove product from wishlist
    public void removeProductFromWishlist(User user, Long productId) {
        WishList wishlist = getOrCreateWishlist(user);
        wishlistProductRepository.deleteByWishlistIdAndProductId(wishlist.getId(), productId);
    }

    // Get wishlist items
    public List<WishListProduct> getWishlistItems(User user) {
        WishList wishlist = getOrCreateWishlist(user);
        return wishlistProductRepository.findByWishlistId(wishlist.getId());
    }
}
