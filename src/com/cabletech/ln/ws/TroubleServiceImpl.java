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
	 * ��EAI����������Ϣ������body��ʽ��
	 * <br><XY>GPS����</XY>
	 * <br><SIMID>���͵�SIM����</SIMID>
	 * <br><TYPE>��������</TYPE>
	 * <br><DESCRIPTION>��������</DESCRIPTION>
	 * <br><LINENAME>����������·</LINENAME>
	 * <br><POINTNAME>����������</POINTNAME>
	 * <br><SENDTIME>��������ʱ��</SENDTIME>
	 * @param trouble
	 * @throws RemoteException 
	 * 
	 */
	public void sendTroubleInfo(ClientRequest trouble) throws RemoteException{
		String title = "��·������Ϣ";
		StringBuffer body = new StringBuffer();
		body.append("<XY>"+trouble.getXy()+"</XY>");
		body.append("<SIMID>"+trouble.getSimid()+"</SIMID>");
		body.append("<TYPE>"+trouble.getTtype()+"</TYPE>");
		body.append("<DESCRIPTION>"+trouble.getTcode()+"</DESCRIPTION>");
		body.append("<LINENAME>"+trouble.getLinename()+"</LINENAME>");
		body.append("<POINTNAME>"+trouble.getPointname()+"</POINTNAME>");
		body.append("<SENDTIME>"+trouble.getSendtime()+"</SENDTIME>");
		logger.info("��EAI���͵�������Ϣ [title��"+title+"; body: "+body+"]");
		boolean result = mqs.sendMessage(title,body.toString());
		if(!result){
			logger.info("����������Ϣʧ�ܣ��ظ���EAI����������Ϣ");
			result = mqs.sendMessage(title,body.toString());
		}
		if(result){
			logger.info("����������Ϣ�ɹ�");
		}else{
			logger.info("����������Ϣʧ��");
		}
	}
}
