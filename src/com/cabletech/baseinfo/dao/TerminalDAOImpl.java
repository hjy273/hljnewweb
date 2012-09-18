package com.cabletech.baseinfo.dao;

import com.cabletech.baseinfo.domainobjects.*;
import com.cabletech.commons.hb.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.log4j.Logger;
import org.apache.struts.util.LabelValueBean;

import java.sql.*;
import com.cabletech.baseinfo.beans.*;
import java.util.*;
import com.cabletech.commons.sm.SendSMRMI;

public class TerminalDAOImpl extends HibernateDaoSupport{

	Logger logger = Logger.getLogger(this.getClass().getName());
	public TerminalDAOImpl(){
	}


	public Terminal addTerminal( Terminal terminal ) throws Exception{
		this.getHibernateTemplate().save( terminal );
		//this.getSession().flush();
		return terminal;
	}


	public Terminal findById( String id ) throws Exception{
		return( Terminal )this.getHibernateTemplate().load( Terminal.class, id );
	}


	public void removeTerminal( Terminal terminal ) throws Exception{
		this.getHibernateTemplate().delete( terminal );
	}


	public Terminal updateTerminal( Terminal terminal ) throws Exception{
		this.getHibernateTemplate().saveOrUpdate( terminal );
		//this.getSession().flush();
		return terminal;
	}


	/**
	 * 设备编号ID是否被占用
	 * @param terminal Terminal
	 * @return int
	 * @throws Exception
	 */
	public int isIdOccupied( Terminal terminal ) throws Exception{
		//1,被占用  0，未被占用
		int flag = 1;

		String sql = "select count(*) from TERMINALINFO where deviceid = '" +
		terminal.getDeviceID() + "' and state is null";

		System.out.println("检索唯一性:" + sql);

		QueryUtil queryUtil = new QueryUtil();
		String[][] resultArr = queryUtil.executeQueryGetArray( sql, "0" );

		flag = Integer.parseInt( resultArr[0][0] );

		return flag;
	}


	/**
	 * 号码是否被占用
	 * @param terminal Terminal
	 * @return int
	 * @throws Exception
	 */
	public int isNumberOccupied( Terminal terminal ) throws Exception{
		//1,被占用  0，未被占用
		int flag = 1;

		String sql = "select count(*) from TERMINALINFO where SIMNUMBER = '" +
		terminal.getSimNumber() + "' and state is null";

		QueryUtil queryUtil = new QueryUtil();
		String[][] resultArr = queryUtil.executeQueryGetArray( sql, "0" );

		flag = Integer.parseInt( resultArr[0][0] );

		return flag;
	}


	/**
	 * 设备编号ID是否被占用 更新用
	 * @param terminal Terminal
	 * @return int
	 * @throws Exception
	 */
	public int isIdOccupied4Edit( Terminal terminal ) throws Exception{
		//1,被占用  0，未被占用
		int flag = 1;

		String sql = "select count(*) from TERMINALINFO where deviceid = '" +
		terminal.getDeviceID() + "' and state is null and terminalid !='"+terminal.getTerminalID()+"'";

		System.out.println("检索唯一性:" + sql);

		QueryUtil queryUtil = new QueryUtil();
		String[][] resultArr = queryUtil.executeQueryGetArray( sql, "0" );

		flag = Integer.parseInt( resultArr[0][0] );

		return flag;
	}


	/**
	 * 号码是否被占用，更新用
	 * @param terminal Terminal
	 * @return int
	 * @throws Exception
	 */
	public int isNumberOccupied4Edit( Terminal terminal, String oldSimnumber ) throws
	Exception{
		//1,被占用  0，未被占用
		int flag = 1;

		String sql = "select count(*) from TERMINALINFO where SIMNUMBER = '" +
		terminal.getSimNumber() + "' and SIMNUMBER != '" + oldSimnumber +
		"' and state is null";

		////System.out.println(sql);

		QueryUtil queryUtil = new QueryUtil();
		String[][] resultArr = queryUtil.executeQueryGetArray( sql, "0" );

		flag = Integer.parseInt( resultArr[0][0] );

		return flag;
	}


