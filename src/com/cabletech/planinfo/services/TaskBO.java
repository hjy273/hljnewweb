package com.cabletech.planinfo.services;

import java.sql.ResultSet;
import java.util.*;

import org.apache.commons.beanutils.*;
import org.apache.struts.util.*;
import com.cabletech.commons.base.*;
import com.cabletech.commons.hb.*;
import com.cabletech.commons.services.*;
import com.cabletech.planinfo.beans.StencilSubTaskBean;
import com.cabletech.planinfo.beans.TaskBean;
import com.cabletech.planinfo.dao.*;
import com.cabletech.planinfo.domainobjects.*;
import org.apache.log4j.*;

public class TaskBO extends BaseBisinessObject {
	Logger logger = Logger.getLogger("TaskBO");

	TaskDAOImpl dao = new TaskDAOImpl();

	StencilBO sbo = new StencilBO();
	
	public Map getPSublineByPatrol(String patrolid,String linelevel) throws Exception{
		String sql = "select sl.SUBLINEID,sl.sublinename from sublineinfo sl,lineinfo l " +
				" where sl.patrolid='"+patrolid+"' and sl.lineid = l.lineid and l.LINETYPE='"+linelevel+"'" +
				" order by sl.sublineid";
		QueryUtil query = new QueryUtil();
		List sublines  = query.queryBeans(sql);
		
		Map sublineMap = new HashMap();
		for(int i =0;i< sublines.size();i++){
			BasicDynaBean bean = (BasicDynaBean)sublines.get(i);
			sublineMap.put(bean.get("sublineid").toString(), bean.get("sublinename").toString());
		}
		//System.out.println(sublineMap);
		return sublineMap;
	}
	public String getPUnitListByPatrolid(String patrolid,String linelevel,String sel_points) throws Exception{
		List taskSubline = new ArrayList();
		long startTime = System.currentTimeMillis();
		
		String sql = "select sl.SUBLINEID sublineid,sl.sublinename sublinename,p.POINTID pointid,p.POINTNAME pointname,p.ADDRESSINFO address,p.ISFOCUS isfocus " +
				" from sublineinfo sl,lineinfo l,pointinfo p " +
				" where p.state = '1' and l.LINETYPE= '" +linelevel+"'"+
				" and sl.patrolid='"+patrolid+"'" +
				" and p.sublineid = sl.sublineid and sl.lineid = l.lineid " +
				" order by sl.sublineid,p.INUMBER";
		QueryUtil query = new QueryUtil();
		System.out.println("sql: "+sql);
		//ResultSet rs = query.executeQuery(sql);
		
		taskSubline =  query.queryBeans(sql);
		long currenttime = System.currentTimeMillis();
		logger.info("查询用时：" +(currenttime-startTime)+"ms");
		StringBuffer htmlTaskSubline = new StringBuffer();
		StringBuffer htmlTaskSubline2 = new StringBuffer();
		if(taskSubline.size()>0){
			int size = taskSubline.size();
			htmlTaskSubline.append("<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"5\">\n");
			String previous = "";
			String current = "";
			String next = "";
			int index = 0;
			String checked = "checked";
			String checkedSubLind = "";
			long fortime = System.currentTimeMillis();
			for(int i=0;i<size;i++){
				BasicDynaBean bean = (BasicDynaBean)taskSubline.get(i);
				if(i!=size-1){
					BasicDynaBean nbean = (BasicDynaBean)taskSubline.get(i+1);
					next = nbean.get("sublineid").toString();
				}
				if(!"".equals(sel_points)){
					if(sel_points.indexOf(bean.get("pointid").toString())== -1){
						checked="";
					}else{
						checked = "checked";
						checkedSubLind = "checked";
					}
				} else {
					checkedSubLind = "checked";
				}
				
				current = bean.get("sublineid").toString();
				if(!current.equals(previous)){
					htmlTaskSubline.append("<tr id=\"typeTr\" index=\""+index+"\">\n");
					htmlTaskSubline.append("<td colspan=\"2\">\n");
					htmlTaskSubline.append("<input type=\"checkbox\" name=\"typeCheck\" id=\"typeCheck\" value=\""+bean.get("sublineid")+"\" onclick=\"setSon(this)\" "); //+checked+">\n"
					htmlTaskSubline2.append("<input type=\"text\" name=\"typeCheck"+index+"\" id=\"typeCheck_"+bean.get("sublineid")+"\" index=\""+index+"\" style=\"border:0;background-color:transparent;width:160;cursor:hand;cursor:pointer\" value=\""+bean.get("sublinename")+"\" onclick=\"showHideSubTr(this,'"+bean.get("sublineid")+"')\" readonly>\n");
					htmlTaskSubline2.append("<a href=\"javascript:open_map('"+bean.get("sublineid")+"','0');\">位置查看</a>\n");
					htmlTaskSubline2.append("</td>\n");
					htmlTaskSubline2.append("</tr>\n");
					htmlTaskSubline2.append("<tr id=\"detailTr_"+bean.get("sublineid")+"\" index=\""+index+"\" status=\"\" style=\"display:none\">\n");
					htmlTaskSubline2.append("<td colspan=\"2\">\n");
					htmlTaskSubline2.append("<table width=\"92%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n");
				}
				
				htmlTaskSubline2.append("<tr>\n");
				htmlTaskSubline2.append(" <td width=\"73\">&nbsp;</td>\n");
				htmlTaskSubline2.append(" <td width=\"920\">\n");//220
				htmlTaskSubline2.append(" <input type=\"checkbox\" name=\"subtaskpoint\" id=\"point_"+bean.get("sublineid")+"_"+i+"\" parentV=\""+bean.get("sublineid")+"\" value=\""+bean.get("pointid")+"\" onclick=\"setParent(this,'"+bean.get("sublineid")+"')\" nameV=\""+bean.get("pointname")+"\" "+checked+">\n");
				htmlTaskSubline2.append(bean.get("pointname"));
				htmlTaskSubline2.append(" </td>\n");
				//htmlTaskSubline.append("<td width=\"500\"></td>\n");//"+bean.get("address")+"
				//htmlTaskSubline.append("<td width=\"200\">\n");
				//if(!bean.get("isfocus").equals("0")){
				//	htmlTaskSubline.append("关键点");
				//}
				//htmlTaskSubline.append("</td>\n");
				htmlTaskSubline2.append("</tr>\n");
				
				if(!current.equals(next)){
					htmlTaskSubline.append(checkedSubLind+">\n");
					htmlTaskSubline.append(htmlTaskSubline2);
					htmlTaskSubline.append("</table>\n");
					htmlTaskSubline.append("</td>\n");
					htmlTaskSubline.append("</tr>\n");
					checkedSubLind = "";
					htmlTaskSubline2 = new StringBuffer();
					index++;
				}
				previous = current;
				current = "";
				next = "";
			}
			long endfor = System.currentTimeMillis();
			logger.info("循环时间:"+(endfor -fortime)+"ms");
			htmlTaskSubline.append("</table>\n");

		}else{
			htmlTaskSubline.append("<table width=\"85%\" border=\"0\"  cellpadding=\"0\" cellspacing=\"0\" >");
			htmlTaskSubline.append("<tr > <td  width=\"81%\" style=\"color:red\">");
			htmlTaskSubline.append("该巡检员在此线路级别内没有负责的线段，请选择该巡检员对应的巡检线段的线路级别 !");
			htmlTaskSubline.append("</td> </tr> </table>");
		}
		long endTime = System.currentTimeMillis();
		long total=(endTime - startTime);
		logger.info("开始时间   "+startTime+"   结束时间  "+endTime +"用时 ："+(endTime - startTime));
		logger.info("查询某一级别线路所用时间："+total+"毫秒") ;
		return htmlTaskSubline.toString();
	}
	/**
	 * 选出巡检员对应的 分类/分类下所有点
	 * 
	 * @param patrolid
	 *            巡检员id
	 * @param fID
	 *            2 按线段; 1 按点显示
	 * @param linelevel
	 *            线路级别
	 * @return
	 * @throws Exception
	 */
//	public Vector getPUnitListByPatrolid(String patrolid, String fID,
//			String linelevel) throws Exception {
//		Vector pointUnitsVct = new Vector();
//		long startTime = System.currentTimeMillis();
//		String sql = getTypeSQL(patrolid, fID, linelevel);
//		DBService dbservice = new DBService();
//		String[][] typeArr = dbservice.queryArray(sql, "");
//
//		if (typeArr != null && typeArr.length > 0) {
//
//			for (int i = 0; i < typeArr.length; i++) {
//
//				Vector oneUnitVct = new Vector();
//
//				oneUnitVct.add(typeArr[i][0]);
//				oneUnitVct.add(typeArr[i][1]);
//
//				sql = getPointInTypeSQL(patrolid, fID, typeArr[i][0], linelevel);
//				oneUnitVct.add(dbservice.queryVector(sql, ""));
//
//				pointUnitsVct.add(oneUnitVct);
//
//			}
//			pointUnitsVct = reMakeVct(pointUnitsVct);
//		}
//		long endTime = System.currentTimeMillis();
//		long total=(endTime - startTime)/1000;
//		logger.info("查询某一级别线路所用时间："+total/60+"分"+total%60+"秒") ;
//		return pointUnitsVct;
//	}

