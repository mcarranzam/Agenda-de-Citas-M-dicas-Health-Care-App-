package DataStructures.tree;
import DataStructures.list.SinglyLinkedList;
import DataStructures.node.AvlNode;
import DataStructures.queue.Queue;

import java.nio.BufferUnderflowException;

public class AvlTree<T extends Comparable<?super T>>{
    private AvlNode<T> root;
    public int possibleNodes = 0;

    public AvlTree() {
        root = null;
    }
    public AvlTree(AvlNode<T> rt) {
        root = rt;
    }

    void updateHeight(AvlNode<T> n) {
        n.height = 1 + Math.max(height(n.left), height(n.right));
    }

    int height(AvlNode<T> n) {
        return n == null ? -1 : n.height;
    }

    int getBalance(AvlNode<T> n) {
        return (n == null) ? 0 : height(n.right) - height(n.left);
    }

    AvlNode<T> rotateRight(AvlNode<T> y) {
        AvlNode<T> x = y.left;
        AvlNode<T> z = x.right;
        x.right = y;
        y.left = z;
        updateHeight(y);
        updateHeight(x);
        return x;
    }

    AvlNode<T> rotateLeft(AvlNode<T> y) {
        AvlNode<T> x = y.right;
        AvlNode<T> z = x.left;
        x.left = y;
        y.right = z;
        updateHeight(y);
        updateHeight(x);
        return x;
    }

    AvlNode<T> rebalance(AvlNode<T> z) {
        updateHeight(z);
        int balance = getBalance(z);
        if (balance > 1) {
            if (height(z.right.right) > height(z.right.left)) {
                z = rotateLeft(z);
            } else {
                z.right = rotateRight(z.right);
                z = rotateLeft(z);
            }
        } else if (balance < -1) {
            if (height(z.left.left) > height(z.left.right))
                z = rotateRight(z);
            else {
                z.left = rotateLeft(z.left);
                z = rotateRight(z);
            }
        }
        return z;
    }
    public void insert(T key) {
        root = insert(root, key);
    }


    public void delete(T key) {
        root = delete(root, key);
    }

    AvlNode<T> insert(AvlNode<T> node, T key) {
        if (node == null) {
            return new AvlNode<T>(key);
        }
        int val = key.compareTo(node.element);
        if (val<0) {
            node.left = insert(node.left, key);
        } else if (val>0) {
            node.right = insert(node.right, key);
        } else {
            throw new RuntimeException("duplicate Key!");
        }
        return rebalance(node);
    }

    AvlNode<T> delete(AvlNode<T> node, T key) {
        if (node == null) {
            return node;
        }
        int val = key.compareTo(node.element);
        if (val<0) {
            node.left = delete(node.left, key);
        } else if (val>0) {
            node.right = delete(node.right, key);
        } else {
            if (node.left == null || node.right == null) {
                node = (node.left == null) ? node.right : node.left;
            } else {
                AvlNode<T> mostLeftChild = findMinAvl(node.right);
                node.element = mostLeftChild.element;
                node.right = delete(node.right, node.element);
            }
        }
        if (node != null) {
            node = rebalance(node);
        }
        return node;
    }

    public T findMinAvl(){
        if(isEmptyAvl()){
            throw new BufferUnderflowException();
        }
        return findMinAvl(root).element;
    }
    public T findMaxAvl(){
        if(isEmptyAvl()){
            throw new BufferUnderflowException();
        }
        return findMaxAvl(root).element;
    }

    private AvlNode <T> findMinAvl( AvlNode <T> t ){
        if (t==null){
            return null;
        } else if(t.left==null){
            return t;
        }
        return findMinAvl(t.left);
    }

    private AvlNode <T> findMaxAvl( AvlNode <T> t ){
        if (t==null){
            return null;
        }else if(t.right==null){
            return t;
        }
        return findMaxAvl(t.right);
    }

    public boolean containsAvl( T x ){ return containsAvl(x,root); }

    private boolean containsAvl( T x, AvlNode <T> t ){
        if (t==null){
            return false;
        }
        int val = x.compareTo(t.element);
        if (val<0){
            return containsAvl(x,t.left);
        }else if(val>0){
            return containsAvl(x,t.right);
        }else{
            return true; //Match
        }
    }
    public void printTreeAvl(){
        printTreeAvl(root);
    }
    private void printTreeAvl(AvlNode<T> t ){
        SinglyLinkedList<T> result = new SinglyLinkedList<T>();
        if (t==null){
            result.print(1,1);
        }else{
            Queue<AvlNode<T>> queue = new Queue<AvlNode<T>>();
            queue.Enqueue(t);
            while(!queue.Empty()){
                AvlNode<T> tmp = queue.Dequeue();
                result.PushBack(tmp.element);
                if(tmp.left!=null){
                    queue.Enqueue(tmp.left);
                }
                if(tmp.right!=null){
                    queue.Enqueue(tmp.right);
                }
            }
            result.print(1,1);
        }
    }
    public void  makeEmptyAvl(){
        root = null;
    }

    public boolean isEmptyAvl(){ return (root==null); }

    public AvlNode<T> getRoot() {
        return root;
    }

}
