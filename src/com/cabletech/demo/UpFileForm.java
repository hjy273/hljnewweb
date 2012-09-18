package com.cabletech.demo;

import org.apache.struts.upload.FormFile;

import com.cabletech.baseinfo.beans.RegionBean;
import com.cabletech.commons.base.BaseBean;

public class UpFileForm extends BaseBean {
	private FormFile file;

	//    private String filename = "";
	//    private String filesize = "";

	public FormFile getFile() {
		//     //System.out.println( "form  get here.........." );
		return file;
	}

	public void setFile(FormFile file) {
		//      //System.out.println( "form  setv here.........." );
		this.file = file;
	}

	//    public String getFilename(){
	//        return this.filename;
	//    }
	//    public void setFilename(String filename){
	//        this.filename = filename;
	//    }
	//    public String getFilesize(){
	//        return this.filesize;
	//    }
	//    public void setFilesize(String filesize){
	//		this.filesize = filesize;
	//    }

	public UpFileForm() {
	};

	public void newObject() {
		RegionBean bean = new RegionBean();
		bean.setId("1111");
		System.out.println("a bean :" + bean);
		setValue(bean);
		System.out.println("getId " + bean.getId());
	}

	public void setValue(RegionBean bean) {
		bean.setId("2222");
		System.out.println("b bean :" + bean);
	}

	public static void main(String[] args) {
		UpFileForm u = new UpFileForm();
		u.newObject();
	}
}