	/**
	 * 指定巡检员是否已经拥有手持设备
	 * @param terminal Terminal
	 * @return int
	 * @throws Exception
	 */
	public int isPatrolmanOccupied( Terminal terminal ) throws Exception{
		//1,被占用  0，未被占用
		int flag = 1;

		String sql = "select count(*) from TERMINALINFO where OWNERID = '" +
		terminal.getOwnerID() + "' and OWNERID !='0'";

		//System.out.println( "检索唯一性" + sql );

		QueryUtil queryUtil = new QueryUtil();
		String[][] resultArr = queryUtil.executeQueryGetArray( sql, "0" );

		flag = Integer.parseInt( resultArr[0][0] );

		//System.out.println( "唯一性 patrolman :" + flag );

		return flag;
	}


	/**
	 * 指定巡检员是否已经拥有手持设备，更新用
	 * @param terminal Terminal
	 * @return int
	 * @throws Exception
	 */
	public int isPatrolmanOccupied4Edit( Terminal terminal, String oldOwnerid ) throws
	Exception{
		//1,被占用  0，未被占用
		int flag = 1;

		String sql = "select count(*) from TERMINALINFO where OWNERID = '" +
		terminal.getOwnerID() + "' and  OWNERID!= '" + oldOwnerid +
		"' and OWNERID !='0'";

		//System.out.println( "检索唯一性" + sql );

		QueryUtil queryUtil = new QueryUtil();
		String[][] resultArr = queryUtil.executeQueryGetArray( sql, "0" );

		flag = Integer.parseInt( resultArr[0][0] );

		//System.out.println( "Flag: " + flag );

		return flag;
	}


	public String[][] getOldValues( Terminal terminal ) throws Exception{
		String sql =
			"select SIMNUMBER, OWNERID from TERMINALINFO where TERMINALID = '" +
			terminal.getTerminalID() + "'";
		QueryUtil queryUtil = new QueryUtil();

		return queryUtil.executeQueryGetArray( sql, "0" );
	}


	public List getSimNumber(UserInfo userinfo){
		List lSim;
		String sql = "select concat(concat(t.SIMNUMBER,',    '),p.PATROLNAME) simnumbername,t.SIMNUMBER "
			+ " from terminalinfo t,patrolmaninfo p "
			+ " where t.OWNERID= p.PATROLID "
			+ "       and (t.STATE != '1'  or t.STATE is null)"
			+ "       and upper(substr(t.TERMINALMODEL,0,2)) = 'CT' "
			+ "	   and t.regionid IN(SELECT     regionid "
			+ "                    FROM REGION  "
			+ "                     CONNECT BY PRIOR regionid = parentregionid  "
			+ "                     START WITH regionid = '" + userinfo.getRegionID() + "')";
		if("2".equals(userinfo.getDeptype()))
			sql +=  "       and p.PARENTID = '" + userinfo.getDeptID() + "' ";
		sql += " order by PATROLNAME ";
		logger.info("SQL:" + sql );
		try{
			QueryUtil qu = new QueryUtil();
			lSim = qu.queryBeans(sql);
			return lSim;
		}
		catch( Exception ex ){
			logger.warn("获得当前单位所有SIM卡号列表出错：" + ex.getMessage());
			return null;
		}
	}

