package com.cabletech.analysis.util;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Paint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.log4j.Logger;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.annotations.CategoryTextAnnotation;
import org.jfree.chart.axis.CategoryAnchor;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPosition;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.CategoryLabelWidthType;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.renderer.category.BarRenderer3D;
import org.jfree.data.Range;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.text.TextBlockAnchor;
import org.jfree.ui.Layer;
import org.jfree.ui.RectangleAnchor;
import org.jfree.ui.TextAnchor;

import com.cabletech.commons.util.DateUtil;

/**
 * ���ɶԱȷ���ͼ��
 */
public class ContrastChart extends HttpServlet {
    private Logger logger = Logger.getLogger("ContrastChart");

    static int intStartMonth = 0;

    static int intEndMonth = 0;

    private int width = 650;

    private int height = 395;

    public void service(ServletRequest req, ServletResponse res) throws ServletException,
            IOException {
        List list = new ArrayList();
        ;
        String viewtype = req.getParameter("viewtype");
        JFreeChart jfreechart = null;
        HttpSession session = ((HttpServletRequest) req).getSession();
        // �����Ƚ�
        if ("area".equals(viewtype)) {
            logger.info("viewtype2 " + viewtype);
            list = (List) session.getAttribute("areaList");
            String title = "����ȫʡѲ�����";
            String name = null;
            name = "����";
            if (list == null) {
                title = "û���ҵ�����Ҫ������";
            }
            jfreechart = generate3DGroupBarChart(getRegionDataSet(list), title, name);
        } else {
            // �������±Ƚ�
            logger.info("viewtype1 " + viewtype);
            intEndMonth = Integer.parseInt((String) session.getAttribute("month"));
            intStartMonth = DateUtil.monthSubtract(intEndMonth, 2);
            String title = "��������Ѳ�����";
            String name = null;
            list = (List) session.getAttribute("monthList");
            if (list == null) {
                title = "û���ҵ�����Ҫ������";
            }
            jfreechart = generate3DBarChart(getMonthDataSet(list), title, name);
        }
        res.setContentType("image/jpeg");
        Integer w = (Integer) session.getAttribute("width");
        Integer h = (Integer) session.getAttribute("height");

        Font font = new Font("����", Font.CENTER_BASELINE, 12);
        ChartUtilities.writeChartAsJPEG(res.getOutputStream(), 1.0f, jfreechart, w == null ? width
                : w.intValue(), h == null ? height : h.intValue(), null);
    }

    private CategoryDataset getMonthDataSet(List list) {
        DefaultCategoryDataset defaultcategorydataset = new DefaultCategoryDataset();
        double value = 0;

        int SqlStartMonth = 0;
        if (list != null && !list.isEmpty()) {
            for (int i = 0, month = intStartMonth;; month++) {
                if (i < list.size()) {
                    SqlStartMonth = Integer.parseInt(((BasicDynaBean) list.get(i)).get("factdate")
                            .toString());
                } else {
                    SqlStartMonth = 0;
                }
                if (month > 12) {
                    month = 1;
                }
                if (SqlStartMonth == month) {
                    value = Double.parseDouble(((BasicDynaBean) list.get(i)).get("examineresult")
                            .toString());
                    i++;
                    if (i >= list.size()) {
                        i--;
                    }
                } else {
                    value = 0;
                }
                logger.info("" + value + "   " + month + "��" + SqlStartMonth);
                defaultcategorydataset.addValue(value, "���˽��(�����)", month + "��");
                if (month == intEndMonth) {
                    break;
                }
            }
        }
        return defaultcategorydataset;
    }