	/**
	 * 加载任务模板
	 * 
	 * @param stencilid
	 * @return
	 */
	public List toLoad(String stencilid) {
		// 读取模板信息
		List StencilList = sbo.getStencilTaskList(stencilid);
		// 将模板封装成TaskBean ,并将Taskbean放到list中
		List taskList = encapsulation(StencilList);
		return taskList;
	}

	/**
	 * 将任务模板封装成taskBean对象
	 * 		（加载模板时，巡检线段信息没有加载）
	 * @param StencilList
	 *            任务模板List
	 * @return list
	 */
	private List encapsulation(List StencilList) {
		List taskList = new ArrayList();
		for (int i = 0; i < StencilList.size(); i++) {
			TaskBean taskBean = new TaskBean();
			StencilSubTaskBean stenciltask = (StencilSubTaskBean) StencilList
					.get(i);
			taskBean.setExcutetimes(stenciltask.getExcutetimes().toString());
			taskBean.setDescribtion(stenciltask.getDescription());
			taskBean.setEvaluatepoint(stenciltask.getEvaluatepoint());
			taskBean.setLinelevel(stenciltask.getLinelevel());
			taskBean.setLineleveltext(stenciltask.getLineleveltext());
			for (int j = 0; j < stenciltask.getStencilsubtaskop().size(); j++) {
				TaskOperationList taskOpeList = new TaskOperationList();
				StencilSubTaskOper s_taskoper = (StencilSubTaskOper) stenciltask
						.getStencilsubtaskop().get(j);
				taskOpeList.setOperationid(s_taskoper.getToid());
				taskBean.Add(taskOpeList);
			}
			for (int k = 0; k < stenciltask.getStenciltaskpoint().size(); k++) {
				SubTask taskpoint = new SubTask();
				StencilTaskPoint s_taskpoint = (StencilTaskPoint) stenciltask
						.getStenciltaskpoint().get(k);
				taskpoint.setObjectid(s_taskpoint.getPointid());
				taskBean.Add(taskpoint);
			}
			List list = getSublineid(stenciltask.getID());
//			for(int j=0;j<list.size();j++){
//				logger.info(list.get(j));
//			}
			taskBean.setTaskSubline(list);
			//------------------------------------
			logger.info("taskid "+stenciltask.getTaskpoint());
			StencilBO stencilbo = new StencilBO();
			taskBean.setTaskpoint(stencilbo.getTaskPointNum(stenciltask.getID()));
			taskBean.setSubline(stencilbo.getTaskSubline(stenciltask.getID()));
			// guixy add by 2009-8-28
			taskBean.setFactpointsnum(stenciltask.getFactpointsnum());
			taskList.add(taskBean);

		}
		return taskList;
	}
	public List getSublineid(String taskid){
		String sql = "select pt.sublineid sublineid from taskpoint_stencil tp,pointinfo pt,subtask_stencil st " +
				" where st.taskid=tp.taskid and tp.pointid=pt.pointid and " +
				" st.taskid='"+taskid+"' group by pt.sublineid ";
		logger.info("sql:"+sql);
		List sublineList = new ArrayList();
		PlanTaskSubline tasksubline = null;
		try {
			QueryUtil query = new QueryUtil();
			ResultSet rs = query.executeQuery(sql);
			while(rs.next()){
				tasksubline = new PlanTaskSubline();
				tasksubline.setSublineid(rs.getString("sublineid"));
				sublineList.add(tasksubline);
			}
			return sublineList;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("getSublineid() throws"+ e.getMessage());
			return null;
		}
		
         
	}

