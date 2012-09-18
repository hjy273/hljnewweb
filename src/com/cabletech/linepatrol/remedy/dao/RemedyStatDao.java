package com.cabletech.linepatrol.remedy.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.commons.beanutils.DynaBean;
import org.apache.log4j.Logger;

import com.cabletech.commons.hb.HibernateDaoSupport;
import com.cabletech.commons.hb.QueryUtil;

public class RemedyStatDao extends HibernateDaoSupport {
    private static Logger logger = Logger.getLogger(RemedyStatDao.class.getName());

    /**
     * 通过区域查询统计
     * 
     * @return
     */
    public List statRemedyByTown(String month) {
        List list = new ArrayList();
        Map townMap = getTownMap();
        list.add(townMap);

        Map labelMapFromDB = getLabelMap(month);
        Map labelMap = new HashMap();
        Map scaleMapFromDB = getScaleMap(month);
        Map scaleMap = new HashMap();

        Iterator iterator1 = townMap.keySet().iterator();
        while (iterator1.hasNext()) {
            String townId = (String) iterator1.next();
            String label = (String) labelMapFromDB.get(townId);
            if (label == null || label.equals("null")) {
                labelMap.put(townId, "0");
            } else {
                labelMap.put(townId, label);
            }
        }
        list.add(labelMap);

        Iterator iterator2 = townMap.keySet().iterator();
        while (iterator2.hasNext()) {
            String townId = (String) iterator2.next();
            String scale = (String) scaleMapFromDB.get(townId);
            scaleMap.put(townId, scale);
        }
        list.add(scaleMap);
        return list;
    }

    /**
     * 统计各区修缮迁改费用
     * 
     * @return
     */
    private Map getLabelMap(String month) {
        Map labelMap = new HashMap();
        List list = new ArrayList();
        List townIdList = getTownIdList();
        for (int i = 0; i < townIdList.size(); i++) {
            /*
             * String sql = "select sum(t.totalfee) as label from
             * linepatrol_remedy t,linepatrol_remedy_strike lrt " + "where
             * t.id=lrt.remedyid and to_char(t.remedydate,'yyyy-MM')='" + month + "' " +
             * "and t.townid='" + townIdList.get(i) + "' and lrt.acceptor is not
             * null order by t.townid";
             */
            String sql = "select sum(lrt.totalfee) as label "
                    + "from linepatrol_remedy t,linepatrol_remedy_balance lrt "
                    + "where t.id=lrt.remedyid and to_char(t.remedydate,'yyyy-MM')='" + month
                    + "' " + " and t.townid='" + townIdList.get(i)
                    + "' and t.state='401' order by t.townid";
            logger.info("getLabelMap_sql: " + sql);
            try {
                QueryUtil query = new QueryUtil();
                list = query.queryBeans(sql);
            } catch (Exception e) {
                logger.error("query labelMap error::::::" + e);
            }
            String totalfee = ((BasicDynaBean) list.get(0)).get("label") + "";
            labelMap.put(townIdList.get(i), totalfee);
            logger.info(labelMap);
        }
        // 测试数据
        /*
         * labelMap.put("1","91279.75"); labelMap.put("3","83956.75");
         * labelMap.put("2","243908.33");
         */
        return labelMap;
    }

    /**
     * 统计占总费用比例
     * 
     * @return
     */
    private Map getScaleMap(String month) {
        Map scaleMap = new HashMap();
        Map labelMap = getLabelMap(month);
        float labelTotal = 0;
        String scale;
        Iterator iterator1 = labelMap.keySet().iterator();
        while (iterator1.hasNext()) {
            String townId = (String) iterator1.next();
            String label = (String) labelMap.get(townId);
            if (label == null || label.equals("null")) {
                labelTotal += 0;
            } else {
                labelTotal += Float.parseFloat(label);
            }

        }
        Iterator iterator2 = labelMap.keySet().iterator();
        while (iterator2.hasNext()) {
            String townId = (String) iterator2.next();
            String label = (String) labelMap.get(townId);
            if (label == null || label.equals("null")) {
                scale = "0%";
            } else {
                java.text.DecimalFormat df = new java.text.DecimalFormat("#0.00");
                scale = df.format(Float.parseFloat(label) / labelTotal * 100) + "%";
            }
            logger.info("计算各区县迁改费用占总费用的比例:" + townId + ":" + scale);
            scaleMap.put(townId, scale);
        }
        // 测试数据
        /*
         * scaleMap.put("2", "28.90%"); scaleMap.put("3", "9.95%");
         * scaleMap.put("1", "10.82%");
         */
        return scaleMap;
    }

