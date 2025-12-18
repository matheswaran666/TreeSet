package TreeSet;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.SortedSet;

class Node<E> {
    E data;
    Node left;
    Node right;
    Node parent;
    boolean color;

    Node(E data) {
        this.data = data;
        this.left = null;
        this.right = null;
        this.parent = null;
        this.color = false;
    }
}

public class MyTreeSet<E extends Comparable<E>> implements Cloneable, Iterable<Node<E>> {


    Node root;
    boolean BLACK = true;
    boolean RED = false;
    Node Nil = new Node(null);

    public MyTreeSet() {
        Nil.color = BLACK;
        Nil.left = Nil;
        Nil.right = Nil;
        Nil.parent = Nil;
        root = Nil;
    }

  
    boolean isEmpty() {
        return root == Nil;
    }

    boolean addAll(Collection<? extends E> c) {
        for (E e : c) {
            this.add(e);
        }
        return true;
    }

    private void addAllRecursive(Node node) {
        if (node != Nil) {
            this.add((E) node.data);
            addAllRecursive(node.left);
            addAllRecursive(node.right);
        }
    }

    boolean addAll(MyTreeSet<E> c) {
        addAllRecursive(c.root);
        return true;
    }

    E pollFirst() {
        Node smallest = this.smallest(root);
        if (smallest == Nil) {
            return null;
        }
        this.remove(smallest.data);
        return (E) smallest.data;
    }

    E pollLast() {
        Node largest = this.largest(root);
        if (largest == Nil) {
            return null;
        }
        this.remove(largest.data);
        return (E) largest.data; 
    }

   

    private boolean containsAllRecursive(Node node) {
        if (node != Nil) {
            if (!this.contains(node.data)) {
                return false;
            }
            return containsAllRecursive(node.left) && containsAllRecursive(node.right);
        }
        return true;
    }

    boolean containsAll(MyTreeSet<E> c) {
        return containsAllRecursive(c.root);
    }

    boolean containsAll(Collection<?> c) {
        for (Object o : c) {
            if (!this.contains(o)) {
                return false;
            }
        }
        return true;
    }
    private void removeAllRecursive(Node node) {
        if (node != Nil) {
            this.remove(node.data);
            removeAllRecursive(node.left);
            removeAllRecursive(node.right);
        }
    }
    boolean removeAll(Collection<?> c) {
        for (Object o : c) {
            this.remove(o);
        }
        return true;
    }

    boolean removeAll(MyTreeSet<E> c) {
        removeAllRecursive(c.root);
        return true;
    }

    private void toArrayRecursive(Node node, ArrayList<Object> arr) {
        if (node != Nil) {
            toArrayRecursive(node.left, arr);
            arr.add(node.data);
            toArrayRecursive(node.right, arr);
        }
    }

    Object[] toArray() {
        ArrayList<Object> arr = new ArrayList<>();
        toArrayRecursive(root, arr);

        return arr.toArray();
    }

    void clear() {
        this.root = Nil;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        MyTreeSet<E> newSet = new MyTreeSet<>();
        newSet.addAll(this);
        return newSet;
    }


    private int inOrderTraversal(Node node, int count) {
        if (node != Nil) {
            count = inOrderTraversal(node.left, count);
            count++;
            count = inOrderTraversal(node.right, count);
        }
        return count;
    }


    private Node largest(Node node) {
        if (node == Nil) {
            return Nil;
        }
        while (node.right != Nil) {
            node = node.right;
        }
        return node;
    }

    private Node smallest(Node node) {
        if (node == Nil) {
            return Nil;
        }
        while (node.left != Nil) {
            node = node.left;
        }
        return node;
    }

    boolean contains(Object o) {
        if (this.findNode((E) o) != Nil) {
            return true;
        }
        return false;
    }

    E first() {

        Node smallest = this.smallest(root);
        if (smallest == Nil) {
            throw new NoSuchElementException();
        }
        return (E) smallest.data;
    }

    E last() {
        Node largest = this.largest(root);
        if (largest == Nil) {
            throw new NoSuchElementException();
        }
        return (E) largest.data;
    }

    int size() {
        return inOrderTraversal(root, 0);
    }

    boolean replace(E oldValue, E newValue) {
            Node node = findNode(oldValue);
            node.data = newValue;
            return true;
    }


    private Node leftRotate(Node root) {
        Node newRoot = root.right;
        root.right = newRoot.left;
        if (newRoot.left != Nil) {
            newRoot.left.parent = root;
        }
        newRoot.parent = root.parent;
        if (root.parent == Nil) {
            this.root = newRoot;
        } else if (root.parent.left == root) {
            root.parent.left = newRoot;
        } else {
            root.parent.right = newRoot;
        }
        newRoot.left = root;
        root.parent = newRoot;
        return newRoot;
    }

