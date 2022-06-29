package DataStructures.node;

public class Node<T> {
    public T key;
    public Node<T> next;

    public Node(T data){
        this.key = data;
        this.next = null;
    }
}
