/*
 * ISortEventListener.java - 
 * Written By: Nicholas DiPasquale 
 */
package com.test.guisort.sorts;

import java.util.*;

/**
 * A generic EventListener interface for swap events performed by a sorting
 * routine
 * @author Nicholas DiPasquale
 * @param <T>
 */
public interface ISortEventListener<T> extends EventListener {

	/**
	 * Called when a swap event is performed
	 * @param event The event data at the time of the swap
	 */
	public void swapPerformed (SortEvent<T> event);
	
}
