package com.cabletech.linepatrol.remedy.dao;

import java.util.List;
import java.util.Map;

import com.cabletech.commons.hb.QueryUtil;
import com.cabletech.linepatrol.remedy.constant.ExecuteCode;
import com.cabletech.linepatrol.remedy.domain.RemedyApplyAttach;

public class RemedyApplyAttachDao extends RemedyBaseDao {
    /**
     * Ö´ÐÐ¸ù¾Ý²éÑ¯Ìõ¼þ»ñÈ¡ÐÞÉÉÉêÇë¸½¼þÐÅÏ¢ÁÐ±í
     * 
     * @param condition
     *            String ²éÑ¯Ìõ¼þ
     * @return List ÐÞÉÉÉêÇë¸½¼þÐÅÏ¢ÁÐ±í
     * @throws Exception
     */
    public List queryList(String condition) throws Exception {
        // TODO Auto-generated method stub
        logger(RemedyApplyAttachDao.class);
        String sql = "select id,REMEDYID,FLAG,ATTACHNAME,ATTACHID from LINEPATROL_REMEDY_ATTACH where 1=1 "
                + condition;
        try {
            logger.info("Execute sql:" + sql);
            QueryUtil queryUtil = new QueryUtil();
            List list = queryUtil.queryBeans(sql);
            return list;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error(e);
            throw e;
        }
    }

    /**
     * Ö´ÐÐ¸ù¾ÝÐÞÉÉÉêÇë±àºÅ±£´æÐÞÉÉÉêÇë¸½¼þÐÅÏ¢
     * 
     * @param applyId
     *            String ÐÞÉÉÉêÇë±àºÅ
     * @param attachList
     *            List ÐÞÉÉÉêÇë¸½¼þÐÅÏ¢ÁÐ±í
     * @param attachType
     *            ÐÞÉÉÉêÇë¸½¼þÀàÐÍ
     * @return String ±£´æÐÞÉÉÉêÇë¸½¼þÐÅÏ¢±àÂë
     * @throws Exception
     */
    public String saveAttachList(String applyId, List attachList, String attachType)
            throws Exception {
        logger(RemedyApplyAttachDao.class);
        RemedyApplyAttach attach;
        String[] seqIds = new String[0];
        Map oneAttach;
        try {
            if (attachList != null && attachList.size() > 0) {
                seqIds = ora.getSeqs("LINEPATROL_REMEDY_ATTACH", "REMEDY_ATTACH", 20, attachList
                        .size());
            }
            for (int i = 0; attachList != null && i < attachList.size(); i++) {
                oneAttach = (Map) attachList.get(i);
                attach = new RemedyApplyAttach();
                attach.setId(seqIds[i]);
                attach.setFlag(attachType);
                attach.setAttachName((String) oneAttach.get("file_name"));
                attach.setAttachId((String) oneAttach.get("file_id"));
                attach.setRemedyId(applyId);
                Object obj = super.insertOneObject(attach);
                if (obj == null) {
                    return ExecuteCode.FAIL_CODE;
                }
            }
            return ExecuteCode.SUCCESS_CODE;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error(e);
            throw e;
        }
    }

    /**
     * Ö´ÐÐ¸ù¾ÝÐÞÉÉÉêÇë±àºÅÉ¾³ýÐÞÉÉÉêÇë¸½¼þÐÅÏ¢
     * 
     * @param applyId
     *            String ÐÞÉÉÉêÇë±àºÅ
     * @param attachType
     *            String ÐÞÉÉÉêÇë¸½¼þÀàÐÍ
     * @return String É¾³ýÐÞÉÉÉêÇë¸½¼þÐÅÏ¢±àÂë
     * @throws Exception
     */
    public String deleteAttachList(String applyId, String attachType) throws Exception {
        logger(RemedyApplyAttachDao.class);
        String condition = " and remedyId='" + applyId + "' and flag='" + attachType + "' ";
        try {
            boolean flag = super.deleteAll("RemedyApplyAttach", condition);
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
