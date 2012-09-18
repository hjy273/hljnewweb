package com.cabletech.linepatrol.resource.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.beanutils.DynaBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.ctf.exception.ServiceException;
import com.cabletech.ctf.service.EntityManager;
import com.cabletech.linepatrol.resource.beans.PipeBean;
import com.cabletech.linepatrol.resource.beans.RepeaterSectionBean;
import com.cabletech.linepatrol.resource.dao.PipeDao;
import com.cabletech.linepatrol.resource.model.Pipe;
import com.cabletech.linepatrol.resource.model.RepeaterSection;
@Service
public class PipeManager extends EntityManager {
	@Resource(name="pipeDao")
	private PipeDao pipeDao;
	@Override
	protected HibernateDao getEntityDao() {
		return pipeDao;
	}
	@Transactional
	public Pipe svaePipe(Pipe pipe){
		pipeDao.save(pipe);
		return pipe;
	}
	@Transactional(readOnly=true)
	public List<Pipe> getAllByDept(UserInfo user,PipeBean pipeBean) throws ServiceException{
			return pipeDao.getAllByHold(user,pipeBean);
	}
	@Transactional(readOnly=true)
	public Pipe getObject(String id) {
		return pipeDao.get(id);
	}
	
		
	/**
	 * ���ݹ������ƺʹ�ά��λ��Ų�ѯ�ܵ���Ϣ
	 * @param workName ��������
	 * @param maintenanceId ��ά��λ���
	 * @return
	 * @throws ServiceException
	 */
	@Transactional(readOnly=true)
	public String searchPipe(String workName, String maintenanceId, String subline) throws ServiceException {
		StringBuffer sb = new StringBuffer("<select name=\"original\" style=\"width: 200px\" size=\"15\" id=\"original\" multiple=\"multiple\" >");
		List<Pipe> list = pipeDao.getPipeByCondition(workName, maintenanceId, subline);
		if(list != null && list.size() > 0){
			for(Pipe rs : list){
				sb.append("<option value=\"");
				sb.append(rs.getId());
				sb.append("\">");
				sb.append(rs.getWorkName());
				sb.append("</option>\n");
				sb.append("\n");
			}
		}
		sb.append("</select>");
		return sb.toString();
	}
	
	/**
	 * ���ݹܵ���Ų�ѯ�ܵ��б���Ϣ
	 * @param sbulineid �ܵ����
	 * @return
	 */
	@Transactional(readOnly=true)
	public List<DynaBean> getPipeInfoList(String sublineid) throws ServiceException {
		List<DynaBean> list = pipeDao.getPipeByKids(sublineid);
		return list;
	}
	
	/**
	 * �ܵ����·���
	 * @param bean
	 * @throws ServiceException
	 */
	@Transactional
	public void assignPipe(PipeBean bean) throws ServiceException {
		pipeDao.assignPipe(bean);
	}
	
	/**
	 * ͨ��PDA��ѯ�ܵ���Ϣ
	 * @param pipeName �ܵ�����
	 * @param userInfo
	 * @return
	 */
	@Transactional
	public List<Map> getPipeFromPDA(String pipeAddress,UserInfo userInfo,String finishTime,String contractorId){
		return pipeDao.getPipeFromPAD(pipeAddress, userInfo,finishTime,contractorId);
	}
	
}
