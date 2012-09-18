package com.cabletech.uploadfile.formbean;

import java.util.*;
import com.cabletech.commons.base.*;
import com.cabletech.uploadfile.*;

public class BaseFileFormBean extends BaseBean{
    private List<UploadFile> attachments;
    public BaseFileFormBean(){
        attachments = new ArrayList<UploadFile>();
        //Ϊ���ܹ���ҳ���ʼ��ʾһ��file
        attachments.add( new UploadFile() );
    }

    //ע����������Ķ���
    public UploadFile getUploadFile( int index ){
        int size = attachments.size(); 
        if( index > size - 1 ){
        	//�������Խ�����⣬zhufeng add
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
