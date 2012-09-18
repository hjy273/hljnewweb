package com.cabletech.commons.hb;

import java.sql.*;
import java.util.*;

import org.apache.commons.beanutils.*;

import com.cabletech.commons.beans.BeanUtil;
import com.cabletech.commons.util.DateUtil;
import com.cabletech.utils.*;
import org.apache.log4j.Logger;

public class QueryUtil {
    private Logger logger = Logger.getLogger(QueryUtil.class);

    private HibernateSession sessionFactory;

    private Connection conn = null;

    private Statement stmt = null;

    /**
     * 构造数据库的连接和访问类
     * 
     * @param conn
     *            数据库的连接
     * @throws Exception
     */
    public QueryUtil() throws Exception {
        this.sessionFactory = new HibernateSession();
        this.conn = sessionFactory.currentSession().connection();
        stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
    }

    // 获取表的id字段
    public String GetColumnName() throws SQLException {
        ResultSet rscolumn = conn.getMetaData().getTables("", "", "filepathinfo", null);
        while (rscolumn.next()) {
            logger.info(" GetColumnName  方法：" + rscolumn.getString(1));
        }
        return null;
    }

    /**
     * 执行SQL语句返回字段集
     * 
     * @param sql
     *            SQL语句
     * @return ResultSet 字段集
     */
    public ResultSet executeQuery(String sql) throws SQLException {
        try {
            if (stmt != null) {
                return stmt.executeQuery(sql);
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("executeQuery 方法中返回ResultSet失败：" + e.getMessage());
            if (stmt != null) {
                stmt.close();
            }
            throw e;
        }
    }

    /**
     * 执行查询，返回bean的集合
     * 
     * @param sql
     *            String
     * @return List
     */
    public List queryBeans(String sql) throws Exception {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            RowSetDynaClass rsdc = new RowSetDynaClass(rs);
            // rs.close();
            // stmt.close();
            return rsdc.getRows();
        } catch (Exception ex) {
            ex.printStackTrace();
            // throw ex;
            return null;
        }
    }

    /**
     * 执行查询，返回Vector结果
     * 
     * @param sql
     * @return
     * @throws SQLException
     */

