package com.cabletech.uploadfile.formbean;

import java.util.*;
import com.cabletech.commons.base.*;
import com.cabletech.uploadfile.*;

public class BaseFileFormBean extends BaseBean{
    private List<UploadFile> attachments;
    public BaseFileFormBean(){
        attachments = new ArrayList<UploadFile>();
        //为了能够在页面初始显示一个file
        attachments.add( new UploadFile() );
    }

    //注意这个方法的定义
    public UploadFile getUploadFile( int index ){
        int size = attachments.size(); 
        if( index > size - 1 ){
        	//解决数组越界问题，zhufeng add
        	for(int i=0; i<index-size+1; i++)
        		attachments.add( new UploadFile() );
        }
        return( UploadFile )attachments.get( index );
    }

    public List<UploadFile> getAttachments(){
        return attachments;
    }

    public void setAttachments( List<UploadFile> attachments ){
        this.attachments = attachments;
    }

}
