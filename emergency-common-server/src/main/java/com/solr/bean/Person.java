package com.solr.bean;

import org.apache.solr.client.solrj.beans.Field;

/**
 * @author LI.HU
 * @date 2019/1/22   16:16
 * <p>Description:
 * 测试使用类
 * </p>
 */

public class Person {

    @Field(value = "id")
    private String id;

    @Field(value = "name")
    private String name;

    @Field(value = "description")
    private String description;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
