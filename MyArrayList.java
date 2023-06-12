import java.io.Serializable;

public class MyArrayList<E> implements Serializable{
    private static final int DEFAULT_CAPACITY = 10;
    private Object[] elements;
    private int size;

    public MyArrayList(int n) {
        elements = new Object[DEFAULT_CAPACITY];
        size = n;
    }
    public MyArrayList() {
        elements = new Object[DEFAULT_CAPACITY];
        size = 0;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void add(E element) {
        if (size == elements.length) {
            expandCapacity();
        }
        elements[size] = element;
        size++;
    }
    //max
    public int max(){
        int max = -1000000;
        for(int i = 0; i < size; i++){
            if((int)elements[i] > max){
                max = (int)elements[i];
            }
        }
        return max;
    }
    //min
    public int min(){
        int min = 1000000;
        for(int i = 0; i < size; i++){
            if((int)elements[i] < min){
                min = (int)elements[i];
            }
        }
        return min;
    }

    @SuppressWarnings("unchecked")
    public E get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        return (E) elements[index];
    }

    public void set(int index, E element) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        elements[index] = element;
    }

    public void remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        for (int i = index; i < size - 1; i++) {
            elements[i] = elements[i + 1];
        }
        size--;
    }
    //contains
    public boolean contains(int element){
        for(int i = 0; i < size; i++){
            if((int)elements[i] == element){
                return true;
            }
        }
        return false;
    }
    private void expandCapacity() {
        int newCapacity = elements.length * 2;
        Object[] newElements = new Object[newCapacity];
        System.arraycopy(elements, 0, newElements, 0, size);
        elements = newElements;
    }
    //clear
    public void clear(){
        for(int i = 0; i < size; i++){
            elements[i] = null;
        }
        size = 0;
    }
    public Object stream() {
        return null;
    }
}
