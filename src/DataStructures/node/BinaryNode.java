package DataStructures.node;

public class BinaryNode<T> {
    public T element;
    public BinaryNode<T> left;
    public BinaryNode<T> right;
    public BinaryNode(){
        element = null;
        left = null;
        right = null;
    }
    public BinaryNode(T x, BinaryNode<T> l, BinaryNode<T> r){
        element = x;
        left = l;
        right= r;
    }
}
