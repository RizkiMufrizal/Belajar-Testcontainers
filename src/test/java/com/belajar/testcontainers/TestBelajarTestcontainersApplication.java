package com.belajar.testcontainers;

import com.belajar.testcontainers.domain.Product;
import com.belajar.testcontainers.repository.ProductRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

import java.math.BigDecimal;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestConfiguration(proxyBeanMethods = false)
public class TestBelajarTestcontainersApplication {

    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            "postgres:alpine"
    );

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @LocalServerPort
    private Integer port;

    @BeforeAll
    static void beforeAll() {
        postgres.start();
    }

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost:" + port;
    }

    @AfterAll
    static void afterAll() {
        postgres.stop();
    }

    @Autowired
    private ProductRepository productRepository;

    @Test
    void save() {
        var product = Product.builder()
                .name("aqua")
                .price(BigDecimal.valueOf(1000L))
                .quantity(5)
                .build();

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(product)
                .post("/api/products")
                .then()
                .statusCode(200)
                .body("name", Matchers.is(product.getName()));
    }


    @Test
    void testFindOne() {
        var product = Product.builder()
                .name("aqua")
                .price(BigDecimal.valueOf(1000L))
                .quantity(5)
                .build();

        var result = productRepository.save(product);

        RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/products/" + result.getId())
                .then()
                .statusCode(200)
                .body("name", Matchers.is(result.getName()));
    }
}
