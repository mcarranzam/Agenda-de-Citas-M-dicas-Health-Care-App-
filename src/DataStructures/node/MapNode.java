package DataStructures.node;

public class MapNode<K, V> {
    private K key;
    private V value;
    private MapNode<K,V> next;

    public MapNode(K key, V value) {
        this.key = key;
        this.value = value;
        this.next = null;
    }

    public K getKey() {
        return this.key;
    }

    public V getValue() {
        return this.value;
    }

    public MapNode<K, V> getNext() {
        return this.next;
    }

    public void setNext(MapNode<K, V> next) {
        this.next = next;
    }
}