/**
 * Copyright 2005 Darren L. Spurgeon
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.cabletech.planstat.util;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Helper class to build valid XML typically returned in a response to the
 * client.
 * 
 * @author Darren Spurgeon
 * @version $Revision: 1.1.2.1 $ $Date: 2008/01/30 03:04:38 $
 */
public class AjaxXmlBuilder {

	private String encoding = "UTF-8";
	private List items = new ArrayList();

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	/**
	 * Add item to XML.
	 * 
	 * @param name
	 *            The name of the item
	 * @param value
	 *            The value of the item
	 * @return
	 */
	public AjaxXmlBuilder addItem(String name, String value) {
		items.add(new Item(name, value, false));
		return this;
	}

	/**
	 * Add item wrapped with inside a CDATA element.
	 * 
	 * @param name
	 *            The name of the item
	 * @param value
	 *            The value of the item
	 * @return
	 */
	public AjaxXmlBuilder addItemAsCData(String name, String value) {
		items.add(new Item(name, value, true));
		return this;
	}

	/**
	 * Add items from a collection.
	 * 
	 * @param collection
	 * @param nameProperty
	 * @param valueProperty
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	public AjaxXmlBuilder addItems(Collection collection, String nameProperty,
			String valueProperty) throws IllegalAccessException,
			InvocationTargetException, NoSuchMethodException {
		return addItems(collection, nameProperty, valueProperty, false);
	}
	
	/**
	 * Add items from a collection.
	 * 
	 * @param collection
	 * @param nameProperty
	 * @param valueProperty
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	public AjaxXmlBuilder addItems(Collection collection, String nameProperty,
			String valueProperty, boolean asCData)
			throws IllegalAccessException, InvocationTargetException,
			NoSuchMethodException {
		System.out.println("collection "+collection.size());
		for (Iterator iter = collection.iterator(); iter.hasNext();) {
			Object element = (Object) iter.next();
			System.out.println("element "+element.toString());
			System.out.println("nameProperty= "+nameProperty.toString());
			System.out.println("valueProperty= "+valueProperty.toString());
			String name = BeanUtils.getProperty(element, nameProperty);
			System.out.println("name "+name.toString());
			String value = BeanUtils.getProperty(element, valueProperty);
			System.out.println("value "+value.toString());
			if (asCData) {
				items.add(new Item(name, value, false));
			} else {
				items.add(new Item(name, value, true));

			}
		}
		return this;
	}

	/**
	 * Add items from a collection as CDATA element.
	 * 
	 * @param collection
	 * @param nameProperty
	 * @param valueProperty
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	public AjaxXmlBuilder addItemsAsCData(Collection collection,
			String nameProperty, String valueProperty)
			throws IllegalAccessException, InvocationTargetException,
			NoSuchMethodException {
		return addItems(collection, nameProperty, valueProperty, true);
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public Map toMap() {
		StringBuffer xml = new StringBuffer();
		if (encoding != null) {
		}
		Map map = new HashMap();
		for (Iterator iter = items.iterator(); iter.hasNext();) {
			Item item = (Item) iter.next();
			map.put(item.getName(),item.getValue());
			//xml.append(item.getName());
			//xml.append(item.getValue());
		}
		//System.out.println("xml in AjaxXMlBuilder: " + xml.toString());
		return map;

	}
	
//	/**
//	 * @param args
//	 */
//	public static void main(String []args){
//		List list = new ArrayList();
//		 String s = "";
//		 list.add(new Bean("aaaaa","a"));
//		 list.add(new Bean("bbbbb","b"));
//		 list.add(new Bean("ccccc","c"));
//		 list.add(new Bean("ddddd","d"));
//		 System.out.println("ss " +s);
//		 try {
//			s =  new AjaxXmlBuilder().addItems(list, "label", "value").toString();
//		} catch (IllegalAccessException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (InvocationTargetException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (NoSuchMethodException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		 System.out.println(s);
//	}
//	/**
//	 * @see java.lang.Object#toString()
//	 */
//	public String toString() {
//		StringBuffer xml = new StringBuffer().append("<?xml version=\"1.0\"");
//		if (encoding != null) {
//			xml.append(" encoding=\"");
//			xml.append(encoding);
//			xml.append("\"");
//		}
//		xml.append(" ?>");
//
//		xml.append("<ajax-response>");
//		xml.append("<response>");
//		for (Iterator iter = items.iterator(); iter.hasNext();) {
//			Item item = (Item) iter.next();
//			xml.append("<item>");
//			xml.append("<name>");
//			if (item.isAsCData()) {
//				xml.append("<![CDATA[");
//			}
//			xml.append(item.getName());
//			if (item.isAsCData()) {
//				xml.append("]]>");
//			}
//			xml.append("</name>");
//			xml.append("<value>");
//			if (item.isAsCData()) {
//				xml.append("<![CDATA[");
//			}
//			xml.append(item.getValue());
//			if (item.isAsCData()) {
//				xml.append("]]>");
//			}
//			xml.append("</value>");
//			xml.append("</item>");
//		}
//		xml.append("</response>");
//		xml.append("</ajax-response>");
//		System.out.println("xml " + xml.toString());
//		return xml.toString();
//
//	}

}
