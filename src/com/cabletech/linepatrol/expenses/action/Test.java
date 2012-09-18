package com.cabletech.linepatrol.expenses.action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.LazyDynaBean;


public class Test {

	public int compare(Object arg0, Object arg1) {   
		int muti0 = (Integer) arg0;   
		int muti1 = (Integer) arg1;   
		if (muti0 < muti1) {   
			return 1;   
		} else {   
			return 0;   
		}   
	}   


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Calendar calen = Calendar.getInstance();
		int year = calen.get(Calendar.YEAR);
		System.out.println(year+"  =====year");
		
		
		
		DynaBean car = new LazyDynaBean();   
		  car.set("carNo",1);      
		  car.set("owner4A","ÕÅÈý");
		System.out.println(car.get("carNo"));
		System.out.println(car.get("owner4A"));
		
		Map<Double,List> map = new HashMap<Double,List>();
		map.put(1.0,new ArrayList());
		map.put(0.8,new ArrayList());
		map.put(0.85,new ArrayList());
		map.put(0.9,new ArrayList());

		List list = new ArrayList(map.keySet());   
		Collections.sort(list,new Comparator(){
			public int compare(Object o1, Object o2) {
				double muti0 = Double.parseDouble(o1.toString());   
				double muti1 =  Double.parseDouble(o2.toString());     
				if (muti0 < muti1) {   
					return 1;   
				} else {   
					return 0;   
				}   
			}   
		});
		for(int j = 0;j<list.size();j++){
			System.out.println("  ========  "+list.get(j));
		}
	//	Collections.sort(list,com);   


		Object[]   chipKey   =    map.keySet().toArray();   
		Arrays.sort(chipKey);
		for (int i = 0;i<chipKey.length;i++){   
			System.out.println(" dddddd   "+chipKey[i]);
		}
	}

}
