package com.cabletech.linepatrol.safeguard.dao;

import org.springframework.stereotype.Repository;

import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.safeguard.module.SafeguardTask;

@Repository
public class SafeguardExamDao extends HibernateDao<SafeguardTask, String> {

}
