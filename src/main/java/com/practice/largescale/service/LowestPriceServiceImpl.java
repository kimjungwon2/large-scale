package com.practice.largescale.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.practice.largescale.vo.Keyword;
import com.practice.largescale.vo.Product;
import com.practice.largescale.vo.ProductGrp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

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

    @Override
    public Keyword getLowestPriceProductByKeyword(String keyword) {
        Keyword returnInfo = new Keyword();
        List<ProductGrp> tempProductGrp = new ArrayList<>();
        tempProductGrp = GetProductGrpUsingKeyword(keyword);

        returnInfo.setKeyword(keyword);
        returnInfo.setProductGrpList(tempProductGrp);

        return returnInfo;
    }

    private List<ProductGrp> GetProductGrpUsingKeyword(String keyword) {
        List<ProductGrp> returnInfo = new ArrayList<>();
        ProductGrp tempProdGrp = new ProductGrp();

        List<String> prodGrpIdList = new ArrayList<>();
        prodGrpIdList = List.copyOf(myProdPriceRedis.opsForZSet().range(keyword, 0, 9));

        Product tempProduct = new Product();
        List<Product> tempProdList = new ArrayList<>();


        for (final String prodGrpId : prodGrpIdList) {
            Set prodAndPriceList = new HashSet();
            prodAndPriceList = myProdPriceRedis.opsForZSet().rangeWithScores(prodGrpId, 0, 9);
            Iterator<Object> prodPriceObj = prodAndPriceList.iterator();

            while(prodPriceObj.hasNext()){
                ObjectMapper objMapper = new ObjectMapper();

                // {"value":00-1001
                Map<String, String> prodPriceMap = objMapper.convertValue(prodPriceObj.next(),Map.class);

                // Product Obj bind
                tempProduct.setProductId(prodPriceMap.get("value"));
                tempProduct.setPrice(Integer.parseInt(prodPriceMap.get("score")));
                tempProdList.add(tempProduct);
            }

            // 10개 입력 완료.
            tempProdGrp.setProdGrpId(prodGrpId);
            tempProdGrp.setProductList(tempProdList);
            returnInfo.add(tempProdGrp);
        }

        return returnInfo;
    }
}
