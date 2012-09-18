package com.cabletech.baseinfo.dao;

import com.cabletech.baseinfo.domainobjects.*;
import com.cabletech.commons.hb.*;

public class PatrolManSonDAOImpl extends HibernateDaoSupport{
    public PatrolManSonDAOImpl(){
    }


    public PatrolManSon addPatrolManSon( PatrolManSon patrolManson ) throws
        Exception{
        this.getHibernateTemplate().save( patrolManson );
        return patrolManson;
    }


    public PatrolManSon findById( String id ) throws Exception{
        return( PatrolManSon )this.getHibernateTemplate().load( PatrolManSon.class,
            id );
    }


    public boolean removePatrolManSon( PatrolManSon PatrolManson ) throws Exception{
        String sql = "update patrolman_son_info set state = '1' where patrolid='" + PatrolManson.getPatrolID() + "'";
        try{
            UpdateUtil exec = new UpdateUtil();
            exec.executeUpdate( sql );
            return true;
        }
        catch( Exception e ){
            //System.out.println( "�������,ɾ��Ѳ����Ա�쳣:" + e.getMessage() );
            return false;
        }
    }


    public PatrolManSon updatePatrolManSon( PatrolManSon PatrolManson ) throws
        Exception{
  //      System.out.println( PatrolManson.getParentID() );
        this.getHibernateTemplate().saveOrUpdate( PatrolManson );
        return PatrolManson;
    }


    /**
     * ����:���˹���Ѳ����Ա��,���Ѳ����Ա��Ϣ
     * ����:patrolManson PatrolManSon:Ѳ����Ա����
     * ����:��ӳɹ����� true ���򷵻� false
     *
     */
    public boolean addPartrolManSonByNoGroup( PatrolManSon pSon ){
        try{
            //Session  session = this.getSession();
            //session.beginTransaction();
            //session.save(pSon);
            pSon.setParent_patrol( pSon.getPatrolID() );
            addPatrolManSon( pSon ); //�������ԱȻ����Ա��Ϣ���Ƶ���Ա��,��һ���˶�Ӧһ����

            PatrolManDAOImpl pDao = new PatrolManDAOImpl();
            PatrolMan pBean = new PatrolMan();

            pBean.setPatrolID( pSon.getParent_patrol() );
            pBean.setPatrolName( pSon.getPatrolName() );
            pBean.setParentID( pSon.getParentID() );
            pBean.setJobInfo( pSon.getJobInfo() );
            pBean.setJobState( pSon.getJobState() );
            pBean.setSex( pSon.getSex() );
            pBean.setBirthday( pSon.getBirthday() );
            pBean.setCulture( pSon.getCulture() );
            pBean.setIsMarriage( pSon.getIsMarriage() );
            pBean.setAge( pSon.getAge() );
            pBean.setPhone( pSon.getPhone() );
            pBean.setMobile( pSon.getMobile() );
            pBean.setPostalcode( pSon.getPostalcode() );
            pBean.setIdentityCard( pSon.getIdentityCard() );
            pBean.setWorkRecord( pSon.getWorkRecord() );
            pBean.setState( pSon.getState() );
            pBean.setRegionID( pSon.getRegionID() );
            pBean.setRemark( pSon.getRemark() );
            pDao.addPatrolMan( pBean );
            //session.save(pBean);
            return true;
        }
        catch( Exception e ){
            //System.out.println( "���˹���Ѳ����Ա��,���Ѳ����Ա��Ϣ�쳣:" + e.getMessage() );
            return false;
        }
    }


