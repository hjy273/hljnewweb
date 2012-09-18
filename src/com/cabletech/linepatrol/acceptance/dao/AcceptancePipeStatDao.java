package com.cabletech.linepatrol.acceptance.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.acceptance.beans.PipesBean;
import com.cabletech.linepatrol.acceptance.model.Apply;

/**
 * ���ս�ά�ܵ�ͳ��
 * @author liusq
 *
 */

@Repository
public class AcceptancePipeStatDao extends HibernateDao<Apply, String> {
	
	private static final String UNPASS = "0";
	private static final String PASSED = "1";
	
	/**
	 * ����ͳ��������ѯ����Ҫ����Ŀ�б�
	 * 
	 * @param bean
	 * @return
	 */
	public List<DynaBean> getStatPipeList(PipesBean bean) {
		StringBuffer sb = new StringBuffer("");
		sb.append("select distinct ap.id from lp_acceptance_apply ap,lp_acceptance_pipe pi where ap.id=pi.apply_id ");
		sb.append(composeCondition(bean));
		sb.append(" group by ap.id,pi.ispassed order by ap.id");
		logger.info("�ܵ�ͳ�Ʋ�ѯ��" + sb.toString());
		return getJdbcTemplate().queryForBeans(sb.toString());
	}

	/**
	 * ��ѯͨ���Ĺܵ�����
	 * 
	 * @param ids ���ݲ�ѯ��Ӳ�ѯ������������
	 * @return	��ѯ����
	 */
	public int getStatPipeNum(String ids) {
		StringBuffer sb = new StringBuffer("");
		sb.append("select count(*) from lp_acceptance_apply ap ");
		sb.append("where not exists (select 1 from lp_acceptance_pipe pi where ap.id=pi.apply_id and pi.ispassed='0') ");
		sb.append("and ap.id in('");
		sb.append(ids.replaceAll(",", "','"));
		sb.append("')");
		//sb.append(" group by ap.id,pi.ispassed order by ap.id");
		logger.info("�ܵ�ͳ�Ʋ�ѯ��" + sb.toString());
		return getJdbcTemplate().queryForInt(sb.toString());
	}

	/**
	 * ��ϲ�ѯ����
	 * 
	 * @param bean ��װ�Ĳ�ѯ����
	 * @return ��ϲ�ѯ����
	 */
	private String composeCondition(PipesBean bean) {
		StringBuffer sb = new StringBuffer("");
		// ��Ŀ����
		if (StringUtils.isNotBlank(bean.getProjectName())) {
			sb.append(" and ap.name like '%");
			sb.append(bean.getProjectName());
			sb.append("%'");
		}
		// ��Ŀ����
		if (StringUtils.isNotBlank(bean.getPcpm())) {
			sb.append(" and pi.pcpm like '%");
			sb.append(bean.getPcpm());
			sb.append("%'");
		}
		// �����Ƿ�ͨ�� ����һ����ͨ��ʱ��Ϊ��ͨ��
		if (StringUtils.isNotBlank(bean.getIsRecord())&&StringUtils.equals(bean.getIsRecord(), "1")&&StringUtils.isNotBlank(bean.getIspassed())) {
			if (StringUtils.equals(bean.getIspassed(), UNPASS)) {
				sb.append(" and pi.ispassed='0'");
			} else if (StringUtils.equals(bean.getIspassed(), PASSED)) {
				sb.append(" and (pi.ispassed='1' and not exists (select 1 from lp_acceptance_pipe p where p.apply_id=ap.id and p.ispassed='0'))");
			}
		}
		// �ܵ�����
		if (StringUtils.isNotBlank(bean.getPipeType())) {
			sb.append(" and pipe_type='");
			sb.append(bean.getPipeType());
			sb.append("'");
		}
		// ��Ȩ����
		if (StringUtils.isNotBlank(bean.getPipeProperty())) {
			sb.append(" and pipe_property='");
			sb.append(bean.getPipeProperty());
			sb.append("'");
		}
		// ��ά��˾
		if (StringUtils.isNotBlank(bean.getContractorid())) {
			sb.append(" and contractorid='");
			sb.append(bean.getContractorid());
			sb.append("'");
		}
		// ��ʼʱ��
		if (StringUtils.isNotBlank(bean.getBegintime())) {
			sb.append(" and pi.plan_acceptance_time>=to_date('");
			sb.append(bean.getBegintime());
			sb.append("','yyyy/mm/dd')");
		}
		// ����ʱ��
		if (StringUtils.isNotBlank(bean.getEndtime())) {
			sb.append(" and pi.plan_acceptance_time<=to_date('");
			sb.append(bean.getEndtime());
			sb.append("','yyyy/mm/dd')");
		}
		// �Ƿ�¼��
		if (StringUtils.isNotBlank(bean.getIsRecord())){
			sb.append(" and pi.isrecord='"+bean.getIsRecord()+"'");
		}
		return sb.toString();
	}
	
	/**
	 * ��List�б�
	 * 
	 * @param list List�б�
	 * @return �����ɵ��ַ���
	 */
	public String listToString(List<DynaBean> list){
		StringBuffer listToString = new StringBuffer("");
		if(list != null && !list.isEmpty()){
			for(int i = 0; i < list.size(); i++){
				DynaBean bean = (DynaBean)list.get(i);
				listToString.append((String)bean.get("id"));
				if(i != list.size()){
					listToString.append(",");
				}
			}
		}
		return listToString.toString();
	}
	
