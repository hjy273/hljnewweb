package com.cabletech.sendtask.util;

import java.util.Calendar;

public class SendTaskUtil {
	
	public static final String SENDTASK_MODELNAME = "[�ɵ�����]";

	/**
	 * ȡ�ô���֤�Ĵ���ʱ�ޣ�ֻ��ȡ��ϵͳ���ںͻظ����ڿ��ܼ��ܣ��������Ӧ��getWeekNum�������ǣ�getWeekNum�еĻظ�����+2�ˣ����ȡ�õļ��Ϻ�Ŀ��������
	 * @param replyDateStr �ظ�ʱ����ַ���
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
	    	// �ظ����ڼ���2
	    	//beginCalendar.add(Calendar.DAY_OF_YEAR, 2);
	    	
	    	// ȡϵͳ����
		    Calendar endCalendar = Calendar.getInstance(); 
		    endCalendar.set(endCalendar.get(Calendar.YEAR), endCalendar.get(Calendar.MONTH), endCalendar.get(Calendar.DAY_OF_MONTH));   

		    
		    // �����ʼʱ��̫�ڽ���ʱ�䣬����������
		    if(!beginCalendar.before(endCalendar)) {
		    	Calendar tmpDate = beginCalendar;
		    	beginCalendar = endCalendar;
		    	endCalendar = tmpDate;
		    	
		    }

		    while (beginCalendar.before(endCalendar)) {     
		        // �����ʼ���ںͽ���������ͬ�ꡢͬ���ҵ�ǰ�µ�ͬһ��ʱ����ѭ��   
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
	 * ȡ����֤���ĵĴ���ʱ��
	 * @param replyDateStr �ظ�ʱ����ַ���
	 * @param validateStr  ��֤ʱ����ַ���
	 * @return
	 */
	public static int getWeeks(String replyDateStr, String validateStr) {
		int weeks = 0;   	
		
		// �ظ�ʱ��
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
	    	// �ظ����ڼ���2
	    	//beginCalendar.add(Calendar.DAY_OF_YEAR, 2);
	    	
	    	// ��֤ʱ��
		    Calendar endCalendar = Calendar.getInstance(); 
	    	if(validateStr != null) {
		    	rYear = Integer.parseInt(validateStr.substring(0, 4));
		    	rMonth = Integer.parseInt(validateStr.substring(5, 7));
		    	rDay = Integer.parseInt(validateStr.substring(8, 10));
		    	endCalendar.set(rYear, rMonth-1, rDay); 
		    } 
		 
		    
		    // �����ʼʱ��̫�ڽ���ʱ�䣬����������
		    if(!beginCalendar.before(endCalendar)) {
		    	Calendar tmpDate = beginCalendar;
		    	beginCalendar = endCalendar;
		    	endCalendar = tmpDate;
		    	
		    }

		    while (beginCalendar.before(endCalendar)) {   

		        // �����ʼ���ںͽ���������ͬ�ꡢͬ���ҵ�ǰ�µ�ͬһ��ʱ����ѭ��   
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
	 * ȡ�ô���֤�Ĵ���ʱ��
	 * @param replyDateStr �ظ�ʱ����ַ���
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
	    	// �ظ����ڼ���2
	    	beginCalendar.add(Calendar.DAY_OF_YEAR, 2);
	    	
	    	// ȡϵͳ����
		    Calendar endCalendar = Calendar.getInstance(); 
		    endCalendar.set(endCalendar.get(Calendar.YEAR), endCalendar.get(Calendar.MONTH), endCalendar.get(Calendar.DAY_OF_MONTH));   

		    
		    // �����ʼʱ��̫�ڽ���ʱ�䣬����������
		    if(!beginCalendar.before(endCalendar)) {
		    	Calendar tmpDate = beginCalendar;
		    	beginCalendar = endCalendar;
		    	endCalendar = tmpDate;
		    	
		    }

		    while (beginCalendar.before(endCalendar)) {      
		        // �����ʼ���ںͽ���������ͬ�ꡢͬ���ҵ�ǰ�µ�ͬһ��ʱ����ѭ��   
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
	 * ȡ����֤���ĵĴ���ʱ��
	 * @param replyDateStr �ظ�ʱ����ַ���
	 * @param validateStr  ��֤ʱ����ַ���
	 * @return
	 */
	public static int getWeekNum(String replyDateStr, String validateStr) {
		int weeks = 0;   	
		
		// �ظ�ʱ��
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
	    	// �ظ����ڼ���2
	    	beginCalendar.add(Calendar.DAY_OF_YEAR, 2);
	    	
	    	// ��֤ʱ��
		    Calendar endCalendar = Calendar.getInstance(); 
	    	if(validateStr != null) {
		    	rYear = Integer.parseInt(validateStr.substring(0, 4));
		    	rMonth = Integer.parseInt(validateStr.substring(5, 7));
		    	rDay = Integer.parseInt(validateStr.substring(8, 10));
		    	endCalendar.set(rYear, rMonth-1, rDay); 
		    } 
		 
		    
		    // �����ʼʱ��̫�ڽ���ʱ�䣬����������
		    if(!beginCalendar.before(endCalendar)) {
		    	Calendar tmpDate = beginCalendar;
		    	beginCalendar = endCalendar;
		    	endCalendar = tmpDate;
		    	
		    }

		    while (beginCalendar.before(endCalendar)) {   

		        // �����ʼ���ںͽ���������ͬ�ꡢͬ���ҵ�ǰ�µ�ͬһ��ʱ����ѭ��   
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
