package com.yueshop.common.to;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author GeneYP
 * @version 1.0
 * @date 2021/12/14 16:05
 * @description com.yueshop.common.to
 */
@Data
public class SpuBoundTo {
    private Long spuId;
    private BigDecimal buyBounds;
    private BigDecimal growBounds;
}
