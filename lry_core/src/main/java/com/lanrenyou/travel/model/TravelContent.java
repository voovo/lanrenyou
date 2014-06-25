package com.lanrenyou.travel.model;

import java.util.Date;
import mybatis.framework.core.model.BaseValueObject;

public class TravelContent extends BaseValueObject {
    /**
     * 主键ID
     */
    private Integer id;

    /**
     * 游记ID
     */
    private Integer tid;

    private Date travelDate;

    /**
     * 详细内容
     */
    private String content;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public Date getTravelDate() {
        return travelDate;
    }

    public void setTravelDate(Date travelDate) {
        this.travelDate = travelDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
}