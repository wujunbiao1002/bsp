package com.bsp.dao;

import com.bsp.entity.UserInfor;

public interface UserInforMapper extends GenericMapper<UserInfor, String> {
    public int getSexSum(String sex);
}