	/**
	 * 新增年月计划任务
	 * 
	 * @param task
	 *            Task
	 * @throws Exception
	 */
	public void addTask(Task task) throws Exception {
		dao.addTask(task);
	}

	/**
	 * 更新一个年月计划任务
	 * 
	 * @param task
	 *            Task
	 * @return Task
	 * @throws Exception
	 */
	public Task updateTask(Task task) throws Exception {
		return dao.updateTask(task);
	}

	/**
	 * 载入一个年月计划任务
	 * 
	 * @param id
	 *            String
	 * @return Task
	 * @throws Exception
	 */
	public Task loadTask(String id) throws Exception {
		return dao.findById(id);
	}

	/**
	 * 取得操作列表
	 * 
	 * @param regionid
	 *            String
	 * @return Vector
	 * @throws Exception
	 */
	public Vector getTaskOperations(String regionid) throws Exception {
		Vector vct = new Vector();

		String sql = " select distinct id , operationname from taskoperation ";
		// if (regionid.length() > 0) {
		// sql = sql + " where regionid = '" + regionid + "'";
		// sql = sql + " ";
		// }

		// System.out.println( "取得巡检操作 ：" + sql );

		DBService dbservice = new DBService();
		vct = dbservice.queryVector(sql, "");
		return vct;
	}

