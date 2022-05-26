package main.java.model;

public class Node<T> {

    private Node<T> prev;
    private T data;
    private Node<T> next;

    public Node(Node<T> prev, T data, Node<T> next) {
        this.setPrev(prev);
        this.setData(data);
        this.setNext(next);
    }

    @Override
    public String toString() {
        return "Node{" + "data=" + getData() + "}";
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Node<T> getNext() {
        return next;
    }

    public void setNext(Node<T> next) {
        this.next = next;
    }

    public Node<T> getPrev() {
        return prev;
    }

    public void setPrev(Node<T> prev) {
        this.prev = prev;
    }
}
