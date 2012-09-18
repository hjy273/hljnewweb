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
            JFreeChart chart = ChartFactory.createPieChart3D( "饼型图", // chart title
                               data, // data
                               true, // include legend
                               true,
                               false
                               );
            //设置图片的背景色
            chart.setBackgroundPaint( java.awt.Color.white );
            //设置图片标题的字体和大小
            Font font = new Font( "黑体", Font.CENTER_BASELINE, 20 );
            TextTitle texttitle = new TextTitle( title, font );
            //texttitle.setFont(font);
            chart.setTitle( texttitle );
            //chart.setAntiAlias(false);


            PiePlot plot = ( PiePlot )chart.getPlot();
            //plot.setInsets( new Insets( 5, 5, 5, 5 ) );   //old
            plot.setInsets(new RectangleInsets(5, 5, 5, 5));   //new
            //在统计图片上建连结
            plot.setURLGenerator( new StandardPieURLGenerator( "link.jsp",
                "section" ) );
            //指定 section 标签的的类型

            //plot.setSectionLabelType(PiePlot.NAME_LABELS);
            //指定 section 轮廓线的颜色
            plot.setSectionOutlinePaint( null );
            //指定 section 轮廓线的厚度
            plot.setSectionOutlineStroke( new BasicStroke( 0 ) );
            //设置第一个 section 的开始位置，默认是12点钟方向
            plot.setStartAngle( 270 );
            //指定 section 的色彩
            plot.setSectionPaint( 1, new Color( 0x99, 0x99, 0xFF ) );
            //指定 section 按逆时针方向依次显示，默认是顺时针方向
            //plot.setDirection(PiePlot.ANTICLOCKWISE);
            //指定显示的饼图上圆形还椭圆形
            //plot.setCircular(false);
            //plot.setToolTipGenerator( new StandardPieItemLabelGenerator() );  //old
            plot.setToolTipGenerator( new StandardPieToolTipGenerator() );     //new
            //指定图片的透明度
            plot.setForegroundAlpha( 0.5f );

            //把生成的图片放到临时目录
            ChartRenderingInfo info = new ChartRenderingInfo( new

                                      StandardEntityCollection() );
            //500是图片长度，300是图片高度
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