    private Node rightRotate(Node root) {
        Node newRoot = root.left;
        root.left = newRoot.right;
        if (newRoot.right != Nil) {
            newRoot.right.parent = root;
        }
        newRoot.parent = root.parent;
        if (newRoot.parent == Nil) {
            this.root = newRoot;
        } else if (root.parent.left == root) {
            root.parent.left = newRoot;

        } else if (root == root.parent.right) {
            root.parent.right = newRoot;
        }
        root.parent = newRoot;
        newRoot.right = root;
        return newRoot;
    }

    private Node successor(Node node) {
        if (node == Nil) {
            return Nil;
        }
        node = node.right;
        while (node.left != Nil) {
            node = node.left;
        }
        return node;
    }

    private Node predeccessor(Node node) {
        if (node == Nil) {
            return Nil;
        }
        node = node.left;
        while (node.right != Nil) {
            node = node.right;
        }
        return node;
    }

    private Node findNode(E value) {
        if (this.root == Nil) {
            return Nil;
        }
        Node node = root;
        while (node != Nil && node.data != Nil) {
            int cmp = value.compareTo((E) node.data);
            if (cmp == 0) {
                return node;
            }
            node = (cmp < 0) ? node.left : node.right;
        }
        return Nil;
    }

    private void recolorFamily(Node grandparent, Node parent, Node uncle) {
        parent.color = BLACK;
        uncle.color = BLACK;
        grandparent.color = RED;
    }

