package com.cabletech.sysmanage.dao;

import java.io.BufferedReader;
import java.io.Reader;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import oracle.sql.CLOB;

import org.apache.log4j.Logger;
import org.hibernate.lob.SerializableClob;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.ctf.exception.ServiceException;
import com.cabletech.sysmanage.beans.NoticeBean;
import com.cabletech.sysmanage.domainobjects.Notice;

@Repository
public class NoticeDAOImpl extends HibernateDao<Notice, String> {
	private static Logger logger = Logger.getLogger("NoticeDAOImpl");

	public List query(String sql) {

		return this.getJdbcTemplate().query(sql, new NoticeMapper());
	}

	public boolean saveNotice(Notice notice) {
		save(notice);
		return true;
	}

	public boolean removeNotice(Notice notice) {
		delete(notice);
		return true;
	}

	public Notice findById(String id) {
		Notice notice = this.get(id);
		SerializableClob sc = (SerializableClob) notice.getContent();
		java.sql.Clob content = sc.getWrappedClob();
		notice.setContentString(ClobToString(content));
		return notice;
	}

	public void isRead(String sql) {
		this.getJdbcTemplate().execute(sql);
	}

	protected class NoticeMapper implements RowMapper {
		NoticeBean notice = null;
		java.sql.Clob clob = null;

		public Object mapRow(ResultSet rst, int rowNum) throws SQLException {
			notice = new NoticeBean();
			notice.setId(rst.getString("id"));
			notice.setMeetPerson(rst.getString("meet_person"));
			notice.setMeetAddress(rst.getString("meet_address"));
			notice.setMeetTime(rst.getString("meet_time"));
			notice.setMeetEndTime(rst.getString("meet_end_time"));
			notice.setTitle(rst.getString("title"));
			notice.setType(rst.getString("type"));
			notice.setIsissue(rst.getString("isissue"));
			notice.setIssueperson(rst.getString("issueperson"));
			notice.setIssuedate(rst.getString("issuedate"));
			notice.setIsCanceled(rst.getString("is_canceled"));
			clob = rst.getClob("content");
			notice.setContentString(ClobToString(clob));
			return notice;
		}
	}

	public String ClobToString(Object in) throws ServiceException {
		String reString = "";
		oracle.sql.CLOB clob = null;
		try {
			if ("oracle.sql.CLOB".equals(in.getClass().getName())) {
				clob = (oracle.sql.CLOB) in;
			} else if ("weblogic.jdbc.wrapper.Clob_oracle_sql_CLOB".equals(in
					.getClass().getName())) {
				Method method = in.getClass().getMethod("getVendorObj",
						new Class[] {});
				clob = (oracle.sql.CLOB) method.invoke(in);
			}
			Reader is = clob.getCharacterStream();// 得到流
			BufferedReader br = new BufferedReader(is);
			String s = br.readLine();
			StringBuffer sb = new StringBuffer();
			while (s != null) {// 执行循环将字符串全部取出付值给StringBuffer由StringBuffer转成STRING
				sb.append(s);
				s = br.readLine();
			}
			reString = sb.toString();
		} catch (SQLException e) {
			throw new ServiceException(e);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		return reString;
	}

	public List queryNotices(String rootregionid, String regionid, String num,
			String type) {
		String sql = "select ID,TITLE,ISISSUE,grade,type,to_char(ISSUEDATE,'yyyy-mm-dd') issuedate, ";
		sql += " to_char(meet_time,'yyyy-mm-dd') as meet_time ";
		sql += " from (select * from NOTICE_CLOB where isissue='y' and type=? and is_canceled='0' and regionid in (?,?) order by issuedate desc) notice ";
		sql += " where rownum <= ?";
		logger.info("SQL:" + sql);
		return this.getJdbcTemplate().query(sql,
				new Object[] { type, rootregionid, regionid, num },
				new NoticeNoClobMapper());
	}

	protected class NoticeNoClobMapper implements RowMapper {
		NoticeBean notice = null;
		CLOB clob = null;

		public Object mapRow(ResultSet rst, int rowNum) throws SQLException {
			notice = new NoticeBean();
			notice.setId(rst.getString("id"));
			notice.setMeetTime(rst.getString("meet_time"));
			notice.setTitle(rst.getString("title"));
			notice.setIsissue(rst.getString("isissue"));
			notice.setGrade(rst.getString("grade"));
			notice.setType(rst.getString("type"));
			notice.setIssuedate(rst.getString("issuedate"));
			return notice;
		}
	}

	public String getSendPhone(String userid) {
		// TODO Auto-generated method stub
		String sql = " select phone from userinfo ";
		sql += " where userid='" + userid + "' ";
		sql += " union ";
		sql += " select mobile as phone from contractorperson ";
		sql += " where id='" + userid + "' ";
		// System.out.println( "SQL:" + sql );
		List list = super.getJdbcTemplate().queryForList(sql, String.class);
		if (list != null && !list.isEmpty()) {
			return (String) list.get(0);
		} else {
			return "";
		}
	}

}
