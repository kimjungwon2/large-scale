package com.practice.largescale.vo;

import lombok.Data;

import java.util.List;

@Data
public class Keyword {
    private String keyword;
    private List<ProductGrp> productGrpList;
}
