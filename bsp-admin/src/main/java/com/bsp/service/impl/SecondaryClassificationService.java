package com.bsp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bsp.dao.SecondaryClassificationMapper;
import com.bsp.dto.QueryObject;
import com.bsp.entity.SecondaryClassification;
import com.bsp.exceptions.DataUpdateException;
import com.bsp.exceptions.SystemErrorException;
import com.bsp.service.ISecondaryClassificationService;
import com.bsp.utils.Page;

@Service("secondaryClassificationService")
public class SecondaryClassificationService implements ISecondaryClassificationService {
	
	@Autowired
	private SecondaryClassificationMapper secondaryClassificationMapper;
	
	public void setSecondaryClassificationMapper(SecondaryClassificationMapper secondaryClassificationMapper) {
		this.secondaryClassificationMapper = secondaryClassificationMapper;
	}

	@Override
	public Page findByQueryObject(QueryObject queryObject) {
		Integer totalCount = null;
		List<SecondaryClassification> list =  null;
		try {
			totalCount = secondaryClassificationMapper.getTotalCount(queryObject);
			list = secondaryClassificationMapper.selectByQueryObject(queryObject);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SystemErrorException("系统异常，查询数据失败");
		}
		return new Page(list, totalCount, queryObject.getLimit(), queryObject.getPageNumber());
	}

	@Override
	public void add(SecondaryClassification secondaryClassification) {
		try {
			secondaryClassificationMapper.insertSelective(secondaryClassification);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SystemErrorException("系统异常，插入数据失败");
		}
	}

	@Override
	public void delete(Integer id) {
		SecondaryClassification pc = null;
		try {
			pc = secondaryClassificationMapper.selectByPrimaryKey(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SystemErrorException("系统异常，查询记录失败");
		}
		if (pc==null) {
			throw new DataUpdateException("记录不存在");
		}
		pc.setIsDelete(new Byte("1"));
		try {
			secondaryClassificationMapper.updateByPrimaryKeySelective(pc);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SystemErrorException("系统异常，删除失败");
		}
	}

	@Override
	public void update(SecondaryClassification pc) {
		try {
			secondaryClassificationMapper.updateByPrimaryKeySelective(pc);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SystemErrorException("系统异常，更新失败");
		}
	}

	@Override
	public void reuse(Integer id) {
		SecondaryClassification pc = null;
		try {
			pc = secondaryClassificationMapper.selectByPrimaryKey(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SystemErrorException("系统异常，查询记录失败");
		}
		if (pc==null) {
			throw new DataUpdateException("记录不存在");
		}
		pc.setIsDelete(new Byte("0"));
		try {
			secondaryClassificationMapper.updateByPrimaryKeySelective(pc);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SystemErrorException("系统异常，恢复失败");
		}
	}

	@Override
	public List<SecondaryClassification> findByPrimaryClassificationId(Integer pcId) {
		List<SecondaryClassification> list =  null;
		try {
			list = secondaryClassificationMapper.selectByPcId(pcId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SystemErrorException("系统异常，查询记录失败");
		}
		return list;
	}

}
