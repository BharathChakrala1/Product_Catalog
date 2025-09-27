package com.ecom.productCatalog.config;

import com.ecom.productCatalog.model.Category;
import com.ecom.productCatalog.model.Product;
import com.ecom.productCatalog.repository.CategoryRepository;
import com.ecom.productCatalog.repository.ProductRepository;
import com.ecom.productCatalog.repository.CartItemRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

	@Autowired
    private CategoryRepository categoryRepository;
	
	@Autowired
    private ProductRepository productRepository;
	
	@Autowired
    private CartItemRepository cartItemRepository; // 💡 Add this

    @Override
    public void run(String... args) {
        // ⚠️ Step 1: Delete Cart Items to avoid FK constraint errors
        cartItemRepository.deleteAll();

        // ⚠️ Step 2: Now safe to delete products and categories
        productRepository.deleteAll();
        categoryRepository.deleteAll();

        // Step 3: Create categories
        Category electronics = new Category();
        electronics.setName("Electronics");

        Category home = new Category();
        home.setName("Home & Living");

        Category beauty = new Category();
        beauty.setName("Beauty & Personal Care");

        Category toys = new Category();
        toys.setName("Toys & Games");


        Category clothing = new Category();
        clothing.setName("Clothing");

        Category books = new Category();
        books.setName("Books");

        List<Category> savedCategories = categoryRepository.saveAll(Arrays.asList(home,beauty,toys,electronics, clothing, books));
        Category savedElectronics = savedCategories.get(0);
        Category savedClothing = savedCategories.get(1);
        Category savedBooks = savedCategories.get(2);

        // Step 4: Create products
        Product phone = new Product();
        phone.setName("Smartphone");
        phone.setDescription("A modern Android smartphone");
        phone.setPrice(new BigDecimal("30000"));
        phone.setImageUrl("https://m.media-amazon.com/images/I/71w3oJ7aWyL._SL1500_.jpg");
        phone.setStockQuantity(10);
        phone.setCategory(savedElectronics);

        Product laptop = new Product();
        laptop.setName("Laptop");
        laptop.setDescription("Powerful laptop for work and play");
        laptop.setPrice(new BigDecimal("99000"));
        laptop.setImageUrl("https://m.media-amazon.com/images/I/71jG+e7roXL._SL1500_.jpg");
        laptop.setStockQuantity(5);
        laptop.setCategory(savedElectronics);

        Product tshirt = new Product();
        tshirt.setName("T-Shirt");
        tshirt.setDescription("100% Cotton T-Shirt");
        tshirt.setPrice(new BigDecimal("1999"));
        tshirt.setImageUrl("https://m.media-amazon.com/images/I/61W9fZIv7tL._UL1500_.jpg");
        tshirt.setStockQuantity(20);
        tshirt.setCategory(savedClothing);

        Product jeans = new Product();
        jeans.setName("Jeans");
        jeans.setDescription("Comfort fit blue jeans");
        jeans.setPrice(new BigDecimal("3999"));
        jeans.setImageUrl("https://m.media-amazon.com/images/I/71QKQ9mwV7L._UL1500_.jpg");
        jeans.setStockQuantity(15);
        jeans.setCategory(savedClothing);

        Product novel = new Product();
        novel.setName("Novel");
        novel.setDescription("Bestselling fiction novel");
        novel.setPrice(new BigDecimal("499"));
        novel.setImageUrl("https://m.media-amazon.com/images/I/81AFxgbV9cL.jpg");
        novel.setStockQuantity(25);
        novel.setCategory(savedBooks);

        Product guide = new Product();
        guide.setName("Study Guide");
        guide.setDescription("Helpful guide for exam preparation");
        guide.setPrice(new BigDecimal("899"));
        guide.setImageUrl("https://m.media-amazon.com/images/I/91asIC1fRwL.jpg");
        guide.setStockQuantity(30);
        guide.setCategory(savedBooks);


        Product earbuds = new Product();
        earbuds.setName("Wireless Earbuds");
        earbuds.setDescription("Crystal clear sound and deep bass");
        earbuds.setPrice(new BigDecimal("2499"));
        earbuds.setImageUrl("https://m.media-amazon.com/images/I/51HBom8xz7L._SL1500_.jpg");
        earbuds.setStockQuantity(50);
        earbuds.setCategory(savedElectronics);

        Product smartwatch = new Product();
        smartwatch.setName("Smartwatch");
        smartwatch.setDescription("Track your fitness, sleep, and heart rate");
        smartwatch.setPrice(new BigDecimal("6999"));
        smartwatch.setImageUrl("https://m.media-amazon.com/images/I/61b5Z3D8xoL._SL1500_.jpg");
        smartwatch.setStockQuantity(20);
        smartwatch.setCategory(savedElectronics);

        Product hoodie = new Product();
        hoodie.setName("Hoodie");
        hoodie.setDescription("Warm fleece hoodie for winter vibes");
        hoodie.setPrice(new BigDecimal("2499"));
        hoodie.setImageUrl("https://m.media-amazon.com/images/I/51Iq5YfXQ2L._UL1500_.jpg");
        hoodie.setStockQuantity(18);
        hoodie.setCategory(savedClothing);

        Product sneakers = new Product();
        sneakers.setName("Sneakers");
        sneakers.setDescription("Stylish unisex sneakers");
        sneakers.setPrice(new BigDecimal("4599"));
        sneakers.setImageUrl("https://m.media-amazon.com/images/I/61jN2ATiDLL._UL1500_.jpg");
        sneakers.setStockQuantity(12);
        sneakers.setCategory(savedClothing);

        Product biography = new Product();
        biography.setName("Elon Musk Biography");
        biography.setDescription("The life of the real-life Tony Stark");
        biography.setPrice(new BigDecimal("649"));
        biography.setImageUrl("https://m.media-amazon.com/images/I/81gTwYAhU7L.jpg");
        biography.setStockQuantity(40);
        biography.setCategory(savedBooks);

        Product cookbook = new Product();
        cookbook.setName("Indian Cooking Guide");
        cookbook.setDescription("Spice up your kitchen with authentic recipes");
        cookbook.setPrice(new BigDecimal("499"));
        cookbook.setImageUrl("https://m.media-amazon.com/images/I/91VokXkn8hL.jpg");
        cookbook.setStockQuantity(35);
        cookbook.setCategory(savedBooks);

        Product boardGame = new Product();
        boardGame.setName("Strategy Board Game");
        boardGame.setDescription("Fun for the whole family, 2-4 players");
        boardGame.setPrice(new BigDecimal("749"));
        boardGame.setImageUrl("https://m.media-amazon.com/images/I/81TW9PZ5pIL._SL1500_.jpg");
        boardGame.setStockQuantity(15);
        boardGame.setCategory(toys);

        Product puzzle = new Product();
        puzzle.setName("500 Piece Jigsaw Puzzle");
        puzzle.setDescription("Relaxing & challenging puzzle game");
        puzzle.setPrice(new BigDecimal("499"));
        puzzle.setImageUrl("https://m.media-amazon.com/images/I/81dQwQlmAXL._SL1500_.jpg");
        puzzle.setStockQuantity(25);
        puzzle.setCategory(toys);

        Product moisturizer = new Product();
        moisturizer.setName("Hydrating Moisturizer");
        moisturizer.setDescription("Aloe vera infused, suitable for all skin types");
        moisturizer.setPrice(new BigDecimal("399"));
        moisturizer.setImageUrl("https://m.media-amazon.com/images/I/51skJ8DPuJL._SL1000_.jpg");
        moisturizer.setStockQuantity(50);
        moisturizer.setCategory(beauty);

        Product perfume = new Product();
        perfume.setName("Unisex Perfume Spray");
        perfume.setDescription("Long-lasting floral musk fragrance");
        perfume.setPrice(new BigDecimal("999"));
        perfume.setImageUrl("https://m.media-amazon.com/images/I/61XQhA4z9oL._SL1500_.jpg");
        perfume.setStockQuantity(40);
        perfume.setCategory(beauty);

        Product lamp = new Product();
        lamp.setName("LED Desk Lamp");
        lamp.setDescription("Adjustable brightness, perfect for reading or working");
        lamp.setPrice(new BigDecimal("1299"));
        lamp.setImageUrl("https://m.media-amazon.com/images/I/61USxt9kAHL._SL1500_.jpg");
        lamp.setStockQuantity(25);
        lamp.setCategory(home);

        Product bedsheet = new Product();
        bedsheet.setName("Cotton Bedsheet Set");
        bedsheet.setDescription("100% cotton, king size, 3 pieces");
        bedsheet.setPrice(new BigDecimal("1599"));
        bedsheet.setImageUrl("\"https://m.media-amazon.com/images/I/81LzIl9IojL._SL1500_.jpg");
        bedsheet.setStockQuantity(30);
        bedsheet.setCategory(home);




        // Step 5: Save all products
        productRepository.saveAll(List.of(phone, laptop, tshirt, jeans, novel, guide, earbuds, cookbook, biography, sneakers,
                hoodie,smartwatch,boardGame,puzzle,moisturizer,perfume,lamp,bedsheet));
    }
}
