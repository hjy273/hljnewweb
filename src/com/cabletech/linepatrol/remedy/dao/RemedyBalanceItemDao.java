package com.cabletech.linepatrol.remedy.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.cabletech.commons.hb.QueryUtil;
import com.cabletech.linepatrol.remedy.constant.ExecuteCode;
import com.cabletech.linepatrol.remedy.domain.RemedyBalanceItem;

public class RemedyBalanceItemDao extends RemedyBaseDao {
    /**
     * ִ���ж����ɽ��㶨�����б��ж������Ƿ����
     * 
     * @param itemList
     *            List ���ɽ��㶨�����б�
     * @return boolean ���ɽ��㶨�����б��ж������Ƿ����
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
     * ִ���ж����ɽ��㶨���������б��ж����������Ƿ����
     * 
     * @param itemTypeList
     *            List ���ɽ��㶨���������б�
     * @return boolean ���ɽ��㶨���������б��ж����������Ƿ����
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
     * ִ�и��ݲ�ѯ������ȡ���ɽ��㶨������Ϣ�б�
     * 
     * @param condition
     *            String ��ѯ����
     * @return List ���ɽ��㶨������Ϣ�б�
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
     * ִ�и������������ű������ɽ��㶨������Ϣ
     * 
     * @param applyId
     *            String ����������
     * @param balanceId
     *            String ���ɽ��㵥���
     * @param itemMap
     *            Map ���ɽ��㶨������ϢMap
     * @return String �������ɽ��㶨������Ϣ����
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
     * ִ�и�������������ɾ�����ɽ��㶨������Ϣ
     * 
     * @param applyId
     *            String ���ɽ�����
     * @return String ɾ�����ɽ��㶨������Ϣ����
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
