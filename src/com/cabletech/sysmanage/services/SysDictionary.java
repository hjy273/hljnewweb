package com.cabletech.sysmanage.services;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.cabletech.commons.hb.QueryUtil;

public class SysDictionary {
	private Logger logger = Logger.getLogger("SysDictionary");
	/**
	 *<p>功能:判断巡检人员管理是按组还是按人进行管理,默认是按组进行管理
	 *<p>参数:
	 *<p>返回:如果是按组管理返回1,不是按组管理返回0
	 */
	@Transactional(readOnly=true)
	public String isManageByArry() {
		String sql = "select type from sysdictionary where lable='巡检人员管理方式'";
		String[][] rst;
		try {
			QueryUtil query = new QueryUtil();
			rst = query.executeQueryGetArray(sql, "1");
			return rst[0][0];
		} catch (Exception e) {
			logger.error("判断巡检人员管理方式出现异常:" + e.getMessage());
			return "1";
		}
	}

	/**
	 *<p>功能:判断程序中是否显示光缆信息,默认是显示的.
	 * 		该条记录是关是否显示光缆信息的配置.当type值为"1"时,显示光缆信息.
	 *		当type值为"0"或为null时,不显示光缆信息
	 * *<p>参数:
	 *<p>返回:如果是按组管理返回1,不是按组管理返回0
	 */
	public String isShowFIB() {
		String sql = "select type from sysdictionary where lable='是否显示光缆信息'";
		String[][] rst;
		try {
			QueryUtil query = new QueryUtil();
			rst = query.executeQueryGetArray(sql, "1");
			return rst[0][0];
		} catch (Exception e) {
			logger.error("判断程序中是否显示光缆信息异常:" + e.getMessage());
			return "1";
		}
	}

	/**
	 *<p>功能:判断程序中有新的申请或审批情况时是否给目标处理人或申请人发送短信,默认是发送的.
	 * 		当type值为"1"时,显示给目标处理人或申请人发送短信.
	 *		当type值为"0"或为null时,反之.
	 *<p>参数:
	 *<p>返回:如果发送返回1,不不发送返回0.
	 */
	public String isSendSM() {
		String sql = "select type from sysdictionary where lable='是否给目标处理人发送短信'";
		String[][] rst;
		try {
			QueryUtil query = new QueryUtil();
			rst = query.executeQueryGetArray(sql, "1");
			return rst[0][0];
		} catch (Exception e) {
			logger.error("判断有新的申请或审批情况时是否给目标处理人或申请人发送短信异常:" + e.getMessage());

			return "1";
		}
	}
}
