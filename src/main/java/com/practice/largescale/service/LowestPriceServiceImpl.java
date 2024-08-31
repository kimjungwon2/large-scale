package com.practice.largescale.service;

import com.practice.largescale.vo.Product;
import com.practice.largescale.vo.ProductGrp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class LowestPriceServiceImpl implements LowestPriceService {

    @Autowired
    private RedisTemplate myProdPriceRedis;

    @Override
    public Set getZsetValue(String key) {
        Set myTempSet = new HashSet();
        myTempSet = myProdPriceRedis.opsForZSet().rangeWithScores(key, 0, 9);
        return myTempSet;
    }

    @Override
    public int setNewProduct(Product newProduct) {
        int rank = 0;
        myProdPriceRedis.opsForZSet().add(newProduct.getProdGrpId(), newProduct.getProductId(), newProduct.getPrice());
        rank = myProdPriceRedis.opsForZSet().rank(newProduct.getProdGrpId(), newProduct.getProductId()).intValue();
        return rank;
    }

    @Override
    public int setnewProductGrp(ProductGrp newProductGrp) {
        List<Product> product = newProductGrp.getProductList();

        String productId = product.get(0).getProductId();
        int price = product.get(0).getPrice();
        myProdPriceRedis.opsForZSet().add(newProductGrp.getProdGrpId(), productId, price);

        return myProdPriceRedis.opsForZSet().zCard(newProductGrp.getProdGrpId()).intValue();
    }

    public int setNewProductGrpToKeyword(String keyword, String prodGrpId, double score) {
        myProdPriceRedis.opsForZSet().add(keyword, prodGrpId, score);
        int rank = myProdPriceRedis.opsForZSet().rank(keyword, prodGrpId).intValue();

        return rank;
    }
}
