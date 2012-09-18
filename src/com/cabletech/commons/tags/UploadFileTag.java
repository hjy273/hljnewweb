package com.cabletech.commons.tags;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.cabletech.commons.config.SpringContext;
import com.cabletech.commons.upload.module.UploadFileInfo;
import com.cabletech.commons.upload.service.UploadFileService;

public class UploadFileTag extends TagSupport {
	private static final long serialVersionUID = -4091356880864770007L;
	//附件id
	private String id = "uploadFile";
	private String entityId;//业务实体系统编号
	private String entityType;//业务实体名，表名
    //编辑状态 添加/编辑/查看
    private String state = "";
    private String cssClass="";
    private String useable = "1";

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
        List<UploadFileInfo> fileList = null;
        String strHtml1 = "<script language=\"javascript\"> \n"
    		+"  fileNum=0;\n"
    		+"   //脚本生成的删除按  钮的删除动作\n"
    		+"  function deleteRow(){\n"
    		+"  	//获得按钮所在行的id\n"
    		+"  	var rowid = \"000\";\n"
    		+"  	rowid = this.id;\n"
    		+"  	rowid = rowid.substring(1,rowid.length);\n"
    		+"   	//由id转换为行索找行的索引,并删除\n"
    		+"  	for(i =0; i<"+id+".rows.length;i++){\n"
    		+"  		if("+id+".rows[i].id == rowid){\n"
    		+"  			"+id+".deleteRow("+id+".rows[i].rowIndex);\n"
    		+"  			fileNum--;\n"
    		+"  		}\n"
    		+"  	}\n"
    		+"  }\n"
    		+"  //添加一个新行\n"
    		+"  function addRow(){\n"
    		+"  	var  onerow="+id+".insertRow(-1);\n"
    		+"  	onerow.id = "+id+".rows.length-2;\n"
    		+"  	var   cell1=onerow.insertCell();\n"
    		+"  	var   cell2=onerow.insertCell();\n"

    		+"  	//创建一个输入框\n"
    		+"  	var t1 = document.createElement(\"input\");\n"
    		+"  	t1.name = \""+id+"[\"+ fileNum + \"].file\";\n"
    		+"  	t1.type= \"file\";\n"
    		+"  	t1.id = \"text\" + onerow.id;\n"
    		+"  	t1.width=\"300\";\n"
    		+"		t1.className=\""+cssClass+"\";\n"
    		+"  	fileNum++;\n"

    		+"  	//创建删除按钮\n"
    		+"  	var b1 =document.createElement(\"button\");\n"
    		+"  	b1.value = \"删除\";\n"
    		+"		b1.className=\"button_minimum\";\n"
    		+"  	b1.id = \"b\" + onerow.id;\n"
    		+"  	b1.onclick=deleteRow;\n"
    		+"  	cell1.appendChild(t1);//文字\n"
    		+"  	cell2.appendChild(b1);\n"
    		+"  }\n"
    		+"  </script>\n";
    	String strHtml2	="  <table id=\""+id+"\"  border=\"0\" align=\"left\" cellpadding=\"0\" cellspacing=\"0\" >\n"
    		+"  <tr class=trcolor>\n"
    		+"  <td>\n";
        String strHtml3 = "</td>\n"
    		+"  </tr>\n"
    		+"  </table>\n"
    		+"  <br/>\n";
    		//+"  <a href=\"javascript:addRow();\">添加附件</a><br/>\n";
        
        UploadFileService  uploadFileService = (UploadFileService)SpringContext.getApplicationContext().getBean("uploadFileService");
        fileList = uploadFileService.getFilesByIds(entityId,entityType,getUseable());
        
      //add    
        	if(state.equals("add")){
            	
            	results.append(strHtml1);
            	results.append(strHtml2);
            	results.append(strHtml3);
            }else if(state.equals("edit")){//edit
            	
            	results.append(strHtml1);
            	results.append(strHtml2);
            	 if( fileList == null || fileList.size() < 1 ){
            		 results.append("<div></div>");
                 }
                 else{
                    for(UploadFileInfo fileInfo :fileList){
                    	linkItem = " <input type='checkbox'  name='delfileid'  value=" + fileInfo.getFileId() + " />删除"
                     	+ " &nbsp&nbsp&nbsp&nbsp<a href='/WebApp/download.do?method=download&fileid="
                     	+ fileInfo.getFileId()
                     	+ "'><img border='0' src='/WebApp/images/attachment.gif' width='15' height='15'>"
                     	+ fileInfo.getOriginalName() + "</a>";
                     results.append(linkItem+"<br/>");
                    }
                   
                 }
                results.append(strHtml3);
            }else{
//            	results.append(strHtml2);
            	if( fileList == null || fileList.size() < 1 ){
            		results.append( "<div>无附件</div>");
                }
                else{
                	for(UploadFileInfo fileInfo :fileList){
                		linkItem = "<a href='/WebApp/download.do?method=download&fileid="
                            + fileInfo.getFileId()
                            + "'><img border='0' src='/WebApp/images/attachment.gif' width='15' height='15'>"
                            + fileInfo.getOriginalName() + "</a>";
                        results.append(linkItem+"<br/>"); 
                	}
                }
//            	 results.append(strHtml3);
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

	public String getEntityId() {
		return entityId;
	}


	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}


	public String getEntityType() {
		return entityType;
	}


	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}


	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}


	public String getCssClass() {
		return cssClass;
	}


	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getUseable() {
		return useable;
	}


	public void setUseable(String useable) {
		this.useable = useable;
	}
	
	
}
