package com.practice.largescale.controller;

import com.practice.largescale.service.LowestPriceService;
import com.practice.largescale.vo.Product;
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

}
