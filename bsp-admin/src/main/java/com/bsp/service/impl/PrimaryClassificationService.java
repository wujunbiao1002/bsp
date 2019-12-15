package com.bsp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bsp.dao.PrimaryClassificationMapper;
import com.bsp.dto.QueryObject;
import com.bsp.entity.PrimaryClassification;
import com.bsp.exceptions.DataUpdateException;
import com.bsp.exceptions.SystemErrorException;
import com.bsp.service.IPrimaryClassificationService;
import com.bsp.utils.Page;

@Service("primaryClassificationService")
public class PrimaryClassificationService implements IPrimaryClassificationService {
	
	@Autowired
	private PrimaryClassificationMapper primaryClassificationMapper;
	
	public void setPrimaryClassificationMapper(PrimaryClassificationMapper primaryClassificationMapper) {
		this.primaryClassificationMapper = primaryClassificationMapper;
	}

	@Override
	public Page findByQueryObject(QueryObject queryObject) {
		Integer totalCount = null;
		List<PrimaryClassification> list =  null;
		try {
			totalCount = primaryClassificationMapper.getTotalCount(queryObject);
			list = primaryClassificationMapper.selectByQueryObject(queryObject);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SystemErrorException("系统异常，查询数据失败");
		}
		return new Page(list, totalCount, queryObject.getLimit(), queryObject.getPageNumber());
	}

	@Override
	public void add(PrimaryClassification primaryClassification) {
		try {
			primaryClassificationMapper.insertSelective(primaryClassification);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SystemErrorException("系统异常，插入数据失败");
		}
	}

	@Override
	public void delete(Integer id) {
		PrimaryClassification pc = null;
		try {
			pc = primaryClassificationMapper.selectByPrimaryKey(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SystemErrorException("系统异常，查询记录失败");
		}
		if (pc==null) {
			throw new DataUpdateException("记录不存在");
		}
		pc.setIsDelete(new Byte("1"));
		try {
			primaryClassificationMapper.updateByPrimaryKeySelective(pc);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SystemErrorException("系统异常，删除失败");
		}
	}
	
	@Override
	public void reuse(Integer id) {
		PrimaryClassification pc = null;
		try {
			pc = primaryClassificationMapper.selectByPrimaryKey(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SystemErrorException("系统异常，查询记录失败");
		}
		if (pc==null) {
			throw new DataUpdateException("记录不存在");
		}
		pc.setIsDelete(new Byte("0"));
		try {
			primaryClassificationMapper.updateByPrimaryKeySelective(pc);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SystemErrorException("系统异常，恢复失败");
		}
	}
	
	@Override
	public void update(PrimaryClassification pc) {
		try {
			primaryClassificationMapper.updateByPrimaryKeySelective(pc);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SystemErrorException("系统异常，更新失败");
		}
	}

	@Override
	public List<PrimaryClassification> findAll() {
		List<PrimaryClassification> list;
		try {
			list = primaryClassificationMapper.selectAll();
		} catch (Exception e) {
			e.printStackTrace();
			throw new SystemErrorException("系统异常，获取一级分类数据失败");
		}
		return list;
	}

}
