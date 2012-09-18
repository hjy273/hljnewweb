/***
 *
 * MtUsedAssessManger.java
 * @auther <a href="kww@mail.cabletech.com.cn">kww</a>
 * 2009-10-10
 **/

package com.cabletech.linepatrol.material.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cabletech.commons.generatorID.impl.OracleIDImpl;
import com.cabletech.commons.hb.HibernateSession;
import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.ctf.exception.ServiceException;
import com.cabletech.ctf.service.EntityManager;
import com.cabletech.linepatrol.material.beans.MtUsedAssessBean;
import com.cabletech.linepatrol.material.beans.MtUsedBean;
import com.cabletech.linepatrol.material.dao.MaterialStockDao;
import com.cabletech.linepatrol.material.dao.MtUsedAssessDao;
import com.cabletech.linepatrol.material.dao.MtUsedDao;
import com.cabletech.linepatrol.material.dao.MtUsedStockAddrDao;
import com.cabletech.linepatrol.material.dao.MtUsedStockDao;
import com.cabletech.linepatrol.material.domain.MtUsedAssess;

@Service
@Transactional
public class MtUsedAssessBo extends EntityManager<MtUsedAssess,String> {
    private OracleIDImpl ora = new OracleIDImpl();
    
    @Resource(name="mtUsedDao")
	private MtUsedDao mtUsedDao;
    
    @Resource(name="mtUsedAssessDao")
	private MtUsedAssessDao mtUsedAssessDao;
    
    @Resource(name="materialStockDao")
	private MaterialStockDao materialStockDao;
    
    @Resource(name="mtUsedStockAddrDao")
	private MtUsedStockAddrDao mtUsedStockAddrDao;
    
    @Resource(name="mtUsedStockDao")
	private MtUsedStockDao mtUsedStockDao;

    public boolean mtApprove(MtUsedAssessBean bean, MtUsedBean mtUsedBean) throws ServiceException {
        try {
            int aid = ora.getIntSeq("MT_USED_ASSESS");
            bean.setAid(aid);

            List mtUsedStockAddrList = mtUsedStockAddrDao.getMtUsedStockList(bean.getMtusedid() + "");
            List mtUsedStockList = mtUsedStockDao.getMtUsedStockList(bean.getMtusedid() + "");
            if (MtUsedBean.STATE_MOBILE_AGREE.equals(mtUsedBean.getState())) {
                boolean flag = materialStockDao.updateMaterialAddrStorage(mtUsedStockAddrList)
                        && materialStockDao.updateMaterialStorage(mtUsedStockList, mtUsedStockAddrList);
                if (!flag) {
                    HibernateSession.rollbackTransaction();
                    return false;
                }
            }
            return mtUsedAssessDao.saveMtUsedAssessBean(bean, mtUsedBean);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    @Transactional(readOnly=true)
    public List getConditionByStateAndUserid(String userId) throws ServiceException {
        return mtUsedDao.getConditionByStateAndUserid(MtUsedBean.STATE_APPLY,userId);
    }

    @Transactional(readOnly=true)
    public List getMtUsedIdAndType0(int mtusedid) throws ServiceException {
        return mtUsedAssessDao.getMtUsedId(mtusedid, "0");
    }

    @Transactional(readOnly=true)
    public List getMtUsedIdAndType1(int mtusedid) throws ServiceException {
        return mtUsedAssessDao.getMobileMtUsedId(mtusedid);
    }

    @Transactional(readOnly=true)
    public List getUnionMtUsedId(int id) throws ServiceException {
        return mtUsedAssessDao.getUnionMtUsedId(id);
    }

	@Override
	protected HibernateDao<MtUsedAssess, String> getEntityDao() {
		// TODO Auto-generated method stub
		return mtUsedAssessDao;
	}

	public MtUsedDao getMtUsedDao() {
		return mtUsedDao;
	}

	public void setMtUsedDao(MtUsedDao mtUsedDao) {
		this.mtUsedDao = mtUsedDao;
	}

	public MtUsedAssessDao getMtUsedAssessDao() {
		return mtUsedAssessDao;
	}

	public void setMtUsedAssessDao(MtUsedAssessDao mtUsedAssessDao) {
		this.mtUsedAssessDao = mtUsedAssessDao;
	}

	public MaterialStockDao getMaterialStockDao() {
		return materialStockDao;
	}

	public void setMaterialStockDao(MaterialStockDao materialStockDao) {
		this.materialStockDao = materialStockDao;
	}

	public MtUsedStockAddrDao getMtUsedStockAddrDao() {
		return mtUsedStockAddrDao;
	}

	public void setMtUsedStockAddrDao(MtUsedStockAddrDao mtUsedStockAddrDao) {
		this.mtUsedStockAddrDao = mtUsedStockAddrDao;
	}

	public MtUsedStockDao getMtUsedStockDao() {
		return mtUsedStockDao;
	}

	public void setMtUsedStockDao(MtUsedStockDao mtUsedStockDao) {
		this.mtUsedStockDao = mtUsedStockDao;
	}

}
