/*
 * HeapSet.java - 
 * Written By: Nicholas DiPasquale 
 */
package com.test.guisort.sorts;

import java.util.*;

/**
 * A class that implements a Heap structure and maintains the objects within it
 * @author Nicholas DiPasquale
 * @param <T>
 */
public class HeapSet<T> extends SortingSet<T> {

	/**
	 * Create a new instance of a HeapSet
	 */
	public HeapSet () {
		super ();
	}
	/**
	 * Create a new instance of a HeapSet with an initial Collection
	 * @param collection The collection to add to the HeapSet
	 */
	public HeapSet (Collection<T> collection) {
		super (collection);
		HeapSet.heapify (this);
	}
	/**
	 * Create a new instance of a HeapSet with the given initial capacity
	 * @param initialCapacity The initial capacity of the new HeapSet
	 */
	public HeapSet (int initialCapacity) {
		super (initialCapacity);
	}
	/*
	 * Private copy constructor
	 */
	private HeapSet (List<T> list, List<ISortEventListener<T> > listeners) {
		super (list, listeners);
	}
	
	/**
	 * Adds the given object to the HeapSet
	 * @param obj The object to add to the HeapSet
	 * @return If the object was successfully added
	 * @see java.util.Set#add (E)
	 */
	public boolean add (T obj) {
		boolean result = _list.add (obj);
		HeapSet.siftUp (this, _list.size () - 1);
		return result;
	}

	/**
	 * Removes the specified object from the HeapSet
	 * @param obj The object to remove from the HeapSet
	 * @return If the object was sucessfully removed
	 * @see java.util.Set#remove (java.lang.Object)
	 */
	public boolean remove (Object obj) {
		boolean result = _list.remove (obj);
		HeapSet.heapify (this);
		return result;
	}

	/**
	 * Adds all of the items in the given Collection to the HeapSet
	 * @param collection The collection of items to add to the HeapSet
	 * @return If the objects were successfully added
	 * @see java.util.Set#addAll (java.util.Collection)
	 */
	public boolean addAll (Collection<? extends T> collection) {
		boolean result = _list.addAll (collection);
		HeapSet.heapify (this);
		return result;
	}

	/**
	 * Retains only the items in the given Collection in the HeapSet
	 * @param collection The items to retain in the HeapSet
	 * @return If the HeapSet was successfully updated
	 * @see java.util.Set#retainAll (java.util.Collection)
	 */
	public boolean retainAll (Collection<?> collection) {
		boolean result = _list.retainAll (collection);
		HeapSet.heapify (this);
		return result;
	}

	/**
	 * Removed all of the items in the Collection from the HeapSet
	 * @param collection The items to remove from the HeapSet
	 * @return If the HeapSet was successfully updated
	 * @see java.util.Set#removeAll (java.util.Collection)
	 */
	public boolean removeAll (Collection<?> collection) {
		boolean result = _list.removeAll (collection);
		HeapSet.heapify (this);
		return result;
	}

	/*
	 * Performs the standard sift down operatin on a HeapSet
	 * @param <T>
	 * @param heap The HeapSet to perform the operation on
	 * @param start The starting index to perform the sift down operation
	 * @param size The size of the HeapSet
	 */
	private static <T> void siftDown (HeapSet<T> heap, int start, int size) {
		int root = start;
		while (root * 2 + 1 < size) {
			int child = root * 2 + 1;
			if ((child < size - 1) && (((Comparable<? super T>) heap._list.get (child)).compareTo (heap._list.get (child + 1)) < 0))
				++child;
			if (((Comparable<? super T>) heap._list.get (root)).compareTo (heap._list.get (child)) < 0) {
				Collections.swap (heap._list, root, child);
				HeapSet.fireEvent (heap, root, child);
				root = child;
			} else {
				return;
			}
		}
	}
	
	/*
	 * Performs the standard sift up operation on a HeapSet
	 * @param <T>
	 * @param heap The HeapSet to perform the operation on
	 * @param start The starting index to perform the sift up operation 
	 */
	private static <T> void siftUp (HeapSet<T> heap, int start) {
		int child = start;
		while (child > 0) {
			int remainder = (child - 1) % 2;
			int root = ((child - 1) - remainder) / 2;
			if (((Comparable<? super T>) heap._list.get (root)).compareTo (heap._list.get (child)) < 0) {
				Collections.swap (heap._list, root, child);
				HeapSet.fireEvent (heap, root, child);
				child = root;
			} else {
				return;
			}
		}
	}
	
	/*
	 * Builds a HeapSet in-place from the given non-heap list
	 * @param <T>
	 * @param heap The list of items to build a heap from
	 */
	private static <T> void heapify (HeapSet<T> heap) {
		for (int i = heap.size () - 1; i >= 0; --i) {
			siftDown (heap, i, heap.size ());
		}
	}
	
	/**
	 * Sorts the contents of a HeapSet into the given array
	 * @param array The array to sort the HeapSet into
	 * @return The sorted items in the HeapSet
	 * @see com.test.guisort.sorts.SortingSet#sort(T[])
	 */
	public T[] sort (T[] array) {
		HeapSet<T> heap = new HeapSet<T> (new ArrayList<T> (_list), _listeners);
		int start = 0;
		for (++start; start <= heap.size () - 2; ++start) {
			siftUp (heap, start);
		}
		for (int end = heap.size () - 1; end > 0; --end) {
			Collections.swap (heap._list, 0, end);
			HeapSet.fireEvent (heap, 0, end);
			siftDown (heap, 0, end);
		}
		return heap.toArray (array);
	}
	
	/**
	 * @see com.test.guisort.sorts.SortingSet#getSortInformation()
	 */
	public String getSortInformation () {
		return "Transformation\nBest Case: O(n log n)\nAverage Case: O(n log n)\nWorst Case: O(n log n)\nMemory Usage: O(1)\nUnstable";
	}
	
	/**
	 * Perform an inplace heap sort on the given array.  This method transforms
	 * the array into a HeapSet then performs the sort operation on the array,
	 * the sorted array is then returned.
	 * @param <T> 
	 * @param array The array to perform the heap sort operation
	 * @return The sorted array
	 */
	public static <T> T[] heapSort (T[] array) {
        HeapSet<T> heap = new HeapSet<T> ();
        heap.addAll (Arrays.asList (array));
        return heap.sort (array);
	}
	/**
	 * Perform an inplace heap sort on the given array.  This method transforms
	 * the array into a HeapSet then performs the sort operation on the array,
	 * the sorted array is then returned.
	 * @param <T> 
	 * @param array The array to perform the heap sort operation
	 * @param listener An ISortEventListener instance to add to the HeapSet to
	 * 				listen to events during sorting
	 * @return The sorted array
	 */
	public static <T> T[] heapSort (T[] array, ISortEventListener<T> listener) {
		HeapSet<T> heap = new HeapSet<T> ();
		if (listener != null) {
			heap.addSortEventListener (listener);
		}
		heap.addAll (Arrays.asList (array));
		return heap.sort (array);
	}
	
}
