package com.cabletech.demo;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.fileupload.FileItem;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.upload.ModuleCatalog;
import com.cabletech.commons.upload.service.UploadFileService;

@Service
public class UploadFileDemoManager {
	@Resource(name = "uploadFileService")
	private UploadFileService uploadFile;

	@Transactional
	public void uploadFile(UploadFileBean bean, UserInfo user, String[] ids) {
		//执行业务数据保存后，获得业务数据ID，及数据库实体名
		System.out.println("SIZE:" + bean.getAttachments().size());
		List<String> delIds = Arrays.asList(ids);
		uploadFile.saveFiles(bean.getAttachments(), delIds, ModuleCatalog.TROUBLE, "北京", "0000001", "ls_trouble",
				"userid");
	}

	@Transactional
	public void uploadFile(List<FileItem> files, UserInfo user) {
		uploadFile.saveFiles(files, ModuleCatalog.TROUBLE, "北京", "0000001", "ls_trouble", "userid");
	}
}
