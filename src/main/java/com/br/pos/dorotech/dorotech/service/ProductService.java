package com.br.pos.dorotech.dorotech.service;

import ch.qos.logback.core.util.StringUtil;
import com.br.pos.dorotech.dorotech.exception.BusinessException;
import com.br.pos.dorotech.dorotech.exception.ProductNotFoundException;
import com.br.pos.dorotech.dorotech.model.Product;
import com.br.pos.dorotech.dorotech.repository.ProductRepository;
import io.micrometer.common.util.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProductService {

    private ProductRepository productsRepository;

    public ProductService(ProductRepository productsRepository){
        this.productsRepository = productsRepository;
    }

    public Product saveProduct(Product product){

        if(StringUtils.isBlank(product.getId())){
            throw new BusinessException("The ID field can't be empty");
        }

        if(StringUtils.isBlank(product.getName())){
            throw new BusinessException("The NAME field can't be empty");
        }

        if(product.getPrice() == null || product.getPrice().compareTo(BigDecimal.ZERO) <= 0){
            throw new BusinessException("The PRICE field can't be empty or less than zero");
        }

        if(product.getAmount() == null || product.getAmount() <= 0){
            throw new BusinessException("The AMOUNT field can't be empty or less than zero");
        }

        productsRepository.saveProduct(product);
        return product;
    }

    public Product findProductById(String id){
        Product product = productsRepository.findProductById(id);
        if(product == null){
            throw new ProductNotFoundException(id + "Not found ! ! ");
        }
        return product;
    }

    public List<Product> findAllProducts() {
        return productsRepository.findAllProducts();

    }

}
