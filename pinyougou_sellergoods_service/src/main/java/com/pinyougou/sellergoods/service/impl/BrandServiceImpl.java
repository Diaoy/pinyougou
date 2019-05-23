package com.pinyougou.sellergoods.service.impl;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.abel533.entity.Example;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pinyougou.mapper.TbBrandMapper;
import com.pinyougou.pojo.TbBrand;
import com.pinyougou.sellergoods.service.BrandService;
import entity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    private TbBrandMapper brandMapper;

    @Override
    public List<TbBrand> findAll() {
        return brandMapper.select(null);
    }

    /*@Override
    public PageResult findPage(Integer pageNo, Integer pageSize) {
        PageResult<TbBrand> pageResult = new PageResult<TbBrand>();

        PageHelper.startPage(pageNo, pageSize);
        List<TbBrand> list = brandMapper.select(null);
        pageResult.setRows(list);

        PageInfo<TbBrand> pageInfo = new PageInfo<TbBrand>(list);
        pageResult.setPages(pageInfo.getPages());

        return pageResult;
    }*/

    @Override
    public PageResult findPage(Integer pageNo, Integer pageSize, TbBrand tbBrand) {
        PageResult<TbBrand> pageResult = new PageResult<TbBrand>();

        PageHelper.startPage(pageNo, pageSize);

        Example example = new Example(TbBrand.class);
        Example.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotEmpty(tbBrand.getName())){
            criteria.andLike("name", "%"+ tbBrand.getName().trim()+"%");
        }
        if (StringUtils.isNotEmpty(tbBrand.getFirstChar())){
            criteria.andEqualTo("firstChar", tbBrand.getFirstChar().trim().toUpperCase());
        }
        List<TbBrand> list = brandMapper.selectByExample(example);
        pageResult.setRows(list);

        PageInfo<TbBrand> info = new PageInfo<TbBrand>(list);
        pageResult.setPages(info.getPages());
        return pageResult;
    }

    @Override
    public void add(TbBrand tbBrand) {
        brandMapper.insertSelective(tbBrand);
    }

    @Override
    public TbBrand findById(Long id) {
        return brandMapper.selectByPrimaryKey(id);
    }

    @Override
    public void update(TbBrand tbBrand) {
        brandMapper.updateByPrimaryKeySelective(tbBrand);
    }

    @Override
    public void dele(Long[] ids) {
        List list = Arrays.asList(ids);

        Example example = new Example(TbBrand.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("id", list);

        brandMapper.deleteByExample(example);
    }
}