    /**
     * 按每个区县各个项统计
     * 
     * @param statRemedyBean
     * @return
     */
    public List statRemedyDetailByTown(String townId, String month) {
        List list = new ArrayList();
        Map itemMap = getItemMap(month);
        list.add(itemMap);

        Map labelMapFromDB = getDetailLabelMapByTownId(month, townId);
        Map labelMap = new HashMap();
        Map scaleMapFromDB = getDetailScaleMap(month, townId);
        Map scaleMap = new HashMap();

        Iterator iterator1 = itemMap.keySet().iterator();
        while (iterator1.hasNext()) {
            String id = (String) iterator1.next();
            String label = (String) labelMapFromDB.get(id);
            if (label == null || "null".equals(label)) {
                labelMap.put(id, "0");
            } else {
                labelMap.put(id, label);
            }
        }
        list.add(labelMap);

        Iterator iterator2 = itemMap.keySet().iterator();
        while (iterator2.hasNext()) {
            String id = (String) iterator2.next();
            String scale = (String) scaleMapFromDB.get(id);
            scaleMap.put(id, scale);
        }
        list.add(scaleMap);

        Map townMap = getTownMap();
        list.add(townMap);
        return list;
    }

    /**
     * 按区县、项目统计占总费用比例
     * 
     * @return
     */
    private Map getDetailScaleMap(String month, String townId) {
        Map scaleMap = new HashMap();
        Map detailLabelMap = getDetailLabelMapByTownId(month, townId);
        float detailLabelTotal = 0;
        String scale;
        // 计算修缮迁改费用
        Iterator iterator1 = detailLabelMap.keySet().iterator();
        while (iterator1.hasNext()) {
            String itemId = (String) iterator1.next();
            String detailLabel = (String) detailLabelMap.get(itemId);
            if (detailLabel == null || "null".equals(detailLabel)) {
                detailLabelTotal += 0;
            } else {
                detailLabelTotal += Float.parseFloat(detailLabel);
            }

        }
        // 计算修缮迁改费用占总费用比例
        Iterator iterator2 = detailLabelMap.keySet().iterator();
        while (iterator2.hasNext()) {
            String itemId = (String) iterator2.next();
            String detailLabel = (String) detailLabelMap.get(itemId);
            if (detailLabel == null || "null".equals(detailLabel)) {
                scale = "0%";
            } else {
                java.text.DecimalFormat df = new java.text.DecimalFormat("#0.00");
                scale = df.format(Float.parseFloat(detailLabel) / detailLabelTotal * 100) + "%";
            }
            logger.info("计算某一区县各修缮项迁改费用占总费用的比例:" + itemId + ":" + scale);
            scaleMap.put(itemId, scale);
        }
        // 测试数据
        /*
         * scaleMap.put("2", "0.67%"); scaleMap.put("3", "0.88%");
         * scaleMap.put("1", "0.33%"); scaleMap.put("4", "0.69%");
         * scaleMap.put("5", "0.56%");
         */
        return scaleMap;
    }

