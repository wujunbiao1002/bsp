package com.bsp.controller;

import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RestController;

/**
 * 首页数据调取
 * @author hayate
 *
 */
@RestController
@Scope(value="prototype")
public class IndexController extends BaseController {

}
