package com.bsp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bsp.dao.LoanableBookMapper;
import com.bsp.dao.NewsMapper;
import com.bsp.dao.PrimaryClassificationMapper;
import com.bsp.dao.SecondaryClassificationMapper;
import com.bsp.dto.LoanableBookQueryObject;
import com.bsp.dto.QueryObject;
import com.bsp.entity.LendingRecord;
import com.bsp.entity.LoanableBook;
import com.bsp.entity.News;
import com.bsp.entity.PrimaryClassification;
import com.bsp.entity.SecondaryClassification;
import com.bsp.service.ITestAspectService;
import com.bsp.utils.Result;


@RestController
@RequestMapping("/test")
public class TestController {
	
	@Autowired private SecondaryClassificationMapper secondaryClassificationMapper;
	@Autowired private PrimaryClassificationMapper primaryClassificationMapper;
	@Autowired private LoanableBookMapper loanableBookMapper;
	@Autowired private NewsMapper newsMapper;
	@Autowired private ITestAspectService testAspectService;
	
	public void setTestAspectService(ITestAspectService testAspectService) {
		this.testAspectService = testAspectService;
	}

	public void setNewsMapper(NewsMapper newsMapper) {
		this.newsMapper = newsMapper;
	}

	public void setLoanableBookMapper(LoanableBookMapper loanableBookMapper) {
		this.loanableBookMapper = loanableBookMapper;
	}

	public void setPrimaryClassificationMapper(PrimaryClassificationMapper primaryClassificationMapper) {
		this.primaryClassificationMapper = primaryClassificationMapper;
	}

	public void setSecondaryClassificationMapper(SecondaryClassificationMapper secondaryClassificationMapper) {
		this.secondaryClassificationMapper = secondaryClassificationMapper;
	}

	@RequestMapping("news")
	public Result testNewsMapping() {
		Result rs = Result.success();
		Integer count = newsMapper.getNewMsgAmount("f01c3b8acd114a689e237564d925789b");
		QueryObject qo = new QueryObject();
		qo.setSearch("f01c3b8acd114a689e237564d925789b");
		List<News> list = newsMapper.selectByQueryObject(qo);
		rs.put("count", count);
		rs.put("list", list);
		return rs;
	}
	
	@RequestMapping("sc")
	public Result testSecondaryClassificationMapper() {
		Result rs = Result.success();
		List<SecondaryClassification> list = secondaryClassificationMapper.selectAll();
		rs.put("list", list);
		return rs;
	}
	
	@RequestMapping("pc")
	public Result testPrimaryClassificationMapper() {
		Result rs = Result.success();
		List<PrimaryClassification> list = primaryClassificationMapper.selectAll();
		rs.put("list", list);
		return rs;
	}
	
	@RequestMapping("lb")
	public Result testLoanableBookMapper() {
		LoanableBookQueryObject queryObject = new LoanableBookQueryObject();
		/*PrimaryClassification pc = new PrimaryClassification();
		pc.setPcId(1);*/
		/*queryObject.setPrimaryClassification(pc);
		SecondaryClassification sc = new SecondaryClassification();
		sc.setScId(8);
		queryObject.setSecondaryClassification(sc);*/
		queryObject.setSearch("哲学");
		/*queryObject.setPageNumber(2);
		queryObject.setLimit(2);*/
		Integer count = loanableBookMapper.getTotalCount(queryObject);
		List<LoanableBook> list = loanableBookMapper.selectByQueryObject(queryObject);
		Result rs = Result.success();
		rs.put("count", count);
		rs.put("list",list);
		rs.put("size", list.size());
		return rs;
	}
	
	@RequestMapping("aspect")
	public Result testAspect() {
		LendingRecord lr = new LendingRecord();
		lr.setAmount(354644455);
		testAspectService.doTest(lr);
		return Result.success();
	}
	
}
