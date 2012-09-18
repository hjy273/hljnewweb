package com.cabletech.commons.base;

import org.apache.commons.lang.builder.ToStringBuilder;

public class BaseDomainObject
    implements java.io.Serializable{
    public BaseDomainObject(){
    }
    public String toString(){
		return ToStringBuilder.reflectionToString(this);
	}
}
