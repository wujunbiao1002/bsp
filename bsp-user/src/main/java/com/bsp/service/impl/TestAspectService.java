package com.bsp.service.impl;

import org.springframework.stereotype.Service;

import com.bsp.entity.LendingRecord;
import com.bsp.service.ITestAspectService;

@Service
public class TestAspectService implements ITestAspectService {

	@Override
	public boolean doTest(LendingRecord lendingRecord) {
		return false;
	}

}
