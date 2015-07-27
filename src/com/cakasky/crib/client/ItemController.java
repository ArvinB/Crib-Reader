package com.cakasky.crib.client;

import java.io.Serializable;
import java.util.ArrayList;

import com.cakasky.crib.shared.Crib;
import com.cakasky.crib.shared.Item;

/*
 *  Purpose:
 *  
 * Limits what the
 * administrator
 * compared to a user
 * could control.
 * 
 *  Last modified by:
 *  Cameron Bhatnagar - 7.7.15
 *  
 */

public class ItemController implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ArrayList<Item> bookItems;
	private ArrayList<Item> storyItems;
	
	private Item activeItem = null;
	private Crib activeCrib = null;
	
	//Default Constructor
	public ItemController() {
		
		this.bookItems = new ArrayList<Item>();
		this.storyItems = new ArrayList<Item>();
	}

	//Initialize
	public void init(ArrayList<Item> books, ArrayList<Item> stories ) {
		
		setBookItems(books);
		setStoryItems(stories);
	}
	
	public Item addBookItem(String bookName) {
		
		Item book = new Item();
		book.setItemName(bookName);
		book.setBook(true);
		bookItems.add(book);
		
		return book;
	}
	
	public Item addStoryItem(String storyName) {
		
		Item story = new Item();
		story.setItemName(storyName);
		story.setStory(true);		
		storyItems.add(story);
		
		return story;
	}
	
	public Crib addCribToActiveItem(String cribName) {
		
		Crib crib = new Crib();
		crib.setCribName(cribName);
		
		Item item = getActiveItem();
		item.addCrib(crib);
		setActiveCrib(crib);
		
		return crib;
	}
	
	public void saveActiveCribContent(String content) {
		
		Crib crib = getActiveCrib();
		if ( crib != null )
			crib.setCribContext(content);
			
	}
	
	public void removeCribFromActiveItem(int cribIndex) {
				
		Item item = getActiveItem();
		if ( item != null ) {
			setActiveCrib(null);
			item.removeCrib(cribIndex);
		}
	}
	
	public ArrayList<Item> getBookItems() {
		return bookItems;
	}

	public void setBookItems(ArrayList<Item> bookItems) {
		this.bookItems = bookItems;
	}

	public ArrayList<Item> getStoryItems() {
		return storyItems;
	}

	public void setStoryItems(ArrayList<Item> storyItems) {
		this.storyItems = storyItems;
	}

	public Item getActiveItem() {
		return activeItem;
	}

	public void setActiveItem(Item activeItem) {
		this.activeItem = activeItem;
	}

	public Crib getActiveCrib() {
		return activeCrib;
	}

	public void setActiveCrib(Crib activeCrib) {
		this.activeCrib = activeCrib;
	}


}
