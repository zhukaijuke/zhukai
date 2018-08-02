package com.zhukai.service;

import java.util.List;
import java.util.Map;

/**
 * 代码生成器Service
 */
public interface SysGeneratorService {

    List<Map<String, Object>> queryList(Map<String, Object> map);

    int queryTotal(Map<String, Object> map);

    Map<String, String> queryTable(String tableName);

    List<Map<String, String>> queryColumns(String tableName);

    /**
     * 生成代码
     */
    void generatorCode(String[] tableNames);
}
