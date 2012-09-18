package com.cabletech.linepatrol.resource.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.resource.model.DatumType;

@Repository
public class DatumTypeDao extends HibernateDao<DatumType, String> {
	public List<DatumType> hadDuplicateName(String name){
		String hql = "from DatumType d where d.name = ?";
		return find(hql, name);
	}
}