	/**
	 * 取得某任务操作列表,通过任务id
	 * 
	 * @param regionid
	 *            String
	 * @return Vector
	 * @throws Exception
	 */
	public Vector getTaskOperListByTaskid(String taskid) throws Exception {
		Vector vct = new Vector();

		String sql = " select operationid from taskoperationlist ";
		sql = sql + " where taskid = '" + taskid + "'";
		// //System.out.println( "SQL:" + sql );
		DBService dbservice = new DBService();
		vct = dbservice.queryVector(sql, "");
		return vct;
	}

	public void addSubTask(SubTask subtask) throws Exception {
		dao.addSubTask(subtask);
	}

	/**
	 * 新增任务操作对应表
	 * 
	 * @param subtask
	 *            SubTask
	 * @throws Exception
	 */
	public void addTaskoperationlist(TaskOperationList taskoperationlist)
			throws Exception {
		dao.addTaskoperationlist(taskoperationlist);
	}

	/**
	 * 通过任务id取得任务对应点
	 * 
	 * @param taskid
	 *            String
	 * @return Vector
	 * @throws Exception
	 */
	public ArrayList getPointsSubtaskList(String taskid) throws Exception {
		String sql = new String();
		sql = "select distinct b.pointname pointname,b.pointid pointid from subtaskinfo a,pointinfo b where a.objectid = b.pointid and taskid='"
				+ taskid + "' and b.state='1'";

		BasicDynaBean bdb;
		ArrayList lableValueList = new ArrayList();

		QueryUtil jutil = new QueryUtil();
		Iterator it = jutil.queryBeans(sql).iterator();
		while (it.hasNext()) {
			bdb = (BasicDynaBean) it.next();
			lableValueList.add(new LabelValueBean((String) (bdb
					.get("pointname")), (String) (bdb.get("pointid"))));
		}
		return lableValueList;
	}

//	public Vector getPUnitListByPatrolid(String patrolid, String fID)
//			throws Exception {
//		Vector pointUnitsVct = new Vector();
//
//		String sql = getTypeSQL(patrolid, fID);
//		DBService dbservice = new DBService();
//		String[][] typeArr = dbservice.queryArray(sql, "");
//
//		if (typeArr != null && typeArr.length > 0) {
//
//			for (int i = 0; i < typeArr.length; i++) {
//
//				Vector oneUnitVct = new Vector();
//
//				oneUnitVct.add(typeArr[i][0]);
//				oneUnitVct.add(typeArr[i][1]);
//
//				sql = getPointInTypeSQL(patrolid, fID, typeArr[i][0]);
//				oneUnitVct.add(dbservice.queryVector(sql, ""));
//
//				pointUnitsVct.add(oneUnitVct);
//
//			}
//			pointUnitsVct = reMakeVct(pointUnitsVct);
//		}
//		return pointUnitsVct;
//	}

