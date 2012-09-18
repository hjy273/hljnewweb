package com.cabletech.planstat.util;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import java.awt.*;

import org.jfree.chart.*;
import org.jfree.chart.title.*;
import org.jfree.data.general.*;

public class ShowPlanExeChart extends HttpServlet{

    public void service( ServletRequest req, ServletResponse res ) throws
        ServletException, IOException{
        res.setContentType( "image/jpeg" );

        String strPRate = req.getParameter( "PRate" );

        float PRate = Float.parseFloat( strPRate );
        float LRate = 100-PRate;

        DefaultPieDataset data;
        //�������
        data = getDataSet( PRate, LRate );

        String title = "Ѳ���ʺ�©���ʶԱ�ͼ";

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
        Font font = new Font( "����", Font.CENTER_BASELINE, 12 );
        //TextTitle texttitle = new TextTitle( title, font );

        JFreeChart chart = ChartFactory.createPieChart3D( title,
                           data,
                           true,
                           false,
                           false
                           );
        ChartUtilities.writeChartAsJPEG( res.getOutputStream(),
            1.0f, chart, width, height, null );
    }


    /**
     * ��ȡһ����ʾ�õļ����ݼ�����
     * @return
     */
    private static DefaultPieDataset getDataSet( float PRate, float LRate ){
        DefaultPieDataset dataset = new DefaultPieDataset();

        dataset.setValue( "©����", LRate );
        dataset.setValue( "Ѳ����", PRate );
        return dataset;
    }
}
