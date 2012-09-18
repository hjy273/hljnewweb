package com.cabletech.commons.sqlbuild;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;

import org.antlr.stringtemplate.StringTemplate;

public class SqlTemplate {
	  private StringTemplate sqlTemplate;
	  private static final SimpleDateFormat timeFormat = new SimpleDateFormat(
	      "yyyy-MM-dd HH:mm:ss");
	  
	  private static final String WKT_POINT = "SDO_UTIL.FROM_WKTGEOMETRY(POINT ({0,number,#.######} {1,number,#.######}))";
	  private static final String GEO_POINT_STR = "MDSYS.SDO_GEOMETRY(2001,8307,SDO_POINT_TYPE({0,number,#.######},{1,number,#.######},NULL),NULL,NULL)";
	  private static final MessageFormat GEO_POINT_FMT = new MessageFormat(GEO_POINT_STR);
	  
	  private static final String SDO_DISTANCE_STR=
	    "sdo_geom.sdo_distance({0},MDSYS.SDO_GEOMETRY(2001,8307,SDO_POINT_TYPE({1,number,#.######},{2,number,#.######},NULL),NULL,NULL),0.005,''unit=meter'') {3}";
	  private static final MessageFormat SDO_DISTANCE_FMT = new MessageFormat(SDO_DISTANCE_STR);
	  
	  
	  
	  private static final String WITHIN_DISTANCE_STR=
	    "sdo_within_distance({0},MDSYS.SDO_GEOMETRY(2001,8307,SDO_POINT_TYPE({1,number,#.######},{2,number,#.######},NULL),NULL,NULL),''distance={3,number,#} unit=meter'')= ''TRUE''";
	  private static final MessageFormat WITHIN_DISTANCE_FMT = new MessageFormat(WITHIN_DISTANCE_STR);
	  
	  private static final String LAT_FMT_STR="{0,number,#.######}";
	  public static final MessageFormat LAT_FMT = new MessageFormat(LAT_FMT_STR);
	  
	  private static final String LON_FMT_STR="{0,number,#.######}";
	  public static final MessageFormat LON_FMT = new MessageFormat(LON_FMT_STR);
	  
	  
	  
	  public SqlTemplate(String sqlPatten) {
	    sqlTemplate = new StringTemplate(sqlPatten);
	  }
	  
	  public void reset() {
	    sqlTemplate.reset();
	  }
	  
	  public void setObject(String attName, Object attObject) {
	    sqlTemplate.setAttribute(attName, attObject);
	  }
	  
	  public void setString(String attName, String rawStr) {
	    if (rawStr == null) {
	      sqlTemplate.setAttribute(attName, "null");
	    } else {
	      sqlTemplate.setAttribute(attName, "'" + rawStr + "'");
	    }
	  }
	  
	  public void setDate(String attName, String timeStr) {
	    
	    if (timeStr == null) {
	      sqlTemplate.setAttribute(attName, "null");
	    } else {
	      sqlTemplate.setAttribute(attName, "to_date('" + timeStr
	          + "','YYYY-MM-DD HH24:MI:SS')");
	    }
	  }
	  
	  public void setDouble(String attName, double value,MessageFormat fmt) {
	    sqlTemplate.setAttribute(attName,fmt.format(new Object[]{ new Double(value)}));
	  }
	  
	  public void setDouble(String attName, double value) {
	    sqlTemplate.setAttribute(attName, Double.toString(value));
	  }
	  
	  public void setDouble(String attName, Double value) {
	    sqlTemplate.setAttribute(attName, value.toString());
	  }
	  
	  public void setInteger(String attName, int value) {
	    sqlTemplate.setAttribute(attName, Integer.toString(value));
	  }  
	  public void setInteger(String attName, Integer value) {
	    sqlTemplate.setAttribute(attName, value.toString());
	  }
	  public void setLong(String attName, long value) {
	    sqlTemplate.setAttribute(attName, Long.toString(value));
	  }  
	  public void setLong(String attName, Long value) {
	    sqlTemplate.setAttribute(attName, value.toString());
	  }  
	  
	  public void setDate(String attName, java.util.Date dt) {
	    if(dt==null){
	      sqlTemplate.setAttribute(attName, "null");
	    }else{
	      sqlTemplate.setAttribute(attName, "to_date('" + timeFormat.format(dt)
	        + "','YYYY-MM-DD HH24:MI:SS')");
	    }
	  }
	  
	  public void setGeoPoint(String attName, double x, double y) {
	    Object[] xy = { new Double(x), new Double(y) };
	    sqlTemplate.setAttribute(attName, GEO_POINT_FMT.format(xy));
	  }
	  
	  /**
	   * 
	   * @param attName     SQL模板中要替换的占位符名
	   * @param geoColName  用于参与地理计算的表的参考Spatial字段名
	   * @param x           用于参与计算距离的实际位置的经度
	   * @param y           用于参与计算距离的实际位置的纬度
	   * @param asName      对该距离计算列的列名
	   */
	  public void setSdoDistance(String attName,String geoColName,double x, double y,String asName) {
	    if(asName==null){
	      asName=attName;
	    }
	    Object[] params = { geoColName,new Double(x), new Double(y),asName};
	    sqlTemplate.setAttribute(attName, SDO_DISTANCE_FMT.format(params));
	  }
	  
	  /**
	   * 
	   * @param attName     SQL模板中要替换的占位符名
	   * @param geoColName  用于参与地理计算的表的参考Spatial字段名
	   * @param x           用于参与计算距离的实际位置的经度
	   * @param y           用于参与计算距离的实际位置的纬度
	   */
	  public void setSdoDistance(String attName,String geoColName,double x, double y) {
	    setSdoDistance(attName,geoColName,x,y,null);
	  }
	  
	  public void setSdoWithinDistance(String attName,String geoColName,double x, double y,double distance) {
	    Object[] params = { geoColName,new Double(x), new Double(y),new Double(distance)};
	    sqlTemplate.setAttribute(attName, WITHIN_DISTANCE_FMT.format(params));
	  }
	  
	  public String getSql() {
	    return sqlTemplate.toString();
	  }
	  
	  public String toString() {
	    return getSql();
	  }
	  
	  public static void main(String[] args) {
	    String sqlPatten = "SELECT * FROM HISTORYPOSITION "
	        + "WHERE activetime>$beginTime$ " + "AND activetime>$endTime$)";
	    SqlTemplate sql = new SqlTemplate(sqlPatten);
	    sql.setDate("beginTime", "2008-03-01 12:01:01");
	    sql.setDate("endTime", "2007-03-01 12:01:01");
	    System.out.println(sql.toString());
	    
	    sql = new SqlTemplate("update pointinfo set geoloc=$geopoint$");
	    sql.setGeoPoint("geopoint", 112.34, 23.16);
	    System.out.println(sql.toString());
	    
	  }
}
