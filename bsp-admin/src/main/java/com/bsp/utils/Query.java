package com.bsp.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * POST参数封装类
 */
public class Query extends LinkedHashMap<String, Object> {
	private static final long serialVersionUID = 1L;
	//当前页码
    private int page;
    //每页条数
    private int limit;

    public Query(Map<String, Object> params){
        this.putAll(params);

         String page1  = (String) params.get("page");
         String  limit1 =(String) params.get("limit");
        //分页参数
        if(page1 != null && limit1 != null) {
            this.page = Integer.parseInt(params.get("page").toString());
            this.limit = Integer.parseInt(params.get("limit").toString());
            this.put("page", page);
            this.put("offset", (page - 1) * limit);
        }
        if(limit1 != null) {
            this.limit = Integer.parseInt(params.get("limit").toString());
            this.put("limit", limit);
        }

        //防止SQL注入（因为sidx、order是通过拼接SQL实现排序的，会有SQL注入风险）
        String sidx = (String)params.get("sidx");
        String order = (String)params.get("order");
        if(StringUtils.isNotBlank(sidx)){
            this.put("sidx", SQLFilter.sqlInject(sidx));
        }
        if(StringUtils.isNotBlank(order)){
            this.put("order", SQLFilter.sqlInject(order));
        }

    }


    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
