package com.yueshop.product;


import com.yueshop.product.service.AttrGroupService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Array;
import java.util.Arrays;

@SpringBootTest
class YueshopProductApplicationTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    AttrGroupService attrGroupService;

    @Test
    public void test(){
        Long []Path = attrGroupService.getCatelogPath(342L);

        System.out.println("路径为"+Arrays.toString(Path));
    }

}
