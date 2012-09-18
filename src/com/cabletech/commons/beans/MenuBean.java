package com.cabletech.commons.beans;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
public class MenuBean  implements java.io.Serializable{
	private String mainlable;
	private String sublable;
	private String sonlable;
	private String mainid;
	private String subid;
	private String sonid;
	private String url;
	public String getMainlable() {
		return mainlable;
	}
	public void setMainlable(String mainlable) {
		this.mainlable = mainlable;
	}
	public String getSublable() {
		return sublable;
	}
	public void setSublable(String sublable) {
		this.sublable = sublable;
	}
	public String getSonlable() {
		return sonlable;
	}
	public void setSonlable(String sonlable) {
		this.sonlable = sonlable;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String toString(){
		return ReflectionToStringBuilder.toString(this);
	}
	public String getMainid() {
		return mainid;
	}
	public void setMainid(String mainid) {
		this.mainid = mainid;
	}
	public String getSubid() {
		return subid;
	}
	public void setSubid(String subid) {
		this.subid = subid;
	}
	public String getSonid() {
		return sonid;
	}
	public void setSonid(String sonid) {
		this.sonid = sonid;
	}
}
