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
	 * 获得在线巡检员
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
				List onLinepatrols = dao.getPatrolGroup(contractorid);//在线巡检组
//				int patrolNum = dao.getPatrolGroupNum(contractorid);
				countSim = dao.getPatrolSimNumByConid(contractorid);//代维下所有巡检组下的设备个数
				contractorSimNum = dao.getSimNumByConid(contractorid);//代维下的设备个数
				jsonArrayB = new JSONArray();
				if(onLinepatrols!=null && onLinepatrols.size()>0){
					contraNum = onLinepatrols.size();
					for(int j=0;j<onLinepatrols.size();j++){//在线巡检组
						int onLineSimNum = 0;
						BasicDynaBean group = (BasicDynaBean) onLinepatrols.get(j);
						String patrolid = (String) group.get("patrolid");
						String patrolname = (String) group.get("patrolname");
						List onlineSims = dao.getPatrolSim(patrolid);//在线的巡检设备
						int simNum = dao.getPatrolSimNUM(patrolid);//巡检设备个数
						jsonArrayC = new JSONArray();
						if(onlineSims!=null && onlineSims.size()>0){
							onLineSimNum = onlineSims.size();
							countUseSim+=onLineSimNum;
							for(int m=0;m<onlineSims.size();m++){//循环sim卡
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
						jsonObjB.put("id", patrolid+"fjj01");//id子父重复点击节点会出现问题
						jsonObjB.put("text", patrolname+"("+onLineSimNum+"/"+simNum+")");//在线个数/巡检设备个数
						//	jsonObjB.put("text", patrolname+"("+onLineSimNum+")");//巡检组下设备在线个数
						jsonObjB.put("children",jsonArrayC);
						jsonArrayB.add(jsonObjB);
					}
				}
				//if(jsonArrayB!=null && jsonArrayB.size()>0){
					jsonObjA = new JSONObject();
					jsonObjA.put("id", contractorid+"conid");
					jsonObjA.put("text", contractorname+"("+contractOnLineNum+"/"+contractorSimNum+")");//代维下设备在线个数/代维下所有设备
					//jsonObjA.put("text", contractorname+"("+contractOnLineNum+"/"+countSim+")");//代维下设备在线个数/代维下巡检组的总设备个数
					//jsonObjA.put("text", contractorname+"("+countUseSim+"/"+countSim+")");//代维下巡检组个数/代维下设备个数
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
