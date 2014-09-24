/*
 * SortEvent.java - 
 * Written By: Nicholas DiPasquale 
 */
package com.test.guisort.sorts;

import java.util.*;

/**
 * Event data for the ISortEventListener, this object contains the Set that the
 * event was called on and the indices of the swap 
 * @author Nicholas DiPasquale
 * @param <T>
 */
public class SortEvent<T> extends EventObject {
	private static final long serialVersionUID = 1L;
	
	/* The Set that holds the SortEvent data */
	private Set<T> _set;
	/* The first swapped index */
	private int _index0;
	/* The second swapped index */
	private int _index1;
	
	/**
	 * Creates a new SortEvent instance
	 * @param source The Set that holds the SortEvent data
	 * @param index0 The first swapped index
	 * @param index1 The second swapped index
	 */
	public SortEvent (Set<T> source, int index0, int index1) {
		super (source);
		_set = source;
		_index0 = index0;
		_index1 = index1;
	}
	
	/**
	 * Returns the Set that contains the SortEvent data
	 * @return An instance of the Set interface
	 */
	public Set<T> getSet () {
		return _set;
	}
	
	/**
	 * Returns the SortEvent Set as an array
	 * @param array An array to store the Set
	 * @return The Set contained in this event as an array
	 */
	public T[] getArray (T[] array) {
		return (T[]) _set.toArray (array);
	}
	
	/**
	 * Returns the first swapped index in the set
	 * @return The first swapped index
	 */
	public int getIndex0 () {
		return _index0;
	}
	
	/**
	 * Returns the second swapped index in the set
	 * @return The second swapped index
	 */
	public int getIndex1 () {
		return _index1;
	}
	
}
