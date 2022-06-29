package DataStructures.stack;

import DataStructures.list.SinglyLinkedList;
import DataStructures.node.Node;

public class Stack<T> extends SinglyLinkedList {
    public Stack(){
        this.head.key = null;
        this.head.next = null;
        this.tail.key = null;
        this.tail.next = null;
    }

    public void Push(Object key){this.PushFront(key); }

    public Object Top(){
        return this.head.next.key;
    }

    public Object Pop(){
        Node head = this.head.next;
        this.PopFront();
        return head.key;
    }
}
