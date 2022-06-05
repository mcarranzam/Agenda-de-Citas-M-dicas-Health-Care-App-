package DataStructures.tree;
import DataStructures.node.BinaryNode;
import java.nio.BufferUnderflowException;

public class BinarySearchTree<T extends Comparable<?super T>> {
    private BinaryNode<T> root;

    public BinarySearchTree(){
        root = null;
    }

    public void insert( T x ){ root = insert(x,root); }

    public void remove( T x ){
        root = remove(x,root);
    }

    public T findMin(){
        if(isEmpty()){
            throw new BufferUnderflowException();
        }
        return findMin(root).element;
    }

    public T findMax(){
        if(isEmpty()){
            throw new BufferUnderflowException();
        }
        return findMax(root).element;
    }
    public boolean contains( T x ){ return contains(x,root); }


    public void  makeEmpty(){
        root = null;
    }

    public boolean isEmpty(){ return (root==null); }


    private BinaryNode <T> insert( T x, BinaryNode <T> t ){
        if(t==null){
            return new BinaryNode<T>(x,null,null);
        }
        int val = x.compareTo(t.element);
        if(val<0){
            t.left = insert(x,t.left);
        }else if(val>0){
            t.right = insert(x,t.right);
        }
        return t;
    }

    private BinaryNode <T> remove( T x, BinaryNode <T> t ){
        if (t==null){
            return t;
        }
        int val = x.compareTo(t.element);
        if (val<0){
            t.left = remove(x,t.left);
        }else if(val>0){
            t.right = remove(x,t.right);
        }else if(t.left!=null && t.right!=null){
            t.element = findMin(t.right).element;
            t.right = remove(t.element,t.right);
        }else{
            t = (t.left!=null) ? t.left : t.right;
        }
        return t;
    }

    private BinaryNode <T> findMin( BinaryNode <T> t ){
        if (t==null){
            return null;
        } else if(t.left==null){
            return t;
        }
        return findMin(t.left);
    }

    private BinaryNode <T> findMax( BinaryNode <T> t ){
        if (t==null){
            return null;
        }else if(t.right==null){
            return t;
        }
        return findMax(t.right);
    }

    private boolean contains( T x, BinaryNode <T> t ){
        if (t==null){
            return false;
        }
        int val = x.compareTo(t.element);
        if (val<0){
            return contains(x,t.left);
        }else if(val>0){
            return contains(x,t.right);
        }else{
            return true; //Match
        }
    }

    public void printTree(){
        ayudanteInorden(root);
    }
    private void ayudanteInorden( BinaryNode<T> node) {
        if(node == null)
            return;
        ayudanteInorden(node.left);
        System.out.print(node.element + " ");
        ayudanteInorden(node.right);
    }

    private int height( BinaryNode <T> t ){
        if(t==null){
            return -1;
        }else{
            return 1+Math.max(height(t.left),height(t.right));
        }
    }
}
