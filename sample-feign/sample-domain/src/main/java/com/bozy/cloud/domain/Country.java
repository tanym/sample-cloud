package com.bozy.cloud.domain;

import java.util.Date;

/**
 * Description: 国家实体类
 * Created by tym on 2018/09/03 16:06.
 */
public class Country implements java.io.Serializable{

    private static final long serialVersionUID = -8787776649406255192L;

    private Integer id;
    private Integer parent_id;
    private String full_name;
    private String simple_name;
    private Date create_time;
    private Date modify_time;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParent_id() {
        return parent_id;
    }

    public void setParent_id(Integer parent_id) {
        this.parent_id = parent_id;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getSimple_name() {
        return simple_name;
    }

    public void setSimple_name(String simple_name) {
        this.simple_name = simple_name;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public Date getModify_time() {
        return modify_time;
    }

    public void setModify_time(Date modify_time) {
        this.modify_time = modify_time;
    }
}