    /**
     * ����3D��״ͼ
     * 
     * @param session
     * @param pw
     * @return String
     */
    public JFreeChart generate3DBarChart(CategoryDataset categorydataset, String title, String name) {
        JFreeChart jfreechart = ChartFactory.createBarChart3D(title, "�·�", "�ȼ�", categorydataset,
                PlotOrientation.VERTICAL, false, true, false);
        CategoryPlot categoryplot = (CategoryPlot) jfreechart.getPlot();
        CustomBarRenderer3D custombarrenderer3d = new CustomBarRenderer3D();
        custombarrenderer3d.setItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        custombarrenderer3d.setBaseItemLabelsVisible(true);
        custombarrenderer3d.setItemLabelAnchorOffset(10.0);
        custombarrenderer3d.setPositiveItemLabelPosition(new ItemLabelPosition(
                ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_LEFT));
        categoryplot.setRenderer(custombarrenderer3d);
        ValueMarker valuemarker = new ValueMarker(3.0, new Color(200, 200, 255), new BasicStroke(
                1.0F), new Color(200, 200, 255), new BasicStroke(1.0F), 1.0F);
        categoryplot.addRangeMarker(valuemarker, Layer.BACKGROUND);
        custombarrenderer3d.setItemLabelsVisible(true);
        custombarrenderer3d.setMaximumBarWidth(0.05);
        CategoryTextAnnotation categorytextannotation = new CategoryTextAnnotation("�����",
                intEndMonth + "��", 3.0);
        categorytextannotation.setCategoryAnchor(CategoryAnchor.START);
        categorytextannotation.setFont(new Font("SansSerif", 0, 12));
        categorytextannotation.setTextAnchor(TextAnchor.BOTTOM_LEFT);
        categoryplot.addAnnotation(categorytextannotation);
        NumberAxis numberaxis = (NumberAxis) categoryplot.getRangeAxis();
        // numberaxis.setNumberFormatOverride(NumberFormat.getIntegerInstance());
        numberaxis.setRange(new Range(0, 5.5));
        numberaxis.setAutoTickUnitSelection(false);
        numberaxis.setUpperMargin(0.1);
        return jfreechart;
    }

    private CategoryDataset getRegionDataSet(List list) {
        DefaultCategoryDataset defaultcategorydataset = new DefaultCategoryDataset();
        for (int i = 0; i < list.size(); i++) {
            BasicDynaBean dynabean = (BasicDynaBean) list.get(i);
            double value = Double.parseDouble(dynabean.get("examineresult").toString());
            double planpoint = Double.parseDouble(dynabean.get("planpoint").toString());
            double factpoint = Double.parseDouble(dynabean.get("factpoint").toString());
            double troublenum = Double.parseDouble(dynabean.get("troublenum").toString());
            String regionname = (String) dynabean.get("regionname");
            defaultcategorydataset.addValue(planpoint, "�ƻ�Ѳ����", regionname);
            defaultcategorydataset.addValue(factpoint, "ʵ��Ѳ����", regionname);
            defaultcategorydataset.addValue(troublenum, "��������", regionname);
            defaultcategorydataset.addValue(value, "���˽��(�����)", regionname);

        }
        return defaultcategorydataset;
    }

    /**
     * ���ɷ����3D��״ͼ
     * 
     * @param session
     * @param pw
     * @return JFreeChart
     */
    public JFreeChart generate3DGroupBarChart(CategoryDataset categorydataset, String title,
            String name) {
        JFreeChart jfreechart = ChartFactory.createBarChart3D(title, name, "", categorydataset,
                PlotOrientation.VERTICAL, true, true, false);
        CategoryPlot categoryplot = (CategoryPlot) jfreechart.getPlot();
        // ��ʾÿ��������ֵ�����޸ĸ���ֵ����������
        BarRenderer3D renderer = (BarRenderer3D) categoryplot.getRenderer();
        renderer.setItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        renderer.setItemLabelsVisible(true);

        renderer.setPositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE1,
                TextAnchor.BOTTOM_RIGHT));
        // ������״���������ƫ����
        renderer.setItemLabelAnchorOffset(10.0);
        categoryplot.setRenderer(renderer);

        CategoryAxis categoryaxis = categoryplot.getDomainAxis();
        CategoryLabelPositions categorylabelpositions = categoryaxis.getCategoryLabelPositions();
        CategoryLabelPosition categorylabelposition = new CategoryLabelPosition(
                RectangleAnchor.LEFT, TextBlockAnchor.CENTER_LEFT, TextAnchor.CENTER_LEFT, 0.0,
                CategoryLabelWidthType.RANGE, 0.3F);
        categoryaxis.setCategoryLabelPositions(CategoryLabelPositions.replaceLeftPosition(
                categorylabelpositions, categorylabelposition));

        return jfreechart;
    }

    static class CustomBarRenderer3D extends BarRenderer3D {
        public CustomBarRenderer3D() {
            /* empty */
        }

        public Paint getItemPaint(int i, int i_0_) {
            CategoryDataset categorydataset = getPlot().getDataset();
            double d = categorydataset.getValue(i, i_0_).doubleValue();
            if (d >= 3.0)
                return Color.green;
            return Color.red;
        }
    }
}
