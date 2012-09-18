package com.cabletech.linepatrol.remedy.service;

import com.cabletech.linepatrol.remedy.constant.RemedyWorkflowConstant;
import com.cabletech.linepatrol.remedy.domain.RemedyApply;

public class RemedyWorkflowOperate {
    /**
     * ִ�и��ݵ�ǰ������ź�����������Ϣ��ȡ��һ���������
     * 
     * @param currentAction
     *            String ��ǰ�������
     * @param apply
     *            RemedyApply ����������Ϣ
     * @return String ��һ���������
     */
    public static String nextAction(String currentAction, RemedyApply apply) {
        int currentDoAction = Integer.parseInt(currentAction);
        int nextDoAction = 0;

        switch (currentDoAction) {
        case RemedyWorkflowConstant.INIT_ACTION:
            nextDoAction = RemedyWorkflowConstant.SAVE_ACTION;
            break;
        case RemedyWorkflowConstant.SAVE_ACTION:
            nextDoAction = RemedyWorkflowConstant.SUBMIT_ACTION;
            break;
        case RemedyWorkflowConstant.SUBMIT_ACTION:
            if (apply.isPassed()) {
                nextDoAction = RemedyWorkflowConstant.ONE_APPROVE_ACTION;
            } else {
                nextDoAction = RemedyWorkflowConstant.SAVE_ACTION;
            }
            break;
        case RemedyWorkflowConstant.ONE_APPROVE_ACTION:
            if (apply.isPassed()) {
                nextDoAction = RemedyWorkflowConstant.TWO_APPROVE_ACTION;
            } else {
                nextDoAction = RemedyWorkflowConstant.SUBMIT_ACTION;
            }
            break;
        case RemedyWorkflowConstant.TWO_APPROVE_ACTION:
            if (apply.isPassed()) {
                float fee = apply.getTotalFee().floatValue() / 10000;
                if (fee <= 1) {
                    nextDoAction = RemedyWorkflowConstant.CHECK_ACTION;
                } else {
                    nextDoAction = RemedyWorkflowConstant.THREE_APPROVE_ACTION;
                }
            } else {
                nextDoAction = RemedyWorkflowConstant.ONE_APPROVE_ACTION;
            }
            break;
        case RemedyWorkflowConstant.THREE_APPROVE_ACTION:
            if (apply.isPassed()) {
                float fee = apply.getTotalFee().floatValue() / 10000;
                if (fee <= 10) {
                    nextDoAction = RemedyWorkflowConstant.CHECK_ACTION;
                } else {
                    nextDoAction = RemedyWorkflowConstant.FOUR_APPROVE_ACTION;
                }
            } else {
                nextDoAction = RemedyWorkflowConstant.TWO_APPROVE_ACTION;
            }
            break;
        case RemedyWorkflowConstant.FOUR_APPROVE_ACTION:
            if (apply.isPassed()) {
                float fee = apply.getTotalFee().floatValue() / 10000;
                nextDoAction = RemedyWorkflowConstant.CHECK_ACTION;
            } else {
                nextDoAction = RemedyWorkflowConstant.THREE_APPROVE_ACTION;
            }
            break;
        case RemedyWorkflowConstant.CHECK_ACTION:
            nextDoAction = RemedyWorkflowConstant.SQUARE_ACTION;
            break;
        case RemedyWorkflowConstant.SQUARE_ACTION:
            nextDoAction = RemedyWorkflowConstant.END_ACTION;
            break;
        default:
            break;
        }

        return Integer.toString(nextDoAction);
    }
}
