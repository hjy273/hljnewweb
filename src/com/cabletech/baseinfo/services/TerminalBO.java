package com.cabletech.baseinfo.services;

import java.util.*;

import org.apache.commons.beanutils.*;
import org.apache.log4j.Logger;

import com.cabletech.baseinfo.dao.*;
import com.cabletech.baseinfo.domainobjects.*;
import com.cabletech.commons.base.*;
import com.cabletech.commons.beans.*;
import com.cabletech.commons.hb.QueryUtil;
import com.cabletech.commons.services.*;
import com.cabletech.commons.util.DateUtil;
import com.cabletech.lineinfo.beans.*;
import com.cabletech.lineinfo.domainobjects.*;
import com.cabletech.lineinfo.services.*;
import com.cabletech.statistics.dao.StatDao;
import com.cabletech.utils.*;
import com.cabletech.commons.hb.*;
import org.apache.log4j.*;
import com.cabletech.baseinfo.beans.TerminalBean;

public class TerminalBO extends BaseBisinessObject {
    TerminalDAOImpl dao = new TerminalDAOImpl();

    private Logger logger = Logger.getLogger("TerminalBO");

    public TerminalBO() {
    }

    public void addTerminal(Terminal data) throws Exception {
        dao.addTerminal(data);
    }

    public void removeTerminal(Terminal data) throws Exception {
        dao.removeTerminal(data);
    }

    public Terminal loadTerminal(String id) throws Exception {
        return dao.findById(id);
    }

    public boolean addUpPhoneBook(TerminalBean bean, String[] simnumber,
            String[] name, String[] phone, UserInfo userinfo) {
        return dao.addUpPhoneBook(bean, simnumber, name, phone, userinfo);
    }

    public List getPhoneBook(UserInfo userinfo) {
        return dao.getPhoneBook(userinfo);
    }

    public List getSimNumber(UserInfo userinfo) {
        return dao.getSimNumber(userinfo);
    }

    public Terminal updateTerminal(Terminal terminal) throws Exception {
        return dao.updateTerminal(terminal);
    }

    /**
     * ��ʼ���豸
     *
     * @param vct
     *            Vector
     * @throws Exception
     * @return int
     */
    public void initTerminal(List list, String serverid) throws Exception {
        // begin
        try {
            for (int i = 0; i < list.size(); i++) {

                BasicDynaBean dyBean = (BasicDynaBean) list.get(i);
                sendInitMsg(dyBean, serverid);
            }
        } catch (Exception e) {
            //System.out.println("��ʼ���豸�쳣 ��" + e.toString());
        }
        // end

    }

    /**
     * ���ͳ�ʼ������
     *
     * @param bean
     *            TerminalBean
     */
    private void sendInitMsg(BasicDynaBean dyBean, String serverid)
            throws Exception {

        String msgContent = getContent(dyBean, serverid);

        // do save db

        SmsSendLogBean msgbean = new SmsSendLogBean();
        DBService dbservice = new DBService();

        // preset the data
        msgbean.setMsg_id(dbservice.getSeq("localizermsglog", 10));
        msgbean.setSendtime(DateUtil.getNowDateString());
        msgbean.setSenttime(DateUtil.getNowDateString());
        msgbean.setIsresponded("0");
        msgbean.setIssent("0");

        msgbean.setContent(msgContent);
        msgbean.setSimid((String) dyBean.get("simnumber"));
        msgbean.setType("3");

        // 1,�·� 2,��λ 3,��ʼ��

        SmsSendLog data = new SmsSendLog();
        BeanUtil.objectCopy(msgbean, data);

        // do save
        LineinfoService service = new LineinfoService();
        service.createSmsSendLog(data);

        // do send msg

    }

    /**
     * ȡ��������Ϣ
     *
     * @param string
     *            String
     * @return String
     */
    private String getContent(BasicDynaBean dyBean, String serverid) {
        String contentStr = "";

        String terminalid = (String) dyBean.get("terminalid");
        int Len = terminalid.length();

        String machineid = terminalid.substring(Len - 4, Len);
        String password = (String) dyBean.get("password");

        contentStr = machineid + password + "I" + machineid + serverid;

        // �ɻ����� + ���� + I + �»����� + ����������

        //System.out.println("����Ϣ���� ��" + contentStr);

        return contentStr;
    }

    /**
     * ID�Ƿ�ռ��
     *
     * @param terminal
     *            Terminal
     * @return int
     * @throws Exception
     */
    public int isIdOccupied(Terminal terminal) throws Exception {
        return dao.isIdOccupied(terminal);
    }
    
    /**
     * ID�Ƿ�ռ��,����ʱ��
     *
     * @param terminal
     *            Terminal
     * @return int
     * @throws Exception
     */
    public int isIdOccupied4Edit(Terminal terminal) throws Exception {
        return dao.isIdOccupied4Edit(terminal);
    }
    
    

    /**
     * �����Ƿ�ռ��
     *
     * @param terminal
     *            Terminal
     * @return int
     * @throws Exception
     */
    public int isNumberOccupied(Terminal terminal) throws Exception {
        return dao.isNumberOccupied(terminal);
    }