	// 为解决计划导出点数和子任务导出点数不一致而重新写的
	public Vector newGetPUnitListByPatrolid(String planid) throws Exception {
		Vector pointUnitsVct = new Vector();
		String[][] typeArr;
		String sql = "select distinct l.SUBLINEID, l.SUBLINENAME"
				+ " from subtaskinfo s,pointinfo p,sublineinfo l"
				+ " where s.OBJECTID = p.POINTID and p.SUBLINEID=l.SUBLINEID"
				+ " and taskid in("
				+ "         select taskid from plantasklist where planid='"
				+ planid + "')";
		// //System.out.println("get list SQL:" + sql);
		try {
			QueryUtil dbservice = new QueryUtil();
			typeArr = dbservice.executeQueryGetArray(sql, "");
		} catch (Exception e) {
			logger.warn("获取线段信息出错：" + e.getMessage());
			return null;
		}
		if (typeArr != null && typeArr.length > 0) {
			for (int i = 0; i < typeArr.length; i++) {
				Vector oneUnitVct = new Vector();
				oneUnitVct.add(typeArr[i][0]);
				oneUnitVct.add(typeArr[i][1]);
				sql = " select distinct p.POINTID,p.pointname,p.ADDRESSINFO,p.INUMBER"
						+ " from subtaskinfo s,pointinfo p,sublineinfo l , taskinfo t "
						+ " where s.OBJECTID = p.POINTID and p.SUBLINEID=l.SUBLINEID and l.SUBLINEID='"
						+ typeArr[i][0]
						+ "'"
						+ " and t.ID =s.TASKID  and p.state = '1' "
						+ " and taskid in("
						+ "           select taskid from plantasklist where planid='"
						+ planid + "')" + " order by p.inumber ";
				// //System.out.println("SQL:" + sql);
				try {
					QueryUtil qu = new QueryUtil();
					oneUnitVct.add(qu.executeQueryGetStringVector(sql, ""));
				} catch (Exception w) {
					logger.warn("获得线段下的点出错:" + w.getMessage());
					return null;
				}
				pointUnitsVct.add(oneUnitVct);
			}
			pointUnitsVct = reMakeVct(pointUnitsVct);
		}
		return pointUnitsVct;
	}

	/**
	 * 选出任务对应的 分类/分类下所有点
	 * 
	 * @param patrolid
	 *            String
	 * @param fID
	 *            String
	 * @return Vector
	 */
	public Vector getTUnitListByPatrolid(String patrolid, String fID,
			String taskid) throws Exception {
		Vector pointUnitsVct = new Vector();

		String sql = getSublineFromTask(taskid);
		DBService dbservice = new DBService();
		String[][] typeArr = dbservice.queryArray(sql, "");

		if (typeArr != null && typeArr.length > 0) {

			for (int i = 0; i < typeArr.length; i++) {

				Vector oneUnitVct = new Vector();

				oneUnitVct.add(typeArr[i][0]);
				oneUnitVct.add(typeArr[i][1]);

				sql = getPointInSublineAndTask(typeArr[i][0], taskid);
				oneUnitVct.add(dbservice.queryVector(sql, ""));

				pointUnitsVct.add(oneUnitVct);

			}
			pointUnitsVct = reMakeVct(pointUnitsVct);
		}
		return pointUnitsVct;
	}

	/**
	 * 导出巡检员负责点列表
	 * 
	 * @param patrolid
	 *            String
	 * @param fID
	 *            String
	 * @return Vector
	 * @throws Exception
	 */
	public Vector getPUnitListDetail(String patrolid, String fID)
			throws Exception {
		Vector pointUnitsVct = new Vector();

		String sql = getTypeSQL(patrolid, fID);
		DBService dbservice = new DBService();
		String[][] typeArr = dbservice.queryArray(sql, "");

		if (typeArr != null && typeArr.length > 0) {

			for (int i = 0; i < typeArr.length; i++) {

				Vector oneUnitVct = new Vector();

				oneUnitVct.add(typeArr[i][0]);
				oneUnitVct.add(typeArr[i][1]);

				sql = getPointInTypeSQL(patrolid, fID, typeArr[i][0]);
				oneUnitVct.add(dbservice.queryVector(sql, ""));

				pointUnitsVct.add(oneUnitVct);

			}
			pointUnitsVct = reMakeVct(pointUnitsVct);
		}
		return pointUnitsVct;
	}

