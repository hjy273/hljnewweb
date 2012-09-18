package com.cabletech.linepatrol.remedy.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.cabletech.commons.hb.QueryUtil;
import com.cabletech.linepatrol.remedy.constant.ExecuteCode;
import com.cabletech.linepatrol.remedy.domain.RemedyApplyItem;

public class RemedyApplyItemDao extends RemedyBaseDao {
    /**
     * ִ���ж��������붨�����б��ж������Ƿ����
     * 
     * @param itemList
     *            List �������붨�����б�
     * @return boolean �������붨�����б��ж������Ƿ����
     * @throws Exception
     */
    public boolean judgeExistItem(List itemList) throws Exception {
        logger(RemedyApplyItemDao.class);
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
     * ִ���ж��������붨���������б��ж����������Ƿ����
     * 
     * @param itemTypeList
     *            List �������붨���������б�
     * @return boolean �������붨���������б��ж����������Ƿ����
     * @throws Exception
     */
    public boolean judgeExistItemType(List itemTypeList) throws Exception {
        logger(RemedyApplyItemDao.class);
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
     * ִ�и��ݲ�ѯ������ȡ�������붨������Ϣ�б�
     * 
     * @param condition
     *            String ��ѯ����
     * @return List �������붨������Ϣ�б�
     * @throws Exception
     */
    public List queryList(String condition) throws Exception {
        // TODO Auto-generated method stub
        logger(RemedyApplyItemDao.class);
        String sql = "select id,remedyid,typename,itemname,item_unit, ";
        sql = sql + " to_char(remedyitemtypeid) as remedyitemtypeid,to_char(item_id) as item_id, ";
        sql = sql
                + " to_char(remedyload) as remedyload,to_char(remedyfee) as remedyfee,to_char(price) as price ";
        sql = sql + " from LINEPATROL_REMEDY_ITEM where 1=1 ";
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
     * ִ�и������������ű����������붨������Ϣ
     * 
     * @param applyId
     *            String ����������
     * @param itemMap
     *            Map �������붨������ϢMap
     * @return String �����������붨������Ϣ����
     * @throws Exception
     */
    public String saveItemList(String applyId, Map itemMap) throws Exception {
        logger(RemedyApplyItemDao.class);
        String[] itemId = (String[]) itemMap.get("item_id");
        String[] itemName = (String[]) itemMap.get("item_name");
        String[] itemType = (String[]) itemMap.get("item_type");
        String[] itemTypeId = (String[]) itemMap.get("item_type_id");
        String[] itemUnit = (String[]) itemMap.get("item_unit");
        String[] itemUnitPrice = (String[]) itemMap.get("item_unit_price");
        String[] itemWorkNumber = (String[]) itemMap.get("item_work_number");
        String[] itemFee = (String[]) itemMap.get("item_fee");
        if (itemName != null && itemName.length > 0) {
            RemedyApplyItem item;
            Object obj;
            String[] seqIds = ora.getSeqs("LINEPATROL_REMEDY_ITEM", "REMEDY_APPLY_ITEM", 20,
                    itemName.length);
            for (int i = 0; i < itemName.length; i++) {
                item = new RemedyApplyItem();
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
                obj = super.insertOneObject(item);
                if (obj == null) {
                    return ExecuteCode.FAIL_CODE;
                }
            }
        }
        return ExecuteCode.SUCCESS_CODE;
    }

    /**
     * ִ�и�������������ɾ���������붨������Ϣ
     * 
     * @param applyId
     *            String ����������
     * @return String ɾ���������붨������Ϣ����
     * @throws Exception
     */
    public String deleteItemList(String applyId) throws Exception {
        logger(RemedyApplyItemDao.class);
        String condition = " and remedyId='" + applyId + "' ";
        try {
            boolean flag = super.deleteAll("RemedyApplyItem", condition);
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