    /**
     * ����:���˹���Ѳ����Ա��,����Ѳ����Ա��Ϣ
     * ����:patrolManson PatrolManSon:Ѳ����Ա����
     * ����:���³ɹ����� true ���򷵻� false
     *
     */
    public boolean updatePatrolManByNoGroup( PatrolManSon pSon ){

        int age = pSon.getAge().equals( "" ) ? 0 : Integer.parseInt( pSon.getAge() );
        //������Ա�����
        String sql = "update patrolman_son_info set "
                     + " patrolname='" + pSon.getPatrolName() + "',"
                     + " parentid='" + pSon.getParentID() + "',"
                     + " parent_patrol='" + pSon.getPatrolID() + "',"
                     + " jobstate = '" + pSon.getJobState() + "',"
                     + " jobinfo = '" + pSon.getJobInfo() + "',"
                     + " sex = '" + pSon.getSex() + "',"
                     + " birthday='" + pSon.getBirthday() + "',"
                     + " culture='" + pSon.getCulture() + "',"
                     + " ismarriage='" + pSon.getIsMarriage() + "',"
                     + " age = " + age + ","
                     + " phone = '" + pSon.getPhone() + "',"
                     + " mobile = '" + pSon.getMobile() + "',"
                     + " postalcode = '" + pSon.getPostalcode() + "',"
                     + " identitycard = '" + pSon.getIdentityCard() + "',"
                     + " familyaddress = '" + pSon.getFamilyAddress() + "',"
                     + " workrecord = '" + pSon.getWorkRecord() + "',"
                     + " state = '" + pSon.getState() + "',"
                     + " regionid = '" + pSon.getRegionID() + "',"
                     + " remark = '" + pSon.getRemark() + "'"
                     + " where patrolid='" + pSon.getPatrolID() + "'";
        //����������
        String sqlg = "update patrolmaninfo set "
                      + " patrolname='" + pSon.getPatrolName() + "',"
                      + " parentid='" + pSon.getParentID() + "',"
                      + " jobstate = '" + pSon.getJobState() + "',"
                      + " jobinfo = '" + pSon.getJobInfo() + "',"
                      + " sex = '" + pSon.getSex() + "',"
                      + " birthday='" + pSon.getBirthday() + "',"
                      + " culture='" + pSon.getCulture() + "',"
                      + " ismarriage='" + pSon.getIsMarriage() + "',"
                      + " age = " + age + ","
                      + " phone = '" + pSon.getPhone() + "',"
                      + " mobile = '" + pSon.getMobile() + "',"
                      + " postalcode = '" + pSon.getPostalcode() + "',"
                      + " identitycard = '" + pSon.getIdentityCard() + "',"
                      + " familyaddress = '" + pSon.getFamilyAddress() + "',"
                      + " workrecord = '" + pSon.getWorkRecord() + "',"
                      + " state = '" + pSon.getState() + "',"
                      + " regionid = '" + pSon.getRegionID() + "',"
                      + " remark = '" + pSon.getRemark() + "'"
                      + " where patrolid='" + pSon.getPatrolID() + "'";
        //���������
        String sqlga = "insert into patrolmaninfo ( patrolid, patrolname, parentid, "
                       + " jobstate,jobinfo,sex,birthday,culture,ismarriage,age,phone,mobile,postalcode,"
                       + " identitycard, familyaddress,workrecord,state,regionid,remark) values("
                       + "'" + pSon.getPatrolID() + "',"
                       + "'" + pSon.getPatrolName() + "',"
                       + "'" + pSon.getParentID() + "',"
                       + "'" + pSon.getJobState() + "',"
                       + "'" + pSon.getJobInfo() + "',"
                       + "'" + pSon.getSex() + "',"
                       + "'" + pSon.getBirthday() + "',"
                       + "'" + pSon.getCulture() + "',"
                       + "'" + pSon.getIsMarriage() + "',"
                       + age + ","
                       + "'" + pSon.getPhone() + "',"
                       + "'" + pSon.getMobile() + "',"
                       + "'" + pSon.getPostalcode() + "',"
                       + "'" + pSon.getIdentityCard() + "',"
                       + "'" + pSon.getFamilyAddress() + "',"
                       + "'" + pSon.getWorkRecord() + "',"
                       + "'" + pSon.getState() + "',"
                       + "'" + pSon.getRegionID() + "',"
                       + "'" + pSon.getRemark() + "')";
        try{
            UpdateUtil exec = new UpdateUtil();
            exec.setAutoCommitFalse();
            try{
 //               System.out.println( "sql:" + sql );
                exec.executeUpdate( sql );
                if( this.valiGroupExist( pSon.getPatrolID() ) == 0 ){
    //                System.out.println( "sqlga:" + sqlga );
                    exec.executeUpdate( sqlga );
                }
                else{
        //            System.out.println( "sqlg:" + sqlg );
                    exec.executeUpdate( sqlg );
                }
                exec.commit();
                exec.setAutoCommitTrue();
            }
            catch( Exception e1 ){
                //System.out.println( "����Ѳ����Ա��Ϣ�쳣:" + e1.getMessage() );
                exec.rollback();
                exec.setAutoCommitTrue();
                return false;
            }
            return true;
        }
        catch( Exception e ){
            //System.out.println( "���˹���Ѳ����Ա��,����Ѳ����Ա��Ϣ�쳣:" + e.getMessage() );

            return false;
        }
    }


    /**
     * ����:���˹���Ѳ����Ա��,ɾ��Ѳ����Ա��Ϣ
     * ����:patrolManson PatrolManSon:Ѳ����Ա����
     * ����:ɾ���ɹ����� true ���򷵻� false
     *
     */
    public boolean removePatrolManSonByNoGroup( PatrolManSon pSon ){
        //ɾ����Ա���
        String sql1 = "update patrolman_son_info set state = '1' where patrolid='" + pSon.getPatrolID() + "'";
        //ɾ�������
        String sql2 = "update patrolmaninfo set state = '1' where patrolid='" + pSon.getParent_patrol() + "'";

        try{
            UpdateUtil exec = new UpdateUtil();
            exec.setAutoCommitFalse();
            try{
                exec.executeUpdate( sql1 );
                exec.executeUpdate( sql2 );
                exec.commit();
                exec.setAutoCommitTrue();
                return true;
            }
            catch( Exception ew ){
                exec.rollback();
                exec.setAutoCommitTrue();
                //System.out.println( "���˹���Ѳ����Ա��,ɾ��Ѳ����Ա��Ϣ�쳣:" + ew.getMessage() );
                return false;
            }
        }
        catch( Exception e ){
            //System.out.println( "���˹���Ѳ����Ա��,ɾ��Ѳ����Ա��Ϣ�쳣:" + e.getMessage() );
            return false;
        }
    }


    /**
     * ����:��Ѳ��ά�����м��ָ�������Ƿ����
     * ����:Ѳ��ά������
     * ����:���ڷ��� 1 ���򷵻� 0 ������ -1
     *
     */
    public int valiGroupExist( String patrolid ){
        String sql = "select count(*) from patrolmaninfo where patrolid='" + patrolid + "'";
        try{
            QueryUtil query = new QueryUtil();
            String[][] str = query.executeQueryGetArray( sql, "" );
            if( str[0][0].equals( "0" ) ){
                return 0;
            }
            else{
                return 1;
            }
        }
        catch( Exception e ){
            //System.out.println( "��Ѳ��ά�����м��ָ�������Ƿ�����쳣:" + e.getMessage() );
            return -1;
        }
    }
}
