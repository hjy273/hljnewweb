package com.cabletech.linepatrol.remedy.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.cabletech.commons.hb.QueryUtil;
import com.cabletech.linepatrol.remedy.constant.ExecuteCode;
import com.cabletech.linepatrol.remedy.domain.RemedyBalanceItem;

public class RemedyBalanceItemDao extends RemedyBaseDao {
    /**
     * 执行判断修缮结算定额项列表中定额项是否存在
     * 
     * @param itemList
     *            List 修缮结算定额项列表
     * @return boolean 修缮结算定额项列表中定额项是否存在
     * @throws Exception
     */
    public boolean judgeExistItem(List itemList) throws Exception {
        logger(RemedyBalanceItemDao.class);
        String baseSql = "select id from LINEPATROL_REMEDYITEM where state='1' ";
        String sql = "";
        try {
            QueryUtil queryUtil = new QueryUtil();
            List list;
            for (int i = 0; itemList != null && i < itemList.size(); i++) {
                sql = baseSql + " and id='" + itemList.get(i) + "' ";
                logger.info("Execute sql:" + sql);
                list = queryUtil.queryBeans(sql);
                if (list == null || list.isEmpty()) {
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error(e);
            throw e;
        }
    }

    /**
     * 执行判断修缮结算定额项类型列表中定额项类型是否存在
     * 
     * @param itemTypeList
     *            List 修缮结算定额项类型列表
     * @return boolean 修缮结算定额项类型列表中定额项类型是否存在
     * @throws Exception
     */
    public boolean judgeExistItemType(List itemTypeList) throws Exception {
        logger(RemedyBalanceItemDao.class);
        String baseSql = "select id from LINEPATROL_REMEDYITEM_TYPE where state='1' ";
        String sql = "";
        try {
            QueryUtil queryUtil = new QueryUtil();
            List list;
            for (int i = 0; itemTypeList != null && i < itemTypeList.size(); i++) {
                sql = baseSql + " and id='" + itemTypeList.get(i) + "' ";
                list = queryUtil.queryBeans(sql);
                if (list == null || list.isEmpty()) {
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error(e);
            throw e;
        }
    }

    /**
     * 执行根据查询条件获取修缮结算定额项信息列表
     * 
     * @param condition
     *            String 查询条件
     * @return List 修缮结算定额项信息列表
     * @throws Exception
     */
    public List queryList(String condition) throws Exception {
        // TODO Auto-generated method stub
        logger(RemedyBalanceItemDao.class);
        String sql = "select id,remedyid,typename,itemname,item_unit, ";
        sql = sql + " to_char(remedyitemtypeid) as remedyitemtypeid,to_char(item_id) as item_id, ";
        sql = sql
                + " to_char(remedyload) as remedyload,to_char(remedyfee) as remedyfee,to_char(price) as price ";
        sql = sql + " from LINEPATROL_REMEDY_BAL_ITEM where 1=1 ";
        sql = sql + condition;
        try {
            logger.info("Execute sql:" + sql);
            QueryUtil queryUtil = new QueryUtil();
            List list = queryUtil.queryBeans(sql);
            if (list != null) {
                return list;
            } else {
                return new ArrayList();
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error(e);
            throw e;
        }
    }

    /**
     * 执行根据修缮申请编号保存修缮结算定额项信息
     * 
     * @param applyId
     *            String 修缮申请编号
     * @param balanceId
     *            String 修缮结算单编号
     * @param itemMap
     *            Map 修缮结算定额项信息Map
     * @return String 保存修缮结算定额项信息编码
     * @throws Exception
     */
    public String saveItemList(String applyId, String balanceId, Map itemMap) throws Exception {
        logger(RemedyBalanceItemDao.class);
        String[] itemId = (String[]) itemMap.get("item_id");
        String[] itemName = (String[]) itemMap.get("item_name");
        String[] itemType = (String[]) itemMap.get("item_type");
        String[] itemTypeId = (String[]) itemMap.get("item_type_id");
        String[] itemUnit = (String[]) itemMap.get("item_unit");
        String[] itemUnitPrice = (String[]) itemMap.get("item_unit_price");
        String[] itemWorkNumber = (String[]) itemMap.get("item_work_number");
        String[] itemFee = (String[]) itemMap.get("item_fee");
        if (itemName != null && itemName.length > 0) {
            RemedyBalanceItem item;
            Object obj;
            String[] seqIds = ora.getSeqs("LINEPATROL_REMEDY_BAL_ITEM", "REMEDY_BAL_ITEM", 20,
                    itemName.length);
            for (int i = 0; i < itemName.length; i++) {
                item = new RemedyBalanceItem();
                item.setId(seqIds[i]);
                item.setApplyId(applyId);
                item.setItemId(Integer.valueOf(itemId[i]).intValue());
                item.setItemFee(Float.valueOf(itemFee[i]));
                item.setItemName(itemName[i]);
                item.setItemTypeId(Integer.valueOf(itemTypeId[i]).intValue());
                item.setItemTypeName(itemType[i]);
                item.setItemUnit(itemUnit[i]);
                item.setItemUnitPrice(Float.valueOf(itemUnitPrice[i]));
                item.setItemWorkNumber(Float.valueOf(itemWorkNumber[i]));
                item.setBalanceId(balanceId);
                obj = super.insertOneObject(item);
                if (obj == null) {
                    return ExecuteCode.FAIL_CODE;
                }
            }
        }
        return ExecuteCode.SUCCESS_CODE;
    }

    /**
     * 执行根据修缮申请编号删除修缮结算定额项信息
     * 
     * @param applyId
     *            String 修缮结算编号
     * @return String 删除修缮结算定额项信息编码
     * @throws Exception
     */
    public String deleteItemList(String applyId) throws Exception {
        logger(RemedyBalanceItemDao.class);
        String condition = " and remedyId='" + applyId + "' ";
        try {
            boolean flag = super.deleteAll("RemedyBalanceItem", condition);
            if (flag) {
                return ExecuteCode.SUCCESS_CODE;
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error(e);
            throw e;
        }
        return ExecuteCode.FAIL_CODE;
    }
}
