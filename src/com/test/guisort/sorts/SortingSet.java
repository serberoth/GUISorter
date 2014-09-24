/*
 * SortingSet.java - 
 * Written By: Nicholas DiPasquale 
 */
package com.test.guisort.sorts;

import java.util.*;

/**
 * Generic abstract class that implements a sortable Set.  Child classes
 * generally implement a single sorting algorithm that the data contained
 * in the set is sorted on when the sort method is invoked all sorting is
 * generally in place
 * @author Nicholas DiPasquale
 * @param <T>
 */
public abstract class SortingSet<T> implements Set<T> {

	/* The sortable data list */
	protected List<T> _list;
	/* ISortEventListener list */
	protected List<ISortEventListener<T> > _listeners;
	
	/**
	 * Create a new instance of a SortingSet
	 */
	protected SortingSet () {
		_list = new ArrayList<T> ();
		_listeners = new LinkedList<ISortEventListener<T> > ();
	}
	/**
	 * Create a new instance of a SortingSet with an initial Collection
	 * @param collection The collection to add to the SortingSet
	 */
	protected SortingSet (Collection<T> collection) {
		_list = new ArrayList<T> (collection);
		_listeners = new LinkedList<ISortEventListener<T> > ();
	}
	/**
	 * Create a new instance of a SortingSet with the given initial capacity
	 * @param initialCapacity The initial capacity of the new SortingSet
	 */
	protected SortingSet (int initialCapacity) {
		_list = new ArrayList<T> (initialCapacity);
		_listeners = new LinkedList<ISortEventListener<T> > ();
	}
	/**
	 * Internal copy constructor
	 */
	protected SortingSet (List<T> list, List<ISortEventListener<T> > listeners) {
		_list = list;
		_listeners = listeners;
	}
	
	/**
	 * Adds an instance of ISortEventListener to the SortingSet when a swap
	 * is performed on the data set the ISortEventListener#swapPerformed
	 * is invoked with the SortingSet as the source.
	 * @param listener The listener to add to the SortingSet
	 */
	public void addSortEventListener (ISortEventListener<T> listener) {
		_listeners.add (listener);
	}
	
	/**
	 * Removes an instance of ISortEventListener from the SortingSet
	 * @param listener The listener to remove from the SortingSet
	 */
	public void removeSortEventListener (ISortEventListener<T> listener) {
		_listeners.remove (listener);
	}
	
	/**
	 * Get the size of the SortingSet
	 * @return The size of the SortingSet
	 * @see java.util.Set#size ()
	 */
	public int size () {
		return _list.size ();
	}

	/**
	 * Determines if the SortingSet instance is empty
	 * @return If the SortingSet is empty or not
	 * @see java.util.Set#isEmpty ()
	 */
	public boolean isEmpty () {
		return _list.isEmpty ();
	}

	/**
	 * Determines if the SortingSet contains the specified Object
	 * @param obj The object to find in the SortingSet
	 * @return If the SortingSet contains the specified object
	 * @see java.util.Set#contains (java.lang.Object)
	 */
	public boolean contains (Object obj) {
		return _list.contains (obj);
	}

	/**
	 * Returns an iterator over the contents of the SortingSet
	 * @return An instance of an iterator for the SortingSet
	 * @see java.util.Set#iterator ()
	 */
	public Iterator<T> iterator () {
		return _list.iterator ();
	}

	/**
	 * Returns the SortingSet in array form
	 * @return The SortingSet in array form
	 * @see java.util.Set#toArray ()
	 */
	public Object[] toArray () {
		return _list.toArray ();
	}

	/**
	 * Returns the SortingSet in array form
	 * @param <T2>
	 * @return The SortingSet in array form
	 * @see java.util.Set#toArray (T[])
	 */
	public <T2> T2[] toArray (T2[] array) {
		return _list.toArray (array);
	}

	/**
	 * Adds the given object to the SortingSet
	 * @param obj The object to add to the SortingSet
	 * @return If the object was successfully added
	 * @see java.util.Set#add (E)
	 */
	public boolean add (T obj) {
		boolean result = _list.add (obj);
		return result;
	}

	/**
	 * Removes the specified object from the SortingSet
	 * @param obj The object to remove from the SortingSet
	 * @return If the object was sucessfully removed
	 * @see java.util.Set#remove (java.lang.Object)
	 */
	public boolean remove (Object obj) {
		boolean result = _list.remove (obj);
		// HeapSet.heapify (this);
		return result;
	}

	/**
	 * Determines if the SortingSet contains all of the items in the Collection
	 * @param collection The collection of items to check for
	 * @see java.util.Set#containsAll (java.util.Collection)
	 */
	public boolean containsAll (Collection<?> collection) {
		return _list.containsAll (collection);
	}

	/**
	 * Adds all of the items in the given Collection to the SortingSet
	 * @param collection The collection of items to add to the SortingSet
	 * @return If the objects were successfully added
	 * @see java.util.Set#addAll (java.util.Collection)
	 */
	public boolean addAll (Collection<? extends T> collection) {
		boolean result = _list.addAll (collection);
		// HeapSet.heapify (this);
		return result;
	}

	/**
	 * Retains only the items in the given Collection in the SortingSet
	 * @param collection The items to retain in the SortingSet
	 * @return If the SortingSet was successfully updated
	 * @see java.util.Set#retainAll (java.util.Collection)
	 */
	public boolean retainAll (Collection<?> collection) {
		boolean result = _list.retainAll (collection);
		// HeapSet.heapify (this);
		return result;
	}

	/**
	 * Removed all of the items in the Collection from the SortingSet
	 * @param collection The items to remove from the SortingSet
	 * @return If the SortingSet was successfully updated
	 * @see java.util.Set#removeAll (java.util.Collection)
	 */
	public boolean removeAll (Collection<?> collection) {
		boolean result = _list.removeAll (collection);
		// HeapSet.heapify (this);
		return result;
	}

	/**
	 * Removes all items from the SortingSet
	 * @see java.util.Set#clear ()
	 */
	public void clear () {
		_list.clear ();
	}
	
	/**
	 * Fires a SortEvent to all of the registered ISortEventListeners on the
	 * SortingSet.  The SortEvent holds the Set and the indices of the items
	 * swapped in the operation.
	 * @param <T>
	 * @param set The set to use to fire the event
	 * @param index0 The first index in the swap
	 * @param index1 The second index in the swap
	 */
	protected static <T> void fireEvent (SortingSet<T> set, int index0, int index1) {
		SortEvent<T> event = new SortEvent<T> (set, index0, index1);
		Iterator<ISortEventListener<T> > iterator = set._listeners.iterator ();
		while (iterator.hasNext ()) {
			ISortEventListener<T> listener = (ISortEventListener<T>) iterator.next ();
			listener.swapPerformed (event);
		}
	}

	/**
	 * Sorts the contents of a SortingSet into the given array
	 * @param array The array to sort the SortingSet into
	 * @return The sorted items in the SortingSet
	 */
	public abstract T[] sort (T[] array);
	
	/**
	 * Get information about the sorting algorithm implemented by this
	 * SortingSet
	 * @return The information about this sorting algorithm implemented by
	 * this SortingSet instance
	 */
	public abstract String getSortInformation ();
	
}
