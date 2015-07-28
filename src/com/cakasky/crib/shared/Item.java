package com.cakasky.crib.shared;

import java.io.Serializable;
import java.util.ArrayList;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Serialize;
import com.googlecode.objectify.annotation.Unindex;
import com.googlecode.objectify.condition.IfTrue;

//import com.googlecode.objectify.annotation.*;
//import com.googlecode.objectify.condition.IfTrue;


/*
 *  Purpose:
 *  The purpose of the Item is to
 *  house either a book or story.
 *  This class will also contain a
 *  list of crib notes for each item.
 * 
 *  Last modified by:
 *  Cameron Bhatnagar - 6.25.2015
 *  
 */

@Entity
public class Item implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id 
	public Long id;

	@Index(IfTrue.class) 
	private boolean isBook;
	@Index(IfTrue.class) 
	private boolean isStory;
	
	@Unindex private String itemName;
	
	@Serialize
	public ArrayList<Crib> cribList = new ArrayList<Crib>();
	
	// Default Constructor
	public Item() {
		
	}
	
	public Long getId() {
		return id;
	}
		 
	public void setId(Long id) {
		this.id = id;
	}
	
	public boolean isBook() {
		return isBook;
	}

	public void setBook(boolean isBook) {
		this.isBook = isBook;
	}
	
	public void addCrib(Crib crib) {
		
		getCribList().add(crib);
	}
	
	public void removeCrib(int index) {
		
		ArrayList<Crib> cribs = getCribList();
		cribs.remove(index);
		setCribList(cribs);
	}
	
	public ArrayList<Crib> getCribList() {
		return this.cribList;
	}

	private void setCribList(ArrayList<Crib> cribList) {
		this.cribList = cribList;
	}

	public boolean isStory() {
		return isStory;
	}

	public void setStory(boolean isStory) {
		this.isStory = isStory;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
}
