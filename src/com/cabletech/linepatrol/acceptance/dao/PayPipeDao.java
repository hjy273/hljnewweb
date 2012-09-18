package com.cabletech.linepatrol.acceptance.dao;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.acceptance.model.PayPipe;

@Repository
public class PayPipeDao extends HibernateDao<PayPipe, String> {
    /**
     * 通过pipeTask 任务id查询一个paypipe
     * 
     * @param ptaskId
     *            pipetask 的Id
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
     *            applyTask中的Id 即pipeTask 中的taskId
     * @return
     */
    public List<PayPipe> getPayPipesByTaskId(String taskId) {
        String hql = "select pp from PayPipe pp,PipeTask pt where pp.taskId=pt.id and pt.id=?";
        return this.find(hql, taskId);
    }
}
