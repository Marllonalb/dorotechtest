package com.br.pos.dorotech.dorotech.repository;

import com.br.pos.dorotech.dorotech.model.Product;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.ScanEnhancedRequest;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.CreateTableRequest;
import software.amazon.awssdk.services.dynamodb.model.ProvisionedThroughput;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ProductRepository {

    private final DynamoDbTable<Product> table;
    private final DynamoDbEnhancedClient enhancedClient;
    private final DynamoDbClient dynamoDbClient;

    public ProductRepository(DynamoDbClient dynamoDbClient) {
        this.dynamoDbClient = dynamoDbClient;
        this.enhancedClient = DynamoDbEnhancedClient.builder()
                .dynamoDbClient(dynamoDbClient)
                .build();
        this.table = enhancedClient.table("Products", TableSchema.fromBean(Product.class));
    }

    @PostConstruct
    public void initTable() {
        try {
            boolean tableExists = dynamoDbClient.listTables().tableNames().contains("Products");

            if (!tableExists) {
                dynamoDbClient.createTable(CreateTableRequest.builder()
                        .tableName("Products")
                        .keySchema(k -> k.attributeName("id").keyType("HASH"))
                        .attributeDefinitions(a -> a.attributeName("id").attributeType("S"))
                        .provisionedThroughput(ProvisionedThroughput.builder()
                                .readCapacityUnits(5L)
                                .writeCapacityUnits(5L)
                                .build())
                        .build());
                System.out.println("Tabela Products criada com sucesso!");
            } else {
                System.out.println("Tabela Products já existe.");
            }
        } catch (Exception e) {
            System.out.println("Erro ao criar/verificar tabela:");
            e.printStackTrace();
        }
    }

    public void saveProduct(Product product){
        table.putItem(product);
    }

    public List<Product> findAllProducts() {
        return table.scan(ScanEnhancedRequest.builder().build())
                .items()
                .stream()
                .collect(Collectors.toList());
    }

    public Product findProductById(String id){
        return table.getItem(r -> r.key(k -> k.partitionValue(id)));
    }
}
