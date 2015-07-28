package com.cakasky.crib.client;

import java.util.ArrayList;
import java.util.List;

import com.cakasky.crib.shared.Crib;
import com.cakasky.crib.shared.Item;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.SimpleRadioButton;
import com.google.gwt.user.client.ui.TextBox;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Crib_Reader implements EntryPoint {
	
	// Instance Variables
	
	private final CribServiceAsync cribServer = GWT.create(CribService.class);
	
	private ItemController items;
	
	private final TextBox itemName = TextBox.wrap(Document.get().getElementById("itemName"));
	private final TextBox cribName = TextBox.wrap(Document.get().getElementById("cribName"));
	private final TextBox cribContent = TextBox.wrap(Document.get().getElementById("cribContent"));
	private final ListBox cribList = ListBox.wrap(Document.get().getElementById("cribList"));
	private final ListBox bookItems = ListBox.wrap(Document.get().getElementById("bookItems"));
	private final ListBox storyItems = ListBox.wrap(Document.get().getElementById("storyItems"));
	private final SimpleRadioButton bookItem = SimpleRadioButton.wrap(Document.get().getElementById("bookItem"));
	private final SimpleRadioButton storyItem = SimpleRadioButton.wrap(Document.get().getElementById("storyItem"));
	private final Button addItem = Button.wrap(Document.get().getElementById("addItem"));
	private final Button bookLoad = Button.wrap(Document.get().getElementById("bookLoad"));
	private final Button storyLoad = Button.wrap(Document.get().getElementById("storyLoad"));
	private final Button cribAdd = Button.wrap(Document.get().getElementById("cribAdd"));
	private final Button cribRemove = Button.wrap(Document.get().getElementById("cribRemove"));
	private final Button contentSave = Button.wrap(Document.get().getElementById("contentSave"));
	private final Label itemLabel = Label.wrap(Document.get().getElementById("itemLabel"));

	//private static Logger log = Logger.getLogger("CribReaderLogger");
	
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {

		items = new ItemController();
		
		cribServer.getBooks( new AsyncCallback<List<Item>>() {
			
			// Get all books from database
			public void onFailure(Throwable caught) {}					
			public void onSuccess(List<Item> allBooks) {
			
				items.setBookItems(new ArrayList<Item>(allBooks));
				
				for (Item anItem : allBooks)
					bookItems.addItem(anItem.getItemName());		
			}
		});
		
		cribServer.getStories( new AsyncCallback<List<Item>>() {
			
			// Get all stories from database
			public void onFailure(Throwable caught) {}					
			public void onSuccess(List<Item> allStories) {
			
				items.setStoryItems(new ArrayList<Item>(allStories));
				
				for (Item anItem : allStories)
					storyItems.addItem(anItem.getItemName());
			}
		});

		// Clear Text Boxes upon onLoad
		itemName.setText("");
		cribName.setText("");
		cribContent.setText("");
		
		// Set the default Radio Button to a Book Item
		bookItem.setValue(true);
		
		// Only allow one selected Crib
		cribList.setMultipleSelect(false);
		
		// Add a Book or Story
		class ItemHandler implements ClickHandler {
			
			@Override
			public void onClick(ClickEvent event) {
				
				String name = itemName.getText().trim();
				if (name != null) {
					
					if (bookItem.getValue()) {	
						
						final Item book = items.addBookItem(name);
						
						cribServer.putItem(book, new AsyncCallback<Void>() {

							public void onFailure(Throwable caught) {}
							public void onSuccess(Void result) {
								
								bookItems.addItem(book.getItemName());
								bookItems.setSelectedIndex(bookItems.getItemCount()-1);
								itemName.setText("");
							}
							
						});
						
					} else if (storyItem.getValue()) { 
						
						final Item story = items.addStoryItem(name);
						
						cribServer.putItem(story, new AsyncCallback<Void>() {

							public void onFailure(Throwable caught) {}
							public void onSuccess(Void result) {
								
								storyItems.addItem(story.getItemName());
								storyItems.setSelectedIndex(storyItems.getItemCount()-1);
								itemName.setText("");
							} 
							
						});
					}
				}
			}
			
		} // ItemHandler Class
		
		
		// Load a Book or Story for Editing
		class LoadHandler implements ClickHandler {

			@Override
			public void onClick(ClickEvent event) {
				
				final Button loadButton = (Button) event.getSource();
				
				int selectedIndex;
				String selectedItemName;
				
				Item activeItem = items.getActiveItem();
				Crib activeCrib = items.getActiveCrib();
				
				if (activeItem != null) {
					
					// TODO Update Existing Item
					// Save Active Crib before Loading
					if ( activeCrib != null ) {
						activeCrib.setCribContext(cribContent.getText());	
					}
					
					cribServer.putItem(activeItem, new AsyncCallback<Void>() {
						
						public void onFailure(Throwable caught) {}
						public void onSuccess(Void result) {}
						
					});
				}
				
				if ("bookLoad" == loadButton.getElement().getId()) {
					
					//Load selected Book and its Cribs
					
					selectedIndex = bookItems.getSelectedIndex();
					selectedItemName = bookItems.getItemText(selectedIndex);
					
					itemLabel.setText("Book: " + selectedItemName);
					
					Item book = items.getBookItems().get(selectedIndex);
					items.setActiveItem(book);
					ArrayList<Crib> cribs = book.getCribList();
					
					cribList.clear();
					cribContent.setText("");
					items.setActiveCrib(null);
					
					for (Crib crib : cribs)	
						cribList.addItem( crib.getCribName() );
					
				} else if ("storyLoad" == loadButton.getElement().getId()) {
					
					//Load selected story and it's crib
					
					selectedIndex = storyItems.getSelectedIndex();
					selectedItemName = storyItems.getItemText(selectedIndex);
					
					itemLabel.setText("Story: " + selectedItemName);
					
					Item story = items.getStoryItems().get(selectedIndex);
					items.setActiveItem(story);
					List<Crib> cribs = story.getCribList();
					
					cribList.clear();
					cribContent.setText("");
					items.setActiveCrib(null);
					
					for (Crib crib : cribs)
						cribList.addItem( crib.getCribName() );
				}
			}
		} // LoadHandler Class
		
		// Adding and Removing Cribs
		class CribHandler implements ClickHandler {

			@Override
			public void onClick(ClickEvent event) {
				
				Item activeItem = items.getActiveItem();
				Crib activeCrib = items.getActiveCrib();
				
				if (activeItem == null) {
					
					// No active item to add a crib into
					cribName.setText("");
					return;
				}
				
				final Button cribButton = (Button) event.getSource();
				
				if ("cribAdd" == cribButton.getElement().getId()) {
					
					final String aCribName = cribName.getText().trim();
					
					// Save Active Crib before adding one
					if ( activeCrib != null ) {
						activeCrib.setCribContext(cribContent.getText());	
					}
					
					// Crib Add button pressed
					if ( !(aCribName.equals(null)) && !(aCribName.equals("")) ) {
					
						//item.cribList.get(cribIndex).setCribContext(content);
						
						items.addCribToActiveItem(aCribName, activeItem);
						
						cribServer.putItem(activeItem, new AsyncCallback<Void>() {
							
							public void onFailure(Throwable caught) { }
							public void onSuccess(Void result) {
								
								cribName.setText("");
								cribContent.setText("");
								cribList.addItem(aCribName);
								cribList.setSelectedIndex(cribList.getItemCount()-1);
								cribList.setFocus(true);
								
							}
						});
					}
					
				} else if ("cribRemove" == cribButton.getElement().getId()) {
					
					// Crib Remove button pressed
					if ( activeCrib != null ) {
					
						activeItem.cribList.remove(activeCrib);
						
						cribServer.putItem(activeItem, new AsyncCallback<Void>() {
						
								public void onFailure(Throwable caught) { }
								public void onSuccess(Void result) {
								int cribIndex = cribList.getSelectedIndex();
								cribList.removeItem(cribIndex);
								cribContent.setText("");
								items.setActiveCrib(null);
							}
						});
						
					}
				}
				
				// TODO Update Existing Item
				
				cribServer.putItem(activeItem, new AsyncCallback<Void>() {
					
					public void onFailure(Throwable caught) {}
					public void onSuccess(Void result) {}
					
				});
				
			}	
		} // CribHandler
		
		// Changing Crib Content
		class ContentHandler implements ClickHandler {

			@Override
			public void onClick(ClickEvent event) {
				
				// Save Crib Content
				Item activeItem = items.getActiveItem();
				Crib activeCrib = items.getActiveCrib();
				
				if (activeItem != null) {
					
					// Save Active Crib before changing it
					if ( activeCrib != null ) {
						activeCrib.setCribContext(cribContent.getText());	
					}
					
					// TODO Update Existing Item
					cribServer.putItem(activeItem, new AsyncCallback<Void>() {
						
						public void onFailure(Throwable caught) {}
						public void onSuccess(Void result) {}
						
					});
				}
			}	
		} // ContentHandler
		
		// Click Handlers
		ItemHandler itemHandler = new ItemHandler();
		addItem.addClickHandler(itemHandler);
		
		LoadHandler loadHandler = new LoadHandler();
		bookLoad.addClickHandler(loadHandler);
		storyLoad.addClickHandler(loadHandler);
		
		CribHandler cribHandler = new CribHandler();
		cribAdd.addClickHandler(cribHandler);
		cribRemove.addClickHandler(cribHandler);
		
		// Change Handler for When a Crib is Selected
		cribList.addChangeHandler(new ChangeHandler() {
			
			@Override
			public void onChange(ChangeEvent event) {
				
				// User has change selected index
				Item activeItem = items.getActiveItem();
				Crib activeCrib = items.getActiveCrib();
				
				if (activeItem != null) {
					
					// Save Active Crib before Changing It
					if ( activeCrib != null ) {
						activeCrib.setCribContext(cribContent.getText());	
					}
					
					int cribIndex = cribList.getSelectedIndex();
					final Crib selectedCrib = activeItem.getCribList().get(cribIndex);
					
					// TODO Update Existing Item
					cribServer.putItem(activeItem, new AsyncCallback<Void>() {
						
						public void onFailure(Throwable caught) {}
						public void onSuccess(Void result) {
							
							items.setActiveCrib(selectedCrib);
							cribContent.setText(selectedCrib.getCribContext());
						}
						
					});
				}
			}
		});
		
		ContentHandler contentHandler = new ContentHandler();
		contentSave.addClickHandler(contentHandler);
	}
}



