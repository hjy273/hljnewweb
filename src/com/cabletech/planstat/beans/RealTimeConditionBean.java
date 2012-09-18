	package com.cabletech.planstat.beans;
	
	import com.cabletech.commons.base.*;
import com.cabletech.commons.util.DateUtil;
	public class RealTimeConditionBean  extends BaseBean{
		private String conID;
	    private String conName;
	    private String regionID;
	    private String regionName;
	    private String endYear;
	    private String endMonth;
	    private String statDate;
	    
	    public RealTimeConditionBean(){
	        endYear = DateUtil.getNowYearStr();
	    }
	    public String getConID(){
	        return conID;
	    }
	
	    public String getConName(){
	        return conName;
	    }
	    
	    public String getRegionID(){
	        return regionID;
	    }
	    
	    public String getRegionName(){
	        return regionName;
	    }
	    
	    public void setConID( String conID ){
	        this.conID = conID;
	    }
	
	    public void setConName( String conName ){
	        this.conName = conName;
	    }
	    
	    public void setRegionID( String regionID ){
	        this.regionID = regionID;
	    }
	
	    public void setRegionName( String regionName ){
	        this.regionName = regionName;
	    }
	    
	    public String getEndYear(){
	        return endYear;
	      }
	
	
	    public void setEndYear( String endYear ){
	        this.endYear = endYear;
	    }
	
	
	    public String getEndMonth(){
	        return endMonth;
	    }
	    
	    public void setEndMonth( String endMonth ){
	        this.endMonth = endMonth;
	    }
	    
	    public String toString(){
	        return conID + "-" + conName + "-" + regionID + "-" + regionName ;
	
	    }
		public String getStatDate() {
			return statDate;
		}
		public void setStatDate(String statDate) {
			this.statDate = statDate;
		}
	}
