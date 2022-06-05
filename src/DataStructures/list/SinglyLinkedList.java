package DataStructures.list;

import DataStructures.list.include.List;
import DataStructures.node.Node;

public class SinglyLinkedList<T> extends List {
    public SinglyLinkedList(){
        this.head.key = null;
        this.head.next = null;
        this.tail.key = null;
        this.tail.next = null;
    }

    @Override
    public void PushFront(Object key) {
        if(this.nullKey(key))return; // Customer is trying to add a null value to the list
        Node newNode = new Node(key);
        if(this.Empty()){ // If the list is empty we set the newNode as the head and tail of the list
            this.head.next = newNode;
            this.tail.next = newNode;
        }else{ // Cause the list is not empty it should have a head
            newNode.next = this.head.next; // So we know that the actual Head will become the second on the list
            this.head.next = newNode; // And the newNode becomes the new Head
        }
    }

    @Override
    public Object TopFront() {
        if(this.Empty()){ // We verify if the list is empty
            return "List is Empty";
        }
        return this.head.next.key; // In case is not empty we return the key on the node pointed by the head
    }

    @Override
    public void PopFront() {
        if(this.Empty()){ // We verify if the list is empty
            System.out.println("It's not possible to remove because list is empty");return;
        }
        if(this.head.next == this.tail.next){ // If the head is the same tail that means we just have 1 element
            this.head.next = null; // Then if we are removing that element the list will be empty
            this.tail.next = null; // So both head and tail must point to null
        }else{ // If not we have more than 1 element
            this.head.next = this.head.next.next; // We just need to change the head to the next node
        }
    }

    @Override
    public void PushBack(Object key) {
        if(this.nullKey(key))return; // Customer is trying to add a null value to the list
        Node newNode = new Node(key);
        if(this.Empty()){ // If the list is empty we set the newNode
            this.head.next = newNode; // as the head
            this.tail.next = newNode; // and tail of the list
        }else{ // Cause the list is not empty it should have a tail
            this.tail.next.next = newNode; // The last tail must point to the newNode
            this.tail.next = newNode; // The newNode will be the tail now
        }
    }

    @Override
    public Object TopBack() {
        if(this.Empty()){ // We verify if the list is empty
            return "List is Empty";
        }
        return this.tail.next.key; // If not we get the key of the node pointed by the tail
    }

    @Override
    public void PopBack() {
        if(this.Empty()){ // We verify if the list is empty
            System.out.println("It's not possible to remove because list is empty");return;
        }
        if(this.head.next == this.tail.next){ // If there's only 1 item on the list
            this.head.next = null; // We point the head to null
            this.tail.next = null; // And the same with the tail
            return;
        } // If there's more than 1 item
        Node node = this.head.next;
        while(node.next.next!=null){ // We need to find the node behind to the tail
            node = node.next;
        }
        node.next = null; // Once we find it we need to point to null with that node
        this.tail.next = node; // And this node will be the new tail
    }

    @Override
    public boolean Find(Object key) {
        Node node = this.head.next;
        while(node!=null){ // While the node exists
            if(node.key.equals(key)){ // We verify if the key is the same
                return true; // If yes return true
            }
            node = node.next; // If not keep going.
        } // If the node is null it's because we got to the final
        return false; // of the list and did not find the key
    }

    @Override
    public void Erase(Object key) {
        Node node = this.head.next;
        if (this.head.next.key.equals(key)) {
            this.PopFront(); return; // If the key is at the head we just run a PopFront() function
        } // If is not the head, we need to see the list item by item
        while(!node.next.key.equals(key)) { // The key of the next node is the key we are looking for?
            node = node.next; // 1) No: keep looking
            if(node.next==null){ // If the next node is null we are at final and without the key
                System.out.println("Key is not in the list");return;
            }
        } // 2) Yes: you found the key
        if (node.next.equals(tail.next) & this.tail.next.key.equals(key)) { // If the node is pointing to the tail
            this.PopBack();return; // and the key is in the tail we just run a PopBack() function
        } // If not, erase the item by changing the pointer of the node before the item to the node after it
        node.next = node.next.next;
    }

    @Override
    public boolean Empty() {
        return this.head.next == null; // If head is null list is empty
    }

    @Override
    public void AddBefore(Node node, Object key) {
        if(this.nullKey(key))return; // Customer is trying to add a null value to the list
        if(this.Empty()){ // If the list is empty we add the value.
            System.out.println("Node "+node+" is not in list, because it's empty so we are adding "+key+" to the list");
            this.PushBack(key);return;
        }
        if(this.head.next.equals(node)){ // If the node is the head we just run a PushFront() function
            this.PushFront(key);return;
        }
        Node node2 = this.head.next;
        while(node2.next!=null){ // We need to see the list item by item and we are verifying 1 node after
            if(!node2.next.equals(node)){ // to keep the node before the one we are looking for
                node2 = node2.next; //If is not the node we want keep going
            }else{ // If is the node
                Node newNode = new Node(key); // Create a new node with the new key
                newNode.next = node; // newNode has to point to the node we were looking for
                node2.next = newNode; // The node before the one we were searching needs to point to the new one
                return;
            }
        }
        System.out.println("Node is not in the list");
    }

    @Override
    public void AddAfter(Node node, Object key) {
        if(this.nullKey(key))return; // Customer is trying to add a null value to the list
        if(this.Empty()){ // If the list is empty we add the value.
            System.out.println("Node "+node+" is not in list, because it's empty so we are adding "+key+" to the list");
            this.PushBack(key);return;
        }
        Node node2 = this.head.next;
        while(node2!=null){ // While the node exist
            if(!node2.equals(node)){ // If is different for the node we are searching
                node2 = node2.next; // Keep going
            }else{ // Bingo! you have the node
                if(node.equals(this.tail.next)){
                    this.PushBack(key);return; // If the node is the tail just run a PushBack() function
                }
                Node newNode = new Node(key); // Create new node
                newNode.next = node2.next; // newNode points to the node next to the node we were searching
                node2.next = newNode; // The node we were looking for points to the newNode
                return;
            }
        }
        System.out.println("Node is not in the list");
    }

    public int size(){ // Counts the number of items in the list
        Node node = this.head.next;
        int count = 0;
        while(node!=null){
            count++;
            node = node.next;
        }
        return count;
    }
    public boolean nullKey(Object key){
        if(key==null){ // Customer is trying to add a null value to the list
            System.out.println("It's not possible to add a null value");
            return true;
        }
        return false;
    }
    public void print(int count, int cases){
        Node printNode = this.head.next;
        System.out.print("[");
        for(int j=0;j<this.size();j++){
            if(j!= this.size()-1){
                System.out.print(printNode.key+",");
            }else{
                if(count==cases-1){
                    System.out.print(printNode.key);
                }else{
                    System.out.print(printNode.key+"]");
                }
            }
            printNode = printNode.next;
        }
    }
}
