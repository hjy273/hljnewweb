package com.cabletech.bj.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BasicDynaBean;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.bj.dao.FrameCommonDao;
import com.cabletech.commons.base.BaseBisinessObject;

public class OnLineBO extends BaseBisinessObject {
	private FrameCommonDao dao = new FrameCommonDao();


	/**
	 * �������Ѳ��Ա
	 * @param user
	 * @return
	 */
	public String getOnlinePatrolman(UserInfo user){
		JSONObject mobileObj, jsonObjA,jsonObjB,jsonObjC;
		JSONArray jsonArrayB,jsonArrayC;
		JSONArray mobileJson = new JSONArray();
		JSONArray jsonArrayA = new JSONArray();
		String deptname = user.getDeptName();
		String deptid = user.getDeptID();
		List contracts = dao.getCons(user);
		int contraNum = 0;
		int countUseSim = 0;
		int countSim = 0;
		int contractorSimNum;
		int allOnLine = 0;
		int allSim=0;
		if(contracts!=null && contracts.size()>0){
			for(int i=0;i<contracts.size();i++){
				int contractOnLineNum=0;
				BasicDynaBean bean = (BasicDynaBean) contracts.get(i);
				String contractorid = (String) bean.get("contractorid");
				String contractorname =(String)bean.get("contractorname");
				List onLinepatrols = dao.getPatrolGroup(contractorid);//����Ѳ����
//				int patrolNum = dao.getPatrolGroupNum(contractorid);
				countSim = dao.getPatrolSimNumByConid(contractorid);//��ά������Ѳ�����µ��豸����
				contractorSimNum = dao.getSimNumByConid(contractorid);//��ά�µ��豸����
				jsonArrayB = new JSONArray();
				if(onLinepatrols!=null && onLinepatrols.size()>0){
					contraNum = onLinepatrols.size();
					for(int j=0;j<onLinepatrols.size();j++){//����Ѳ����
						int onLineSimNum = 0;
						BasicDynaBean group = (BasicDynaBean) onLinepatrols.get(j);
						String patrolid = (String) group.get("patrolid");
						String patrolname = (String) group.get("patrolname");
						List onlineSims = dao.getPatrolSim(patrolid);//���ߵ�Ѳ���豸
						int simNum = dao.getPatrolSimNUM(patrolid);//Ѳ���豸����
						jsonArrayC = new JSONArray();
						if(onlineSims!=null && onlineSims.size()>0){
							onLineSimNum = onlineSims.size();
							countUseSim+=onLineSimNum;
							for(int m=0;m<onlineSims.size();m++){//ѭ��sim��
								BasicDynaBean sim = (BasicDynaBean) onlineSims.get(m);
								String simid = (String) sim.get("simid");
								String holder = (String)sim.get("holder");
								//String operate = (String) sim.get("operate");
								String activetime = (String) sim.get("activetime");
								jsonObjC = new JSONObject();
								jsonObjC.put("id", simid);
								jsonObjC.put("text", (holder==null?"":holder)+"("+simid+")- "+activetime);
								jsonObjC.put("leaf",true);
								jsonArrayC.add(jsonObjC);
							}
						}  
						contractOnLineNum+=onLineSimNum;
						jsonObjB = new JSONObject();
						jsonObjB.put("id", patrolid+"fjj01");//id�Ӹ��ظ�����ڵ���������
						jsonObjB.put("text", patrolname+"("+onLineSimNum+"/"+simNum+")");//���߸���/Ѳ���豸����
						//	jsonObjB.put("text", patrolname+"("+onLineSimNum+")");//Ѳ�������豸���߸���
						jsonObjB.put("children",jsonArrayC);
						jsonArrayB.add(jsonObjB);
					}
				}
				//if(jsonArrayB!=null && jsonArrayB.size()>0){
					jsonObjA = new JSONObject();
					jsonObjA.put("id", contractorid+"conid");
					jsonObjA.put("text", contractorname+"("+contractOnLineNum+"/"+contractorSimNum+")");//��ά���豸���߸���/��ά�������豸
					//jsonObjA.put("text", contractorname+"("+contractOnLineNum+"/"+countSim+")");//��ά���豸���߸���/��ά��Ѳ��������豸����
					//jsonObjA.put("text", contractorname+"("+countUseSim+"/"+countSim+")");//��ά��Ѳ�������/��ά���豸����
					jsonObjA.put("children",jsonArrayB);
					mobileJson.add(jsonObjA);
				//}
				allOnLine+=contractOnLineNum;
				allSim+=contractorSimNum;
			}
//			if(jsonArrayA!=null && jsonArrayA.size()>0){
//				mobileObj = new JSONObject();
//				mobileObj.put("id",deptid);
//				mobileObj.put("text", deptname+"("+allOnLine+"/"+allSim+")");
//				mobileObj.put("children",jsonArrayA);
//				mobileJson.add(mobileObj);
//			}
		}
		return mobileJson.toString();
	}
}
