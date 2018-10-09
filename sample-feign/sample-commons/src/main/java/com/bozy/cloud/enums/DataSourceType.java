package com.bozy.cloud.enums;

/**
 * Description: 主从库配置
 * Created by tym on 2018/08/27 10:44.
 */
public enum DataSourceType {
    read("read", "从库"),
    write("write", "主库");

    private String type;

    private String name;

    DataSourceType(String type, String name) {
        this.type = type;
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
