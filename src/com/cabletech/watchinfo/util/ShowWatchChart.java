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
        //�������
        data = getDataSet( PRate, LRate );

        String title = "�����δ����ʶԱ�ͼ";

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
            1, chart, width, height, null );
    }


    /**
     * ��ȡһ����ʾ�õļ����ݼ�����
     * @return
     */
    private static DefaultPieDataset getDataSet( float PRate, float LRate ){
        DefaultPieDataset dataset = new DefaultPieDataset();

        dataset.setValue( "δ�����", LRate );
        dataset.setValue( "�����", PRate );
        return dataset;
    }
}
