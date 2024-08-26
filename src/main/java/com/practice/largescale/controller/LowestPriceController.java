package com.practice.largescale.controller;

import com.practice.largescale.service.LowestPriceService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/")
public class LowestPriceController {

    private LowestPriceService myLowestPriceService;

    @GetMapping("/getZSETValue")
    public Set GetZsetValue(String key){
        return myLowestPriceService.getZsetValue(key);
    }
}
