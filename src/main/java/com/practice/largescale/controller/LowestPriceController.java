package com.practice.largescale.controller;

import com.practice.largescale.service.LowestPriceService;
import com.practice.largescale.vo.Keyword;
import com.practice.largescale.vo.Product;
import com.practice.largescale.vo.ProductGrp;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/")
public class LowestPriceController {

    private LowestPriceService myLowestPriceService;

    @GetMapping("/getZSETValue")
    public Set GetZsetValue(String key){
        return myLowestPriceService.getZsetValue(key);
    }

    @PutMapping("/product")
    public int SetNewProduct(@RequestBody Product newProduct){
        return myLowestPriceService.setNewProduct(newProduct);
    }

    @PutMapping("/productGroup")
    public int SetNewProductGroup(@RequestBody ProductGrp newProduct){
        return myLowestPriceService.setnewProductGrp(newProduct);
    }

    @PutMapping("/productGroupToKeyword")
    public int setNewProductGrpToKeyword(String keyword, String prodGrpId, double score){
        return myLowestPriceService.setNewProductGrpToKeyword(keyword, prodGrpId, score);
    }

    @GetMapping("/productPrice/lowest")
    public Keyword getLowestPriceProductByKeyword(String keyword){
        return myLowestPriceService.getLowestPriceProductByKeyword(keyword);
    }


}
