package com.ubisys.drone.generator.bean;

import lombok.Data;

@Data
public class EntityOfEntity {

    private String entityPackage;

    private String daoPackage;

    private String servicePackage;

    private String serviceImplPackage;

    private String controllerPackage;

    private String author;

    private String className;

    private String classNameLowerCase;

    private String tableName;

    private String description;

    private String primaryKeyType;

}
