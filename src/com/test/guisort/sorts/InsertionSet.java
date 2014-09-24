/*
 * InsertionSet.java - 
 * Written By: Nicholas DiPasquale 
 */
package com.test.guisort.sorts;

import java.util.*;

/**
 * A SortingSet that implements the well known insertion sort algorithm to sort
 * the data contained in the set
 * @author Nicholas DiPasquale
 * @param <T>
 */
public class InsertionSet<T> extends SortingSet<T> {

	/**
	 * Create a new instance of an empty InsertionSet
	 */
	public InsertionSet () {
		super ();
	}
	/**
	 * Create a new instance of an InsertionSet that initially contains the
	 * items in the given Collection
	 * @param collection A Colllection of items to initially add to the
	 * InsertionSet
	 */
	public InsertionSet (Collection<T> collection) {
		super (collection);
	}
	/**
	 * Create a new instance of an InsertionSet that has the given initial
	 * capacity
	 * @param initialCapacity The initial capacity of the InsertionSet
	 */
	public InsertionSet (int initialCapacity) {
		super (initialCapacity);
	}
	
	/*
	 * Perform an insertion sort on the given data set
	 * @param <T>
	 * @param set The InsertionSet to perform the sort
	 * @param left The left offset of the data
	 * @param right The right offset of the data
	 */
	private static <T> void insertionsort (InsertionSet<T> set, int left, int right) {
        for (int i = left; i < right; ++i) {
            for (int j = i; (j > left) && (((Comparable<? super T>) set._list.get (j - 1)).compareTo (set._list.get (j)) > 0); --j) {
            	Collections.swap (set._list, j, j - 1);
            	fireEvent (set, j - 1, j);
            }
        }
	}
	
	/**
	 * @see com.test.guisort.sorts.SortingSet#sort(T[])
	 */
	public T[] sort (T[] array) {
        insertionsort (this, 0, array.length);
		return toArray (array);
	}
	
	/**
	 * @see com.test.guisort.sorts.SortingSet#getSortInformation()
	 */
	public String getSortInformation () {
		return "Brute Force\nBest Case: O(n)\nAverage Case: O(n + d)\nWorst Case: O(n^2)\nMemory Usage: O(1)\nStable\nwhere d is O(n^2)";
	}
	
	/**
	 * Perform an insertion sort on the given array
	 * @param <T>
	 * @param array The array of data to sort
	 * @return The sorted data array
	 */
	public static <T> T[] insertionSort (T[] array) {
		return insertionSort (array, null);
	}
	/**
	 * Perform an insertion sort on the given array with the given
	 * ISortEventListener
	 * @param <T>
	 * @param array The array of data to sort
	 * @param listener The ISortEventListener to use while sorting
	 * @return The sorted data array
	 */
	public static <T> T[] insertionSort (T[] array, ISortEventListener<T> listener) {
		InsertionSet<T> set = new InsertionSet<T> ();
		if (listener != null) {
			set.addSortEventListener (listener);
		}
		set.addAll (Arrays.asList (array));
		return set.sort (array);
	}
	
	
}