	/**
	 * 重新安排一个Vector
	 * 
	 * @param pointUnitsVct
	 *            Vector
	 * @return Vector
	 */
	private Vector reMakeVct(Vector pointUnitsVct) {
		return pointUnitsVct;
	}

	/**
	 * 取得点分类的sql
	 * 
	 * @param patrolid
	 *            String
	 * @param fID
	 *            String 1, 按点类型分类 2, 按线段分类
	 * @return String
	 */
	public String getTypeSQL(String patrolid, String fID, String linelevel) {

		String sql = "\n";
		if (fID.equals("1")) { // 按点类型分类
			sql += " select distinct b.linetype linetype, decode(b.linetype,'00','直埋','01','架空','02','管道','架空') linetypename from pointinfo a, sublineinfo b, lineinfo c where  \n";
		} else { // 按线段分类
			sql += " select distinct b.sublineid sublineid , sublinename sublinename from pointinfo a, sublineinfo b , lineinfo c where  \n";
		}
		sql += "a.sublineid = b.sublineid and b.lineid = c.lineid and b.patrolid = '"
				+ patrolid + "' and c.linetype = '" + linelevel + "' ";

		logger.info("分类SQL :" + sql);

		return sql;
	}

	public String getTypeSQL(String patrolid, String fID) {

		String sql = "\n";
		if (fID.equals("1")) { // 按点类型分类
			sql += " select distinct b.linetype linetype, decode(b.linetype,'00','直埋','01','架空','02','管道','架空') linetypename from pointinfo a, sublineinfo b where  \n";
		} else { // 按线段分类
			sql += " select distinct b.sublineid sublineid , sublinename sublinename from pointinfo a, sublineinfo b where  \n";
		}
		sql += "a.sublineid = b.sublineid and b.patrolid = '" + patrolid + "'";

		// //System.out.println("分类SQL :" + sql);

		return sql;
	}

	/**
	 * 取得一个子任务的线段
	 * 
	 * @param patrolid
	 *            String
	 * @param fID
	 *            String
	 * @return String
	 */
	public String getSublineFromTask(String taskid) {

		String sql = "\n";
		sql += " select distinct  d.sublineid , d.sublinename from taskinfo	a,subtaskinfo	b,pointinfo	c,sublineinfo	d where ";
		sql += " a.id = b.taskid		and	b.OBJECTID = c.POINTID	and	c.SUBLINEID = d.sublineid ";
		sql += " and a.id = '" + taskid + "' ";

		// //System.out.println( "取得一个子任务的线段SQL :" + sql );

		return sql;
	}

	/**
	 * 取得某个分类下的所有点
	 * 
	 * @param patrolid
	 *            String
	 * @param fID
	 *            String
	 * @return String
	 */
	public String getPointInTypeSQL(String patrolid, String fID, String typeid) {

		String sql = "\n";

		sql += "select distinct a.pointid pointid, a.pointname pointname, a.ADDRESSINFO ADDRESSINFO, a.inumber  from pointinfo a, sublineinfo b where   \n";
		sql += "a.sublineid = b.sublineid and b.patrolid = '" + patrolid + "'";
		if (fID.equals("1")) { // 按点类型分类
			sql += " and b.linetype = '" + typeid + "' \n";
		} else { // 按线段分类
			sql += "and b.sublineid = '" + typeid + "' \n";
		}

		sql += " and a.state = '1' ";

		sql += " order by a.inumber ";

		// //System.out.println( "分类内点的SQL :" + sql );

		return sql;
	}

	public String getPointInTypeSQL(String patrolid, String fID, String typeid,
			String linelevel) {

		String sql = "\n";

		sql += "select distinct a.pointid pointid, a.pointname pointname, a.ADDRESSINFO ADDRESSINFO,ISFOCUS isfocus, a.inumber from pointinfo a, sublineinfo b, lineinfo c where   \n";
		sql += "a.sublineid = b.sublineid and b.lineid = c.lineid and b.patrolid = '"
				+ patrolid + "' and c.linetype='" + linelevel + "' ";
		if (fID.equals("1")) { // 按点类型分类
			sql += " and b.linetype = '" + typeid + "' \n";
		} else { // 按线段分类
			sql += "and b.sublineid = '" + typeid + "' \n";
		}

		sql += " and a.state = '1' ";

		sql += " order by a.inumber ";

		//logger.info("分类内点的SQL :" + sql);

		return sql;
	}

