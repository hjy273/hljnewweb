package com.cabletech.commons.hb;

import java.io.Serializable;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.id.SequenceGenerator;
import org.hibernate.type.Type;
import org.hibernate.util.PropertiesHelper;
/**
 * ”√∑®£∫
 * <id name="id" type="java.lang.String" column="id">
			<generator class="com.cabletech.commons.hb.StrSequenceGenerator">
               <param name="sequence">SEQ_WORKSHEET_ID</param>
               <param name="length">10</param>
            </generator>             
	</id>
 * @author YuLeyuan
 *
 */
public class StrSequenceGenerator extends SequenceGenerator{
	public static final String PARAM_NAME = "length";
	public static final int DEFAULT_SEQUENCE_LENGTH = 12;
	private int sequenceLength;
	
	public void configure(Type type, Properties params, Dialect dialect) throws MappingException {
		super.configure(type, params, dialect);	
		sequenceLength=PropertiesHelper.getInt(PARAM_NAME, params, DEFAULT_SEQUENCE_LENGTH);
		
	}
	
	
	@Override
	public Serializable generate(SessionImplementor session, Object obj) 
	throws HibernateException {
		
		Serializable theKey=super.generate(session, obj);
		return StringUtils.leftPad(theKey.toString(), sequenceLength,'0');
	}
}
