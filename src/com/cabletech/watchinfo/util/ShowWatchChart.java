package com.cabletech.watchinfo.util;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import java.awt.*;

import org.jfree.chart.*;
import org.jfree.chart.title.*;
import org.jfree.data.general.*;

public class ShowWatchChart extends HttpServlet{

    public void service( ServletRequest req, ServletResponse res ) throws
        ServletException, IOException{
        res.setContentType( "image/jpeg" );

        String strPRate = req.getParameter( "PRate" );
        String strLRate = req.getParameter( "LRate" );

        float PRate = Float.parseFloat( strPRate );
        float LRate = Float.parseFloat( strLRate );

        DefaultPieDataset data;
        //填充数据
        data = getDataSet( PRate, LRate );

        String title = "完成率未完成率对比图";

        if( req.getParameter( "title" ) != null ){
            title = req.getParameter( "title" );
        }

        int width = 300;
        if( req.getParameter( "width" ) != null ){
            width = Integer.parseInt( req.getParameter( "width" ), 10 );
        }
        int height = 165;
        if( req.getParameter( "height" ) != null ){
            height = Integer.parseInt( req.getParameter( "height" ), 10 );
        }
        Font font = new Font( "黑体", Font.CENTER_BASELINE, 12 );
        //TextTitle texttitle = new TextTitle( title, font );

        JFreeChart chart = ChartFactory.createPieChart3D( title,
                           data,
                           true,
                           false,
                           false
                           );
        ChartUtilities.writeChartAsJPEG( res.getOutputStream(),
            1, chart, width, height, null );
    }


    /**
     * 获取一个演示用的简单数据集对象
     * @return
     */
    private static DefaultPieDataset getDataSet( float PRate, float LRate ){
        DefaultPieDataset dataset = new DefaultPieDataset();

        dataset.setValue( "未完成率", LRate );
        dataset.setValue( "完成率", PRate );
        return dataset;
    }
}
