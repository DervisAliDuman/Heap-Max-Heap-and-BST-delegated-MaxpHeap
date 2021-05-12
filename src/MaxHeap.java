
import java.util.*;

/**
 * Structure like Heap but biggest element in root, and it stores count of elements
 * you can store same element same place more than one time
 */
public class MaxHeap < E extends Comparable<E>> extends AbstractQueue < E > implements Iterable<E>, Comparable<MaxHeap<E>> {

    private ArrayList < Node<E > > theData; //ArrayList of datas holding node

    private int element_count = 0; //elements count

    Comparator < E > comparator = null; //For compare() method

    /**
     * compares two heaps with first elements
     * @param es second heap
     * @return compares two heaps with first elements
     */
    @Override
    public int compareTo(MaxHeap<E> es) {
        return compare(theData.get(0).data , es.get(0).data);
    }

    /**
     * Node class that holds data and its occurances
     * @param <E>
     */
    protected static class Node<E extends Comparable<E>> implements Comparable<E>{
        protected E data = null; //data
        protected int count = 0; //occurances

        public Node(E item){
            data = item;
        }

        public Node(){
            data = null;
        }

        public String toString(){
            return data.toString();
        }

        @Override
        public int compareTo(E e) {
            return this.data.compareTo(e);
        }

        public E getData() {
            return data;
        }

        public int getCount() {
            return count;
        }
    }

    /**
     * No parameter onstructor
     */
    public MaxHeap() {
        theData = new ArrayList < Node<E> > ();
    }

    /**
     *  Constructor
     * @param capacity capacity of list will be created
     * @param comp comparator object of given type
     */
    public MaxHeap(int capacity, Comparator < E > comp) {
        if (capacity < 1) throw new IllegalArgumentException();
        theData = new ArrayList < Node<E> > (capacity + 1);
        comparator = comp;
    }

    /**
     * finds item returns occurances
     * @param item will be found
     * @return occurances of item
     */
    public int find(E item){
        if (theData.size() <= 0) return 0;

        for (int i = 0; i< theData.size() ; i++)
            if (theData.get(i).getData().compareTo(item)==0)
                return theData.get(i).count;

        return 0;
    }

    /**
     *
     * @return string version of heap
     */
    @Override
    public String toString() {

        if (theData.size() == 0)
            return "[]";

        StringBuilder sb = new StringBuilder();
        sb.append('[');

        for (int i = 0 ; i< theData.size() ; i++){
            sb.append(theData.get(i).data + "."+theData.get(i).count);
            if (i == theData.size()-1)
                return sb.append(']').toString();

            sb.append(',').append(' ');
        }
        return sb.toString();
    }

    /**
     *  returns node in index
     * @param index index of node
     * @return node in index
     */
    public Node<E> get(int index){
        if (index < 0 || index >=theData.size() ) return null;
        return theData.get(index);
    }

    /**
     * gets element count
     * @return element count
     */
    public int get_element_count(){
        return element_count;
    }

    /**
     * returns mode node
     * @return node of mode
     */
    public Node<E> find_mode(){
        if(theData.size() == 0) return null;
        int max_index = 0, max_count = 0 ;
        //finding mode
        for (int i = 0 ; i< theData.size() ; i++ ){
            if(theData.get(i).count > max_count){
                max_count = theData.get(i).count;
                max_index = i;
            }
        }
        return theData.get(max_index);
    }

    /**
     *  fixes heap with given item index
     * @param indexItem takes index of item that may need fixing position on heap
     */
    private void fixHeap(int indexItem){
        int child = indexItem;
        int parent = get_parent(child);
        // Fixing heap for upperbounds
        while (parent >= 0 && compare(theData.get(parent).data, theData.get(child).data) < 0) {
            swap(parent, child);
            child = parent;
            parent = get_parent(child);
        }

        parent = indexItem;

        // Fixing heap for lowerbounds
        while (true) {
            int leftChild = get_left_child(parent);
            if (leftChild >= theData.size()) {
                break;
            }
            int rightChild = leftChild + 1;
            int maxChild = leftChild;

            if (rightChild < theData.size()  && compare(theData.get(leftChild).data, theData.get(rightChild).data) < 0) {
                maxChild = rightChild;
            }

            if (compare(theData.get(parent).data, theData.get(maxChild).data) < 0) {
                swap(parent, maxChild);
                parent = maxChild;
            } else {
                break;
            }
        }
    }

    /**
     * removes given element
     * @param item given element will removed
     * @return  element count after remove
     */
    public int remove_item(E item){
        if (theData.size() <= 0) return 0;

        int index = -1;

        for (int i = 0; i< theData.size() ; i++) {
            if (theData.get(i).data.compareTo(item) == 0) {
                index = i;
                break;
            }
        }

        if (index == -1) return 0;

        if (theData.get(index).count == 1 ){

            if(index != theData.size()-1){
                theData.set(index, theData.remove(theData.size() - 1));
                fixHeap(index);
            }else {
                theData.remove(theData.size() - 1);
            }


        }else{
            theData.get(index).count-=1;
        }

        return find(item);
    }

