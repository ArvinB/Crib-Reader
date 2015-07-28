package com.cakasky.crib.client;

import java.util.List;

import com.cakasky.crib.shared.Item;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client-side stub for the RPC service.
 */
@RemoteServiceRelativePath("item")
public interface CribService extends RemoteService {
	
	List<Item>getItems();
	List<Item>getBooks();
	List<Item>getStories();
	void putItem(Item item);
	void updateItem(Item item);

}
