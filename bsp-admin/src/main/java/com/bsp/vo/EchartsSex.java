package com.bsp.vo;

/**
 * @program: bsp
 * @Author：邬俊标
 * @Description：
 * @Date：22:15 2018/9/9
 * @Version: 1.0
 */
public class EchartsSex {
    String sex = null;
    Integer number = 0;

    @Override
    public String toString() {
        return "EchartsSex{" +
                "sex='" + sex + '\'' +
                ", number=" + number +
                '}';
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
}
