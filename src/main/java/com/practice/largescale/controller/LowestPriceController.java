package com.practice.largescale.controller;

import com.practice.largescale.service.LowestPriceService;
import com.practice.largescale.vo.Keyword;
import com.practice.largescale.vo.ProductGrp;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Set;

@RestController
@RequestMapping("/")
public class LowestPriceController {

    private LowestPriceService myLowestPriceService;

    @GetMapping("/product")
    public Set GetZsetValue(String key){
        return myLowestPriceService.getZsetValue(key);
    }



    @GetMapping("/product1")
    public Set GetZsetValueWithStatus(String key){
        try{
            return myLowestPriceService.getZsetValueWithStatus(key);
        } catch (Exception ex) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, ex.getMessage(), ex);
        }
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
