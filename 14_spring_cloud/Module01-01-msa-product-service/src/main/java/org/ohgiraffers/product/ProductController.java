package org.ohgiraffers.product;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/{id}")
    public ProductResponse getProduct(@PathVariable String id) {

        logger.info("[getProduct] id = " + id);

        return new ProductResponse(id, "Sample Product", 100);
    }
}

record ProductResponse(String id, String name, int price) {}
