package com.practice.largescale.controller;

import com.practice.largescale.service.LowestPriceService;
import com.practice.largescale.vo.Keyword;
import com.practice.largescale.vo.NotFoundException;
import com.practice.largescale.vo.ProductGrp;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
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

    @GetMapping("/product2")
    public Set GetZsetValueUsingExController(String key) throws Exception{
        try{
            return myLowestPriceService.getZsetValueWithStatus(key);
        } catch (Exception ex) {
            throw new Exception(ex);
        }
    }

    @GetMapping("/product3")
    public ResponseEntity<Set> GetZsetValueUsingExControllerWithSpecificException(String key) throws Exception{
        Set<String> mySet = new HashSet<>();

        try{
            mySet =  myLowestPriceService.getZsetValueWithStatus(key);
        } catch (NotFoundException ex) {
            throw new NotFoundException(ex.getMessage(), HttpStatus.NOT_FOUND);
        }

        HttpHeaders responseHeaders = new HttpHeaders();

        return new ResponseEntity<Set>(mySet, responseHeaders, HttpStatus.OK);
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
