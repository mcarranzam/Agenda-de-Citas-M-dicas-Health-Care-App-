package DataStructures.heap;
import java.util.Arrays;

class Heap<T extends Comparable<T>> {
	private static final int DEFAULT_CAPACITY = 10;
	private T[] heap;
	private int length;
	private boolean min;

	public Heap() {
		heap = (T[]) new Comparable[DEFAULT_CAPACITY];
		length = 0;
		min = true;
	}

	public Heap(T[] array, boolean min)	{
		heap = (T[]) new Comparable[DEFAULT_CAPACITY];
		length = 0;
		this.min = min;

		for (T each : array) {
			add(each);
		}
	}

	public Heap(boolean min)	{
		heap = (T[]) new Comparable[DEFAULT_CAPACITY];
		length = 0;
		this.min = min;
	}

	public T[] getHeap () {
		return Arrays.copyOfRange(heap, 1, length + 1);
	}

	public void add(T value) {
		if (this.length >= heap.length - 1)	{
			heap = this.resize();
		}

		length++;
		heap[length] = value;
		siftUp();
	}

	public T remove() {
		T result = peek();
		swap(1, length);
		heap[length] = null;
		length--;
		siftDown();
		return result;
	}

	public boolean remove(T value) {
		for (int i = 0; i < heap.length; i++) {
			if (value.equals(heap[i])) {
				System.out.println(i);
				swap(i, length);
				heap[length] = null;
				length--;
				siftDown();
				return true;
			}
		}
		return false;
	}


	public T poll()	{
		if (isEmpty()) return null;

		T result = peek();

		swap(1, length);
		heap[length] = null;
		length--;

		siftDown();

		return result;
	}


	public boolean isEmpty() {
		return length == 0;
	}

	public T peek()	{
		if (isEmpty()) throw new IllegalStateException();
		return heap[1];
	}


	public int length()	{
		return length;
	}

	private T[] resize() {
		return Arrays.copyOf(heap, heap.length + DEFAULT_CAPACITY);
	}

	private void siftUp()	{
		int index = length;
		if (min) {
			while (hasParent(index) && (parent(index).compareTo(heap[index]) > 0)) {
				swap(index, parentIndex(index));
				index = parentIndex(index);
			}	
		}
		else {
			while (hasParent(index) && (parent(index).compareTo(heap[index]) < 0)) {
				swap(index, parentIndex(index));
				index = parentIndex(index);
			}	
		}
	}

	private void siftDown() {
		int index = 1;
		if (min) {

			while (hasLeftChild(index)) {
				int smaller = leftIndex(index);
				if (hasRightChild(index) && heap[leftIndex(index)].compareTo(heap[rightIndex(index)]) > 0) {
					smaller = rightIndex(index);
				}
				if (heap[index].compareTo(heap[smaller]) > 0) {
					swap(index, smaller);
				}
				else break;
				index = smaller;
			}				
		}
		else {
			while (hasLeftChild(index)) {
				int larger = leftIndex(index);
				if (hasRightChild(index) && heap[leftIndex(index)].compareTo(heap[rightIndex(index)]) < 0) {
					larger = rightIndex(index);
				}
				if (heap[index].compareTo(heap[larger]) < 0) {
					swap(index, larger);
				}
				else break;
				index = larger;
			}				
		}
	}

	private boolean hasParent(int i) {
		return i > 1;
	}

	private int leftIndex(int i) {
		return i * 2;
	}

	private int rightIndex(int i) {
		return i * 2 + 1;
	}

	private boolean hasLeftChild(int i) {
		return leftIndex(i) <= length;
	}

	private boolean hasRightChild(int i) {
		return rightIndex(i) <= length;
	}

	private int parentIndex(int i) {
		return i / 2;
	}

	private T parent(int i) {
		return heap[parentIndex(i)];
	}

	private void swap(int index1, int index2) {
		T temp = heap[index1];
		heap[index1] = heap[index2];
		heap[index2] = temp;
	}

	@Override
	public String toString() {
		String retval = "";
		for (T each : heap) {
			if (each != null) retval += each + " : ";
		}
		return retval + "\n";
	}
}