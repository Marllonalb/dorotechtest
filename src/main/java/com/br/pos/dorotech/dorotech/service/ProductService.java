package com.br.pos.dorotech.dorotech.service;

import com.br.pos.dorotech.dorotech.model.Product;
import com.br.pos.dorotech.dorotech.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private ProductRepository productsRepository;

    public ProductService(ProductRepository productsRepository){
        this.productsRepository = productsRepository;
    }

    public void saveProduct(Product products){
        productsRepository.saveProduct(products);
    }

    public Product findProductById(String id){
        return productsRepository.findProductById(id);
    }

    public List<Product> findAllProducts(){
        return productsRepository.findAllProducts();
    }

}
