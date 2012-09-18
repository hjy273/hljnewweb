package com.cabletech.commons.tags;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.cabletech.uploadfile.dao.UploadDAO;
import com.cabletech.uploadfile.model.UploadFileInfo;
import com.cabletech.watchinfo.util.UploadWatchFile;

public class Attachment2EditTag  extends TagSupport{
	//����id
	private String fileIdList;
    //�༭״̬ ���/�༭/�鿴
    private String state = "";

    /**
     * Generate the required input tag.
     * <p>
     * Support for indexed property since Struts 1.1
     *
     * @exception JspException if a JSP exception has occurred
     */
    public int doStartTag() throws JspException{
        StringBuffer results = new StringBuffer();
        String linkItem;
        UploadFileInfo fileInfo;
        List fileList = null;
        String strHtml1 = "<script language=\"javascript\"> \n"
    		+"  fileNum=0;\n"
    		+"   //�ű����ɵ�ɾ����  ť��ɾ������\n"
    		+"  function deleteRow(){\n"
    		+"  	//��ð�ť�����е�id\n"
    		+"  	var rowid = \"000\";\n"
    		+"  	rowid = this.id;\n"
    		+"  	rowid = rowid.substring(1,rowid.length);\n"
    		+"   	//��idת��Ϊ�������е�����,��ɾ��\n"
    		+"  	for(i =0; i<uploadID.rows.length;i++){\n"
    		+"  		if(uploadID.rows[i].id == rowid){\n"
    		+"  			uploadID.deleteRow(uploadID.rows[i].rowIndex);\n"
    		+"  			fileNum--;\n"
    		+"  		}\n"
    		+"  	}\n"
    		+"  }\n"
    		+"  //���һ������\n"
    		+"  function addRow(){\n"
    		+"  	var  onerow=uploadID.insertRow(-1);\n"
    		+"  	onerow.id = uploadID.rows.length-2;\n"
    		+"  	var   cell1=onerow.insertCell();\n"
    		+"  	var   cell2=onerow.insertCell();\n"

    		+"  	//����һ�������\n"
    		+"  	var t1 = document.createElement(\"input\");\n"
    		+"  	t1.name = \"uploadFile[\"+ fileNum + \"].file\";\n"
    		+"  	t1.type= \"file\";\n"
    		+"  	t1.id = \"text\" + onerow.id;\n"
    		+"  	t1.width=\"300\";\n"
    		+"  	fileNum++;\n"

    		+"  	//����ɾ����ť\n"
    		+"  	var b1 =document.createElement(\"button\");\n"
    		+"  	b1.value = \"ɾ��\";\n"
    		+"  	b1.id = \"b\" + onerow.id;\n"
    		+"  	b1.onclick=deleteRow;\n"
    		+"  	cell1.appendChild(t1);//����\n"
    		+"  	cell2.appendChild(b1);\n"
    		+"  }\n"
    		+"  </script>\n"
    		
    		+"  <table id=\"uploadID\"  border=\"0\" align=\"left\" cellpadding=\"0\" cellspacing=\"0\" >\n"
    		+"  <tr class=trcolor>\n"
    		+"  <td>\n";
        String strHtml2 = "</td>\n"
    		+"  </tr>\n"
    		+"  </table>\n"
    		+"  <br/>\n";
    		//+"  <a href=\"javascript:addRow();\">��Ӹ���</a><br/>\n";
        
        if (fileIdList.endsWith("*")){
        	fileIdList = fileIdList.substring(0, fileIdList.length() -2);
        	System.out.println("fileIdList is:" + fileIdList);
        	fileList = UploadDAO.getWatchFileInfos( fileIdList );
        	results.append(strHtml1);
        	if( fileList == null || fileList.size() < 1 ){
        		results.append( "<div>�޸���</div>");
            }
            else{
                Iterator iter = fileList.iterator();
                int i = 0;
                UploadWatchFile watchFileInfo = null;
                String url="";
                while( iter.hasNext() ){
                	watchFileInfo = ( UploadWatchFile )iter.next();
                	url = watchFileInfo.getRelativeURL();
                    linkItem = "<a href='/WebApp/downloadAction.do?isWatch=1&fileid="
                        + watchFileInfo.getFileId()
                        + "'><img border='0' src='/WebApp/images/attachment.gif' width='15' height='15'>"
                        + url.substring(url.lastIndexOf("/")+1) + "</a>";
                    results.append(linkItem+"<br/>");
                }
            }
        	 results.append(strHtml2);
        }else{
        	fileList = UploadDAO.getFileInfos( fileIdList );
            if(state.equals("add")){
            	//add
            	results.append(strHtml1);
            	results.append(strHtml2);
            }else if(state.equals("edit")){
            	//edit
            	results.append(strHtml1);
            	 if( fileList == null || fileList.size() < 1 ){
            		 results.append("<div></div>");
                 }
                 else{
                     Iterator iter = fileList.iterator();
                     int i = 0;
                     while( iter.hasNext() ){
                         fileInfo = ( UploadFileInfo )iter.next();
                         linkItem = " <input type='checkbox'  name='delfileid'  value=" + fileInfo.getFileId() + " />ɾ��"
                         	+ " &nbsp&nbsp&nbsp&nbsp<a href='/WebApp/downloadAction.do?fileid="
                         	+ fileInfo.getFileId()
                         	+ "'><img border='0' src='/WebApp/images/attachment.gif' width='15' height='15'>"
                         	+ fileInfo.getOriginalName() + "</a>";
                         results.append(linkItem+"<br/>");
                     }
                 }
                results.append(strHtml2);
            }else{
            	results.append(strHtml1);
            	if( fileList == null || fileList.size() < 1 ){
            		results.append( "<div>�޸���</div>");
                }
                else{
                    Iterator iter = fileList.iterator();
                    int i = 0;
                    while( iter.hasNext() ){
                        fileInfo = ( UploadFileInfo )iter.next();
                        linkItem = "<a href='/WebApp/downloadAction.do?fileid="
                            + fileInfo.getFileId()
                            + "'><img border='0' src='/WebApp/images/attachment.gif' width='15' height='15'>"
                            + fileInfo.getOriginalName() + "</a>";
                        results.append(linkItem+"<br/>");
                    }
                }
            	 results.append(strHtml2);
            }
        }        

        try{
            this.pageContext.getOut().print( results.toString() );
        }
        catch( IOException ex ){
            ex.printStackTrace();
        }
        return this.SKIP_BODY;
    }


    /**
     * Release any acquired resources.
     */
    public void release(){
        super.release();
    }

    public void setFileIdList( String fileIdList ){
        this.fileIdList = fileIdList;
    }

    public String getFileIdList(){
        return fileIdList;
    }

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
}