    public Vector executeQueryGetVector(String sql) throws SQLException {
        // return FromResultToVector(executeQueryWithCloseStmt(sql));
        ResultSet result = null;
        try {
            if (stmt != null) {
                result = stmt.executeQuery(sql);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            if (stmt != null) {
                stmt.close();
            }
            throw e;
        }

        if (result == null) {
            return null;
        }
        // 得到结果集的定义结构
        ResultSetMetaData resultmd = result.getMetaData();

        // 得到列的总数
        int colCount = resultmd.getColumnCount();
        // 对放回的全部数据逐一处理
        Vector vTotal = new Vector();
        while (result.next()) {
            Vector vRow = new Vector();
            for (int i = 1; i <= colCount; i++) {
                // 获取字段类型
                int type = resultmd.getColumnType(i);
                // 取得数据
                vRow.add(getValue(result, i, type));
            }
            vTotal.add(vRow);
        }

        stmt.close();
        result.close();
        return vTotal;

    }

    /**
     * 得到结果集Vector,并且已经转化为String,如果为null,侧返回缺省值
     * 
     * @param sql
     * @return
     * @throws SQLException
     */

    public Vector executeQueryGetStringVector(String sql, String strDeault) throws SQLException {

        ResultSet result = null;
        try {
            if (stmt != null) {
                result = stmt.executeQuery(sql);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            if (stmt != null) {
                stmt.close();
            }
            throw e;
        }

        if (result == null) {
            return null;
        }
        // 得到结果集的定义结构
        ResultSetMetaData resultmd = result.getMetaData();

        // 得到列的总数
        int colCount = resultmd.getColumnCount();
        // 对放回的全部数据逐一处理
        Vector vTotal = new Vector();
        while (result.next()) {
            Vector vRow = new Vector();
            for (int i = 1; i <= colCount; i++) {
                // 获取字段类型
                int type = resultmd.getColumnType(i);
                // 取得数据
                vRow.add(ObjectToString(getValue(result, i, type), strDeault));
            }
            vTotal.add(vRow);
        }
        stmt.close();
        result.close();
        return vTotal;
    }

    /**
     * 执行查询，返回Array
     * 
     * @param sql
     * @param strTemp
     * @return
     * @throws SQLException
     */
    public String[][] executeQueryGetArray(String sql, String strTemp) throws SQLException {

        ResultSet result = null;
        try {
            if (stmt != null) {
                result = stmt.executeQuery(sql);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            if (stmt != null) {
                stmt.close();
            }
            throw e;
        }

        if (result == null) {
            return null;
        }
        // 得到结果集的定义结构
        ResultSetMetaData resultmd = result.getMetaData();

        // 得到列的总数
        int colCount = resultmd.getColumnCount();
        // 取得所有的行数
        result.last();
        int iTotalRow = result.getRow();
        result.first();
        if (iTotalRow == 0) {
            result.close();
            result = null;
            return null;
        }
        // 定义结果数组
        String[][] strArray = new String[iTotalRow][colCount];
        // 对放回的全部数据逐一处理
        int intRow = 0;
        do {
            for (int i = 1; i <= colCount; i++) {
                // 获取字段类型
                int type = resultmd.getColumnType(i);
                // 取得数据
                strArray[intRow][i - 1] = this.ObjectToString(getValue(result, i, type), strTemp);
            }
            intRow++;
        } while (result.next());
        stmt.close();
        result.close();
        return strArray;

    }

    /**
     * 从结果集中取得数据
     * 
     * @param rs
     * @param colNum
     * @param type
     * @return
     * @throws SQLException
     */
    private Object getValue(final ResultSet rs, int colNum, int type) throws SQLException {
        switch (type) {
        case Types.ARRAY:
        case Types.BLOB:
        case Types.CLOB:
        case Types.DISTINCT:
        case Types.LONGVARBINARY:
        case Types.VARBINARY:
        case Types.BINARY:
        case Types.REF:
        case Types.STRUCT:
            return null;
        default: {
            Object value = rs.getObject(colNum);
            if (rs.wasNull() || (value == null)) {
                return null;
            } else {
                return value;
            }
        }
        }
    }

    /**
     * 从
     * 
     * @return
     * @throws SQLException
     */
    private Vector FromResultToVector(ResultSet result) throws SQLException {
        if (result == null) {
            return null;
        }
        // 得到结果集的定义结构
        ResultSetMetaData resultmd = result.getMetaData();

        // 得到列的总数
        int colCount = resultmd.getColumnCount();
        // 对放回的全部数据逐一处理
        Vector vTotal = new Vector();
        while (result.next()) {
            Vector vRow = new Vector();
            for (int i = 1; i <= colCount; i++) {
                // 获取字段类型
                int type = resultmd.getColumnType(i);
                // 取得数据
                vRow.add(getValue(result, i, type));
            }
            vTotal.add(vRow);
        }
        result.close();
        return vTotal;
    }

    private Vector FromResultToStringVector(ResultSet result, String strDefalut)
            throws SQLException {
        if (result == null) {
            return null;
        }
        // 得到结果集的定义结构
        ResultSetMetaData resultmd = result.getMetaData();

        // 得到列的总数
        int colCount = resultmd.getColumnCount();
        // 对放回的全部数据逐一处理
        Vector vTotal = new Vector();
        while (result.next()) {
            Vector vRow = new Vector();
            for (int i = 1; i <= colCount; i++) {
                // 获取字段类型
                int type = resultmd.getColumnType(i);
                // 取得数据
                vRow.add(ObjectToString(getValue(result, i, type), strDefalut));
            }
            vTotal.add(vRow);
        }
        result.close();
        return vTotal;
    }

    /**
     * 从
     * 
     * @return
     * @throws SQLException
     */
    private String[][] FromResultToArray(ResultSet result, String strDefalut) throws SQLException {
        if (result == null) {
            return null;
        }
        // 得到结果集的定义结构
        ResultSetMetaData resultmd = result.getMetaData();

        // 得到列的总数
        int colCount = resultmd.getColumnCount();
        // 取得所有的行数
        result.last();
        int iTotalRow = result.getRow();
        result.first();
        if (iTotalRow == 0) {
            result.close();
            result = null;
            return null;
        }
        // 定义结果数组
        String[][] strArray = new String[iTotalRow][colCount];
        // 对放回的全部数据逐一处理
        int intRow = 0;
        do {
            for (int i = 1; i <= colCount; i++) {
                // 获取字段类型
                int type = resultmd.getColumnType(i);
                // 取得数据
                strArray[intRow][i - 1] = this
                        .ObjectToString(getValue(result, i, type), strDefalut);
            }
            intRow++;
        } while (result.next());
        result.close();
        return strArray;
    }

    /**
     * 把对象值转化为字符串，
     * 
     * @param obj
     * @param strDefalut
     * @return
     */
    private String ObjectToString(Object obj, String strDefalut) {
        if (obj == null) {
            return strDefalut;
        }
        // 日期型java.sql.Date
        if (obj instanceof java.sql.Date) {
            return DateUtil.DateToString((java.sql.Date) obj, strDefalut);
            // 日期型java.sql.T
        } else {
            if (obj instanceof Timestamp) {
                return DateUtil.TimestampToString((java.sql.Timestamp) obj, strDefalut);
                // 数字型
            } else {
                if (obj instanceof java.math.BigDecimal) {
                    return ((java.math.BigDecimal) obj).toString();
                } else {
                    return obj.toString();
                }
            }
        }
    }

    /**
     * 报表统计
     * 
     * @param sql
     *            String
     * @return List
     * @throws Exception
     */
    public Vector commonQueryWithFieldNum(String sql, int fieldNum) throws Exception {
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            // RowSetDynaClass rsdc = new RowSetDynaClass(rs);
            Vector resultVct = new Vector();
            while (rs.next()) {
                Vector oneRecordVct = new Vector();
                for (int i = 1; i < fieldNum + 1; i++) {
                    oneRecordVct.add(rs.getString(i));
                }
                resultVct.add(oneRecordVct);
            }

            rs.close();
            stmt.close();
            return resultVct;
        } catch (Exception ex) {
            rs.close();
            stmt.close();
            ex.printStackTrace();
            logger.error("commonQueryWithFieldNum() 报表统计" + ex.getMessage());
            throw ex;
        }
    }

    public void close() {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

}