    /**
     * returns min node element
     * @return removed min node element
     */
    public E returnMinElement(){
        if (theData.size() <= 0) return null;

        E min = theData.get(0).getData();

        for (int i = 0; i< theData.size() ; i++){
            if (min.compareTo(theData.get(i).getData()) > 0 ){
                min = theData.get(i).getData();
            }
        }
        return min;
    }

    /**
     * removes min node all occurances recursively
     * @return removed min node
     */
    public Node<E> remove_min_node(){
        if (theData.size() <= 0) return null;

        int index = 0 ;
        E min = theData.get(0).getData();
        //searching min element
        for (int i = 0; i< theData.size() ; i++){
            if (min.compareTo(theData.get(i).getData()) > 0 ){
                min = theData.get(i).getData();
                index = i;
            }
        }

        //copying element for return
        Node<E> item = new Node<E>(theData.get(index).getData());
        item.data = theData.get(index).getData();
        item.count = theData.get(index).getCount();

        //removing all occurances
        while (theData.get(index).count > 1)
            theData.get(index).count-=1;

        if (theData.get(index).count == 1 ){

            if(index != theData.size()-1){
                theData.set(index, theData.remove(theData.size() - 1));
                fixHeap(index);
            }else
                theData.remove(theData.size() - 1);
        }
        return item;
    }

    /**
     * removes first node all occurances recursively
     * @return removed first node
     */
    public Node<E> remove_first_node(){
        if (theData.size() <= 0) return null;

        Node<E> item = new Node<E>(theData.get(0).getData());
        item.data = theData.get(0).getData();
        item.count = theData.get(0).getCount();

        if (theData.get(0).count == 1 ){

            if(0 != theData.size()-1){
                theData.set(0, theData.remove(theData.size() - 1));
                fixHeap(0);
            }else {
                theData.remove(theData.size() - 1);
            }


        }else{
            theData.get(0).count-=1;
            remove_first_node();
        }

        return item;
    }

    /**
     * removes last node all occurances
     * @return removed node
     */
    public Node<E> remove_last_node(){
        if (theData.size() <= 0) return null;

        Node<E> item = theData.get(theData.size()-1);

        theData.remove(theData.size() - 1);

        return item;
    }

    /**
     * returns left child of index
     * @param index index of element
     * @return left child of index
     */
    private int get_left_child(int index){
        return 2 * index + 1;
    }

    /**
     * return right child of index
     * @param index index of element
     * @return right child of index
     */
    private int get_right_child(int index){
        return 2 * index + 2;
    }

    /**
     *  returns parent of index
     * @param index index of element
     * @return parent of index
     */
    private int get_parent(int index){
        return (index-1)/2;
    }

    public int add_item(E item){

        int index = -1;
        //finding index of item if it is exist
        for (int i = 0; i< theData.size() ; i++){
            if (theData.get(i).data.equals(item)){
                index = i;
                break;
            }
        }
        //offering item
        offer(item);
        //returning count
        if(index == -1){
            return 1;
        }else{
            return theData.get(index).count;
        }
    }

    /**
     * adds element in heap
     * @param item item will be added
     * @return if succesfully added
     */
    public boolean offer(E item) {

        int index = -1;
        //finding index of item if it is exist
        for (int i = 0; i< theData.size() ; i++){
            if (theData.get(i).data.equals(item)){
                index = i;
                break;
            }
        }

        if (index < 0 ){ // creating and adding new node with element
            Node<E> temp = new Node<E>(item);
            theData.add(temp);
            theData.get(theData.size()-1).count+=1;
            fixHeap(theData.size() - 1);
        }else{  //increase count because alredy exist
            theData.get(index).count+=1;
        }

        element_count +=1;
        return true;
    }

    /**
     * deletes first element of heap
     * @return first element of heap
     */
    public E poll() {
        if (isEmpty()) {
            return null;
        }
        //first element of heap
        E result = theData.get(0).data;

        if (theData.size() == 1 && theData.get(theData.size()-1).count == 1 ) {
            theData.remove(0);
            return result;
        }
        //deleting first element of heap
        if (theData.get(0).count == 1 ){
            theData.set(0, theData.remove(theData.size() - 1));

            fixHeap(0);
        }else{ //first element of heap if exist more than 1, decreases count
            theData.get(0).count-=1;
        }
        //fixes heap
        fixHeap(0);

        return result;
    }

    /**
     *  compares items
     * @param left item1
     * @param right item2
     * @return output like compareTo() method
     */
    private int compare(E left, E right) {
        if (comparator != null) { // A Comparator is defined.
            return comparator.compare(left, right);
        }
        else { // Use left’s compareTo method.
            return ( (Comparable < E > ) left).compareTo(right);
        }
    }

    /**
     * swaps values on index a and b
     * @param a value index will swapped
     * @param b value index will swapped
     */
    public void swap(int a, int b) {
        Node<E> temp = theData.get(a);
        theData.set(a,theData.get(b));
        theData.set(b,temp);
    }

    /**
     * returns size of heap
     * @return size of heap
     */
    public int size() {
        return theData.size();
    }

    public Iterator iterator() {
        return theData.iterator();
    }

    public Iterator myIterator(){
        return theData.iterator();
    }

    /**
     *  return first element of heap
     * @return first element of heap
     */
    public E peek() {
        return theData.get(0).getData();
    }
}
