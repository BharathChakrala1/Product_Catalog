package com.ecom.productCatalog.controller;

import com.ecom.productCatalog.model.CartItem;
import com.ecom.productCatalog.model.User;
import com.ecom.productCatalog.service.CartService;
import com.ecom.productCatalog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;

    // 🛒 View Cart Page
    @GetMapping
    public String viewCart(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        Optional<User> userOpt = userService.findByUsername(userDetails.getUsername());
        if (userOpt.isPresent()) {
            
        	User user = userOpt.get();
            List<CartItem> cartItems = cartService.getCartItems(user);
            BigDecimal total = cartService.getTotal(user);

            model.addAttribute("cartItems", cartItems);
            model.addAttribute("total", total);
        } else {
            model.addAttribute("cartItems", null);
            model.addAttribute("total", 0);
        }

        return "cart"; // This maps to cart.html
    }

    // ➕ Add to Cart
    @PostMapping("/add/{productId}")
    public String addToCart(@PathVariable Long productId,
                            @RequestParam(defaultValue = "1") int quantity,
                            @AuthenticationPrincipal UserDetails userDetails) {

        User user = userService.findByUsername(userDetails.getUsername()).orElseThrow();
        cartService.addProductToCart(user, productId, quantity);

        return "redirect:/cart"; // Go to cart page after adding
    }

    // 🔁 Update Quantity
    @PostMapping("/update/{cartItemId}")
    public String updateCartItem(@PathVariable Long cartItemId,
                                 @RequestParam int quantity) {
        cartService.updateCartItem(cartItemId, quantity);
        return "redirect:/cart";
    }

    // ❌ Remove Item
    @PostMapping("/remove/{cartItemId}")
    public String removeCartItem(@PathVariable Long cartItemId) {
        cartService.removeCartItem(cartItemId);
        return "redirect:/cart";
    }


    @PostMapping("/checkout")
    public String checkout(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        // Implement your logic
        return "checkout-success"; // or redirect to another view
    }

}
