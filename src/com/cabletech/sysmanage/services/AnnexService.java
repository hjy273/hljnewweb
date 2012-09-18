package com.cabletech.sysmanage.services;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cabletech.commons.upload.dao.UploadFileDao;
import com.cabletech.commons.upload.module.AnnexAddOne;
import com.cabletech.commons.upload.module.UploadFileInfo;
import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.ctf.service.EntityManager;
import com.cabletech.sysmanage.dao.AnnexDao;
import com.cabletech.sysmanage.domainobjects.ConPerson;
import com.cabletech.uploadfile.UploadFile;

@Service("annexService")
@Transactional
public class AnnexService extends EntityManager<AnnexAddOne, String> {
	@Resource(name = "annexDao")
	private AnnexDao annexDao;
	@Resource(name = "uploadFileDao")
	private UploadFileDao uploadFileDao;
	private Logger logger = Logger.getLogger(AnnexService.class.getName());

	/**
	 * 获得所有附件类型
	 * 
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<String> getModuleCatalog() {
		return annexDao.getModuleCatalog();
	}

	/**
	 * 按条件对附件进行查询
	 */
	public List findAnnex(String moduleCatalog, String uploader,
			String originalName, String beginTime, String endTime) {
		// TODO Auto-generated method stub
		return annexDao.getAnnex(moduleCatalog, uploader, originalName,
				beginTime, endTime);
	}

	@Override
	protected HibernateDao<AnnexAddOne, String> getEntityDao() {
		// TODO Auto-generated method stub
		return annexDao;
	}

	public UploadFileInfo getFileNameById(String id) {
		UploadFileInfo uploadFileInfo = uploadFileDao.get(id);
		uploadFileDao.initObject(uploadFileInfo);
		return uploadFileInfo;
	}

	public void updateFileName(String fileId, String name) {
		// TODO Auto-generated method stub
		AnnexAddOne annex = annexDao.get(fileId);
		UploadFileInfo uploadFile = uploadFileDao.get(annex.getFileId());
		uploadFileDao.initObject(uploadFile);
		uploadFile.setOriginalName(name + uploadFile.getFileType());
		uploadFileDao.save(uploadFile);
	}
}
