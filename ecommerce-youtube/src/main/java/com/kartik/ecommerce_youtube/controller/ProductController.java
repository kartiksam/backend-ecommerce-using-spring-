package com.kartik.ecommerce_youtube.controller;

import com.kartik.ecommerce_youtube.exception.ProductException;
import com.kartik.ecommerce_youtube.model.Product;
import com.kartik.ecommerce_youtube.request.CreateProductRequest;
import com.kartik.ecommerce_youtube.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public ResponseEntity<Page<Product>> findProductByCategoryHandler(
            @RequestParam String category, @RequestParam List<String> color, @RequestParam List<String> size,
            @RequestParam Integer minPrice, @RequestParam Integer maxPrice, @RequestParam Integer minDiscount,
            @RequestParam String sort, @RequestParam String stock, @RequestParam Integer pageNumber,
            @RequestParam Integer pageSize) {

        Page<Product> res = productService.getAllProduct(
                category, color, size, minPrice, maxPrice, minDiscount, sort, stock, pageNumber, pageSize
        );
        System.out.println("complete products");
        return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
    }

    @GetMapping("/products/id/{productId}")
    public ResponseEntity<Product> findProductByIdHandler(@PathVariable Long productId) throws ProductException {
        try {
            Product product = productService.findProductById(productId);
            return new ResponseEntity<>(product, HttpStatus.ACCEPTED);
        } catch (ProductException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }


    @PostMapping("/create")
    public ResponseEntity<String> createProduct(@RequestBody CreateProductRequest request) {
        productService.createProduct(request);
        return ResponseEntity.ok("Product created successfully");
    }


}















