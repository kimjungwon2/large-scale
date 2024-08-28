package com.practice.largescale.service;

import com.practice.largescale.vo.Product;

import java.util.Set;

public interface LowestPriceService {

    Set getZsetValue(String key);

    int setNewProduct(Product newProduct);
}
