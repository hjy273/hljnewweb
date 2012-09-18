package com.cabletech.linepatrol.quest.beans;

import java.util.ArrayList;
import java.util.List;

public class QuestionTable {
	List<Item> itemList = new ArrayList<Item>();//每个被调查对象
	List<String> manufactorList = new ArrayList<String>();//保存参评对象



	public List<Item> getItemList() {
		return itemList;
	}

	public void setItemList(List<Item> itemList) {
		this.itemList = itemList;
	}

	public List<String> getManufactorList() {
		return manufactorList;
	}

	public void setManufactorList(List<String> manufactorList) {
		this.manufactorList = manufactorList;
	}

}
