package DataStructures.hashSet;
import DataStructures.node.SetNode;
import java.util.NoSuchElementException;

public class HashSet<K,V> {
    private int size;
    private SetNode<K,V>[] bucket;

    public HashSet(int capacity) {
        this.bucket = new SetNode[nextPrime(capacity)];
        this.size = 0;
    }

    public void put(K key, V value) {
        int hashKey = getHashCode(key);
        SetNode<K, V> cur = new SetNode<K, V>(key, value);

        if(bucket[hashKey] == null) {
            bucket[hashKey] = cur;
        } else {
            cur.setNext(bucket[hashKey]);
            bucket[hashKey] = cur;
        }
        size++;
        return;
    }

    public V get(K key) {
        int hashKey = getHashCode(key);
        if(bucket[hashKey] == null) {
            throw new NoSuchElementException();
        }

        SetNode<K, V> start = bucket[hashKey];
        SetNode<K, V> end = start;

        if(start.getKey().equals(key)) return start.getValue();

        while(end.getNext() != null && !end.getNext().getKey().equals(key)) {
            end = end.getNext();
        }

        if(end.getNext() != null && end.getNext().getKey().equals(key)) {
            return end.getNext().getValue();
        }
        throw new NoSuchElementException();
    }

    public int nextPrime(int num) {
        if(num % 2 == 0) return num + 1;

        for(; !isPrime(num); num +=2);

        return num;
    }

    public boolean isPrime(int num) {
        if(num == 2 || num == 3) return true;
        if(num == 1 || num % 2 == 0) return false;

        for(int i = 3; i * i <= num; ++i) {
            if(num % i == 0) return false;
        }
        return true;
    }

    public int getHashCode(K key) {
        int hashValue = key.hashCode();
        hashValue %= bucket.length;

        if(hashValue < 0) {
            hashValue += bucket.length;
        }
        return hashValue;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void clear() {
        int length = bucket.length;
        bucket = new SetNode[length];
        this.size = 0;
    }

    public int size() {
        return this.size;
    }

    public void print() {
        SetNode<K, V> cur = null;
        SetNode<K, V> next = null;
        for(int i = 0; i < bucket.length; ++i) {
            cur = bucket[i];
            next = cur;
            System.out.println("Bucket " + i);

            while(next != null) {
                System.out.println(next.getKey() + ", " + next.getValue());
                next = next.getNext();
            }
        }
    }
}