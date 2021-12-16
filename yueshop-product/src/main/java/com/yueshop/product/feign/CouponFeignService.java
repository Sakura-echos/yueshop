package com.yueshop.product.feign;

import com.yueshop.common.to.SkuReductionTo;
import com.yueshop.common.to.SpuBoundTo;
import com.yueshop.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author GeneYP
 * @version 1.0
 * @date 2021/12/14 15:59
 * @description com.yueshop.product.feign
 */
@FeignClient("yueshop-coupon")
public interface CouponFeignService {

    @PostMapping("/coupon/spubounds/save")
    R saveSpuBounds(@RequestBody SpuBoundTo spuBoundTo);

    @PostMapping("/coupon/skufullreduction/saveinfo")
    R saveSkuReduction(@RequestBody SkuReductionTo skuReductionTo);
}
