package com.yueshop.common.to;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author GeneYP
 * @version 1.0
 * @date 2021/12/14 16:20
 * @description com.yueshop.common.to
 */
@Data
public class SkuReductionTo {

    private Long skuId;
    private int fullCount;
    private BigDecimal discount;
    private int countStatus;
    private BigDecimal fullPrice;
    private BigDecimal reducePrice;
    private int priceStatus;
    private List<MemberPrice> memberPrice;
}