    /**
     * 按区县、项目统计修缮迁改费用
     * 
     * @return
     */
    private Map getDetailLabelMapByTownId(String month, String townId) {
        Map labelMap = new HashMap();
        List list = new ArrayList();
        Map itemMap = getItemMapForInverse(month);
        Iterator iterator = itemMap.keySet().iterator();
        while (iterator.hasNext()) {
            String itemName = (String) iterator.next();
            /*
             * String sql = "select sum(lri.remedyfee) as label from
             * linepatrol_remedy t,linepatrol_remedy_strike
             * lrt,linepatrol_remedy_item lri" + " where t.id=lrt.remedyid" + "
             * and lri.remedyid=t.id" + " and lri.itemname='" + itemName + "'" + "
             * and to_char(t.remedydate,'yyyy-MM')='" + month + "'" + " and
             * t.townid='" + townId + "'" + " and lrt.acceptor is not null";
             */
            String sql = "select sum(lri.remedyfee) as label "
                    + " from linepatrol_remedy t,linepatrol_remedy_balance lrt,LINEPATROL_REMEDY_BAL_ITEM lri"
                    + " where t.id=lrt.remedyid" + " and lri.remedy_balance_id=lrt.id"
                    + " and lri.itemname='" + itemName + "'"
                    + " and to_char(t.remedydate,'yyyy-MM')='" + month + "'" + " and t.townid='"
                    + townId + "'" + " and t.state='401'";
            logger.info("getDetailLabelMapByTownId: " + sql);
            try {
                QueryUtil query = new QueryUtil();
                list = query.queryBeans(sql);
            } catch (Exception e) {
                logger.info("sql " + sql);
                logger.error("query labelMap error::::::" + e);
            }
            String totalfee = ((BasicDynaBean) list.get(0)).get("label") + "";
            labelMap.put(itemMap.get(itemName), totalfee);
            logger.info(labelMap);
        }
        // 测试数据
        /*
         * labelMap.put("1", "301.3"); labelMap.put("3", "800");
         * labelMap.put("2", "612"); labelMap.put("4", "630"); labelMap.put("5",
         * "510");
         */
        return labelMap;
    }

    /**
     * 通过代维查询统计
     * 
     * @return
     */
    public List statRemedyByContractor(String month, String contractorId) {
        List itemNameList = getItemNameList(month, contractorId);
        List detailLabelList = getDetailLabelListByContractorId(month, contractorId);
        List list = new ArrayList();
        list.add(itemNameList);
        list.add(detailLabelList);
        return list;
    }

    /**
     * 按各修缮项，各代维统计修缮迁改费用
     * 
     * @return
     */
    private List getDetailLabelListByContractorId(String month, String contractorId) {
        List list = new ArrayList();
        List totalLabelList = new ArrayList();
        List labelList = new ArrayList();
        List itemNameList = getItemNameList(month, contractorId);

        List remedyCodeList = queryRemedyCodeForStrike();
        DynaBean bean;
        for (int i = 0; i < remedyCodeList.size(); i++) {
            labelList = new ArrayList();
            bean = (DynaBean) remedyCodeList.get(i);
            if (bean.get("contractorid") != null && bean.get("contractorid").equals(contractorId)) {
                labelList.add(bean.get("remedycode"));
                for (int j = 0; j < itemNameList.size(); j++) {
                    String sql = "select sum(lri.remedyfee) as label from linepatrol_remedy t,linepatrol_remedy_strike lrt,linepatrol_remedy_bal_item lri"
                            + " where t.id=lrt.remedyid"
                            + " and lri.remedyid=t.id"
                            + " and lri.itemname='"
                            + itemNameList.get(j)
                            + "'"
                            + " and to_char(t.remedydate,'yyyy-MM')='"
                            + month
                            + "'"
                            + " and t.contractorid='"
                            + contractorId
                            + "'"
                            + " and t.remedycode='"
                            + bean.get("remedycode") + "'" + " and lrt.acceptor is not null";
                    logger.info("getDetailLabelListByContractorId_sql " + sql);
                    try {
                        QueryUtil query = new QueryUtil();
                        List resultList = query.queryBeans(sql);
                        String totalfee;
                        totalfee = ((BasicDynaBean) resultList.get(0)).get("label") + "";
                        if (totalfee == null || "null".equals(totalfee)) {
                            labelList.add("0");
                        } else {
                            labelList.add(totalfee); // 统计各修缮项费用
                            logger.info("totalfee=" + totalfee);
                        }
                    } catch (Exception e) {
                        logger.error("query getDetailLabelListByContractorId error::::::" + e);
                    }
                }
            }
            // 合计
            if (labelList != null && labelList.size() > 0) {
                float sum = 0;
                for (int m = 1; m < labelList.size(); m++) {
                    String item = (String) labelList.get(m);
                    if (item != null && !item.equals("")) {
                        sum += Float.parseFloat(item);
                    }
                }
                labelList.add(sum + "");
                labelList.add(bean.get("id"));
                list.add(labelList);
            }
        }
        logger.info(list);
        totalLabelList.add("合计");
        for (int i = 1; i < itemNameList.size() + 2; i++) {
            float totalLable = 0;
            for (int j = 0; j < list.size(); j++) {
                logger.info(list.get(j));
                if (list.get(j) == null || ((List) list.get(j)).size() == 0) {
                    break;
                }
                Object obj = ((List) list.get(j)).get(i);
                logger.info(obj);
                if (obj == null) {
                    break;
                }
                String totalfee = ((List) list.get(j)).get(i) + "";
                logger.info("totalfee=" + totalfee);
                totalLable += Float.parseFloat(totalfee);
            }
            logger.info("totalLable=" + totalLable);
            totalLabelList.add(totalLable + "");
        }

        list.add(totalLabelList);
        totalLabelList.add("");
        return list;
    }

