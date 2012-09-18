package com.cabletech.linepatrol.quest.beans;

public class Type {
	private String key;
	private String name;
	private int score;
	private int itemCount=0;
	
	public Type(String key,String name){
		this.key=key;
		this.name=name;
	}
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getItemCount() {
		return itemCount;
	}
	
	public void itemCountInc(){
		itemCount++;
	}
}
