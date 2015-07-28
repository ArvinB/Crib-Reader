package com.cakasky.crib.server;

import java.util.ArrayList;
import java.util.List;

import com.cakasky.crib.client.CribService;
import com.cakasky.crib.shared.Crib;
import com.cakasky.crib.shared.Item;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;

public class CribServer extends RemoteServiceServlet implements CribService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	static {
		
		factory().register(Item.class);
		factory().register(Crib.class);
	}
	
	public static Objectify ofy() {
		
		return ObjectifyService.ofy();
	}
	
	public static ObjectifyFactory factory() {
		
		return ObjectifyService.factory();
	}
	
	@Override
	public List<Item> getItems() {
		
		return new ArrayList<Item>(ofy().load().type(Item.class).list());
	}

	@Override
	public List<Item> getBooks() {
		
		return new ArrayList<Item>(ofy().load().type(Item.class).filter("isBook ==", true).list());
	}

	@Override
	public List<Item> getStories() {
		
		return new ArrayList<Item>(ofy().load().type(Item.class).filter("isStory ==", true).list());
		
	}
	
	@Override
	public void putItem(Item item) {
		
		ofy().save().entity(item).now();
	}

	@Override
	public void updateItem(Item item) {
		// TODO Auto-generated method stub
		
	}
}
