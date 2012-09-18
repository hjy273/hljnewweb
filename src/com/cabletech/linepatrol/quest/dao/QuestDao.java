package com.cabletech.linepatrol.quest.dao;

import org.springframework.stereotype.Repository;

import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.quest.module.QuestIssue;

@Repository
public class QuestDao extends HibernateDao<QuestIssue, String> {

}
