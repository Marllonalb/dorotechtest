package com.br.pos.dorotech.dorotech.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@DynamoDbBean
public class Product {

    private String id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer amount;

    @DynamoDbPartitionKey
    public String getId() {
        return id;
    }
}
