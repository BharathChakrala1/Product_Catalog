package com.ecom.productCatalog.controller;

import com.ecom.productCatalog.model.User;
import com.ecom.productCatalog.model.WishList;
import com.ecom.productCatalog.model.WishListProduct;
import com.ecom.productCatalog.repository.UserRepository;
import com.ecom.productCatalog.repository.WishListProductRepository;
import com.ecom.productCatalog.repository.WishListRepository;
import com.ecom.productCatalog.service.UserService;
import com.ecom.productCatalog.service.WishListService;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/wishlist")
@RequiredArgsConstructor
public class WishListController {

	@Autowired
    private WishListService wishlistService;
	
	@Autowired
    private UserService userService;
	
	@Autowired
    private UserRepository userRepository;
	
	@Autowired
    private WishListRepository wishListRepository;
	
	@Autowired
    private WishListProductRepository wishListProductRepository;

    // ✅ Show wishlist page
    @GetMapping
    public String showWishlist(Model model, Principal principal) {
        String email = principal.getName();
        System.out.println("Fetching wishlist for: " + email);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));

        WishList wishlist = wishListRepository.findByUser(user).orElse(null);

        if (wishlist != null) {
            List<WishListProduct> items = wishListProductRepository.findByWishlist(wishlist);
            model.addAttribute("wishlistItems", items);
        } else {
            model.addAttribute("wishlistItems", new ArrayList<>());
        }

        return "wishlist";
    }

    // ✅ Add product to wishlist
    @PostMapping("/add/{productId}")
    public String addToWishlist(@PathVariable Long productId,
                                @AuthenticationPrincipal UserDetails userDetails) {

        System.out.println("Attempting to add product " + productId + " to wishlist for user: " + userDetails.getUsername());

        User user = userService.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found: " + userDetails.getUsername()));

        try {
            wishlistService.addProductToWishlist(user, productId);
        } catch (Exception e) {
            System.err.println("Error while adding to wishlist: " + e.getMessage());
            e.printStackTrace();
            return "redirect:/wishlist?error=true";
        }

        return "redirect:/wishlist";
    }

    // ✅ Remove product from wishlist
    @PostMapping("/remove/{productId}")
    public String removeFromWishlist(@PathVariable Long productId,
                                     @AuthenticationPrincipal UserDetails userDetails) {

        System.out.println("Removing product " + productId + " from wishlist for user: " + userDetails.getUsername());

        User user = userService.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found: " + userDetails.getUsername()));

        try {
            wishlistService.removeProductFromWishlist(user, productId);
        } catch (Exception e) {
            System.err.println("Error while removing from wishlist: " + e.getMessage());
            e.printStackTrace();
            return "redirect:/wishlist?error=true";
        }

        return "redirect:/wishlist";
    }
}
