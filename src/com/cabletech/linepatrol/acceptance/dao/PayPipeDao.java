package com.cabletech.linepatrol.acceptance.dao;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.acceptance.model.PayPipe;

@Repository
public class PayPipeDao extends HibernateDao<PayPipe, String> {
    /**
     * ͨ��pipeTask ����id��ѯһ��paypipe
     * 
     * @param ptaskId
     *            pipetask ��Id
     * @return
     */
    public PayPipe getPayPipeFrompTaskId(String ptaskId) {
        String hql = "select pp from PayPipe pp,PipeTask pt where pp.taskId=pt.id and pt.id=?";
        // return findByUnique(hql, ptaskId);
        List<PayPipe> list = super.find(hql, ptaskId);
        if (CollectionUtils.isNotEmpty(list)) {
            return list.get(0);
        }
        return new PayPipe();
    }

    /**
     * 
     * @param taskId
     *            applyTask�е�Id ��pipeTask �е�taskId
     * @return
     */
    public List<PayPipe> getPayPipesByTaskId(String taskId) {
        String hql = "select pp from PayPipe pp,PipeTask pt where pp.taskId=pt.id and pt.id=?";
        return this.find(hql, taskId);
    }
}
