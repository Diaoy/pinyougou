package com.pinyougou.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.abel533.entity.Example;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pinyougou.mapper.TbSpecificationMapper;
import com.pinyougou.mapper.TbSpecificationOptionMapper;
import com.pinyougou.pojo.TbSpecification;
import com.pinyougou.pojo.TbSpecificationOption;
import com.pinyougou.sellergoods.service.SpecificationService;
import entity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import pojogroup.Specification;

import java.util.Arrays;
import java.util.List;

/**
 * 业务逻辑实现
 * @author Steven
 *
 */
@Service
public class SpecificationServiceImpl implements SpecificationService {

	@Autowired
	private TbSpecificationMapper specificationMapper;

	@Autowired
	private TbSpecificationOptionMapper specificationOptionMapper;
	
	/**
	 * 查询全部
	 */
	@Override
	public List<TbSpecification> findAll() {
		return specificationMapper.select(null);
	}

	/**
	 * 按分页查询
	 */
	@Override
	public PageResult findPage(int pageNum, int pageSize,TbSpecification specification) {
		PageResult<TbSpecification> result = new PageResult<TbSpecification>();
        //设置分页条件
        PageHelper.startPage(pageNum, pageSize);

        //构建查询条件
        Example example = new Example(TbSpecification.class);
        Example.Criteria criteria = example.createCriteria();
		
		if(specification!=null){			
						//如果字段不为空
			if (specification.getSpecName()!=null && specification.getSpecName().length()>0) {
				criteria.andLike("specName", "%" + specification.getSpecName() + "%");
			}
	
		}

        //查询数据
        List<TbSpecification> list = specificationMapper.selectByExample(example);
        //返回数据列表
        result.setRows(list);

        //获取总页数
        PageInfo<TbSpecification> info = new PageInfo<TbSpecification>(list);
        result.setPages(info.getPages());
		
		return result;
	}

	/**
	 * 增加
	 */
	@Override
	public void add(Specification specification) {
		//添加TbSpecification的数据
		specificationMapper.insertSelective(specification.getSpecification());
		//添加TbSpecificationOption的数据
		for (TbSpecificationOption tbSpecificationOption : specification.getSpecificationOptionList()) {
			tbSpecificationOption.setSpecId(specification.getSpecification().getId());
			specificationOptionMapper.insertSelective(tbSpecificationOption);
		}
	}

	
	/**
	 * 修改
	 */
	@Override
	public void update(Specification specification){
		//先把之前的选项数据清空
		TbSpecificationOption tbSpecificationOption = new TbSpecificationOption();
		tbSpecificationOption.setSpecId(specification.getSpecification().getId());
		specificationOptionMapper.delete(tbSpecificationOption);

		specificationMapper.updateByPrimaryKeySelective(specification.getSpecification());
		for (TbSpecificationOption specificationOption : specification.getSpecificationOptionList()) {
			specificationOption.setSpecId(specification.getSpecification().getId());
			specificationOptionMapper.insertSelective(specificationOption);
		}
	}
	
	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	@Override
	public Specification getById(Long id){
		Specification specification = new Specification();
		specification.setSpecification(specificationMapper.selectByPrimaryKey(id));

		TbSpecificationOption where = new TbSpecificationOption();
		where.setSpecId(id);
		List<TbSpecificationOption> options = specificationOptionMapper.select(where);
		specification.setSpecificationOptionList(options);

		return specification;
	}

	/**
	 * 批量删除
	 */
	@Override
	public void delete(Long[] ids) {
		//数组转list
        List longs = Arrays.asList(ids);
        //构建查询条件
        Example example = new Example(TbSpecification.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("id", longs);


        //跟据查询条件删除数据
        specificationMapper.deleteByExample(example);

		Example example2 = new Example(TbSpecificationOption.class);
		Example.Criteria criteria1 = example2.createCriteria();
		criteria1.andIn("specId", longs);
		specificationOptionMapper.deleteByExample(example2);
	}
	
	
}
