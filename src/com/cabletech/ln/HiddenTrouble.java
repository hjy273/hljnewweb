package com.cabletech.ln;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.cabletech.commons.base.BaseBisinessObject;
import com.cabletech.commons.hb.QueryUtil;
import com.cabletech.commons.hb.UpdateUtil;
import com.cabletech.ln.ws.ClientRequest;

/**
 * 获得接收到的隐患
 * @author Administrator
 *
 */
public class HiddenTrouble extends BaseBisinessObject implements Job {

	private Logger logger = Logger.getLogger("HiddenTrouble");

	public List getHiddenTrouble() {
		List troubles = new ArrayList();
		QueryUtil query;
		String hiddenTroublesql = "select a.keyid,STR_TO_GEO_POINT(a.GPSCOORDINATE) xy,a.PATROLID,a.SIMID,a.SENDTIME,GET_TROUBLE_TYPE(substr(a.OPERATIONCODE,1,3)) ttype,GET_TROUBLE_NAME(substr(a.OPERATIONCODE,4,3))tcode,p.POINTNAME,l.LINENAME "
				+ "	from accident a, pointinfo p,lineinfo l "
				+ " where p.POINTID = a.PID and l.LINEID = a.lid "
				+ " and xxx=0 order by SENDTIME desc";
		try {
			query = new QueryUtil();
			ResultSet rs = query.executeQuery(hiddenTroublesql);
			while (rs.next()) {
				ClientRequest clientRequest = new ClientRequest();
				clientRequest.setKeyid(rs.getString("keyid"));
				clientRequest.setXy(rs.getString("xy"));
				clientRequest.setPatrolid(rs.getString("patrolid"));
				clientRequest.setSendtime(rs.getString("sendtime"));
				clientRequest.setSimid(rs.getString("simid"));
				clientRequest.setTcode(rs.getString("tcode"));
				clientRequest.setTtype(rs.getString("ttype"));
				clientRequest.setPointname(rs.getString("pointname"));
				clientRequest.setLinename(rs.getString("linename"));
				troubles.add(clientRequest);
				//				Map trouble = new HashMap();
				//				trouble.put("keyid", rs.getString("keyid"));
				//				trouble.put("xy",rs.getString("xy"));
				//				trouble.put("patrolid",rs.getString("patrolid"));
				//				trouble.put("simid",rs.getString("simid"));
				//				trouble.put("sendtime",rs.getString("sendtime"));
				//				trouble.put("ttype",rs.getString("ttype"));
				//				trouble.put("tcode",rs.getString("tcode"));
				//				trouble.put("pointname",rs.getString("pointname"));
				//				trouble.put("linename",rs.getString("linename"));
				//				troubles.add(trouble);
			}
			return troubles;
		} catch (Exception e) {
			logger.error("查询隐患报告异常" + e);
			return troubles;
		}
	}

	public void setHiddenTroubleReaded(String troubleid) {
		try {
			UpdateUtil update = new UpdateUtil();
			String sql = "update accident set xxx=1 where keyid='" + troubleid + "'";
			update.executeUpdate(sql);
		} catch (Exception e) {
			logger.error("更新隐患是否读取" + e);
		}
	}

	public void execute(JobExecutionContext context) throws JobExecutionException {
		List troubles = getHiddenTrouble();
		//TroubleService service = new TroubleServiceImpl();
		logger.info("-------");
		for (int i = 0; i < troubles.size(); i++) {
			ClientRequest trouble = (ClientRequest) troubles.get(i);
			//调用webService发送隐患信息
			//service.sendTroubleInfo(trouble);
			logger.info("trouble  " + trouble.toString());
			setHiddenTroubleReaded(trouble.getKeyid());
		}
	}
}