    /**
     * 得到区县列表
     * 
     * @param regionId
     * @return
     */
    private Map getTownMap() {
        Map townMap = new HashMap();
        List list = new ArrayList();
        String sql = "select t.id,t.town from linepatrol_towninfo t";
        try {
            QueryUtil query = new QueryUtil();
            list = query.queryBeans(sql);
        } catch (Exception e) {
            logger.info("sql " + sql);
            logger.error("query town error::::::" + e);
        }
        for (int i = 0; i < list.size(); i++) {
            logger.info("BasicDynaBean=" + list.get(i));
            String id = ((BasicDynaBean) list.get(i)).get("id") + "";
            String town = ((BasicDynaBean) list.get(i)).get("town") + "";
            townMap.put(id, town);
        }
        return townMap;
    }

    /**
     * 得到区县的ID列表
     * 
     * @param regionId
     * @return
     */
    private List getTownIdList() {
        List townIdList = new ArrayList();
        List list = new ArrayList();
        String sql = "select t.id from linepatrol_towninfo t order by t.id";
        try {
            QueryUtil query = new QueryUtil();
            list = query.queryBeans(sql);
        } catch (Exception e) {
            logger.info("sql " + sql);
            logger.error("query town error::::::" + e);
        }
        for (int i = 0; i < list.size(); i++) {
            logger.info("BasicDynaBean=" + list.get(i));
            String id = ((BasicDynaBean) list.get(i)).get("id") + "";
            townIdList.add(id);
        }
        return townIdList;
    }

    /**
     * 查询修缮项名称列表
     * 
     * @param month
     * @param contractorId
     * 
     * @return
     */
    private List getItemNameList(String month, String contractorId) {
        List itemNameList = new ArrayList();
        List list = new ArrayList();

        // String sql = "select t.id,t.itemname from linepatrol_remedyitem t
        // where t.state='1' order by t.id";
        String sql = "select t.id,t.itemname from linepatrol_remedyitem t ";
        sql = sql + " where exists( ";
        sql = sql
                + " select item.item_id from linepatrol_remedy_item item,linepatrol_remedy remedy ";
        sql = sql + " where item.remedyid=remedy.id and item.item_id=t.id ";
        sql = sql + " and to_char(remedy.remedydate,'yyyy-MM')='" + month + "'";
        sql = sql + " and remedy.contractorid='" + contractorId + "' ";
        sql = sql + " )";
        sql = sql + " and t.state='1' order by t.id";
        try {
            QueryUtil query = new QueryUtil();
            list = query.queryBeans(sql);
        } catch (Exception e) {
            logger.info("getItemNameList sql： " + sql);
            logger.error("query town error::::::" + e);
        }
        for (int i = 0; list != null && i < list.size(); i++) {
            String itemname = ((BasicDynaBean) list.get(i)).get("itemname") + "";
            itemNameList.add(itemname);
        }
        return itemNameList;
    }

    /**
     * 以itemname为key id为value
     * 
     * @param month
     * 
     * @return
     */
    private Map getItemMapForInverse(String month) {
        Map itemMap = new HashMap();
        List list = new ArrayList();
        // String sql = "select t.id,t.itemname from linepatrol_remedyitem t
        // where t.state='1' order by t.id";
        String sql = "select t.id,t.itemname from linepatrol_remedyitem t ";
        sql = sql + " where exists( ";
        sql = sql
                + " select item.item_id from linepatrol_remedy_item item,linepatrol_remedy remedy ";
        sql = sql + " where item.remedyid=remedy.id and item.item_id=t.id ";
        sql = sql + " and to_char(remedy.remedydate,'yyyy-MM')='" + month + "'";
        sql = sql + " )";
        sql = sql + " and t.state='1' order by t.id";
        try {
            QueryUtil query = new QueryUtil();
            list = query.queryBeans(sql);
        } catch (Exception e) {
            logger.info("sql " + sql);
            logger.error("query town error::::::" + e);
        }
        for (int i = 0; list != null && i < list.size(); i++) {
            String itemname = ((BasicDynaBean) list.get(i)).get("itemname") + "";
            String id = ((BasicDynaBean) list.get(i)).get("id") + "";
            itemMap.put(itemname, id);
        }
        return itemMap;
    }