    @Override
    public String toString() {
        if (this.isEmpty()) {
            return "(empty)";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        inOrderTraversal(root, sb);
        if (sb.length() > 1) {
            sb.setLength(sb.length() - 2);
        }
        sb.append("]");
        return sb.toString();
    }

    private void inOrderTraversal(Node node, StringBuilder sb) {
        if (node != Nil) {
            inOrderTraversal(node.left, sb);
            sb.append(node.data).append(", ");
            inOrderTraversal(node.right, sb);
        }
    }

    boolean remove(Object o) {
        if (this.root == Nil) {
            return false;
        }
        return this.delete((E) o);

    }

    private static final String REDCOLOR = "\u001b[31;1;40m";
    private static final String BLACKCOLOR = "\u001b[30;1;40m";
    private static final String RESET = "\u001B[0m";

    public void print() {
        if (root == Nil) {
            System.out.println("(empty)");
            return;
        }
        printRecursive(root, "", true);
    }

    private void printRecursive(Node node, String prefix, boolean isTail) {
        if (node == Nil)
            return;

        System.out.println(prefix + (isTail ? "└── " : "├── ") + formatNode(node));

        boolean hasLeft = node.left != Nil;
        boolean hasRight = node.right != Nil;

        if (!hasLeft && !hasRight)
            return;

        if (hasLeft)
            printRecursive(node.left, prefix + (isTail ? "    " : "│   "), !hasRight);

        if (hasRight)
            printRecursive(node.right, prefix + (isTail ? "    " : "│   "), true);
    }

    private String formatNode(Node node) {
        return (node.color == BLACK ? BLACKCOLOR + " B" : REDCOLOR + " R") + node.data + RESET;
    }

    boolean add(E e) {

        Node newNode = new Node(e);
        newNode.left = Nil;
        newNode.right = Nil;
        newNode.parent = Nil;

        if (root == Nil) {
            root = newNode;
            root.color = BLACK;
            return true;
        }
        Node node = root;

        int cmp = 0;
        while (true) {
            cmp = e.compareTo((E) node.data);
            if (cmp == 0) {
                return false;
            } else if (cmp < 0) {
                if (node.left == Nil) {
                    node.left = newNode;
                    newNode.parent = node;
                    this.fixAfterInsert(newNode);
                    return true;
                }
                node = node.left;
            } else {
                if (node.right == Nil) {
                    node.right = newNode;
                    newNode.parent = node;
                    this.fixAfterInsert(newNode);
                    return true;
                }
                node = node.right;
            }
        }
    }

    private void fixAfterInsert(Node node) {

        if (node.parent == Nil) {
            node.color = BLACK;
            root = node;
            return;
        }

        Node parent = node.parent;
        Node grandParent = parent.parent;

        if (parent.color == BLACK) {
            return;
        }

        if (grandParent == Nil) {
            return;
        }

        Node uncle = grandParent.left == parent ? grandParent.right : grandParent.left;

        if (uncle.color == RED) {
            this.recolorFamily(grandParent, parent, uncle);
            fixAfterInsert(grandParent);
            return;
        }
        rBInsertFixup(node);
        root.color = BLACK;
    }

    private void rBInsertFixup(Node node) {

        Node parent = node.parent;
        Node grandParent = parent.parent;

        if (grandParent.left == parent && parent.left == node) {
            this.rightRotate(grandParent);
            parent.color = BLACK;
            grandParent.color = RED;
        }

        else if (grandParent.right == parent && parent.right == node) {
            this.leftRotate(grandParent);
            parent.color = BLACK;
            grandParent.color = RED;

        }

        else if (grandParent.left == parent && parent.right == node) {
            this.leftRotate(parent);
            this.rightRotate(grandParent);
            node.color = BLACK;
            grandParent.color = RED;
        }

        else if (grandParent.right == parent && parent.left == node) {
            this.rightRotate(parent);
            this.leftRotate(grandParent);
            node.color = BLACK;
            grandParent.color = RED;
        }

    }

    private boolean delete(E value) {
        Node node = findNode(value);
        if (node == Nil) {
            return false;
        }
        if (node.right != Nil && node.left != Nil) {
            Node successor = successor(node);
            node.data = successor.data;
            node = successor;
        }
        Node parent = node.parent;
        Node child = (node.left == Nil) ? node.right : node.left;
        if (node == root) {
            root = child;
        } else {
            if (node == parent.left) {
                parent.left = child;
            } else {
                parent.right = child;
            }
            child.parent = parent;
        }
        if (node.color == BLACK) {
            fixAfterDelete(child);
        }
        
        return true;
    }

    private void fixAfterDelete(Node node) {
        while (node != root && node.color == BLACK) {
            if (node == node.parent.left) {
                Node sibling = node.parent.right;
                if (sibling.color == RED) {
                    sibling.color = BLACK;
                    node.parent.color = RED;
                    leftRotate(node.parent);
                    sibling = node.parent.right;
                }

                if (sibling.left.color == BLACK && sibling.right.color == BLACK) {
                    sibling.color = RED;
                    node = node.parent;
                } else {
                    if (sibling.right.color == BLACK) {
                        sibling.left.color = BLACK;
                        sibling.color = RED;
                        rightRotate(sibling);
                        sibling = node.parent.right;
                    }
                    sibling.color = node.parent.color;
                    node.parent.color = BLACK;
                    sibling.right.color = BLACK;
                    leftRotate(node.parent);
                    node = root;
                }
            } else {
                Node sibling = node.parent.left;
                if (sibling.color == RED) {
                    sibling.color = BLACK;
                    node.parent.color = RED;
                    rightRotate(node.parent);
                    sibling = node.parent.left;
                }   
                if (sibling.right.color == BLACK && sibling.left.color == BLACK) {
                    sibling.color = RED;
                    node = node.parent;
                } else {
                    if (sibling.left.color == BLACK) {
                        sibling.right.color = BLACK;
                        sibling.color = RED;
                        leftRotate(sibling);
                        sibling = node.parent.left;
                    }
                    sibling.color = node.parent.color;
                    node.parent.color = BLACK;
                    sibling.left.color = BLACK;
                    rightRotate(node.parent);
                    node = root;
                }
            }

        }
        node.color = BLACK;
    }                   
    public static void main(String[] args) {
        MyTreeSet<Integer> set = new MyTreeSet<>();
        int[] num = {31,7,18,27,45,333,17,8,13,334};
        for(int i=0;i<num.length;i++){
            set.add(num[i]);
        }
        set.print();
        set.remove(8);
        set.print();
        set.remove(27);
        set.print();
        set.remove(17);
        set.print();
        set.remove(333);
        set.print();



    }



     private class TreeIterator implements Iterator<Node<E>> {

        ArrayDeque<Node<E>> stack = new ArrayDeque<>();

        TreeIterator() {
            pushData(root);
        }

        private void pushData(Node<E> node) {
            if(node == Nil){
                return;
            }
            pushData(node.right);
            stack.push(node);
            pushData(node.left);
            
            
        }


        @Override
        public boolean hasNext() {
            return !stack.isEmpty();
            
        }

        @Override
        public Node<E> next() {
            if(stack.isEmpty()){
                throw new NoSuchElementException();
            }
            return stack.pop();
        }
    


        
    }

    @Override
    public Iterator<Node<E>> iterator() {
      return new TreeIterator();
    }

}
