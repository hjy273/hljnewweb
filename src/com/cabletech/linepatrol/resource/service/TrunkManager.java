package com.cabletech.linepatrol.resource.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.tags.dao.DictionaryDao;
import com.cabletech.commons.tags.module.Dictionary;
import com.cabletech.commons.upload.ModuleCatalog;
import com.cabletech.commons.upload.dao.AnnexAddOneDao;
import com.cabletech.commons.upload.service.UploadFileService;
import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.ctf.service.EntityManager;
import com.cabletech.linepatrol.acceptance.service.AcceptanceConstant;
import com.cabletech.linepatrol.resource.dao.RepeaterSectionDao;
import com.cabletech.linepatrol.resource.model.RepeaterSection;

@Service
@Transactional
public class TrunkManager extends EntityManager<RepeaterSection, String> {

    @Resource(name = "repeaterSectionDao")
    private RepeaterSectionDao dao;

    @Resource(name = "dictionaryDao")
    private DictionaryDao dictionaryDao;

    @Resource(name = "annexAddOneDao")
    private AnnexAddOneDao annexAddOneDao;

    @Resource(name = "uploadFileService")
    private UploadFileService uploadFile;

    public String getTrunkstr(Set<String> trunkIds, List<RepeaterSection> trunkList) {
        StringBuffer sb = new StringBuffer();
        for (RepeaterSection t : trunkList) {
            sb.append("<tr><td>");
            sb.append("<input type='checkbox' name='trunkname' onclick='setValue(this)' value='");
            sb.append(t.getKid());
            sb.append("' ");
            sb.append(getIsCheckedString(trunkIds, t.getKid()));
            sb.append("/>");
            sb.append(t.getSegmentname());
            sb.append("</td></tr>");
        }
        return sb.toString();
    }

    public List<RepeaterSection> getTrunks(String filter, String level, UserInfo user) {
        return dao.getRepeaterSection(filter, level, user);
    }

    public Set<String> setTrunks(Set<String> trunkIds, List<RepeaterSection> trunkList) {
        if (trunkList != null && !trunkList.isEmpty()) {
            for (RepeaterSection r : trunkList) {
                trunkIds.add(r.getKid());
            }
        }
        return trunkIds;
    }

    public String getIsCheckedString(Set<String> trunkIds, String id) {
        String str = "";
        if (trunkIds.contains(id)) {
            str = "checked";
        }
        return str;
    }

    /**
     * 将选择的中继段的id转换为中继段的名称
     * 
     * @param trunkIds
     *            中继段id
     * @return 返回用逗号分割的中继段名称
     */
    public String getTrunkNameString(Set<String> trunkIds) {
        String queryId = "";
        queryId = "'" + StringUtils.join(trunkIds.iterator(), "','") + "'";
        List<RepeaterSection> trunks = dao.queryByTrunkIds(queryId);
        List<String> trunkNameList = new ArrayList<String>();
        Map<Object, Object> dictMap = dictionaryDao.queryByCode("cabletype");
        for (RepeaterSection t : trunks) {
            String cableLevel = "";
            if (t.getCableLevel() != null && dictMap != null
                    && dictMap.containsKey(t.getCableLevel())) {
                cableLevel = "(" + dictMap.get(t.getCableLevel()) + ")";
            }
            trunkNameList.add(t.getSegmentname() + cableLevel);
        }
        return StringUtils.join(trunkNameList.toArray(), ",");
    }

    public String getTrunk(String id) {
        return dao.getTrunkName(id);
    }

    public List<Map> getAnnexAddOneList(String id, String type, String state) {
        if (type.equals(AcceptanceConstant.CABLEFILE)) {
            return annexAddOneDao.getAnnexAddOneList(id, AcceptanceConstant.CABLEFILE,
                    ModuleCatalog.OPTICALCABLE, state);
        } else {
            return annexAddOneDao.getAnnexAddOneList(id, AcceptanceConstant.PIPEFILE,
                    ModuleCatalog.PIPELINE, state);
        }
    }

    public void updateFile(List<FileItem> files, String id, UserInfo userInfo, String type) {
        saveFiles(files, id, userInfo, type);
    }

    public void saveFiles(List<FileItem> files, String id, UserInfo userInfo, String type) {
        if (type.equals(AcceptanceConstant.CABLEFILE)) {
            uploadFile.saveFiles(files, ModuleCatalog.OPTICALCABLE, userInfo.getRegionName(), id,
                    AcceptanceConstant.CABLEFILE, userInfo.getUserID(), "0");
        } else {
            uploadFile.saveFiles(files, ModuleCatalog.PIPELINE, userInfo.getRegionName(), id,
                    AcceptanceConstant.PIPEFILE, userInfo.getUserID(), "0");
        }
    }

    public void updateApprove(String id, String type) {
        List<Map> list = getAnnexAddOneList(id, type, "0");
        for (Map map : list) {
            annexAddOneDao.setPass((String) map.get("ID"));
        }
    }

    public String getTrunkNamefromId(String id) {
        return dao.getTrunkName(id);
    }

    @Override
    protected HibernateDao<RepeaterSection, String> getEntityDao() {
        return dao;
    }
}
