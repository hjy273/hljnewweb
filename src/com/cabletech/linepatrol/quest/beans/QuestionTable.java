package com.cabletech.linepatrol.quest.beans;

import java.util.ArrayList;
import java.util.List;

public class QuestionTable {
	List<Item> itemList = new ArrayList<Item>();//ÿ�����������
	List<String> manufactorList = new ArrayList<String>();//�����������



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
