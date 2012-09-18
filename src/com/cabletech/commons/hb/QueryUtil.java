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
     * �������ݿ�����Ӻͷ�����
     * 
     * @param conn
     *            ���ݿ������
     * @throws Exception
     */
    public QueryUtil() throws Exception {
        this.sessionFactory = new HibernateSession();
        this.conn = sessionFactory.currentSession().connection();
        stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
    }

    // ��ȡ���id�ֶ�
    public String GetColumnName() throws SQLException {
        ResultSet rscolumn = conn.getMetaData().getTables("", "", "filepathinfo", null);
        while (rscolumn.next()) {
            logger.info(" GetColumnName  ������" + rscolumn.getString(1));
        }
        return null;
    }

    /**
     * ִ��SQL��䷵���ֶμ�
     * 
     * @param sql
     *            SQL���
     * @return ResultSet �ֶμ�
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
            logger.error("executeQuery �����з���ResultSetʧ�ܣ�" + e.getMessage());
            if (stmt != null) {
                stmt.close();
            }
            throw e;
        }
    }

    /**
     * ִ�в�ѯ������bean�ļ���
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
     * ִ�в�ѯ������Vector���
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
        // �õ�������Ķ���ṹ
        ResultSetMetaData resultmd = result.getMetaData();

        // �õ��е�����
        int colCount = resultmd.getColumnCount();
        // �ԷŻص�ȫ��������һ����
        Vector vTotal = new Vector();
        while (result.next()) {
            Vector vRow = new Vector();
            for (int i = 1; i <= colCount; i++) {
                // ��ȡ�ֶ�����
                int type = resultmd.getColumnType(i);
                // ȡ������
                vRow.add(getValue(result, i, type));
            }
            vTotal.add(vRow);
        }

        stmt.close();
        result.close();
        return vTotal;

    }

    /**
     * �õ������Vector,�����Ѿ�ת��ΪString,���Ϊnull,�෵��ȱʡֵ
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
        // �õ�������Ķ���ṹ
        ResultSetMetaData resultmd = result.getMetaData();

        // �õ��е�����
        int colCount = resultmd.getColumnCount();
        // �ԷŻص�ȫ��������һ����
        Vector vTotal = new Vector();
        while (result.next()) {
            Vector vRow = new Vector();
            for (int i = 1; i <= colCount; i++) {
                // ��ȡ�ֶ�����
                int type = resultmd.getColumnType(i);
                // ȡ������
                vRow.add(ObjectToString(getValue(result, i, type), strDeault));
            }
            vTotal.add(vRow);
        }
        stmt.close();
        result.close();
        return vTotal;
    }

    /**
     * ִ�в�ѯ������Array
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
        // �õ�������Ķ���ṹ
        ResultSetMetaData resultmd = result.getMetaData();

        // �õ��е�����
        int colCount = resultmd.getColumnCount();
        // ȡ�����е�����
        result.last();
        int iTotalRow = result.getRow();
        result.first();
        if (iTotalRow == 0) {
            result.close();
            result = null;
            return null;
        }
        // ����������
        String[][] strArray = new String[iTotalRow][colCount];
        // �ԷŻص�ȫ��������һ����
        int intRow = 0;
        do {
            for (int i = 1; i <= colCount; i++) {
                // ��ȡ�ֶ�����
                int type = resultmd.getColumnType(i);
                // ȡ������
                strArray[intRow][i - 1] = this.ObjectToString(getValue(result, i, type), strTemp);
            }
            intRow++;
        } while (result.next());
        stmt.close();
        result.close();
        return strArray;

    }

    /**
     * �ӽ������ȡ������
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
     * ��
     * 
     * @return
     * @throws SQLException
     */
    private Vector FromResultToVector(ResultSet result) throws SQLException {
        if (result == null) {
            return null;
        }
        // �õ�������Ķ���ṹ
        ResultSetMetaData resultmd = result.getMetaData();

        // �õ��е�����
        int colCount = resultmd.getColumnCount();
        // �ԷŻص�ȫ��������һ����
        Vector vTotal = new Vector();
        while (result.next()) {
            Vector vRow = new Vector();
            for (int i = 1; i <= colCount; i++) {
                // ��ȡ�ֶ�����
                int type = resultmd.getColumnType(i);
                // ȡ������
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
        // �õ�������Ķ���ṹ
        ResultSetMetaData resultmd = result.getMetaData();

        // �õ��е�����
        int colCount = resultmd.getColumnCount();
        // �ԷŻص�ȫ��������һ����
        Vector vTotal = new Vector();
        while (result.next()) {
            Vector vRow = new Vector();
            for (int i = 1; i <= colCount; i++) {
                // ��ȡ�ֶ�����
                int type = resultmd.getColumnType(i);
                // ȡ������
                vRow.add(ObjectToString(getValue(result, i, type), strDefalut));
            }
            vTotal.add(vRow);
        }
        result.close();
        return vTotal;
    }

    /**
     * ��
     * 
     * @return
     * @throws SQLException
     */
    private String[][] FromResultToArray(ResultSet result, String strDefalut) throws SQLException {
        if (result == null) {
            return null;
        }
        // �õ�������Ķ���ṹ
        ResultSetMetaData resultmd = result.getMetaData();

        // �õ��е�����
        int colCount = resultmd.getColumnCount();
        // ȡ�����е�����
        result.last();
        int iTotalRow = result.getRow();
        result.first();
        if (iTotalRow == 0) {
            result.close();
            result = null;
            return null;
        }
        // ����������
        String[][] strArray = new String[iTotalRow][colCount];
        // �ԷŻص�ȫ��������һ����
        int intRow = 0;
        do {
            for (int i = 1; i <= colCount; i++) {
                // ��ȡ�ֶ�����
                int type = resultmd.getColumnType(i);
                // ȡ������
                strArray[intRow][i - 1] = this
                        .ObjectToString(getValue(result, i, type), strDefalut);
            }
            intRow++;
        } while (result.next());
        result.close();
        return strArray;
    }

    /**
     * �Ѷ���ֵת��Ϊ�ַ�����
     * 
     * @param obj
     * @param strDefalut
     * @return
     */
    private String ObjectToString(Object obj, String strDefalut) {
        if (obj == null) {
            return strDefalut;
        }
        // ������java.sql.Date
        if (obj instanceof java.sql.Date) {
            return DateUtil.DateToString((java.sql.Date) obj, strDefalut);
            // ������java.sql.T
        } else {
            if (obj instanceof Timestamp) {
                return DateUtil.TimestampToString((java.sql.Timestamp) obj, strDefalut);
                // ������
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
     * ����ͳ��
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
            logger.error("commonQueryWithFieldNum() ����ͳ��" + ex.getMessage());
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
