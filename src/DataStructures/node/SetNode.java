package DataStructures.node;

public class SetNode<K, V> {
    private K key;
    private V value;
    private SetNode<K,V> next;

    public SetNode(K key, V value) {
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

    public SetNode<K, V> getNext() {
        return this.next;
    }

    public void setNext(SetNode<K, V> next) {
        this.next = next;
    }
}