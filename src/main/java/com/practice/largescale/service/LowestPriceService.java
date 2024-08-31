package com.practice.largescale.service;

import com.practice.largescale.vo.Product;
import com.practice.largescale.vo.ProductGrp;

import java.util.Set;

public interface LowestPriceService {

    Set getZsetValue(String key);

    int setNewProduct(Product newProduct);

    int setnewProductGrp(ProductGrp newProduct);

    int setNewProductGrpToKeyword(String keyword, String prodGrpId, double score);
}
