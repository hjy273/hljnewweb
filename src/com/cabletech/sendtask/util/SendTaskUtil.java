package com.cabletech.sendtask.util;

import java.util.Calendar;

public class SendTaskUtil {
	
	public static final String SENDTASK_MODELNAME = "[派单管理]";

	/**
	 * 取得待验证的处理时限（只是取得系统日期和回复日期跨周几周，与下面对应的getWeekNum的区别是，getWeekNum中的回复日期+2了，这个取得的加上后的跨周情况）
	 * @param replyDateStr 回复时间的字符串
	 * @return
	 */
	public static int getWeeks(String replyDateStr) {
		int weeks = 0;   	
		
	    Calendar beginCalendar = Calendar.getInstance();  
	    try {
	    	if(replyDateStr != null) {
		    	int rYear = Integer.parseInt(replyDateStr.substring(0, 4));
		    	int rMonth = Integer.parseInt(replyDateStr.substring(5, 7));
		    	int rDay = Integer.parseInt(replyDateStr.substring(8, 10));
		    	beginCalendar.set(rYear, rMonth-1, rDay); 
		    }
	    	// 回复日期加上2
	    	//beginCalendar.add(Calendar.DAY_OF_YEAR, 2);
	    	
	    	// 取系统日期
		    Calendar endCalendar = Calendar.getInstance(); 
		    endCalendar.set(endCalendar.get(Calendar.YEAR), endCalendar.get(Calendar.MONTH), endCalendar.get(Calendar.DAY_OF_MONTH));   

		    
		    // 如果开始时间太于结束时间，交换两日期
		    if(!beginCalendar.before(endCalendar)) {
		    	Calendar tmpDate = beginCalendar;
		    	beginCalendar = endCalendar;
		    	endCalendar = tmpDate;
		    	
		    }

		    while (beginCalendar.before(endCalendar)) {     
		        // 如果开始日期和结束日期在同年、同月且当前月的同一周时结束循环   
		        if (beginCalendar.get(Calendar.YEAR) == endCalendar   
		                .get(Calendar.YEAR)   
		                && beginCalendar.get(Calendar.MONTH) == endCalendar   
		                        .get(Calendar.MONTH)   
		                && beginCalendar.get(Calendar.WEEK_OF_YEAR) == endCalendar   
		                        .get(Calendar.WEEK_OF_YEAR)) {   
		            break;   

		        } else {   

		            beginCalendar.add(Calendar.DAY_OF_YEAR, 7);   
		            weeks += 1;   
		        }   
		    }   

	    } catch(Exception e) {
	    
	    }   
	    return weeks;
	}
	
	
	/**
	 * 取得验证过的的处理时限
	 * @param replyDateStr 回复时间的字符串
	 * @param validateStr  验证时间的字符串
	 * @return
	 */
	public static int getWeeks(String replyDateStr, String validateStr) {
		int weeks = 0;   	
		
		// 回复时间
	    Calendar beginCalendar = Calendar.getInstance();  
	    try {
	    	int rYear;
	    	int rMonth;
	    	int rDay;
	    	if(replyDateStr != null) {
		    	rYear = Integer.parseInt(replyDateStr.substring(0, 4));
		    	rMonth = Integer.parseInt(replyDateStr.substring(5, 7));
		    	rDay = Integer.parseInt(replyDateStr.substring(8, 10));
		    	beginCalendar.set(rYear, rMonth-1, rDay); 
		    }
	    	// 回复日期加上2
	    	//beginCalendar.add(Calendar.DAY_OF_YEAR, 2);
	    	
	    	// 验证时间
		    Calendar endCalendar = Calendar.getInstance(); 
	    	if(validateStr != null) {
		    	rYear = Integer.parseInt(validateStr.substring(0, 4));
		    	rMonth = Integer.parseInt(validateStr.substring(5, 7));
		    	rDay = Integer.parseInt(validateStr.substring(8, 10));
		    	endCalendar.set(rYear, rMonth-1, rDay); 
		    } 
		 
		    
		    // 如果开始时间太于结束时间，交换两日期
		    if(!beginCalendar.before(endCalendar)) {
		    	Calendar tmpDate = beginCalendar;
		    	beginCalendar = endCalendar;
		    	endCalendar = tmpDate;
		    	
		    }

		    while (beginCalendar.before(endCalendar)) {   

		        // 如果开始日期和结束日期在同年、同月且当前月的同一周时结束循环   
		        if (beginCalendar.get(Calendar.YEAR) == endCalendar   
		                .get(Calendar.YEAR)   
		                && beginCalendar.get(Calendar.MONTH) == endCalendar   
		                        .get(Calendar.MONTH)   
		                && beginCalendar.get(Calendar.WEEK_OF_YEAR) == endCalendar   
		                        .get(Calendar.WEEK_OF_YEAR)) {   
		            break;   

		        } else {   

		            beginCalendar.add(Calendar.DAY_OF_YEAR, 7);   
		            weeks += 1;   
		        }   
		    }   

	    } catch(Exception e) {
	    
	    }
	    return weeks;  
		
	}
	
