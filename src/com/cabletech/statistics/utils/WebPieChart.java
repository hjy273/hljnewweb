package com.cabletech.statistics.utils;

import java.io.*;
import javax.servlet.http.*;

import java.awt.*;

import org.jfree.chart.*;
import org.jfree.chart.entity.*;
import org.jfree.chart.labels.*;
import org.jfree.chart.plot.*;
import org.jfree.chart.servlet.*;
import org.jfree.chart.title.*;
import org.jfree.chart.urls.*;
import org.jfree.data.general.*;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.RectangleInsets;

public class WebPieChart{
    public WebPieChart(){
    }


    private DefaultPieDataset data = new DefaultPieDataset();

    public void setValue( String key, double value ){
        data.setValue( key, value );
    }


    public String generatePieChart( String title, HttpSession session,
        PrintWriter pw ){
        String filename = null;
        try{
            JFreeChart chart = ChartFactory.createPieChart3D( "����ͼ", // chart title
                               data, // data
                               true, // include legend
                               true,
                               false
                               );
            //����ͼƬ�ı���ɫ
            chart.setBackgroundPaint( java.awt.Color.white );
            //����ͼƬ���������ʹ�С
            Font font = new Font( "����", Font.CENTER_BASELINE, 20 );
            TextTitle texttitle = new TextTitle( title, font );
            //texttitle.setFont(font);
            chart.setTitle( texttitle );
            //chart.setAntiAlias(false);


            PiePlot plot = ( PiePlot )chart.getPlot();
            //plot.setInsets( new Insets( 5, 5, 5, 5 ) );   //old
            plot.setInsets(new RectangleInsets(5, 5, 5, 5));   //new
            //��ͳ��ͼƬ�Ͻ�����
            plot.setURLGenerator( new StandardPieURLGenerator( "link.jsp",
                "section" ) );
            //ָ�� section ��ǩ�ĵ�����

            //plot.setSectionLabelType(PiePlot.NAME_LABELS);
            //ָ�� section �����ߵ���ɫ
            plot.setSectionOutlinePaint( null );
            //ָ�� section �����ߵĺ��
            plot.setSectionOutlineStroke( new BasicStroke( 0 ) );
            //���õ�һ�� section �Ŀ�ʼλ�ã�Ĭ����12���ӷ���
            plot.setStartAngle( 270 );
            //ָ�� section ��ɫ��
            plot.setSectionPaint( 1, new Color( 0x99, 0x99, 0xFF ) );
            //ָ�� section ����ʱ�뷽��������ʾ��Ĭ����˳ʱ�뷽��
            //plot.setDirection(PiePlot.ANTICLOCKWISE);
            //ָ����ʾ�ı�ͼ��Բ�λ���Բ��
            //plot.setCircular(false);
            //plot.setToolTipGenerator( new StandardPieItemLabelGenerator() );  //old
            plot.setToolTipGenerator( new StandardPieToolTipGenerator() );     //new
            //ָ��ͼƬ��͸����
            plot.setForegroundAlpha( 0.5f );

            //�����ɵ�ͼƬ�ŵ���ʱĿ¼
            ChartRenderingInfo info = new ChartRenderingInfo( new

                                      StandardEntityCollection() );
            //500��ͼƬ���ȣ�300��ͼƬ�߶�
            filename = ServletUtilities.saveChartAsPNG( chart, 500, 300, info,

                       session );

//          ChartUtilities.writeImageMap( pw, filename, info ); //old
            ChartUtilities.writeImageMap( pw, filename, info,false);  //new
            pw.flush();

        }
        catch( Exception e ){
            System.out.println( "Exception - " + e.toString() );
            e.printStackTrace( System.out );
            filename = "public_error_500x300.png";
        }
        return filename;
    }

}