	/**
	 * ��ù�������
	 * 
	 * @param column ��ѯ��һ��
	 * @param ids ��ѯ��ϵͳ���
	 * @param condition ��ѯ����
	 * @return ��ѯ����
	 */
	public int getPipeLength(String column, String ids, String condition){
		StringBuffer sb = new StringBuffer("");
		sb.append("select ");
		sb.append(column);
		sb.append(" as num from lp_acceptance_apply ap,lp_acceptance_pipe pi where ap.id=pi.apply_id and ap.id in('");
		sb.append(ids.replaceAll(",", "','"));
		sb.append("')");
		sb.append(condition);
		return getJdbcTemplate().queryForInt(sb.toString());
	}
	
	/**
	 * ���ͳ�Ƶ���ϸ��Ϣ�б�
	 * 
	 * @param ids ���뵥���
	 * @return
	 */
	public List<Object> getDetailListInfo(String ids,PipesBean pipesBean){
		List<Object> detailList = new ArrayList<Object>();
		StringBuffer sb = new StringBuffer("");
		//ͳ��������Ϣ
		sb.append("select distinct ap.id,ap.name,pi.pcpm,'' ispassed,to_char(ap.apply_date,'yyyy-mm-dd') applydate,");
		sb.append("'' ispassednum,'' unpassednum,'' pitchlength,'' holelength ");
		sb.append("from lp_acceptance_apply ap,lp_acceptance_pipe pi where ap.id=pi.apply_id ");
		sb.append("and ap.id in('");
		sb.append(ids.replaceAll(",", "','"));
		sb.append("')");
		logger.info("ͳ��������ϢSQL��" + sb.toString());
		List<DynaBean> list = getJdbcTemplate().queryForBeans(sb.toString());
		List<Object> oneDetailList = null;
		if(list != null && list.size() > 0) {
			for(int i = 0; i < list.size(); i++){
				oneDetailList = new ArrayList<Object>();
				DynaBean bean = (DynaBean)list.get(i);
				String applyid = (String)bean.get("id");
				sb = new StringBuffer("");
				//ͳ������
				sb.append("select count(1) passnum,count (1) recordnum,pi.ispassed,pi.isrecord,sum(pi.pipe_length_0) pitchlength,sum(pi.pipe_length_1) holelength ");
				sb.append("from lp_acceptance_pipe pi where pi.apply_id='");
				sb.append(applyid);
				sb.append("' group by pi.isrecord,pi.ispassed");
				logger.info("ͳ������SQL��" + sb.toString());
				List<DynaBean> applyStatList = getJdbcTemplate().queryForBeans(sb.toString());
				int _passnum = 0;//�ϸ���
				int _unpassnum = 0;//δ�ϸ���
				float _pitchlength = 0;//��������
				float _holelength = 0;//��������
				if(applyStatList != null && applyStatList.size() > 0){
					for(int j = 0; j < applyStatList.size(); j++){
						DynaBean applyStatBean = (DynaBean)applyStatList.get(j);
						int passnum = ((BigDecimal)applyStatBean.get("passnum")).intValue();
						String ispassed = (String)applyStatBean.get("ispassed");
						float pitchlength = ((BigDecimal)applyStatBean.get("pitchlength")).floatValue();
						float holelength = ((BigDecimal)applyStatBean.get("holelength")).floatValue();
						if(StringUtils.equals("1", ispassed)){
							_passnum += passnum;
						}else if(StringUtils.equals("0", ispassed)){
							_unpassnum += passnum;
						}
						_pitchlength += pitchlength;
						_holelength += holelength;
					}
				}
				//���ø����뵥�Ƿ�ͨ������
				if(_unpassnum > 0){
					bean.set("ispassed", "��");
				}else{
					bean.set("ispassed", "��");
				}
				bean.set("ispassednum", Integer.toString(_passnum));//δͨ����
				bean.set("unpassednum", Integer.toString(_unpassnum));//��ͨ����
				bean.set("pitchlength", Float.toString(_pitchlength));//������
				bean.set("holelength", Float.toString(_holelength));//������
				//���ܿ���ϸ��Ϣ��ӵ�List��
				oneDetailList.add(bean);
				
				//��ѯ������ϸ��Ϣ�б�
				sb = new StringBuffer("");
				sb.append("select distinct pi.pipe_address,(select t.lable from dictionary_formitem t where t.code=pi.pipe_property and t.assortment_id='property_right') pipe_property,");
				sb.append("(select t.lable from dictionary_formitem t where t.code=pi.pipe_type and t.assortment_id='pipe_type') pipe_type,");
				sb.append("pi.pipe_route,'��'||pi.pipe_length_0||' ��'||pi.pipe_length_1 gdgm,");
				sb.append("to_char(pi.plan_acceptance_time,'yyyy-mm-dd') plan_date,to_char(pl.apply_date,'yyyy-mm-dd') apply_date,");
				sb.append("(select t.contractorname from contractorinfo t where t.contractorid=pi.contractorid) contractorid,");
				sb.append("decode(pi.ispassed,'0','δͨ��','1','ͨ��') ispassed, decode (pi.isrecord,'0','δ����','1','����') isrecord ");
				sb.append("from lp_acceptance_apply pl,lp_acceptance_pipe pi where pl.id=pi.apply_id and pl.id='");
				sb.append(applyid);
				sb.append("'");
				if(StringUtils.isNotBlank(pipesBean.getIsRecord())){
					sb.append(" and pi.isrecord='"+pipesBean.getIsRecord()+"'");
					if(StringUtils.equals(pipesBean.getIsRecord(), "1")){
						if(StringUtils.isNotBlank(pipesBean.getIspassed())){
							sb.append(" and pi.ispassed='"+pipesBean.getIspassed()+"'");
						}
					}
				}
				logger.info("��ѯ������ϸ��Ϣ�б�SQL��" + sb.toString());
				List<DynaBean> pipeList = getJdbcTemplate().queryForBeans(sb.toString());
				oneDetailList.add(pipeList);
				detailList.add(oneDetailList);
			}
		}
		return detailList;
	}
}
