package com.xwc1125.common.util.sql;

import com.xwc1125.common.util.string.StringUtils;

import java.util.LinkedHashMap;
import java.util.Map.Entry;

/**
 * <p>
 * Title: SqlUtils
 * </p>
 * <p>
 * Description: TODO(describe the types)
 * </p>
 * <p>
 *
 * </p>
 *
 * @author xwc1125
 * @date 2015-7-13下午2:11:13
 *
 */
public class SqlUtils {
    /**
     * 仅支持字母、数字、下划线、空格、逗号（支持多个字段排序）
     */
    public static String SQL_PATTERN = "[a-zA-Z0-9_\\ \\,]+";

    /**
     * 检查字符，防止注入绕过
     */
    public static String escapeOrderBySql(String value) {
        if (StringUtils.isNotEmpty(value) && !isValidOrderBySql(value)) {
            return StringUtils.EMPTY;
        }
        return value;
    }

    /**
     * 验证 order by 语法是否符合规范
     */
    public static boolean isValidOrderBySql(String value) {
        return value.matches(SQL_PATTERN);
    }
    /**
     * 获取分页查询语句
     *
     * @param sql
     *            正常查询的语句
     * @param offset
     *            位移量
     * @param count
     *            每页显示的数据,0:代表不分页查询
     *
     *            如果offset=0,count=5,则表示查出的数据为1到5
     *
     * @return
     */
    // public static String getPagingSql(String sql, int offset, int count) {
    // if (count == 0) {
    // return sql;
    // }
    // int endOffset = offset + count;
    // String pageSql = "SELECT * FROM ( SELECT A.*, ROWNUM RN FROM (" + sql
    // + ") A  WHERE ROWNUM <= " + endOffset + " )  WHERE RN > "
    // + offset;
    // return pageSql;
    // }

    /**
     * 获取分组查询语句
     *
     * @param sql
     *            :类似from digitsms.zzx_call_notify where called='18611052888'
     *            ORDER BY call_time DESC
     * @param tableName
     * @param partNames
     * @param orderBys
     *            如果为空，则选择使用分组数最多的进行排序
     *
     *            select tableName.*,row_number() over( partition by call_from
     *            order by create_time desc) cn sql
     * @return
     */
    public static String getGroupSql(String tableName, String[] partNames,
                                     String orderBys, String sql) {
        if (partNames.length == 0) {
            String sql2 = "select " + tableName + ".*" + sql;
            return sql2;
        }
        String parts = "";
        for (int i = 0; i < partNames.length; i++) {
            if (i == partNames.length - 1) {
                parts += partNames[i];
            } else {
                parts += partNames[i] + ",";
            }
        }
        String sql2 = null;
        if (StringUtils.isEmpty(orderBys)) {
            sql2 = "select " + tableName + ".*,row_number() over(partition by "
                    + parts + " order by rownum) cn " + sql;
        } else {
            sql2 = "select " + tableName + ".*,row_number() over(partition by "
                    + parts + orderBys + ") cn " + sql;
        }
        return sql2;
    }

    /**
     * 获取分组查询每组的第一条数据
     *
     * @param sql
     *            :类似from digitsms.zzx_call_notify where called='18611052888'
     *            ORDER BY call_time DESC
     * @param tableName
     * @param partNames
     *
     *            select *from ( select tableName.*,row_number() over( partition
     *            by call_from order by create_time desc) cn from
     *            digitsms.zzx_call_notify where called ='13141065140' order by
     *            create_time desc )where cn = 1 order by create_time desc
     * @return
     */
    public static String getGroupOneSql(String tableName, String[] partNames,
                                        String orderBys, String sql) {
        if (partNames.length == 0) {
            String sql2 = "select " + tableName + ".*" + sql;
            return sql2;
        }
        String sql2 = getGroupSql(tableName, partNames, orderBys, sql);
        sql2 = "select *from ( " + sql2 + " )where cn = 1";
        return sql2;
    }

    /**
     *
     * <p>
     * Title: getGroupOneSelectSql
     * </p>
     * <p>
     * Description: select selectParameter from ( select
     * tableName.*,row_number() over( partition by call_from order by
     * create_time desc) cn from digitsms.zzx_call_notify where called
     * ='13141065140' order by create_time desc )where cn = 1 order by
     * create_time desc
     *
     * </p>
     *
     * @param tableName
     * @param selectParameter
     * @param partNames
     * @param orderBys
     * @param sql
     * @return
     * @author xwc1125
     * @date 2015-6-5上午10:47:05
     */
    public static String getGroupOneSelectSql(String tableName,
                                              String selectParameter, String[] partNames, String orderBys,
                                              String sql) {
        if (partNames.length == 0) {
            String sql2 = "select " + tableName + ".*" + sql;
            return sql2;
        }
        String sql2 = getGroupSql(tableName, partNames, orderBys, sql);
        if (StringUtils.isEmpty(selectParameter)) {
            sql2 = "select *from ( " + sql2 + " )where cn = 1";
        } else {
            sql2 = "select " + selectParameter + " from ( " + sql2
                    + " )where cn = 1";
        }
        return sql2;
    }

