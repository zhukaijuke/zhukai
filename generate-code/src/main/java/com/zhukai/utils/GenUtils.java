/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.zhukai.utils;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import com.zhukai.entity.ColumnEntity;
import com.zhukai.entity.TableEntity;

/**
 * 代码生成器   工具类
 */
public class GenUtils {

    private static List<String> templates = null;
    private static List<String> ignoreColumn = null;

    static {
        templates = new ArrayList<>();
        templates.add("template/velocity/Entity.java.vm");
        templates.add("template/velocity/Mapper.xml.vm");
        // templates.add("template/velocity/Mapper.java.vm");
        // templates.add("template/velocity/Service.java.vm");
        // templates.add("template/velocity/ServiceImpl.java.vm");
        // templates.add("template/velocity/Controller.java.vm");
        // templates.add("template/velocity/page.vue.vm");
        // templates.add("template/velocity/list.html.vm");
        // templates.add("template/velocity/list.js.vm");
        // templates.add("template/velocity/menu.sql.vm");
        // templates.add("template/velocity/Dao.java.vm");
        // templates.add("template/velocity/Dao.xml.vm");

        ignoreColumn = new ArrayList<>();
        ignoreColumn.add("id");
        ignoreColumn.add("orgId");
        ignoreColumn.add("ocId");
        ignoreColumn.add("recordVersion");
        ignoreColumn.add("createDate");
        ignoreColumn.add("createUserCode");
        ignoreColumn.add("createUserName");
        ignoreColumn.add("lastUpdDate");
        ignoreColumn.add("lastUpdUserCode");
        ignoreColumn.add("lastUpdUserName");
    }

    public static List<String> getTemplates() {
        return templates;
    }

    public static List<String> getIgnoreColumn() {
        return ignoreColumn;
    }

