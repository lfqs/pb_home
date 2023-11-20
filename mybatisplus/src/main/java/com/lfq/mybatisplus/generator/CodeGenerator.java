package com.lfq.mybatisplus.generator;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig.Builder;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.TemplateType;
import com.baomidou.mybatisplus.generator.config.builder.Entity;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.querys.MySqlQuery;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;
import com.baomidou.mybatisplus.generator.fill.Column;
import com.baomidou.mybatisplus.generator.fill.Property;
import com.baomidou.mybatisplus.generator.keywords.MySqlKeyWordsHandler;
import java.util.Collections;

import com.lfq.common.core.base.BaseController;
import com.lfq.common.core.base.BaseEntity;
import lombok.Data;

/**
 * @作者 lfq
 * @DATE 2023-11-20
 * current year
 **/
@Data
@lombok.Builder
public class CodeGenerator {

    private String author;
    private String module;
    private String tableName;
    private String databaseUrl;
    private String username;
    private String password;
    private String parentPackage;
    private Boolean isExtendsFromBaseEntity;

    /**
     * 避免覆盖掉原有生成的类生成的类 放在mybatisplus子模块下的srcresources目录底下
     * 有需要更新的实体自己在手动覆盖或者挪动过去
     */
    public static void main(String[] args) {
        String databaseUrl = "jdbc:mysql://localhost:3306/pb_home";
        String username = "root";
        String password = "root";

        CodeGenerator generator = CodeGenerator.builder()
            .databaseUrl(databaseUrl)
            .username(username)
            .password(password)
            //作者
            .author("lfq")
            //生成的类 放在orm子模块下的/target/generated-code目录底下
            .module("/mybatisplus/src/main/resources")
            .parentPackage("com.lfq")
            .tableName("sys_menu")
            // 决定是否继承基类
            .isExtendsFromBaseEntity(true)
            .build();
        generator.generateCode();
    }

    public void generateCode() {
        FastAutoGenerator generator = FastAutoGenerator.create(
            new Builder(databaseUrl, username, password)
                .dbQuery(new MySqlQuery())
                .typeConvert(new MySqlTypeConvert())
                .keyWordsHandler(new MySqlKeyWordsHandler()));
        globalConfig(generator);
        packageConfig(generator);
        injectionConfig(generator);
        strategyConfig(generator);
        // 默认的是Velocity引擎模板
        generator.templateEngine(new VelocityTemplateEngine());
        generator.execute();
    }


    /**
     * 为了避免  覆盖掉service中的方法
     * @param generator 生成器
     */
    private void globalConfig(FastAutoGenerator generator) {
        generator.globalConfig(
            builder -> builder
                .fileOverride()
                .outputDir(System.getProperty("user.dir") + module + "/src/main/java")
                .dateType(DateType.ONLY_DATE)
                // 配置生成文件中的author
                .author(author)
                .enableSwagger()
                // 注释日期的格式
                .commentDate("yyyy-MM-dd")
                .build());
    }


    private void packageConfig(FastAutoGenerator generator) {
        generator.packageConfig(builder -> builder
            .parent(parentPackage)
            .moduleName("mybatisplus")
            .entity("entity")
            .service("service")
            .serviceImpl("service.impl")
            .mapper("mapper")
            .xml("mapper.xml")
            .controller("controller")
            .other("other")
            .pathInfo(Collections.singletonMap(OutputFile.mapperXml, System.getProperty("user.dir") + module
                + "/src/main/resources/mapper"))
            .build());
    }

    private void templateConfig(FastAutoGenerator generator) {
        generator.templateConfig(builder -> builder
            .disable(TemplateType.ENTITY)
            .entity("/templates/entity.java")
            .service("/templates/service.java")
            .serviceImpl("/templates/serviceImpl.java")
            .mapper("/templates/mapper.java")
            .mapperXml("/templates/mapper.xml")
            .controller("/templates/controller.java")
            .build());
    }

    private void injectionConfig(FastAutoGenerator generator) {
        generator.injectionConfig(builder -> {
            // Customization
            builder.beforeOutputFile((tableInfo, objectMap) -> System.out.println("tableInfo: " +
                    tableInfo.getEntityName() + " objectMap: " + objectMap.size()))
                .build();
        });
    }


    private void strategyConfig(FastAutoGenerator generator) {
        generator.strategyConfig(builder -> {
            builder
                .enableCapitalMode()
                .enableSkipView()
                .disableSqlFilter()
                .addInclude(tableName);
            entityConfig(builder);
            controllerConfig(builder);
            serviceConfig(builder);
            mapperConfig(builder);
        });
    }


    private void entityConfig(StrategyConfig.Builder builder) {
        Entity.Builder entityBuilder = builder.entityBuilder();
        entityBuilder
            .enableLombok()
            .enableTableFieldAnnotation()
            .enableActiveRecord()
            .logicDeleteColumnName("deleted")
            .naming(NamingStrategy.underline_to_camel)
            .columnNaming(NamingStrategy.underline_to_camel)
            .addTableFills(new Column("create_time", FieldFill.INSERT))
            .addTableFills(new Column("creator_id", FieldFill.INSERT))
            .addTableFills(new Property("updateTime", FieldFill.INSERT_UPDATE))
            .addTableFills(new Property("updaterId", FieldFill.INSERT_UPDATE))
            .idType(IdType.AUTO)
            .formatFileName("%sEntity");

        if (isExtendsFromBaseEntity) {
            entityBuilder
                .superClass(BaseEntity.class)
                .addSuperEntityColumns("creator_id", "create_time", "creator_name", "updater_id", "update_time",
                    "updater_name", "deleted");
        }

        entityBuilder.build();
    }


    private void controllerConfig(StrategyConfig.Builder builder) {
        builder.controllerBuilder()
            .superClass(BaseController.class)
            .enableHyphenStyle()
            .enableRestStyle()
            .formatFileName("%sController")
            .build();
    }

    private void serviceConfig(StrategyConfig.Builder builder) {
        builder.serviceBuilder()
            .formatServiceFileName("%sService")
            .formatServiceImplFileName("%sServiceImpl")
            .build();
    }

    private void mapperConfig(StrategyConfig.Builder builder) {
        builder.mapperBuilder()
            .formatMapperFileName("%sMapper")
            .formatXmlFileName("%sMapper")
            .build();
    }


}
