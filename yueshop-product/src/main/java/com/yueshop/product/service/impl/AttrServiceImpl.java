package com.yueshop.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.yueshop.common.constant.ProductConstant;
import com.yueshop.product.dao.AttrAttrgroupRelationDao;
import com.yueshop.product.dao.AttrGroupDao;
import com.yueshop.product.dao.CategoryDao;
import com.yueshop.product.entity.*;
import com.yueshop.product.service.AttrGroupService;
import com.yueshop.product.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yueshop.common.utils.PageUtils;
import com.yueshop.common.utils.Query;

import com.yueshop.product.dao.AttrDao;
import com.yueshop.product.service.AttrService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;


@Service("attrService")
public class AttrServiceImpl extends ServiceImpl<AttrDao, AttrEntity> implements AttrService {

    @Autowired
    AttrAttrgroupRelationDao relationDao;
    @Autowired
    AttrGroupDao attrGroupDao;
    @Autowired
    CategoryDao categoryDao;
    @Autowired
    AttrGroupService attrGroupService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrEntity> page = this.page(
                new Query<AttrEntity>().getPage(params),
                new QueryWrapper<AttrEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    @Transactional
    public void addAttrVo(AttrVo attr) {
//        1、将属性数据添加到属性表【Attr对象】
        AttrEntity attrEntity = new AttrEntity();
        //使用spring中的bean的工具类来数据拷贝[将相同的属性，复制到另一个对象中]
        BeanUtils.copyProperties(attr, attrEntity);
        //保存   attrEntity 添加后的持久化的对象  - 生成id
        this.save(attrEntity);
        //如果是基本属性才去中间信息表中添加记录   [当前的属性为基本属性并且属性分组不为空的时候  再去添加中间信息]
        if (attrEntity.getAttrType() == ProductConstant.AttrEnum.ATTR_BASE_TYPE.getCode()
                &&attr.getAttrGroupId()!=null) {
//        2、在属性-属性分组中间信息表中添加数据
            AttrAttrgroupRelationEntity relationEntity = new AttrAttrgroupRelationEntity();
            relationEntity.setAttrGroupId(attr.getAttrGroupId());
            relationEntity.setAttrId(attrEntity.getAttrId());
            //保存数据--调用dao层方法进行保存
            relationDao.insert(relationEntity);
        }
    }

    @Override
    public PageUtils queryBaseAttrPage(Map<String, Object> params, String type, Long categoryId) {
        //1、定义条件构造器
        QueryWrapper wrapper = new QueryWrapper<AttrEntity>();
        //2、属性的类型【基本属性1、销售属性0】
        wrapper.eq("attr_type", "base".equalsIgnoreCase(type) ? ProductConstant.AttrEnum.ATTR_BASE_TYPE.getCode() : ProductConstant.AttrEnum.ATTR_SALE_TYPE.getCode());
        //3、分类条件（如果不为空）
        if (categoryId != null && categoryId != 0) {
            wrapper.eq("catelog_id", categoryId);
        }
        //4、key条件查询【id查询  --  key模糊查询】
        String key = (String) params.get("key");
        //判断不为空
        if (!StringUtils.isEmpty(key)) {
            wrapper.eq("attr_id", key);
            wrapper.or();
            wrapper.like("attr_name", key);
        }
        //查询
        IPage<AttrEntity> page = this.page(
                new Query<AttrEntity>().getPage(params),
                wrapper
        );
        //查询每个属性所对应的分组和分类名称，并且赋值
        //1000w 1000个分组  笛卡尔积   产生1000w x 1000  == 1000亿条中间表数据  【不推荐使用多表联结查询】
        //1、上面查询的所有数据都获取出来  -- 获取所有属性【集合】
        PageUtils pageUtils = new PageUtils(page);
        List<AttrEntity> records = page.getRecords();
        //2、遍历--流式遍历--过滤   最终得到  List<AttrRespVo> respVoList
        List<AttrRespVo> respVoList = records.stream().map((attrEntity) -> {
            //AttrRespVo  用于封装返回的数据
            AttrRespVo respVo = new AttrRespVo();
            //将结果赋值到respVo
            BeanUtils.copyProperties(attrEntity, respVo);
            // --查询分组
            if ("base".equalsIgnoreCase(type)) {//判断是否为基本属性，而不是销售属性
                //1从中间信息表中分组id查询出来 [根据属性id查询属性分组]
                AttrAttrgroupRelationEntity relationEntity = relationDao.selectOne(
                        new QueryWrapper<AttrAttrgroupRelationEntity>()
                                .eq("attr_id", attrEntity.getAttrId())
                );
                //先判断是不为空
                if (relationEntity != null) {
                    //2根据分组id查询分组信息
                    AttrGroupEntity groupEntity = attrGroupDao.selectById(relationEntity.getAttrGroupId());
                    //把分组名赋值到respVo
                    respVo.setGroupName(groupEntity.getAttrGroupName());
                }
            }
            // --查询分类
            CategoryEntity categoryEntity = categoryDao.selectById(attrEntity.getCatelogId());
            //把分类名赋值到respVo
            if (categoryEntity != null) {
                respVo.setCatelogName(categoryEntity.getName());
            }
            // --返回respVo
            return respVo;
        }).collect(Collectors.toList());
        //把  List<AttrRespVo> respVoList  赋值到pageUtils
        pageUtils.setList(respVoList);
        //返回
        return pageUtils;
    }

    @Override
    public AttrRespVo getAttrinfoById(Long attrId) {
        //1、定义AttrRespVo对象
        AttrRespVo respVo = new AttrRespVo();
        //把属性数据赋值AttrRespVo对象
        AttrEntity attrEntity = this.getById(attrId);
        BeanUtils.copyProperties(attrEntity, respVo);
        //2、设置分组信息
        if (respVo.getAttrType() == ProductConstant.AttrEnum.ATTR_BASE_TYPE.getCode()) {//判断属性类型是否为基本属性  base
            //获取分组信息  -- 中间信息表 --分组id  --分组信息
            //根据属性id获取该属性分组的中间信息对象
            AttrAttrgroupRelationEntity relationEntity = relationDao.selectOne(
                    new QueryWrapper<AttrAttrgroupRelationEntity>()
                            .eq("attr_id", attrId)
            );
            if (relationEntity != null) {
                //根据属性分组id查询属性分组对象
                AttrGroupEntity groupEntity = attrGroupDao.selectById(relationEntity.getAttrGroupId());
                //获取属性分组名
                respVo.setCatelogName(groupEntity.getAttrGroupName());
                //设置分组id
                respVo.setAttrGroupId(relationEntity.getAttrGroupId());
            }
        }
        //3、设置分类信息
        //获取分类id
        Long categoryId = attrEntity.getCatelogId();
        //根据分类id获取三级分类的路径
        Long[] catelogPath = attrGroupService.getCatelogPath(categoryId);
        respVo.setCatelogPath(catelogPath);
        //根据分类获取分类名
        CategoryEntity categoryEntity = categoryDao.selectById(categoryId);
        if (categoryEntity != null) {
            respVo.setCatelogName(categoryEntity.getName());
        }
        //返回结果
        return respVo;
    }

    //修改属性
    @Override
    @Transactional
    public void updateAttrById(AttrRespVo attr) {
        //1、修改当前属性中的基本数据
        AttrEntity attrEntity = new AttrEntity();
        BeanUtils.copyProperties(attr, attrEntity);
        this.updateById(attrEntity);
        //2、在中间信息表中修改分组关联信息【判断为基本属性才修改修改】
        if (attrEntity.getAttrType() == ProductConstant.AttrEnum.ATTR_BASE_TYPE.getCode()) {
            //中间信息实体对象
            AttrAttrgroupRelationEntity relationEntity = new AttrAttrgroupRelationEntity();
            relationEntity.setAttrId(attr.getAttrId());
            relationEntity.setAttrGroupId(attr.getAttrGroupId());
            //判断中间记录是否存在  【根据属性id和分组id查询是否有记录】
            Integer count = relationDao.selectCount(new QueryWrapper<AttrAttrgroupRelationEntity>()
            .eq("attr_id",attr.getAttrId()));
            //1、存在中间信息记录--修改
            if(count>0){
                //修改中间信息表--根据属性id修改属性分组id
                relationDao.update(relationEntity, new UpdateWrapper<AttrAttrgroupRelationEntity>()
                        .eq("attr_id", attr.getAttrId()));//实体对象，条件构造器对象
            }else{
            //2、不存在中间信息记录--添加
                relationDao.insert(relationEntity);
            }
        }
    }

    //根据属性分组id查询该分组下的所有的关联属性
    @Override
    public List<AttrEntity> getRelationAttr(Long attrgroupId) {
        //1、从中间信息表中获取该属性分组下的中间信息对象集
        List<AttrAttrgroupRelationEntity> relationEntities = relationDao.selectList(new QueryWrapper<AttrAttrgroupRelationEntity>()
                .eq("attr_group_id", attrgroupId));
        //2、获取所有属性id的集合
        List<Long> attrIds =  relationEntities.stream().map((attr)->{
            return  attr.getAttrId();
        }).collect(Collectors.toList());
        //3、根据属性id查询所有的属性对象集
        if(attrIds==null||attrIds.size()==0){
            return null;
        }
        //进行批量查询
        List<AttrEntity> attrEntityList = this.listByIds(attrIds);
        return attrEntityList;
    }

    @Override
    public void deleteRelation(AttrgGroupRalationVo[] vos) {
        //定义一个方法实现一次删除多个数据
        //1、把所有的属性id和属性分组id都抽取到AttrAttrgroupRelationEntity对象中
        List<AttrAttrgroupRelationEntity> relationEntities = Arrays.asList(vos).stream().map((item)->{
            //创建AttrAttrgroupRelationEntity对象
            AttrAttrgroupRelationEntity relationEntity = new AttrAttrgroupRelationEntity();
            //把vo的数据赋值到AttrAttrgroupRelationEntity对象
            BeanUtils.copyProperties(item,relationEntity);
            //返回AttrAttrgroupRelationEntity对象
            return relationEntity;
        }).collect(Collectors.toList());
        //2、根据集合数据执行批量删除
        relationDao.deleteBatchRelations(relationEntities);
    }

    //根据属性分组id--查询该分组还没有关联的属性
    @Override
    public PageUtils getNoattrRelation(Map<String, Object> params, Long attrgroupId) {
        //1、获取当前属性分组的分类id
        AttrGroupEntity groupEntity = attrGroupDao.selectById(attrgroupId);
        Long categoryId = groupEntity.getCatelogId();
        //2、查询当前分类下的所有分组
        List<AttrGroupEntity> groupEntityList = attrGroupDao.selectList(new QueryWrapper<AttrGroupEntity>()
                .eq("catelog_id",categoryId));
        //获取分组的id
        List<Long> groupIdList = groupEntityList.stream().map((item)->{
            return item.getAttrGroupId();
        }).collect(Collectors.toList());
        //3、把所有关联属性查询   从中间信息表中根据分组id查询所有的属性
        List<AttrAttrgroupRelationEntity> relationEntityList = relationDao.selectList(
                new QueryWrapper<AttrAttrgroupRelationEntity>()
                .in("attr_group_id",groupIdList));
        //获取所有的属性id集
        List<Long> attrIdList =  relationEntityList.stream().map((item)->{
            return item.getAttrId();
        }).collect(Collectors.toList());
        //4、移除已经关联的属性
        //定义一个条件构造器，查询属性
        QueryWrapper wrapper = new QueryWrapper<AttrEntity>()
                .eq("catelog_id",categoryId)
                .eq("attr_type",ProductConstant.AttrEnum.ATTR_BASE_TYPE.getCode());
        //判断所有的属性集不为空，
        if(attrIdList!=null&&attrIdList.size()>0){
            wrapper.notIn("attr_id",attrIdList);
        }
        //添加模糊查询的条件
        String key = (String) params.get("key");
        if(!StringUtils.isEmpty(key)){
            wrapper.eq("attr_id",key);
            wrapper.or();
            wrapper.like("attr_name",key);
        }
        //查询
        IPage iPage =  this.page(new Query<AttrEntity>().getPage(params),wrapper);
        PageUtils pageUtils = new PageUtils(iPage);
        //返回
        return pageUtils;
    }

}