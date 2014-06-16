package com.lanrenyou.dict.model;

import mybatis.framework.core.model.BaseValueObject;

public class DictCity extends BaseValueObject {
    /**
     * 主键ID
     */
    private Integer id;

    /**
     * 编码
     */
    private Integer code;

    /**
     * 城市
     */
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }
}