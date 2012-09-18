package com.cabletech.ln.ws;

import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import org.apache.log4j.Logger;
import org.tempuri.MQServiceLocator;
import org.tempuri.MQServiceSoap;

public class TroubleServiceImpl implements TroubleService{
	private Logger logger = Logger.getLogger(TroubleServiceImpl.class);
	MQServiceSoap mqs ;
	public TroubleServiceImpl() throws ServiceException{
		mqs = new MQServiceLocator().getMQServiceSoap12();
	}
	/**
	 * 向EAI发送隐患信息，其中body格式：
	 * <br><XY>GPS坐标</XY>
	 * <br><SIMID>发送的SIM卡号</SIMID>
	 * <br><TYPE>隐患类型</TYPE>
	 * <br><DESCRIPTION>隐患描述</DESCRIPTION>
	 * <br><LINENAME>发生隐患线路</LINENAME>
	 * <br><POINTNAME>发生隐患点</POINTNAME>
	 * <br><SENDTIME>发送隐患时间</SENDTIME>
	 * @param trouble
	 * @throws RemoteException 
	 * 
	 */
	public void sendTroubleInfo(ClientRequest trouble) throws RemoteException{
		String title = "线路隐患信息";
		StringBuffer body = new StringBuffer();
		body.append("<XY>"+trouble.getXy()+"</XY>");
		body.append("<SIMID>"+trouble.getSimid()+"</SIMID>");
		body.append("<TYPE>"+trouble.getTtype()+"</TYPE>");
		body.append("<DESCRIPTION>"+trouble.getTcode()+"</DESCRIPTION>");
		body.append("<LINENAME>"+trouble.getLinename()+"</LINENAME>");
		body.append("<POINTNAME>"+trouble.getPointname()+"</POINTNAME>");
		body.append("<SENDTIME>"+trouble.getSendtime()+"</SENDTIME>");
		logger.info("向EAI发送的隐患信息 [title："+title+"; body: "+body+"]");
		boolean result = mqs.sendMessage(title,body.toString());
		if(!result){
			logger.info("发送隐患信息失败，重复向EAI发送隐患信息");
			result = mqs.sendMessage(title,body.toString());
		}
		if(result){
			logger.info("发送隐患信息成功");
		}else{
			logger.info("发送隐患信息失败");
		}
	}
}
