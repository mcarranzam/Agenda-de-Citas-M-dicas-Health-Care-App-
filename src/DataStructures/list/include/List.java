package DataStructures.list.include;

import DataStructures.node.Node;

public abstract class List<T>{

    public Node<T> head, tail;

    public List() {
        this.head = new Node<T>(null);
        this.tail = new Node<T>(null);
    }

    public abstract void PushFront(T key);
    public abstract T TopFront();
    public abstract void PopFront();
    public abstract void PushBack(T key);
    public abstract T TopBack();
    public abstract void PopBack();
    public abstract boolean Find(T key);
    public abstract void Erase(T key);
    public abstract boolean Empty();
    public abstract void AddBefore(Node<T> n, T key);
    public abstract void AddAfter(Node<T> n, T key);
}
