package com.cabletech.planstat.util;

import org.displaytag.decorator.TableDecorator;
import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.log4j.Logger;

import com.cabletech.baseinfo.action.LineAction;

public class MyWrapper extends TableDecorator{
	private static Logger logger =
        Logger.getLogger( MyWrapper.class.getName() );
	public MyWrapper ()
    {
        super();
    }
    
    public String getLinkcollect()
    {
    	BasicDynaBean myModule = (BasicDynaBean) getCurrentRowObject();
    	
    	int lid=this.getListIndex();
        String simid = (String)myModule.get("simid");
        String operatedate = (String)myModule.get("operatedate");
        //String url="/WebApp/PlanMonthlyStatAction.do?method=showPerCardPerDay&handlestate=3&simid=" + simid + "&operatedate=" + operatedate;
        return "<a href=\"/WebApp/PlanMonthlyStatAction.do?method=showPerCardPerDay&lid=" + lid + "&simid=" + simid + "&operatedate=" + operatedate
               + "&handlestate=3\">" + myModule.get("collectnum") + "</a>";
    }

    public String getLinkmatch()
    {
    	BasicDynaBean myModule = (BasicDynaBean) getCurrentRowObject();
    	
    	int lid=this.getListIndex();
        String simid = (String)myModule.get("simid");
        String operatedate = (String)myModule.get("operatedate");
        //String url="/WebApp/PlanMonthlyStatAction.do?method=showPerCardPerDay&handlestate=3&simid=" + simid + "&operatedate=" + operatedate;
        return "<a href=\"/WebApp/PlanMonthlyStatAction.do?method=showPerCardPerDay&lid=" + lid + "&simid=" + simid + "&operatedate=" + operatedate
               + "&handlestate=7,8\">" + myModule.get("matchnum") + "</a>";
    }
    
    public String getLinkunmatch()
    {
    	BasicDynaBean myModule = (BasicDynaBean) getCurrentRowObject();
    	
    	int lid=this.getListIndex();
        String simid = (String)myModule.get("simid");
        String operatedate = (String)myModule.get("operatedate");
        //String url="/WebApp/PlanMonthlyStatAction.do?method=showPerCardPerDay&handlestate=3&simid=" + simid + "&operatedate=" + operatedate;
        return "<a href=\"/WebApp/PlanMonthlyStatAction.do?method=showPerCardPerDay&lid=" + lid + "&simid=" + simid + "&operatedate=" + operatedate
               + "&handlestate=4,12\">" + myModule.get("unmatchnum") + "</a>";
    }
}
