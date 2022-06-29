package DataStructures.queue;

import DataStructures.list.SinglyLinkedList;
import DataStructures.node.Node;

public class Queue<T> extends SinglyLinkedList {
    public Queue(){
        this.head.key = null;
        this.head.next = null;
        this.tail.key = null;
        this.tail.next = null;
    }

    public void Enqueue(T key) {
    	this.PushBack(key);
    }

    public T Dequeue(){
        Node head = this.head.next;
        this.PopFront();
        return (T) head.key;
    }

    public Object Top(){
        return this.head.next.key;
    }

    public void printQueue(){
        Node printNode = this.head.next;
        System.out.print("[");
        for(int j=0;j<this.size();j++){
            if(j!= this.size()-1){
                System.out.print(printNode.key+", ");
            }else{
                System.out.print(printNode.key+"]");
            }
            printNode = printNode.next;
        }
    }
}