    /**
     *
     * <p>
     * Title: getMergeIntoSql
     * </p>
     * <p>
     * Description: 有则更新，无则插入
     * </p>
     * <p>
     * 在传约束条件时，需要将数据传两遍<br>
     *
     * MERGE INTO tableName t<br>
     * USING (select count(*) co<br>
     * from tableName<br>
     * where content = ? and imsi = ?) b<br>
     * ON (b.co <> 0)<br>
     * WHEN MATCHED THEN<br>
     * update<br>
     * set t.status = 1, t.update_time = sysdate<br>
     * where t.content = ? and t.imsi = ?<br>
     * WHEN NOT MATCHED THEN<br>
     * insert (add_time, content)<br>
     * values (?, ?)<br>
     *
     * </p>
     *
     * @param tableName
     *            表名
     * @param whereClause
     *            约束条件字段和值
     * @param updateClause
     *            更新的字段和值【如果有更新，需要将判断条件再输一次】
     * @param insertClause
     *            插入的字段和值
     * @return
     *
     * @author xwc1125
     * @date 2016年9月13日 上午10:17:56
     */
    public static String getMergeIntoSql(String tableName,
                                         LinkedHashMap<String, Object> whereClause,
                                         LinkedHashMap<String, Object> updateClause,
                                         LinkedHashMap<String, Object> insertClause) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("MERGE INTO ").append(tableName).append(" t ");
        buffer.append(" USING (");
        buffer.append(" select count(*) co from ").append(tableName);

        StringBuffer whereClauseSql = new StringBuffer(" where ");
        StringBuffer whereClauseSql2 = new StringBuffer(" where ");
        for (Entry<String, Object> entry : whereClause.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue() + "";
            if (StringUtils.isNotEmpty(key) && StringUtils.isNotEmpty(value)) {
                whereClauseSql.append(key + " ").append("=")
                        .append(value + " ");
                whereClauseSql.append(" and ");
                whereClauseSql2.append("t." + key + " ").append("=")
                        .append(value + " ");
                whereClauseSql2.append(" and ");
            }
        }
        String whereClauseStr = whereClauseSql.toString();
        buffer.append(whereClauseStr.substring(0, whereClauseStr.length() - 4));
        buffer.append(" ) b ");
        buffer.append(" ON (b.co <> 0) ");
        if (updateClause != null && updateClause.size() > 0) {
            // 更新
            buffer.append(" WHEN MATCHED THEN ");
            StringBuffer updateClauseSql = new StringBuffer(" update set ");
            for (Entry<String, Object> entry : updateClause.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue() + "";
                if (StringUtils.isNotEmpty(key)
                        && StringUtils.isNotEmpty(value)) {
                    updateClauseSql.append("t." + key + " ").append("=")
                            .append(value + ",");
                }
            }
            String updateClauseStr = updateClauseSql.toString();
            buffer.append(updateClauseStr.substring(0,
                    updateClauseStr.length() - 1));
            String whereClause2Str = whereClauseSql2.toString();
            buffer.append(whereClause2Str.substring(0, whereClause2Str.length() - 4));
        } else {

        }
        if (insertClause != null && insertClause.size() > 0) {
            // 插入
            buffer.append(" WHEN NOT MATCHED THEN ");
            StringBuffer insertClauseKeySql = new StringBuffer(" insert( ");
            StringBuffer insertClauseValueSql = new StringBuffer(" values( ");
            for (Entry<String, Object> entry : insertClause.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue() + "";
                if (StringUtils.isNotEmpty(key)
                        && StringUtils.isNotEmpty(value)) {
                    insertClauseKeySql.append(key + ",");
                    insertClauseValueSql.append(value + ",");
                }
            }
            String insertKeyStr = insertClauseKeySql.toString();
            buffer.append(insertKeyStr.substring(0, insertKeyStr.length() - 1)
                    + ")");
            String insertValuesStr = insertClauseValueSql.toString();
            buffer.append(insertValuesStr.substring(0,
                    insertValuesStr.length() - 1) + ")");
        } else {

        }
        return buffer.toString();
    }
}
