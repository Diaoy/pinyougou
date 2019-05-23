package com.pinyougou.sellergoods.service;

import com.pinyougou.pojo.TbBrand;
import entity.PageResult;

import java.util.List;

public interface BrandService {

    List<TbBrand> findAll();

    //PageResult findPage(Integer pageNo, Integer pageSize);

    PageResult findPage(Integer pageNo, Integer pageSize, TbBrand tbBrand);

    void add(TbBrand tbBrand);

    TbBrand findById(Long id);

    void update(TbBrand tbBrand);

    void dele(Long[] ids);

}
