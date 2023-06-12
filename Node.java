public class Node<E> {
    E data;
    Node<E> next;
    Node<E> prev;

    public Node(E e) {
        data = e;
        next = null;
        prev = null;
    }

    public E get() {
        return data;
    }

    public Node<E> next() {
        return next;
    }

    public Node<E> prev() {
        return prev;
    }

    public void setData(E newData) {
        this.data = newData;
    }

    public void setNext(Node<E> nextNode) {
        next = nextNode;
    }

    public void setPrev(Node<E> prevNode) {
        prev = prevNode;
    }

    public String toString() {
        return data.toString();
    }

}