    public int isNumberOccupied4Edit(Terminal terminal, String oldSimnumber)
            throws Exception {
        return dao.isNumberOccupied4Edit(terminal, oldSimnumber);
    }

    /**
     * ָ���������Ƿ�ռ��
     *
     * @param terminal
     *            Terminal
     * @return int
     * @throws Exception
     */
    public int isPatrolmanOccupied(Terminal terminal) throws Exception {
        return dao.isPatrolmanOccupied(terminal);
    }

    public int isPatrolmanOccupied4Edit(Terminal terminal, String oldOwnerid)
            throws Exception {
        return dao.isPatrolmanOccupied4Edit(terminal, oldOwnerid);
    }

    public String[][] getOldValues(Terminal terminal) throws Exception {

        return dao.getOldValues(terminal);
    }

    /**
     * ��ȡ���豸
     *
     * @param userinfo
     * @return
     */
    public List getNewTerminal(UserInfo userinfo) {
        // TODO Auto-generated method stub
        // String sql = "select p.patrolname,t.TERMINALID from terminalinfo t,
        // patrolmaninfo p where p.PATROLID = t.OWNERID and terminalmodel like
        // 'CT%'";
        String where= "";
        // ���ƶ�
        if (userinfo.getDeptype().equals("1")
                && !userinfo.getType().equals("11")) {
            where = " t.regionid IN ('" + userinfo.getRegionID()
                    + "') AND (t.state <> '1' or t.STATE is null)";
        }
        // �д�ά
        if (userinfo.getDeptype().equals("2")
                && !userinfo.getType().equals("11")) {
            where = "  (t.state <> '1' or t.STATE is null)  and t.CONTRACTORID='" + userinfo.getDeptID()
                    + "' ";
        }
        // ʡ�ƶ�
        if (userinfo.getDeptype().equals("1")
                && userinfo.getType().equals("11")) {
            where = "  (t.state <> '1' or t.STATE is null)";
        }
        // ʡ��ά
        if (userinfo.getDeptype().equals("2")
                && userinfo.getType().equals("11")) {
            where = " (t.state <> '1' or t.STATE is null) and t.CONTRACTORID in( SELECT contractorid FROM contractorinfo CONNECT BY PRIOR contractorid=PARENTCONTRACTORID START WITH contractorid='"
                    + userinfo.getDeptID() + "')";
        }
        String condition = "where p.PATROLID = t.OWNERID and terminalmodel like 'CT%' and "+where ;// and
                                                                                        // +Popedom.getPopedom(userinfo);Ȩ�޿���
        StatDao statdao = new StatDao();
        List NewTerminal = statdao.getSelectForTag("p.patrolname",
                "t.simnumber", "terminalinfo t, patrolmaninfo p", condition);
        return NewTerminal;
    }

    /**
     * ��ȡ��ǰ24Сʱ�ڵ�Ƿѹ�豸
     *
     * @param userinfo
     * @return List
     */
    public List getLowVoltage(UserInfo userinfo) {
        String where= "";
        // ���ƶ�
        if (userinfo.getDeptype().equals("1")
                && !userinfo.getType().equals("11")) {
            where = " t.regionid IN ('" + userinfo.getRegionID()
                    + "') AND (t.state <> '1' or t.STATE is null)";
        }
        // �д�ά
        if (userinfo.getDeptype().equals("2")
                && !userinfo.getType().equals("11")) {
            where = "  (t.state <> '1' or t.STATE is null)  and t.CONTRACTORID='" + userinfo.getDeptID()
                    + "' ";
        }
        // ʡ�ƶ�
        if (userinfo.getDeptype().equals("1")
                && userinfo.getType().equals("11")) {
            where = "  (t.state <> '1' or t.STATE is null) ";
        }
        // ʡ��ά
        if (userinfo.getDeptype().equals("2")
                && userinfo.getType().equals("11")) {
            where = " (t.state <> '1' or t.STATE is null) and t.CONTRACTORID in( SELECT contractorid FROM contractorinfo CONNECT BY PRIOR contractorid=PARENTCONTRACTORID START WITH contractorid='"
                    + userinfo.getDeptID() + "')";
        }

        String sql = "select v.DEVICEID,t.SIMNUMBER,p.PATROLNAME,to_char(v.RESPVOLTAGE) RESPVOLTAGE,to_char(v.RESPTIME,'mm-dd hh24:mi') resptime  from voltage_history v,terminalinfo t ,patrolmaninfo p where v.DEVICEID=SUBSTR(t.TERMINALID,length(t.TERMINALID)-7,length(t.TERMINALID)) and t.OWNERID = p.PATROLID and v.RESPTIME>hoursbeforenow(24)  and " + where;
        QueryUtil util;
        try {
            util = new QueryUtil();
            logger.info("sql --> " + sql);
            return util.queryBeans(sql);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("��ȡ��ǰǷѹ�豸�쳣��" + e.getMessage());
            return null;
        }

    }

}
