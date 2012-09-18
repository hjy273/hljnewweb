package com.cabletech.planstat.util;

import org.apache.commons.lang.builder.ToStringBuilder;

public class Bean {
		private String label;
	  private String value;

	  /**
	   * Constructor for Car.
	   */
	  public Bean() {
	    super();
	  }

	  /**
	   * Constructor for Car.
	   */
	  public Bean(String label, String value) {
	    super();
	    this.label = label;
	    this.value = value;
	  }

	  /**
	   * @return Returns the name.
	   */
	  public String getLabel() {
	    return this.label;
	  }

	  /**
	   * @param label The label to set.
	   */
	  public void setLabel(String label) {
	    this.label = label;
	  }

	  /**
	   * @return Returns the value.
	   */
	  public String getValue() {
	    return this.value;
	  }

	  /**
	   * @param value The value to set.
	   */
	  public void setValue(String value) {
	    this.value = value;
	  }

	  /**
	   * @see java.lang.Object#toString()
	   */
	  public String toString() {
	    return new ToStringBuilder(this).append("label", label).append("value", value).toString();
	  }

}