    /**
     * 生成代码
     */
    public static void generatorCode(Map<String, String> table, List<Map<String, String>> columns){
        //配置信息
        Configuration config = getConfig();

        //表信息
        TableEntity tableEntity = new TableEntity();
        tableEntity.setTableName(table.get("tableName"));
        tableEntity.setComments(table.get("tableComment"));
        //表名转换成Java类名
        String className = tableToJava(tableEntity.getTableName(), config.getString("tablePrefix"));
        tableEntity.setClassName(className);
        tableEntity.setClassname(StringUtils.uncapitalize(className));

        boolean hasBigDecimal = false;
        boolean hasDate = false;
        boolean hasStatus = false;
        //列信息
        List<ColumnEntity> columsList = new ArrayList<>();
        List<ColumnEntity> extColumnList = new ArrayList<>();
        for(Map<String, String> column : columns){
            ColumnEntity columnEntity = new ColumnEntity();
            columnEntity.setColumnName(column.get("columnName"));
            columnEntity.setDataType(column.get("dataType"));
            columnEntity.setComments(column.get("columnComment"));
            columnEntity.setExtra(column.get("extra"));

            //列名转换成Java属性名
            String attrName = columnToJava(columnEntity.getColumnName());
            String attrname = StringUtils.uncapitalize(attrName);
            columnEntity.setAttrName(attrName);
            columnEntity.setAttrname(attrname);

            // 是否有status字段
            if ("status".equals(column.get("columnName"))) {
                hasStatus = true;
            }

            //列的数据类型，转换成Java类型
            String attrType = config.getString(columnEntity.getDataType(), "unknowType");
            if ("tinyint".equals(columnEntity.getDataType())) {
                char a = column.get("columnType").charAt(8);
                if (a == '1') {
                    attrType = "Boolean";
                } else {
                    attrType = "Integer";
                }
            }

            columnEntity.setAttrType(attrType);
            if ("BigDecimal".equals(attrType)) {
                hasBigDecimal = true;
            } else if ("Date".equals(attrType)) {
                hasDate = true;
            }
            //是否主键
            if("PRI".equalsIgnoreCase(column.get("columnKey")) && tableEntity.getPk() == null){
                tableEntity.setPk(columnEntity);
            }

            columsList.add(columnEntity);

            if (!getIgnoreColumn().contains(attrname)) {
                extColumnList.add(columnEntity);
            }
        }
        tableEntity.setColumns(columsList);

        //没主键，则第一个字段为主键
        if (tableEntity.getPk() == null) {
            tableEntity.setPk(tableEntity.getColumns().get(0));
        }

        //设置velocity资源加载器
        Properties prop = new Properties();
        prop.put("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        Velocity.init(prop);

        //封装模板数据
        Map<String, Object> map = new HashMap<>();
        map.put("tableName", tableEntity.getTableName());
        map.put("comments", tableEntity.getComments());
        map.put("pk", tableEntity.getPk());
        map.put("className", tableEntity.getClassName());
        map.put("classname", tableEntity.getClassname());
        map.put("pathName", tableEntity.getClassname().toLowerCase());
        map.put("columns", tableEntity.getColumns());
        map.put("extColumns", extColumnList);
        map.put("package", config.getString("package"));
        map.put("author", config.getString("author"));
        map.put("email", config.getString("email"));
        map.put("datetime", DateUtils.format(new Date(), DateUtils.DATE_TIME_PATTERN));
        map.put("hasBigDecimal", hasBigDecimal);
        map.put("hasDate", hasDate);
        map.put("hasStatus", hasStatus);
        VelocityContext context = new VelocityContext(map);
        // OutputStream out = null;
        //获取模板列表
        List<String> templates = getTemplates();
        String rootPath = GenUtils.class.getClassLoader().getResource("").getFile() + "../../java_src/";
        for(String template : templates){
            //渲染模板
        	PrintWriter writer = null;
            try {
                // 新创建的文件目录
                String fileName = getFileName(template, tableEntity.getClassName(), config.getString("package"));
                String filePath = rootPath + fileName;
                File file = new File(filePath);
                // 检查文件目录是否存在, 不存在则创建
                checkParentFileExists(file);
                writer = new PrintWriter(file);
                Template tpl = Velocity.getTemplate(template, "UTF-8");
	            tpl.merge(context, writer);
                writer.flush();
                System.out.println("Generator: java_src\\" + fileName);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("渲染模板失败,表名:" + tableEntity.getTableName() + ", 模板:" + template, e);
            } finally {
                if (writer != null) {
                    writer.close();
                }
            }
        }
    }
    
    /**
     * 检查文件目录是否存在, 不存在则创建
     */
    private static void checkParentFileExists(File file) {
    	if (!file.getParentFile().exists()) {
            boolean result = file.getParentFile().mkdirs();  
            if (!result) {  
                throw new RuntimeException("创建文件夹失败:" + file.getPath());
            }  
        }
    }

    /**
     * 列名转换成Java属性名
     */
    public static String columnToJava(String columnName) {
        return WordUtils.capitalizeFully(columnName, new char[]{'_'}).replace("_", "");
    }

    /**
     * 表名转换成Java类名
     */
    public static String tableToJava(String tableName, String tablePrefix) {
        if(StringUtils.isNotBlank(tablePrefix)){
            tableName = tableName.replace(tablePrefix, "");
        }
        return columnToJava(tableName);
    }

    /**
     * 获取配置信息
     */
    public static Configuration getConfig(){
        try {
            return new PropertiesConfiguration("generator.properties");
        } catch (ConfigurationException e) {
            throw new RuntimeException("获取配置文件失败，", e);
        }
    }

    /**
     * 获取文件名
     */
    public static String getFileName(String template, String className, String packageName){
        /*String packagePath = "";
        if(StringUtils.isNotBlank(packageName)){
            packagePath += packageName.replace(".", File.separator) + File.separator;
        }*/

        if(template.contains("Entity.java.vm")){
            return "code" + File.separator + "entity" + File.separator + className + ".java";
        }

        if(template.contains("Dao.java.vm")){
            return "code" + File.separator + "dao" + File.separator + className + "Dao.java";
        }

        if(template.contains("Dao.xml.vm")){
            return "code" + File.separator + "sqlmap" + File.separator + className + "Dao.xml";
        }
        
        if(template.contains("Mapper.java.vm")){
        	return "code" + File.separator + "dao" + File.separator + className + "Mapper.java";
        }
        
        if(template.contains("Mapper.xml.vm")){
        	return "code" + File.separator + "sqlmap" + File.separator + className + "Mapper.xml";
        }

        if(template.contains("Service.java.vm")){
            return "code" + File.separator + "service" + File.separator + className + "Service.java";
        }

        if(template.contains("ServiceImpl.java.vm")){
            return "code" + File.separator + "service" + File.separator + "impl" + File.separator + className + "ServiceImpl.java";
        }

        if(template.contains("Controller.java.vm")){
            return "code" + File.separator + "controller" + File.separator + className + "Controller.java";
        }
        
        if(template.contains("page.vue.vm")){
        	return "webapp" + File.separator + "page" + File.separator + StringUtils.uncapitalize(className) + "Page.vue";
        }

        if(template.contains("list.html.vm")){
            return "webapp" + File.separator + "page" + File.separator + StringUtils.uncapitalize(className) + ".html";
        }

        if(template.contains("list.js.vm")){
            return "webapp" + File.separator + "js" + File.separator + StringUtils.uncapitalize(className) + ".js";
        }

        if(template.contains("menu.sql.vm")){
            return className.toLowerCase() + "_menu.sql";
        }

        return null;
    }

}