	public List getPhoneBook(UserInfo userinfo){
		List lPhone = new Vector() ;
		ResultSet rst = null;
		String sql = "select t.SIMNUMBER,t.PHONEBOOK,p.PATROLNAME "
			+ " from terminalinfo t,patrolmaninfo p "
			+ " where t.OWNERID= p.PATROLID "
			+ "       and (t.state <> '1' or t.STATE is null) "
			+ "       and upper(substr(t.TERMINALMODEL,0,2)) = 'CT' "
			+ "	   and t.regionid = '" + userinfo.getRegionID() + "'";
		if("2".equals(userinfo.getDeptype()))
			sql +="       and p.PARENTID = '" +  userinfo.getDeptID()+ "' ";
		sql += " order by PATROLNAME	";
		try{
			logger.info("SQL:" + sql);
			QueryUtil qu = new QueryUtil();
			rst = qu.executeQuery(sql);
			while(rst != null && rst.next()){
				String str = rst.getString("phonebook");
				if(str==null || str.trim().equals("")){
					continue;
				}
				String[] phoneArr = str.split(";");
				for(int i =0; i<phoneArr.length; i++){
					PhoneBook pb = new PhoneBook();
					String[] temp = phoneArr[i].split(",");

					pb.setSimNumber(rst.getString("simnumber"));
					pb.setName(temp[0]);
					pb.setPhone(temp[1]);
					lPhone.add(pb);
				}
			}
			if(rst != null)
				rst.close();
			return lPhone;
		}
		catch( Exception ex ){
			logger.warn("查询手持设备通讯录异常:" + ex.getMessage());
			return null;
		}
	}

	public boolean addUpPhoneBook(TerminalBean bean,String[] simnumber,
			String[] name,String[] phone,
			UserInfo userinfo){
		StringBuffer phoneBook =	 new StringBuffer();
		if (name != null){
			for(int i=0;i<name.length;i++){
				phoneBook.append(name[i]);
				phoneBook.append(",");
				phoneBook.append(phone[i]);
				if(i!=name.length-1)
					phoneBook.append(";");
			}
		}
		String sql = "";
		if(bean.getSetnumber().equals("all")){
			sql = "update terminalinfo set phonebook = '" + phoneBook.toString() + "'"
			+ " where simnumber in (select t.SIMNUMBER "
			+ " from terminalinfo t,patrolmaninfo p "
			+ " where t.OWNERID= p.PATROLID "
			+ "       and (t.state <> '1' or t.STATE is null) "
			+ "       and upper(substr(t.TERMINALMODEL,0,2)) = 'CT' "
			+ "       and p.PARENTID = '" +  userinfo.getDeptID()+ "') ";

		}else{
			StringBuffer condition = new StringBuffer();
			for(int i=0; simnumber != null && i<simnumber.length;i++){
				if(i!=simnumber.length-1){
					condition.append("'" + simnumber[i] + "',");
				}else{
					condition.append("'" + simnumber[i] + "'");
				}
			}
			sql = "update terminalinfo set phonebook='" + phoneBook.toString() + "' where simnumber in(  " + condition.toString() + ")";
		}
		logger.warn("SQL:" + sql);
		try{
			UpdateUtil up = new UpdateUtil();
			up.executeUpdate(sql);
			up.commit();
			//todo 发送短信.
			SendSMRMI.setPhoneBook(bean,userinfo,simnumber);
			return true;
		}
		catch( Exception ex ){
			logger.warn("更新通讯录出错：" + ex.getMessage());
			return false;
		}
	}
	public boolean updateTerminal(String sql){
		try{
			UpdateUtil up = new UpdateUtil();
			up.executeUpdate(sql);
			up.commit();
			return true;
		}
		catch( Exception ex ){
			logger.warn("更新出错：" + ex.getMessage());
			return false;
		}
	}
	public List queryTerminal(String sql){
		BasicDynaBean dynaBean = null;
		ArrayList lableList = new ArrayList();
		try{
			QueryUtil query = new QueryUtil();
			Iterator it = query.queryBeans( sql ).iterator();
			while( it.hasNext() ){
				dynaBean = ( BasicDynaBean )it.next();
				lableList.add( new LabelValueBean( ( String ) ( dynaBean.get( "deviceid" ) ),
						( String ) ( dynaBean.get( "terminalid" ) ) ) );
				//lableList.add(dynaBean);
			}
			logger.info("lableList "+lableList);
			return lableList;
		}catch(Exception e){
			logger.error("查询设备信息 异常:" + e.getMessage());
			return null;
		}
	}

}
