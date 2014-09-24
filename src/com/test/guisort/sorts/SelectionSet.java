/*
 * SelectionSet.java - 
 * Written By: Nicholas DiPasquale 
 */
package com.test.guisort.sorts;

import java.util.*;

/**
 * A SortingSet that implements the well known selection sort algorithm to sort
 * the data contaied in the set
 * @author Nicholas DiPasquale
 * @param <T>
 */
public class SelectionSet<T> extends SortingSet<T> {

	/**
	 * Create a new instance of an empty SelectionSet
	 */
	public SelectionSet () {
		super ();
	}
	/**
	 * Create a new instance of a SelectionSet that initially contains the
	 * items in the given Collection
	 * @param collection The Collection of items to initiall add to the new
	 * SelectionSet
	 */
	public SelectionSet (Collection<T> collection) {
		super (collection);
	}
	/**
	 * Create a new instance of a SelectionSet with the given initial capacity
	 * @param initialCapacity The initial capacity of the new SelectionSet
	 */
	public SelectionSet (int initialCapacity) {
		super (initialCapacity);
	}
	
	/*
	 * Perform selection sort on the given SelectionSet
	 * @param <T>
	 * @param set The SelectionSet to perform the sort
	 * @param left The left offset of the data
	 * @param right The right offset of the data
	 */
	private static <T> void selectionsort (SelectionSet<T> set, int left, int right) {
		int min;
		for (int i = left; i < right - 1; ++i) {
			min = i;
			for (int j = i + 1; j < right; ++j) {
				if (((Comparable<? super T>) set._list.get (j)).compareTo (set._list.get (min)) <  0) {
					min = j;
				}
			}
			Collections.swap (set._list, i, min);
			fireEvent (set, i, min);
		}
	}
	
	/**
	 * @see com.test.guisort.sorts.SortingSet#sort(T[])
	 */
	public T[] sort (T[] array) {
        selectionsort (this, 0, array.length);
		return toArray (array);
	}
	
	/**
	 * @see com.test.guisort.sorts.SortingSet#getSortInformation()
	 */
	public String getSortInformation () {
		return "Brute Force\nBest Case: O(n^2)\nAverage Case: O(n^2)\nWorst Case: O(n^2)\nMemory Usage: O(1)\nUnstable";
	}
	
	/**
	 * Perform selection sort on the given data array
	 * @param <T>
	 * @param array The array of data to sort
	 * @return The sorted data array
	 */
	public static <T> T[] selectionSort (T[] array) {
		return selectionSort (array, null);
	}
	/**
	 * Perform selection sort on the given data array with the specified
	 * ISortEventListener
	 * @param <T>
	 * @param array The array of data to sort
	 * @param listener The ISortEventListener to use while sorting
	 * @return The sorted data array
	 */
	public static <T> T[] selectionSort (T[] array, ISortEventListener<T> listener) {
		SelectionSet<T> set = new SelectionSet<T> ();
		if (listener != null) {
			set.addSortEventListener (listener);
		}
		set.addAll (Arrays.asList (array));
		return set.sort (array);
	}
	
}
