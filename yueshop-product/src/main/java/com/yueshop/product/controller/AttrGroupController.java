package com.yueshop.product.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.yueshop.product.entity.AttrEntity;
import com.yueshop.product.entity.AttrGroupWithAttrsVo;
import com.yueshop.product.entity.AttrgGroupRalationVo;
import com.yueshop.product.service.AttrAttrgroupRelationService;
import com.yueshop.product.service.AttrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.yueshop.product.entity.AttrGroupEntity;
import com.yueshop.product.service.AttrGroupService;
import com.yueshop.common.utils.PageUtils;
import com.yueshop.common.utils.R;


/**
 * 属性分组
 *
 * @author Jerry
 * @email Jerrt@gmail.com
 * @date 2021-11-25 17:02:03
 */
@RestController
@RequestMapping("product/attrgroup")
public class AttrGroupController {
    @Autowired
    private AttrGroupService attrGroupService;
    @Autowired
    private AttrService attrService;
    @Autowired
    private AttrAttrgroupRelationService relationService;


    /**
     * 根据分类id查询属性分组和属性数据
     * product/attrgroup/225/withattr
     */
    @GetMapping("/{catelogId}/withattr")
    public R getAttrGroupWithAttrs(@PathVariable("catelogId") Long catelogId) {
        //调用业务方法
        List<AttrGroupWithAttrsVo>  vos = attrGroupService.getAttrGroupWithAttrs(catelogId);
        //保存返回
        return R.ok().put("data",vos);
    }

    /**
     * 添加属性分组的属性关联   属性分组id--属性id
     * /product/attrgroup/attr/relation
     */
    @PostMapping("/attr/relation")
    public R addRelation(@RequestBody List<AttrgGroupRalationVo> vos) {
        //调用业务方法
        relationService.saveBatch(vos);
        //保存返回
        return R.ok();
    }

    /**
     * 根据属性分组id--查询该分组还没有关联的属性
     * /product/attrgroup/1/noattr/relation
     */
    @GetMapping("/{attrgroupId}/noattr/relation")
    public R getNoattrRelation(@PathVariable("attrgroupId") Long attrgroupId
            , @RequestParam Map<String, Object> params) {
        //调用业务方法
        PageUtils page = attrService.getNoattrRelation(params, attrgroupId);
        //保存返回
        return R.ok().put("page", page);
    }


    /**
     * 根据属性分组id-属性id进行的删除操作
     * /product/attrgroup/attr/relation/delete
     */
    @RequestMapping("/attr/relation/delete")
    public R deleteRelation(@RequestBody AttrgGroupRalationVo[] vos) {
        //调用业务方法删除多个数据
        attrService.deleteRelation(vos);
        //保存返回
        return R.ok();
    }

    /**
     * 根据属性分组id查询该分组下的所有的关联属性
     * product/attrgroup/1/attr/relation?t=1639038744603
     */
    @RequestMapping("/{attrgroupId}/attr/relation")
    public R attrRelation(@PathVariable(value = "attrgroupId") Long attrgroupId) {
        //调用业务方法查询属性的集合数据
        List<AttrEntity> attrEntities = attrService.getRelationAttr(attrgroupId);
        //保存返回
        return R.ok().put("data", attrEntities);
    }

    /**
     * 列表:根据分类的id查询属性分组信息
     */
    @RequestMapping("/list/{categoryId}")
    public R list(@RequestParam Map<String, Object> params,
                  @PathVariable(value = "categoryId", required = false) Long categoryId) {
//        PageUtils page = attrGroupService.queryPage(params);
        PageUtils page = attrGroupService.queryPageByCategory(params, categoryId);
        return R.ok().put("page", page);
    }


    /**
     * 分组id信息查询
     */
    @RequestMapping("/info/{attrGroupId}")
    public R info(@PathVariable("attrGroupId") Long attrGroupId) {
        //1、根据分组id查询属性分组
        AttrGroupEntity attrGroup = attrGroupService.getById(attrGroupId);
        //2、获取三级分类id
        Long catelogId = attrGroup.getCatelogId();
        //3、根据三级分类id把父分类的id也查询处理，并且组装成一个long数组【2,6,165】
        Long[] catelogPath = attrGroupService.getCatelogPath(catelogId);
        //4、将long数组赋值到属性分组对象中
        attrGroup.setCatelogPath(catelogPath);
        return R.ok().put("attrGroup", attrGroup);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody AttrGroupEntity attrGroup) {
        attrGroupService.save(attrGroup);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody AttrGroupEntity attrGroup) {
        attrGroupService.updateById(attrGroup);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] attrGroupIds) {
        attrGroupService.removeByIds(Arrays.asList(attrGroupIds));

        return R.ok();
    }

}
