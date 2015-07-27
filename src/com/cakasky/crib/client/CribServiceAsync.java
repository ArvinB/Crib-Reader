package com.cakasky.crib.client;

import java.util.List;

import com.cakasky.crib.shared.Item;
import com.google.gwt.user.client.rpc.AsyncCallback;



/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface CribServiceAsync {
	
	void getItems(AsyncCallback<List<Item>> callback);
	void getBooks(AsyncCallback<List<Item>> callback);
	void getStories(AsyncCallback<List<Item>> callback);
	void putItem(Item item, AsyncCallback<Void> callback);
}
