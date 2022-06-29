package DataStructures.node;

public class AvlNode<T> {
    public T element;
    public AvlNode<T> left;
    public AvlNode<T> right;
    public int height;
    public boolean visited;

    public AvlNode(T x){
        element = x;
        left = null;
        right = null;
        visited = false;
    }
    public AvlNode(T x, AvlNode<T> l, AvlNode<T> r, int h) {
        element = x;
        left = l;
        right= r;
        height = h;
    }
}
