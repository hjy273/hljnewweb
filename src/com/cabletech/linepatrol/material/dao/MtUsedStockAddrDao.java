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
import com.cabletech.linepatrol.material.domain.MtUsedStockAddr;

/**
 * 材料 apply Dao
 * 
 * @author yangjun
 * 
 */
@Repository
public class MtUsedStockAddrDao extends HibernateDao<MtUsedStockAddr, String> {

    private static Logger logger = Logger.getLogger(MtUsedStockAddrDao.class.getName());

    public List getMtUsedStockList(String mtUsedId) {
        String sql = "select materialid,to_char(addressid) as addressid, ";
        sql = sql + " to_char(old_stock) as old_stock, ";
        sql = sql + " to_char(new_stock) as new_stock ";
        sql = sql + " from LP_mt_used_detail t where mtusedid=?";
        logger.info("mtUsedId:" + mtUsedId);
        logger.info("执行sql：" + sql);
        return this.getJdbcTemplate().queryForBeans(sql, mtUsedId);
    }

    public void deleteMtUsedStock(String tid) {
        String sql = "delete from LP_MT_USED_DETAIL where MTUSEDID='" + tid + "' ";
        this.getJdbcTemplate().execute(sql);
    }

    public boolean saveMtUsedStock(String materialId, String addressId, String oldStock,
            String newStock, String seq, String tid) {
        // TODO Auto-generated method stub
        boolean flag = false;

        String sql = "insert into LP_MT_USED_DETAIL( ";
        sql = sql + " ID,MTUSEDID,MATERIALID,ADDRESSID,OLD_STOCK,NEW_STOCK ";
        sql = sql + " ) values( ";
        sql = sql + " '" + seq + "','" + tid + "','" + materialId + "', ";
        sql = sql + " '" + addressId + "','" + oldStock + "', ";
        sql = sql + " '" + newStock + "')";
        UpdateUtil util;
        try {
            logger.info("执行sql：" + sql);
            util = new UpdateUtil();
            util.executeUpdate(sql);
            flag = true;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("执行sql出错：", e);
        }

        return flag;
    }
}
