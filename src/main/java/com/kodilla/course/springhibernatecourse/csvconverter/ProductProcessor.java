package com.kodilla.course.springhibernatecourse.csvconverter;

import org.springframework.batch.item.ItemProcessor;

public class ProductProcessor implements ItemProcessor<Product, Product> {

    @Override
    public Product process(Product item) throws Exception {
        //cena zostanie podniesiona o 10% i zaokrąglona do liczb całkowitych
        return new Product(item.getId(), item.getQuantity(), (int)(item.getPrice()*1.1));
    }
}
