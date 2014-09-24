/*
 * QuickSet.java - 
 * Written By: Nicholas DiPasquale 
 */
package com.test.guisort.sorts;

import java.util.*;

/**
 * A SortingSet that implements the well known quick sort algorithm to sort
 * the data containd in the set
 * @author Nicholas DiPasquale
 * @param <T>
 */
public class QuickSet<T> extends SortingSet<T> {

	/* The insertion sort drop-off threshold size */
	private static final int SORT_THRESHOLD = 10;
	/* The pseudo-median of 9 selection algorithm threshold */
	private static final int LARGE_SORT_THRESHOLD = 40;
	
	/**
	 * Create a new instance of an empty QuickSet
	 */
	public QuickSet () {
		super ();
	}
	/**
	 * Create a new instance of a QuickSet that initially contains the items
	 * in the given Collection
	 * @param collection A Collection of items to initally add to the QuickSet
	 */
	public QuickSet (Collection<T> collection) {
		super (collection);
	}
	/**
	 * Create a new instance of a QuickSet that has the given initial capacity
	 * @param initialCapacity The initial capacity of the new QuickSet
	 */
	public QuickSet (int initialCapacity) {
		super (initialCapacity);
	}
	
	/*
	 * Perform the recursive optimized quick sort with drop-off to insertion
	 * sort and median of 3 partitioning and pseudo-median of 9 partitioning
	 * for larger data sets 
	 * @param <T>
	 * @param set The QuickSet of data to sort
	 * @param left The left offset of the data
	 * @param length The length of the data set to sort
	 */
	private static <T> void quicksort (QuickSet<T> set, int left, int length) {
        if (length < SORT_THRESHOLD) {
        	/*
            for (int i = left; i < left + length; ++i) {
                for (int j = i; (j > left) && (((Comparable<? super T>) set._list.get (j - 1)).compareTo (set._list.get (j)) > 0); --j) {
                	Collections.swap (set._list, j, j - 1);
                	fireEvent (set, j, j - 1);
                }
            }
            return;
            */
        }
        
        int middle = left + (length >> 1);
        if (length > SORT_THRESHOLD) {
        	int l = left;
        	int n = left + length - 1;
        	if (length > LARGE_SORT_THRESHOLD) {
        		int s = length / 8;
        		l = medianOf3 (set, l, l + s, l + 2 * s);
        		middle = medianOf3 (set, middle - s, middle, middle + s);
        		n = medianOf3 (set, n - 2 * s, n - s, n);
        	}
        	middle = medianOf3 (set, l, middle, n);
        }
        
        T pivot = set._list.get (middle);
        
        int a = left, b = a, c = left + length - 1, d = c;
        while (true) {
        	while (b <= c && ((Comparable<? super T>) set._list.get (b)).compareTo (pivot) <= 0) {
        		if (((Comparable<? super T>) set._list.get (b)).compareTo (pivot) == 0) {
        			Collections.swap(set._list, a++, b);
        			fireEvent(set, a - 1, b);
        		}
        		++b;
        	}
        	while (c >= b && ((Comparable<? super T>) set._list.get (c)).compareTo (pivot) >= 0) {
        		if (((Comparable<? super T>) set._list.get (c)).compareTo (pivot) == 0) {
        			Collections.swap(set._list, c, d--);
        			fireEvent (set, c, d + 1);
        		}
        		--c;
        	}
        	if (b > c) {
        		break;
        	}
        	Collections.swap (set._list, b++, c--);
        	fireEvent (set, b - 1, c + 1);
        }
        
        int s, n = left + length;
        s = Math.min (a - left, b - a);
        swap (set, left, b - s, s);
        s = Math.min (d - c, n - d - 1);
        swap (set, b, n - s, s);
        
        if ((s = b - a) > 1) {
        	quicksort (set, left, s);
        }
        if ((s = d - c) > 1) {
        	quicksort (set, n - s, s);
        }
	}
	
	/*
	 * Perform a vector swap of the data from positions in the data set for
	 * the given length
	 * @param <T>
	 * @param set The QuickSet to perform the vector swap
	 * @param a The first index into the data set
	 * @param b The second index into the data set
	 * @param length The length of the data swap
	 */
	private static <T> void swap (QuickSet<T> set, int a, int b, int length) {
		for (int i = 0; i < length; ++i, ++a, ++b) {
			Collections.swap(set._list, a, b);
			fireEvent (set, a, b);
		}
	}
	
	/*
	 * Select a partition using median of three values in the given QuickSet
	 * @param <T>
	 * @param set The QuickSet to select a partition
	 * @param a The first potential pivot index
	 * @param b The second potential pivot index
	 * @param c The third potential pivot index
	 * @return The selected pivot index
	 */
	private static <T> int medianOf3 (QuickSet<T> set, int a, int b, int c) {
		T ta = set._list.get (a);
		T tb = set._list.get (b);
		T tc = set._list.get (c);
		
		if (((Comparable<? super T>) ta).compareTo (tb) < 0) {
			if (((Comparable<? super T>) tb).compareTo (tc) < 0) {
				return b;
			} else {
				if (((Comparable<? super T>) ta).compareTo (tc) < 0) {
					return c;
				} else {
					return a;
				}
			}
		} else {
			if (((Comparable<? super T>) tb).compareTo (tc) > 0) {
				return b;
			} else {
				if (((Comparable<? super T>) ta).compareTo (tc) > 0) {
					return c;
				} else {
					return a;
				}
			}
		}
	}
	
	/**
	 * @see com.test.guisort.sorts.SortingSet#sort(T[])
	 */
	public T[] sort (T[] array) {
		quicksort (this, 0, array.length);
		return toArray (array);
	}
	
	/**
	 * @see com.test.guisort.sorts.SortingSet#getSortInformation()
	 */
	public String getSortInformation () {
		return "Divide and conquer\nBest Case: O(n log n)\nAverage Case: O(n log n)\nWorst Case: O(n^2)\nMemory Usage: O(1)\nUnstable";
	}
	
	/**
	 * Perform a quick sort on the given array of data
	 * @param <T>
	 * @param array The array of data to sort
	 * @return The sorted data array
	 */
	public static <T> T[] quickSort (T[] array) {
		return quickSort (array, null);
	}
	/**
	 * Perform a quick sort on the given array of data with the specified
	 * ISortEventListener
	 * @param <T>
	 * @param array The array of data to sort
	 * @param listener The ISortEventListener to use while sorting
	 * @return The sorted data array
	 */
	public static <T> T[] quickSort (T[] array, ISortEventListener<T> listener) {
		QuickSet<T> set = new QuickSet<T> ();
		if (listener != null) {
			set.addSortEventListener (listener);
		}
		set.addAll (Arrays.asList (array));
		return set.sort (array);
	}
	
}