	/**
	 * 取得某个段下的所有点
	 * 
	 * @param patrolid
	 *            String
	 * @param fID
	 *            String
	 * @return String
	 */
	public String getPointInSublineAndTask(String sublineId, String taskid) {

		String sql = "\n";

		sql += "select distinct a.pointid pointid, a.pointname pointname, a.ADDRESSINFO ADDRESSINFO, a.inumber  from pointinfo a, sublineinfo b, taskinfo c, subtaskinfo d where   \n";
		sql += "a.sublineid = b.sublineid and a.pointid = d.objectid and c.id =d.taskid  and c.id = '"
				+ taskid + "'";

		sql += " and b.sublineid = '" + sublineId
				+ "'  and a.state = '1'  order by a.inumber \n";

		// //System.out.println( "分类内点的SQL :" + sql );

		return sql;
	}

	/**
	 * 删除原有对应纪录
	 * 
	 * @param taskid
	 *            String
	 * @throws Exception
	 */
	public void removeRelatedOperationsAndSubtask(String taskid)
			throws Exception {
		String sql = "delete from taskoperationlist where taskid = '" + taskid
				+ "'";

		UpdateUtil util = new UpdateUtil();
		util.executeUpdate(sql);

		sql = "delete from subtaskinfo where taskid = '" + taskid + "'";

		util = new UpdateUtil();
		util.executeUpdate(sql);
	}

