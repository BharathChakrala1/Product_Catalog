package com.ecom.productCatalog.service;

import com.ecom.productCatalog.model.*;
import com.ecom.productCatalog.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    // ✅ Get or create a cart for user
    public Cart getCartByUser(User user) {
        return cartRepository.findByUser(user).orElseGet(() -> cartRepository.save(new Cart(user)));
    }

    // ✅ Fetch cart items for a user
    public List<CartItem> getCartItems(User user) {
   
        Cart cart = getCartByUser(user);
        return cartItemRepository.findByCart(cart);
    }

    // ✅ Add product to cart using User object (recommended)
    public void addProductToCart(User user, Long productId, int quantity) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));
        Cart cart = getCartByUser(user);
        CartItem item = cartItemRepository.findByCartAndProduct(cart, product).orElse(new CartItem(cart, product, 0));
        item.setQuantity(item.getQuantity() + quantity);
        cartItemRepository.save(item);
    }

    // ✅ (Alternative) Add product using only IDs
    public void addProductToCart(Long userId, Long productId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        addProductToCart(user, productId, 1); // default quantity = 1
    }

    // ✅ Update quantity of a specific cart item
    public void updateCartItem(Long cartItemId, int quantity) {
        CartItem item = cartItemRepository.findById(cartItemId).orElseThrow(() -> new RuntimeException("Cart item not found"));
        item.setQuantity(quantity);
        cartItemRepository.save(item);
    }

    // ✅ Remove cart item
    public void removeCartItem(Long cartItemId) {
        cartItemRepository.deleteById(cartItemId);
    }

    // ✅ Calculate total cost of user's cart
    public BigDecimal getTotal(User user) {
        return getCartItems(user).stream()
                .map(item -> item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