    /**
     * 得到修缮项列表
     * 
     * @param month
     * 
     * @param regionId
     * @return
     */
    public Map getItemMap(String month) {
        Map itemMap = new HashMap();
        List list = new ArrayList();
        // String sql = "select t.id,t.itemname from linepatrol_remedyitem t
        // where t.state='1' order by t.id";
        String sql = "select t.id,t.itemname from linepatrol_remedyitem t ";
        sql = sql + " where exists( ";
        sql = sql
                + " select item.item_id from linepatrol_remedy_item item,linepatrol_remedy remedy ";
        sql = sql + " where item.remedyid=remedy.id and item.item_id=t.id ";
        sql = sql + " and to_char(remedy.remedydate,'yyyy-MM')='" + month + "'";
        sql = sql + " )";
        sql = sql + " and t.state='1' order by t.id";
        try {
            QueryUtil query = new QueryUtil();
            list = query.queryBeans(sql);
        } catch (Exception e) {
            logger.info("sql " + sql);
            logger.error("query town error::::::" + e);
        }
        for (int i = 0; list != null && i < list.size(); i++) {
            logger.info("BasicDynaBean=" + list.get(i));
            String id = ((BasicDynaBean) list.get(i)).get("id") + "";
            String itemname = ((BasicDynaBean) list.get(i)).get("itemname") + "";
            itemMap.put(id, itemname);
        }
        return itemMap;
    }

    /**
     * 查询登陆用户所属区域下所有代维
     * 
     * @return
     */
    public List queryAllContractor(String regionId) {
        List list = new ArrayList();
        String sql = "select c.contractorid,c.contractorname from contractorinfo c where c.regionid='"
                + regionId + "'" + " and depttype='2'" + " and state is null ";

        try {
            QueryUtil query = new QueryUtil();
            list = query.queryBeans(sql);
        } catch (Exception e) {
            logger.info("sql " + sql);
            logger.error("query Contractor error::::::" + e);
        }

        return list;
    }

    /**
     * 查询登陆用户所属区域下所有代维
     * 
     * @return
     */
    public List queryRemedyCodeForStrike() {
        List list = new ArrayList();
        String sql = "select t.id, t.remedycode,t.contractorid from linepatrol_remedy t,linepatrol_remedy_strike lis"
                + " where t.id=lis.remedyid" + " and lis.acceptor is not null" + " order by t.id";
        logger.info("queryRemedyCodeForStrike sql: " + sql);
        try {
            QueryUtil query = new QueryUtil();
            list = query.queryBeans(sql);
        } catch (Exception e) {
            logger.error("query Contractor error::::::" + e);
        }
        return list;
    }

    /**
     * 查询登陆用户所属区域下所有代维
     * 
     * @return
     */
    public String queryContractorNameById(String contractorId) {
        List list = new ArrayList();
        String sql = "select t.contractorname from contractorinfo t where t.contractorid='"
                + contractorId + "'" + " and depttype='2'";
        logger.info("sql " + sql);
        try {
            QueryUtil query = new QueryUtil();
            list = query.queryBeans(sql);
        } catch (Exception e) {
            logger.error("query Contractor error::::::" + e);
        }
        String contractorname = ((BasicDynaBean) list.get(0)).get("contractorname") + "";
        return contractorname;
    }

    /**
     * 查询登陆用户所属区域下所有区县
     * 
     * @return
     */
    public List queryAllTown(String regionId) {
        List list = new ArrayList();
        String sql = "select t.id,t.town from linepatrol_towninfo t where t.regionid='" + regionId
                + "'";
        try {
            QueryUtil query = new QueryUtil();
            list = query.queryBeans(sql);
        } catch (Exception e) {
            logger.info("sql " + sql);
            logger.error("query town error::::::" + e);
        }
        return list;
    }
}
