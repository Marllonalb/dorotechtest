package com.br.pos.dorotech.dorotech.controller;

import com.br.pos.dorotech.dorotech.model.Product;
import com.br.pos.dorotech.dorotech.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("api/v1/product")
public class ProductController {

    private ProductService productService;
    private ProductController(ProductService productService){
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<String> saveProduct(@RequestBody Product products){
        try {
            productService.saveProduct(products);
            return ResponseEntity.status(HttpStatus.CREATED).body("Produto salvo");
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao salvar produto: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> findProductById(@PathVariable String id) {
        Product product = productService.findProductById(id);
        if (product == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(product);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Product>> findAllProducts(){
        List<Product> products = productService.findAllProducts();
        if (products.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(products);
    }
}
