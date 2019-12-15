package com.bsp.controller;

import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 数据统计
 * @author hayate
 *
 */
@RestController
@Scope(value="prototype")
@RequestMapping("data")
public class DataAnalyzeController {

}
