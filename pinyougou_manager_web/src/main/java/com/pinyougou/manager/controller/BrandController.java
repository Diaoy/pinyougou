package com.pinyougou.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pojo.TbBrand;
import com.pinyougou.sellergoods.service.BrandService;
import entity.PageResult;
import entity.Result;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/brand")
public class BrandController {

    @Reference
    private BrandService brandService;

    @RequestMapping(path = "/findAll")
    public List<TbBrand> findAll(){
        return brandService.findAll();
    }

    /*@RequestMapping(path = "/findPage")
    public PageResult findPage(Integer pageNo, Integer pageSize){
        return brandService.findPage(pageNo, pageSize);
    }*/
    @RequestMapping(path = "/findPage")
    public PageResult findPage(Integer pageNo, Integer pageSize, @RequestBody TbBrand tbBrand){
        return brandService.findPage(pageNo, pageSize, tbBrand);
    }

    @RequestMapping(path = "/add")
    public Result add(@RequestBody TbBrand tbBrand){
        try {
            brandService.add(tbBrand);
            return new Result(true, "添加成功!");
        } catch(Exception e) {
            e.printStackTrace();
            return new Result(false, "添加失败!");
        }
    }

    @RequestMapping(path = "/findById")
    public TbBrand findById(Long id){
        return brandService.findById(id);
    }

    @RequestMapping(path = "/update")
    public Result update(@RequestBody TbBrand tbBrand){
        try {
            brandService.update(tbBrand);
            return new Result(true, "修改成功!");
        } catch(Exception e) {
            e.printStackTrace();
            return new Result(false, "修改失败!");
        }
    }

    @RequestMapping(path = "/dele")
    public Result dele(Long[] ids){
        try {
            brandService.dele(ids);
            return new Result(true, "删除成功!");
        } catch(Exception e) {
            e.printStackTrace();
            return new Result(false, "删除失败!");
        }
    }
}
