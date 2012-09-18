package com.cabletech.linepatrol.trouble.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cabletech.baseinfo.dao.ContractorDAOImpl;
import com.cabletech.baseinfo.domainobjects.Contractor;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.ctf.exception.ServiceException;
import com.cabletech.ctf.service.EntityManager;
import com.cabletech.linepatrol.trouble.beans.TroubleQueryStatBean;
import com.cabletech.linepatrol.trouble.dao.TroubleQueryStatDAO;
import com.cabletech.linepatrol.trouble.module.TroubleInfo;
@Service
@Transactional
public class TroubleQueryStatBO extends EntityManager<TroubleInfo,String> {
	@Resource(name="troubleQueryStatDAO")
	private TroubleQueryStatDAO dao;
	
	/**
	 * 查询故障负责代维
	 * @param user
	 * @return
	 */
	@Transactional(readOnly = true)
	public List getContractors(UserInfo user){
		return dao.getContractors(user);
	}
	
	
	/**
	 * 查询已办
	 * @param user
	 * @return
	 */
	public List getHandeledWorks(UserInfo user,String taskName,String taskOutCome){
		String condition="";
		if(taskName!=null && !taskName.equals("")){
			condition += " and handled_task_name='" + taskName + "' ";
		}
	//	condition += " and process.operate_user_id='" + user.getUserID() + "' ";
		//condition += ConditionGenerate.getConditionByInputValues("process.handled_task_name", taskName, "process.handled_task_name");
		//condition += ConditionGenerate.getConditionByInputValues("process.task_out_come", taskOutCome, "process.task_out_come");

		return dao.getHandeledWorks(user,condition,"");
	}
	
	@Transactional(readOnly = true)
	public List getTroubles(TroubleQueryStatBean bean,UserInfo user,List trunks,String act){
		String sqltrunks = this.listParseToStr(trunks);
		bean.setTrunks(sqltrunks);
		return dao.getTroubles(bean,user,act);
	}
	
	/**
	 * 根据条件查询故障信息(三级快捷菜单的条件)
	 * @param condition
	 * @return
	 */
	public List getTroubleByCondition(UserInfo user,String condition){
		return dao.getTroubleByCondition(user,condition);
	}
	
	
	/**
	 * 解析字符串为'',''分割，可以直接用于sql中
	 * @param str
	 * @return
	 */
	public String listParseToStr(List list){
		String sqlstr = "";
		if(list!=null && list.size()>0){
			for(int i = 0;i<list.size();i++){
				if(i==0){
					 sqlstr+="'"+list.get(i)+"'";
				}else{
					 sqlstr+=",'"+list.get(i)+"'";
				}
			}
		}
		return sqlstr;
	}
	
	@Override
	protected HibernateDao<TroubleInfo, String> getEntityDao() {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional
	public List<Map> queryTroubleFromPDA(UserInfo userInfo){
		List<Map> troubles=dao.queryTroubleFromPDA(userInfo);
		return troubles;
	}
	
	public List<Map> queryApprovalTroubleFromPDA(UserInfo userInfo){
		List<Map> approveTroubles=dao.queryApprovalTroubleFromPDA(userInfo);
		return approveTroubles;
	}


	public List<Map> queryEOMSTroubleFromPDA(UserInfo userInfo){
		List<Map> eomsTroubles=dao.queryEOMSTroubleFromPDA(userInfo);
		return eomsTroubles;
		
	}
	
	public Map<Object, Object> loadContractor() throws ServiceException{
		return  dao.getJdbcTemplate().queryForMap("select contractorId,contractorname from contractorinfo where state is null", null);
	}
	
	public List<Map> queryApproveTroubleFromPDA(UserInfo userInfo){
		List<Map> approveTroubles=dao.queryApproveTroubleFromPDA(userInfo);
		return approveTroubles;
	}
	
	public List<Map> queryTroubleNumFromPDA(UserInfo userInfo){
		List<Map> num=new ArrayList<Map>();
		ContractorDAOImpl contractorDao=new ContractorDAOImpl();
		List<Map<String,Object>> troubleNums=dao.queryTroubleNumFromPDA(userInfo);
		List<Contractor> contractors=contractorDao.getAllContractor(userInfo);
		Map<String,Integer> troubleSum=new HashMap<String, Integer>();
		troubleSum.put("currentAllTouble", 0);
		troubleSum.put("currentTrouble", 0);
		troubleSum.put("monthTroubleNum", 0);
		troubleSum.put("finishTrouble", 0);
		for(Contractor contractor:contractors){
			Map<String,Object> trouble=new HashMap<String, Object>();
			setTroubleNum(troubleNums, contractor, trouble, num,troubleSum);
			
		}
		num.add(troubleSum);
		return num;
	}
	private void setTroubleNum(List<Map<String,Object>> troubleNums, Contractor contractor, Map<String, Object> trouble,List<Map> num,Map<String,Integer> troubleSum) {
		int currentAllTouble = 0;
		int currentTrouble = 0;
		int monthTroubleNum = 0;
		int finishTrouble=0;
		for (Map troubleNum : troubleNums) {
			if (contractor.getContractorName().equals(troubleNum.get("contractorName"))) {
				currentAllTouble = setNumber(troubleNum, "0",currentAllTouble);
				currentTrouble = setNumber(troubleNum, "1",currentTrouble);
				monthTroubleNum = setNumber(troubleNum, "2",monthTroubleNum);
				finishTrouble = setNumber(troubleNum, "3",finishTrouble);
			}
		}
		trouble.put("contractorName", contractor.getContractorName());
		trouble.put("0", currentAllTouble);
		trouble.put("1", currentTrouble);
		trouble.put("2", monthTroubleNum);
		trouble.put("3", finishTrouble);
		troubleSum.put("currentAllTouble", troubleSum.get("currentAllTouble")+currentAllTouble);
		troubleSum.put("currentTrouble", troubleSum.get("currentTrouble")+currentTrouble);
		troubleSum.put("monthTroubleNum", troubleSum.get("monthTroubleNum")+monthTroubleNum);
		troubleSum.put("finishTrouble", troubleSum.get("finishTrouble")+finishTrouble);
		num.add(trouble);
	}

	private int setNumber(Map troubleNum, String state,int value) {
		int waitValue=value;
		if (troubleNum.get("state").equals(state)) {
			waitValue = Integer.valueOf(troubleNum.get("num").toString());
		}
		return waitValue;
	}
	
}