	/**
	 * 取得待验证的处理时限
	 * @param replyDateStr 回复时间的字符串
	 * @return
	 */
	public static int getWeekNum(String replyDateStr) {
		int weeks = 0;   	
		
	    Calendar beginCalendar = Calendar.getInstance();  
	    try {
	    	if(replyDateStr != null) {
		    	int rYear = Integer.parseInt(replyDateStr.substring(0, 4));
		    	int rMonth = Integer.parseInt(replyDateStr.substring(5, 7));
		    	int rDay = Integer.parseInt(replyDateStr.substring(8, 10));
		    	beginCalendar.set(rYear, rMonth-1, rDay); 
		    }
	    	// 回复日期加上2
	    	beginCalendar.add(Calendar.DAY_OF_YEAR, 2);
	    	
	    	// 取系统日期
		    Calendar endCalendar = Calendar.getInstance(); 
		    endCalendar.set(endCalendar.get(Calendar.YEAR), endCalendar.get(Calendar.MONTH), endCalendar.get(Calendar.DAY_OF_MONTH));   

		    
		    // 如果开始时间太于结束时间，交换两日期
		    if(!beginCalendar.before(endCalendar)) {
		    	Calendar tmpDate = beginCalendar;
		    	beginCalendar = endCalendar;
		    	endCalendar = tmpDate;
		    	
		    }

		    while (beginCalendar.before(endCalendar)) {      
		        // 如果开始日期和结束日期在同年、同月且当前月的同一周时结束循环   
		        if (beginCalendar.get(Calendar.YEAR) == endCalendar   
		                .get(Calendar.YEAR)   
		                && beginCalendar.get(Calendar.MONTH) == endCalendar   
		                        .get(Calendar.MONTH)   
		                && beginCalendar.get(Calendar.WEEK_OF_YEAR) == endCalendar   
		                        .get(Calendar.WEEK_OF_YEAR)) {   
		            break;   

		        } else {   

		            beginCalendar.add(Calendar.DAY_OF_YEAR, 7);   
		            weeks += 1;   
		        }   
		    }   

	    } catch(Exception e) {
	    
	    }   
	    return weeks;
	}
	
	
	/**
	 * 取得验证过的的处理时限
	 * @param replyDateStr 回复时间的字符串
	 * @param validateStr  验证时间的字符串
	 * @return
	 */
	public static int getWeekNum(String replyDateStr, String validateStr) {
		int weeks = 0;   	
		
		// 回复时间
	    Calendar beginCalendar = Calendar.getInstance();  
	    try {
	    	int rYear;
	    	int rMonth;
	    	int rDay;
	    	if(replyDateStr != null) {
		    	rYear = Integer.parseInt(replyDateStr.substring(0, 4));
		    	rMonth = Integer.parseInt(replyDateStr.substring(5, 7));
		    	rDay = Integer.parseInt(replyDateStr.substring(8, 10));
		    	beginCalendar.set(rYear, rMonth-1, rDay); 
		    }
	    	// 回复日期加上2
	    	beginCalendar.add(Calendar.DAY_OF_YEAR, 2);
	    	
	    	// 验证时间
		    Calendar endCalendar = Calendar.getInstance(); 
	    	if(validateStr != null) {
		    	rYear = Integer.parseInt(validateStr.substring(0, 4));
		    	rMonth = Integer.parseInt(validateStr.substring(5, 7));
		    	rDay = Integer.parseInt(validateStr.substring(8, 10));
		    	endCalendar.set(rYear, rMonth-1, rDay); 
		    } 
		 
		    
		    // 如果开始时间太于结束时间，交换两日期
		    if(!beginCalendar.before(endCalendar)) {
		    	Calendar tmpDate = beginCalendar;
		    	beginCalendar = endCalendar;
		    	endCalendar = tmpDate;
		    	
		    }

		    while (beginCalendar.before(endCalendar)) {   

		        // 如果开始日期和结束日期在同年、同月且当前月的同一周时结束循环   
		        if (beginCalendar.get(Calendar.YEAR) == endCalendar   
		                .get(Calendar.YEAR)   
		                && beginCalendar.get(Calendar.MONTH) == endCalendar   
		                        .get(Calendar.MONTH)   
		                && beginCalendar.get(Calendar.WEEK_OF_YEAR) == endCalendar   
		                        .get(Calendar.WEEK_OF_YEAR)) {   
		            break;   

		        } else {   

		            beginCalendar.add(Calendar.DAY_OF_YEAR, 7);   
		            weeks += 1;   
		        }   
		    }   

	    } catch(Exception e) {
	    
	    }
	    return weeks;  
		
	}
}
