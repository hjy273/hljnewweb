package com.cabletech.commons.upload.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.cabletech.commons.upload.module.AnnexAddOne;
import com.cabletech.ctf.dao.HibernateDao;

@Repository
public class AnnexAddOneDao extends HibernateDao<AnnexAddOne, String> {
    /**
     * 通过一组id删除附件附加信息
     * 
     * @param ids
     */
    public void deleteAnnexByIds(List<String> ids) {
        for (String id : ids) {
            super.delete(id);
        }
    }

    public AnnexAddOne getAnnexAddOneByFileId(String fileId) {
        return super.findByUnique("fileId", fileId);
    }

    // public List<AnnexAddOne> getAnnexAddOneList(String entityId, String type,
    // String module){
    // String hql = "from AnnexAddOne a where a.entityId = ? and a.entityType =
    // ? and a.module = ?";
    // return find(hql, entityId, type, module);
    // }

    public List<Map> getAnnexAddOneList(String entityId, String type, String module, String state) {
        String sql = "select a.ID,a.FILEID,a.IS_USABLE,to_char(f.ONCREATE,'yyyy-mm-dd hh24:mi:ss') ONCREATE,a.UPLOADER,f.ORIGINALNAME "
                + "from ANNEX_ADD_ONE a,FILEPATHINFO f "
                + "where a.fileid = f.fileid and a.ENTITY_ID = '"
                + entityId
                + "' and a.state=0"
                + " AND a.ENTITY_TYPE = '" + type + "' and a.MODULE = '" + module + "' ";
        sql += " and a.is_usable = '" + state + "'";
        logger.info("getAnnexAddOneList sql=== :" + sql);
        return this.getJdbcTemplate().queryForList(sql);
    }

    public void setPass(String id) {
        AnnexAddOne addOne = findUniqueByProperty("id", id);
        addOne.setIsUsable("1");
        save(addOne);
    }

}
