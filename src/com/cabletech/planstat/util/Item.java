/**
 * A generic item class, basically representing a name-value pair.
 *
 * @author Darren Spurgoen
 * @version $Revision: 1.1.2.1 $ $Date: 2008/01/30 03:04:38 $
 */
package com.cabletech.planstat.util;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * @author Darren L. Spurgeon
 * @version $Revision: 1.1.2.1 $ $Date: 2008/01/30 03:04:38 $
 */
class Item {

  protected String name;

  protected String value;

  protected boolean asData;

  /**
   * Constructor for Item.
   */
  public Item() {
    super();
  }

  /**
   * Constructor for Item.
   *
   * @param name
   * @param value
   */
  public Item(String name, String value, boolean asData) {
    super();
    this.name = name;
    this.value = value;
    this.asData = asData;
  }

  /**
   * @return Returns the name.
   */
  public String getName() {
    return this.name;
  }

  /**
   * @param name The name to set.
   */
  public void setName(String name) {
    this.name = name;
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
   * @return Returns the asCData.
   */
  public boolean isAsCData() {
    return this.asData;
  }

  /**
   * @param asData The asData to set.
   */
  public void setAsData(boolean asData) {
    this.asData = asData;
  }

  /**
   * @see java.lang.Object#toString()
   */
  public String toString() {
    return new ToStringBuilder(this).append("name", name)
        .append("value", value).append("asData", asData).toString();
  }

}