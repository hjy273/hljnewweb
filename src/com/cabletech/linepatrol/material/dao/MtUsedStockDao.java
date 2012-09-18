/***
 *
 * MtUsedDao.java
 * @auther <a href="kww@mail.cabletech.com.cn">kww</a>
 * 2009-10-10
 **/

package com.cabletech.linepatrol.material.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.cabletech.commons.hb.UpdateUtil;
import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.ctf.exception.ServiceException;
import com.cabletech.linepatrol.material.domain.MtUsedStock;

/**
 * ²ÄÁÏ apply Dao
 * 
 * @author yangjun
 * 
 */
@Repository
public class MtUsedStockDao extends HibernateDao<MtUsedStock, String> {

    private static Logger logger = Logger.getLogger(MtUsedStockDao.class.getName());

    public List getMtUsedStockList(String mtUsedId) {
        String sql = "select materialid,t.mtusedid,to_char(last_month_stock) as last_month_stock, ";
        sql = sql + " to_char(new_added_stock) as new_added_stock, ";
        sql = sql + " to_char(new_used_stock) as new_used_stock, ";
        sql = sql + " to_char(real_stock) as real_stock,u.deptid as contractorid ";
        sql = sql + " from LP_mt_used_stock t,LP_MT_USED used,userinfo u ";
        sql = sql + " where t.mtusedid=used.id and used.creator=u.userid ";
        sql = sql + " and t.mtusedid=?";
        logger.info("mtUsedId:" + mtUsedId);
        logger.info("Ö´ÐÐsql£º" + sql);
        return this.getJdbcTemplate().queryForBeans(sql, mtUsedId);
    }

    public void deleteMtUsedStock(String tid) {
        String sql = "delete from LP_MT_USED_STOCK where MTUSEDID='" + tid + "' ";
        this.getJdbcTemplate().execute(sql);
    }

    public void saveMtUsedStock(String materialId, String lastMonthStock, String newAddedStock,
            String newUsedStock, String realStock, String seq, String tid) throws ServiceException {
        String sql = "insert into LP_MT_USED_STOCK( ";
        sql = sql
                + " ID,MTUSEDID,MATERIALID,LAST_MONTH_STOCK,NEW_ADDED_STOCK,NEW_USED_STOCK,REAL_STOCK ";
        sql = sql + " ) values( ";
        sql = sql + " '" + seq + "','" + tid + "','" + materialId + "', ";
        sql = sql + " '" + lastMonthStock + "','" + newAddedStock + "', ";
        sql = sql + " '" + newUsedStock + "','" + realStock + "')";
        this.getJdbcTemplate().execute(sql);
    }
}
