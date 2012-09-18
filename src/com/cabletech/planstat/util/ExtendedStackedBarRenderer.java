/* ExtendedStackedBarRenderer - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
package com.cabletech.planstat.util;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.geom.Rectangle2D;
import java.text.NumberFormat;

import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.entity.CategoryItemEntity;
import org.jfree.chart.entity.EntityCollection;
import org.jfree.chart.labels.CategoryItemLabelGenerator;
import org.jfree.chart.labels.CategoryToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.CategoryItemRendererState;
import org.jfree.chart.renderer.category.StackedBarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.text.TextUtilities;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.TextAnchor;

public class ExtendedStackedBarRenderer extends StackedBarRenderer
{
    private boolean showPositiveTotal = true;
    private boolean showNegativeTotal = true;
    private Font totalLabelFont = new Font("SansSerif", 1, 12);
    private NumberFormat totalFormatter = NumberFormat.getInstance();
    
    public NumberFormat getTotalFormatter() {
	return totalFormatter;
    }
    
    public void setTotalFormatter(NumberFormat numberformat) {
	if (numberformat == null)
	    throw new IllegalArgumentException("Null format not permitted.");
	totalFormatter = numberformat;
    }
    
    public void drawItem(Graphics2D graphics2d,
			 CategoryItemRendererState categoryitemrendererstate,
			 Rectangle2D rectangle2d, CategoryPlot categoryplot,
			 CategoryAxis categoryaxis, ValueAxis valueaxis,
			 CategoryDataset categorydataset, int i, int i_0_,
			 int i_1_) {
	Number number = categorydataset.getValue(i, i_0_);
	if (number != null) {
	    double d = number.doubleValue();
	    PlotOrientation plotorientation = categoryplot.getOrientation();
	    double d_2_
		= (categoryaxis.getCategoryMiddle(i_0_, getColumnCount(),
						  rectangle2d,
						  categoryplot
						      .getDomainAxisEdge())
		   - categoryitemrendererstate.getBarWidth() / 2.0);
	    double d_3_ = 0.0;
	    double d_4_ = 0.0;
	    for (int i_5_ = 0; i_5_ < i; i_5_++) {
		Number number_6_ = categorydataset.getValue(i_5_, i_0_);
		if (number_6_ != null) {
		    double d_7_ = number_6_.doubleValue();
		    if (d_7_ > 0.0)
			d_3_ += d_7_;
		    else
			d_4_ += d_7_;
		}
	    }
	    RectangleEdge rectangleedge = categoryplot.getRangeAxisEdge();
	    double d_8_;
	    double d_9_;
	    if (d > 0.0) {
		d_8_ = valueaxis.valueToJava2D(d_3_, rectangle2d,
					       rectangleedge);
		d_9_ = valueaxis.valueToJava2D(d_3_ + d, rectangle2d,
					       rectangleedge);
	    } else {
		d_8_ = valueaxis.valueToJava2D(d_4_, rectangle2d,
					       rectangleedge);
		d_9_ = valueaxis.valueToJava2D(d_4_ + d, rectangle2d,
					       rectangleedge);
	    }
	    double d_10_ = Math.min(d_8_, d_9_);
	    double d_11_
		= Math.max(Math.abs(d_9_ - d_8_), getMinimumBarLength());
	    Object object = null;
	    Rectangle2D.Double var_double;
	    if (plotorientation == PlotOrientation.HORIZONTAL)
		var_double = new Rectangle2D.Double(d_10_, d_2_, d_11_,
						    categoryitemrendererstate
							.getBarWidth());
	    else
		var_double = new Rectangle2D.Double(d_2_, d_10_,
						    categoryitemrendererstate
							.getBarWidth(),
						    d_11_);
	    Paint paint = getItemPaint(i, i_0_);
	    graphics2d.setPaint(paint);
	    graphics2d.fill(var_double);
	    if (isDrawBarOutline()
		&& categoryitemrendererstate.getBarWidth() > 3.0) {
		graphics2d.setStroke(getItemStroke(i, i_0_));
		graphics2d.setPaint(getItemOutlinePaint(i, i_0_));
		graphics2d.draw(var_double);
	    }
	    CategoryItemLabelGenerator categoryitemlabelgenerator
		= getItemLabelGenerator(i, i_0_);
	    if (categoryitemlabelgenerator != null
		&& isItemLabelVisible(i, i_0_))
		drawItemLabel(graphics2d, categorydataset, i, i_0_,
			      categoryplot, categoryitemlabelgenerator,
			      var_double, d < 0.0);
	    if (d > 0.0) {
		if (showPositiveTotal
		    && isLastPositiveItem(categorydataset, i, i_0_)) {
		    graphics2d.setPaint(Color.black);
		    graphics2d.setFont(totalLabelFont);
		    double d_12_ = (calculateSumOfPositiveValuesForCategory
				    (categorydataset, i_0_));
		    float f = (float) var_double.getCenterX();
		    float f_13_ = (float) var_double.getMinY() - 4.0F;
		    TextAnchor textanchor = TextAnchor.BOTTOM_CENTER;
		    if (plotorientation == PlotOrientation.HORIZONTAL) {
			f = (float) var_double.getMaxX() + 4.0F;
			f_13_ = (float) var_double.getCenterY();
			textanchor = TextAnchor.CENTER_LEFT;
		    }
		    TextUtilities.drawRotatedString(totalFormatter
							.format(d_12_),
						    graphics2d, f, f_13_,
						    textanchor, 0.0,
						    TextAnchor.CENTER);
		}
	    } else if (showNegativeTotal
		       && isLastNegativeItem(categorydataset, i, i_0_)) {
		graphics2d.setPaint(Color.black);
		graphics2d.setFont(totalLabelFont);
		double d_14_
		    = calculateSumOfNegativeValuesForCategory(categorydataset,
							      i_0_);
		float f = (float) var_double.getCenterX();
		float f_15_ = (float) var_double.getMaxY() + 4.0F;
		TextAnchor textanchor = TextAnchor.TOP_CENTER;
		if (plotorientation == PlotOrientation.HORIZONTAL) {
		    f = (float) var_double.getMinX() - 4.0F;
		    f_15_ = (float) var_double.getCenterY();
		    textanchor = TextAnchor.CENTER_RIGHT;
		}
		TextUtilities.drawRotatedString(totalFormatter.format(d_14_),
						graphics2d, f, f_15_,
						textanchor, 0.0,
						TextAnchor.CENTER);
	    }
	    if (categoryitemrendererstate.getInfo() != null) {
		EntityCollection entitycollection
		    = categoryitemrendererstate.getEntityCollection();
		if (entitycollection != null) {
		    String string = null;
		    CategoryToolTipGenerator categorytooltipgenerator
			= getToolTipGenerator(i, i_0_);
		    if (categorytooltipgenerator != null)
			string
			    = categorytooltipgenerator
				  .generateToolTip(categorydataset, i, i_0_);
		    String string_16_ = null;
		    if (getItemURLGenerator(i, i_0_) != null)
			string_16_
			    = getItemURLGenerator(i, i_0_)
				  .generateURL(categorydataset, i, i_0_);
		    CategoryItemEntity categoryitementity
			= new CategoryItemEntity(var_double, string,
						 string_16_, categorydataset,
						 i,
						 categorydataset
						     .getColumnKey(i_0_),
						 i_0_);
		    entitycollection.add(categoryitementity);
		}
	    }
	}
    }
    
    private boolean isLastPositiveItem(CategoryDataset categorydataset, int i,
				       int i_17_) {
	boolean bool = true;
	Number number = categorydataset.getValue(i, i_17_);
	if (number == null)
	    return false;
	for (int i_18_ = i + 1; i_18_ < categorydataset.getRowCount();
	     i_18_++) {
	    number = categorydataset.getValue(i_18_, i_17_);
	    if (number != null)
		bool = bool && number.doubleValue() <= 0.0;
	}
	return bool;
    }
    
    private boolean isLastNegativeItem(CategoryDataset categorydataset, int i,
				       int i_19_) {
	boolean bool = true;
	Number number = categorydataset.getValue(i, i_19_);
	if (number == null)
	    return false;
	for (int i_20_ = i + 1; i_20_ < categorydataset.getRowCount();
	     i_20_++) {
	    number = categorydataset.getValue(i_20_, i_19_);
	    if (number != null)
		bool = bool && number.doubleValue() >= 0.0;
	}
	return bool;
    }
    
    private double calculateSumOfPositiveValuesForCategory
	(CategoryDataset categorydataset, int i) {
	double d = 0.0;
	for (int i_21_ = 0; i_21_ < categorydataset.getRowCount(); i_21_++) {
	    Number number = categorydataset.getValue(i_21_, i);
	    if (number != null) {
		double d_22_ = number.doubleValue();
		if (d_22_ > 0.0)
		    d += d_22_;
	    }
	}
	return d;
    }
    
    private double calculateSumOfNegativeValuesForCategory
	(CategoryDataset categorydataset, int i) {
	double d = 0.0;
	for (int i_23_ = 0; i_23_ < categorydataset.getRowCount(); i_23_++) {
	    Number number = categorydataset.getValue(i_23_, i);
	    if (number != null) {
		double d_24_ = number.doubleValue();
		if (d_24_ < 0.0)
		    d += d_24_;
	    }
	}
	return d;
    }
}