	/**
	 * 将任务点转换为以,分割的字符串.供页面选取使用.
	 * 
	 * @param taskPoint
	 * @return
	 */
	public String getStrTaskPoint(List taskPoint) {
		StringBuffer str_taskpoint = new StringBuffer();// 存放点id字符串
		for (int i = 0; i < taskPoint.size(); i++) {
			SubTask subtask = (SubTask) taskPoint.get(i);
			str_taskpoint.append(subtask.getObjectid().toString());
			str_taskpoint.append(",");
		}
		
		return str_taskpoint.toString();
	}
	
	
	/**
	 * 根据维护组获取线段ID
	 * @param patrolid
	 * @param linelevel
	 * @param string
	 * @return
	 * @throws Exception
	 */
	public String getSublineByPatrolid(String patrolid, String linelevel,
			String string) throws Exception {
		List taskSubline = new ArrayList();
		long startTime = System.currentTimeMillis();
		
		String sql = "select sl.SUBLINEID sublineid,sl.sublinename sublinename "
				+ " from sublineinfo sl,lineinfo l"
				+ " where l.LINETYPE= "+ linelevel
				+ " and sl.patrolid='"+ patrolid + "'"
				+ " and  sl.lineid = l.lineid" + " order by sl.sublineid";
		QueryUtil query = new QueryUtil();
		//System.out.println("sql: "+sql);
		
		taskSubline =  query.queryBeans(sql);
		long currenttime = System.currentTimeMillis();
		logger.info("查询用时：" +(currenttime-startTime)+"ms");
		StringBuffer htmlTaskSubline = new StringBuffer();
		if(taskSubline.size()>0){
			int size = taskSubline.size();
			htmlTaskSubline.append("<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"5\">\n");
			int index = 0;
			String checked = "checked";
			long fortime = System.currentTimeMillis();
			for(int i=0;i<size;i++){
				BasicDynaBean bean = (BasicDynaBean)taskSubline.get(i);
				
				htmlTaskSubline.append("<tr id=\"typeTr\" index=\""+index+"\" onclick>\n");
				htmlTaskSubline.append("<td colspan=\"2\">\n");
				htmlTaskSubline.append("<input type=\"checkbox\" name=\"typeCheck\" value=\""+bean.get("sublineid")+"\" onclick=\"setSon(this)\" "+checked+">\n");
				htmlTaskSubline.append("<input type=\"text\" name=\"typeCheck"+index+"\" idV=\""+bean.get("sublineid")+"\" index=\""+index+"\" style=\"border:0;background-color:transparent;width:160;cursor:hand\" value=\""+bean.get("sublinename")+"\" onclick=\"showHideSubTr(this)\" readonly>\n");
				htmlTaskSubline.append("<a href=\"javascript:open_map('"+bean.get("sublineid")+"','0');\">位置查看</a>\n");
				htmlTaskSubline.append("</td>\n");
				htmlTaskSubline.append("</tr>\n");
				htmlTaskSubline.append("<tr id=\"detailTr\" index=\""+index+"\" status=\"\" style=\"display:none\">\n");
				htmlTaskSubline.append("<td colspan=\"2\">\n");
				htmlTaskSubline.append("<span id=\""+bean.get("sublineid")+"\"></span>");
				htmlTaskSubline.append("</td>\n");
				htmlTaskSubline.append("</tr>\n");
				index++;	
				
			}
			long endfor = System.currentTimeMillis();
			logger.info("循环时间:"+(endfor -fortime)+"ms");
			htmlTaskSubline.append("</table>\n");

		}else{
			htmlTaskSubline.append("<table width=\"85%\" border=\"0\"  cellpadding=\"0\" cellspacing=\"0\" >");
			htmlTaskSubline.append("<tr > <td  width=\"81%\" style=\"color:red\">");
			htmlTaskSubline.append("该巡检员在此线路级别内没有负责的线段，请选择该巡检员对应的巡检线段的线路级别 !");
			htmlTaskSubline.append("</td> </tr> </table>");
		}
		long endTime = System.currentTimeMillis();
		long total=(endTime - startTime);
		logger.info("开始时间   "+startTime+"   结束时间  "+endTime +"用时 ："+(endTime - startTime));
		logger.info("查询某一级别线路所用时间："+total+"毫秒") ;
		///System.out.println(htmlTaskSubline.toString());
		return htmlTaskSubline.toString();
	}
	
	
	/**
	 * 根据线段ID 获取 点信息
	 * @param sublineid
	 * @return
	 * @throws Exception
	 */
	public String getPointsBySublineid(String sublineid) throws Exception {
		List taskPoints = new ArrayList();
		long startTime = System.currentTimeMillis();
		
		String sql = "select p.SUBLINEID sublineid,p.POINTID pointid,p.POINTNAME pointname,p.ADDRESSINFO address,p.ISFOCUS isfocus from pointinfo p where p.state='1' and p.sublineid = '"+sublineid+"'";
		QueryUtil query = new QueryUtil();
		//System.out.println("sql: "+sql);
		
		taskPoints =  query.queryBeans(sql);
		long currenttime = System.currentTimeMillis();
		logger.info("查询用时：" +(currenttime-startTime)+"ms");
		StringBuffer htmlTaskSubline = new StringBuffer();
		if(taskPoints.size()>0){
			int size = taskPoints.size();
			htmlTaskSubline.append("<table width=\"92%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n");
			String checked = "checked";
			long fortime = System.currentTimeMillis();
			for(int i=0;i<size;i++){
				BasicDynaBean bean = (BasicDynaBean)taskPoints.get(i);
				
				htmlTaskSubline.append("<tr>\n");
				htmlTaskSubline.append("<td width=\"73\">&nbsp;</td>\n");
				htmlTaskSubline.append(" <td width=\"220\">\n");
				htmlTaskSubline.append(" <input type=\"checkbox\" name=\"subtaskpoint\" parentV=\""+bean.get("sublineid")+"\" value=\""+bean.get("pointid")+"\" onclick=\"setParent(this)\" nameV=\""+bean.get("pointname")+"\" "+checked+">\n");
				htmlTaskSubline.append(bean.get("pointname"));
				htmlTaskSubline.append("</td>\n");
				htmlTaskSubline.append("<td width=\"500\">"+bean.get("address")+"</td>\n");
				htmlTaskSubline.append("<td width=\"200\">\n");
				if(!bean.get("isfocus").equals("0")){
					htmlTaskSubline.append("关键点");
				}
				htmlTaskSubline.append("</td>\n");
				htmlTaskSubline.append("</tr>\n");
				
			}
			long endfor = System.currentTimeMillis();
			logger.info("循环时间:"+(endfor -fortime)+"ms");
			htmlTaskSubline.append("</table>\n");

		}else{
			htmlTaskSubline.append("没有找到巡检点!");
		}
		long endTime = System.currentTimeMillis();
		long total=(endTime - startTime);
		logger.info("开始时间   "+startTime+"   结束时间  "+endTime +"用时 ："+(endTime - startTime));
		logger.info("查询某一级别线路所用时间："+total+"毫秒") ;
		return htmlTaskSubline.toString();
	}

}
