package com.br.pos.dorotech.dorotech.controller;

import com.br.pos.dorotech.dorotech.model.Product;
import com.br.pos.dorotech.dorotech.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
@RestController
@RequestMapping("api/v1/product")
public class ProductController {

    private ProductService productService;
    private ProductController(ProductService productService){
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<Product> saveProduct(@RequestBody Product product){
            Product saveProduct =  productService.saveProduct(product);
            return ResponseEntity.status(HttpStatus.CREATED).body(saveProduct);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> findProductById(@PathVariable String id) {
        Product product = productService.findProductById(id);
        return ResponseEntity.ok(product);
    }

    @GetMapping
    public ResponseEntity<List<Product>> findAllProducts(){
        List<Product> products = productService.findAllProducts();
        return ResponseEntity.ok(products);
    }
}
