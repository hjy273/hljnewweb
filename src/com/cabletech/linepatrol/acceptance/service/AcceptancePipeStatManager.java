package com.cabletech.linepatrol.acceptance.service;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.beanutils.DynaBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.ctf.service.EntityManager;
import com.cabletech.linepatrol.acceptance.beans.PipesBean;
import com.cabletech.linepatrol.acceptance.dao.AcceptancePipeStatDao;
import com.cabletech.linepatrol.acceptance.model.Apply;

/**
 * 验收交维管道统计业务类
 * @author liusq
 *
 */

@Service
@Transactional
public class AcceptancePipeStatManager extends EntityManager<Apply, String> {

	@Resource(name = "acceptancePipeStatDao")
	private AcceptancePipeStatDao dao;
	
	@Override
	protected HibernateDao<Apply, String> getEntityDao() {
		return dao;
	}

	/**
	 * 管道统计列表
	 * @param bean
	 * @return
	 */
	public List<Object> getPipeStatList(PipesBean bean){
		//统计结果信息
		List<Object> backInfo = null;
		//获得按条件查询的内容
		List<DynaBean> applyList = dao.getStatPipeList(bean);
		if(applyList != null && applyList.size() > 0){
			backInfo = new ArrayList<Object>();
			List<Object> totalList = new ArrayList<Object>();
			//总数量
			String ids = dao.listToString(applyList);
			
			//获取所有的数量
			int pipeNumAll = applyList.size();
			//获得通过的数量
			int pipeNumPassed = dao.getStatPipeNum(ids);
			
			//沟总数量
			int ditchNumAll = dao.getPipeLength("sum(pi.pipe_length_0)", ids, "");
			//沟通过数
			int ditchNumPassed = dao.getPipeLength("sum(pi.pipe_length_0)", ids, " and pi.ispassed='1'");
			//孔总数量
			int holeNumAll = dao.getPipeLength("sum(pi.pipe_length_1)", ids, "");
			//孔通过数
			int holeNumPassed = dao.getPipeLength("sum(pi.pipe_length_1)", ids, " and pi.ispassed='1'");
			//格式化浮点数
			NumberFormat nf = NumberFormat.getNumberInstance();
			nf.setMaximumFractionDigits(2);
			//添加信息
			totalList.add(pipeNumAll);//项目总数
			totalList.add(pipeNumPassed);//通过项目数
			totalList.add(nf.format(100.0 * pipeNumPassed / pipeNumAll));//项目通过率
			totalList.add(ditchNumAll);//沟总公里数
			totalList.add(holeNumAll);//孔总公里数
			totalList.add(nf.format(100.0 * ditchNumPassed / ditchNumAll));//沟公里通过率
			totalList.add(nf.format(100.0 * holeNumPassed / holeNumAll));//孔公里通过率
			//获得统计的详细信息
			List<Object> detailList = dao.getDetailListInfo(ids,bean);
			backInfo.add(totalList);
			backInfo.add(detailList);
		}
		return backInfo;
	}
}
