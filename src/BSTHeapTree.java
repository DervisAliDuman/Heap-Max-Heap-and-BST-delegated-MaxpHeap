
import java.util.NoSuchElementException;

/**
 * Tree that keeps heaps in its nodes
 */
public class BSTHeapTree<E extends Comparable<E>>{
    KWBinarySearchTree<MaxHeap<E>> heaps;  //heap tree

    private static final int MAX_HEAP_NUMBER = 7;   //max item count on heap

    public BSTHeapTree(){
        heaps = new KWBinarySearchTree<MaxHeap<E>>();
    }

    /**
     * calls toString method of tree
     * @return calls toString method of tree
     */
    public String toString(){
        return heaps.toString();
    }

    /**
     * Finds item returns count of item
     * @param target item that we are searching
     * @return count of item
     */
    public int find(E target) {
        if (heaps.getData().get(0).getData().equals(target))
            return heaps.getData().get(0).getCount();
        return find(heaps.getRoot(),target);
    }

    /**
     * Finds item returns count of item
     * @param localRoot root of tree
     * @param target item that we are searching
     * @return count of item
     */
    private int find( KWBinarySearchTree<MaxHeap<E>>.Node<MaxHeap<E> > localRoot, E target) {
        if(localRoot == null)
            return 0;
        int compResult = target.compareTo(localRoot.data.get(0).getData());

        //traversing tree and using max heaps find() method as well
        if (compResult == 0 || localRoot.data.find(target) > 0){
            return localRoot.data.find(target);
        } else if (compResult<0) {
            return find(localRoot.left, target);
        }else {
            return find(localRoot.right, target);
        }
    }

    /**
     * finds mode
     * @return mode element
     */
    public E find_mode(){
        mode = null;
        find_mode(heaps.getRoot());
        return (E)mode.data;
    }

    private MaxHeap.Node mode = null;

    /**
     * finds mode
     * @param localRoot root element of where we are
     * @return mode element
     */
    private void find_mode(KWBinarySearchTree<MaxHeap<E>>.Node<MaxHeap<E> > localRoot){
        if(localRoot == null)
            return ;

        MaxHeap.Node tempnode = new MaxHeap.Node();
        tempnode=  localRoot.data.find_mode();
        //find() method of heap

        if (mode == null){
            mode = tempnode;
        } else if (mode.count < tempnode.count){
            mode = tempnode;
        }
        //traverse tree
        find_mode(localRoot.left);
        find_mode(localRoot.right);

    }

    /**
     * adds item on tree
     * @param item item that we want to add
     * @return count after adding
     */
    public int add(E item){
        return add(heaps.getRoot(), item);
    }

    /**
     * adds item on tree
     * @param localRoot root element of where we are
     * @param target item that we want to add
     * @return count after adding
     */
    private int add(KWBinarySearchTree<MaxHeap<E>>.Node<MaxHeap<E> > localRoot, E target){
        if(localRoot == null){
            //if local root is null we can add
            MaxHeap<E> temp = new MaxHeap<E>();
            temp.add_item(target);
            heaps.add(temp);
            return 1;
        } else if( localRoot.data.size() < MAX_HEAP_NUMBER){
            //if heap still empty we can add item in heap
            return localRoot.data.add_item(target);
        } else{
            int compResult = target.compareTo(localRoot.data.get(0).getData());
            //searching tree
            if (compResult == 0 || localRoot.data.find(target) > 0){
                return localRoot.data.add_item(target);
            } else if (compResult<0) {
                return add(localRoot.left, target);
            }else {
                return add(localRoot.right, target);
            }
        }
    }

    private KWBinarySearchTree<MaxHeap<E>>.Node<MaxHeap<E> > PreviousNode = null;
    private Boolean rightFlag = false;
    //Theese variables for storing previous node

    /**
     * adds element from appropriate child and deletes it
     * @param localRoot root element of where we are
     * @return deleted node
     */
    private MaxHeap.Node shift_remove(KWBinarySearchTree<MaxHeap<E>>.Node<MaxHeap<E> > localRoot){
        if (localRoot == null ) return null;
        if (localRoot.left == null && localRoot.right == null) return null;
        //theese means we dont have item will removed anymore

        KWBinarySearchTree<MaxHeap<E>>.Node<MaxHeap<E> > iter = localRoot;
        KWBinarySearchTree<MaxHeap<E>>.Node<MaxHeap<E> > secondIter = localRoot;
        //iterators from tree

        //deleting biggest element of left side if left isnt null
        if (localRoot.left != null){
            rightFlag = false;
            PreviousNode = iter;
            iter = iter.left;

            while (iter.right != null){ //going mostright child
                rightFlag = true;
                PreviousNode = iter;
                iter = iter.right;
            }

            MaxHeap.Node temp = iter.data.remove_first_node();
            //removing biggest element

            if (temp != null) //adding element removed into root
                for (int i = 0 ; i < temp.count ; i++ )
                    localRoot.data.add_item((E)temp.data);

            //if tree node is empty, deletes it
            if(iter.data.size() == 0 && iter.getLeft() == null && iter.getRight() == null){
                if (rightFlag)
                    PreviousNode.right = null;
                else
                    PreviousNode.left = null;
            }else {  //if tree node doesnt empty, go to another child recursively
                KWBinarySearchTree<MaxHeap<E>>.Node<MaxHeap<E> > tempNode = PreviousNode;
                shift_remove(iter);
                PreviousNode = tempNode;
            }

        }else if (localRoot.right != null){ //deleting smallest element of left side
            rightFlag = true;
            PreviousNode = iter;
            iter=iter.right;
            E minElement = null;
            secondIter = iter;
            minElement = iter.data.returnMinElement();
            int left_count = 0;

            //going leftmost element
            while (iter.left != null){
                iter = iter.left;

                if (iter.data.returnMinElement() !=null && iter.data.returnMinElement().compareTo(minElement) < 0){
                    rightFlag = false;
                    PreviousNode = iter;    //taking preious node of secondIter
                    minElement = iter.data.returnMinElement();
                    secondIter = iter;
                }
            }

            MaxHeap.Node temp = secondIter.data.remove_min_node();
            //removes min node of heap

            if (temp != null) //adds element to heap
                for (int i = 0 ; i < temp.count ; i++ )
                    localRoot.data.add_item((E)temp.data);

            //if tree node is empty, deletes it
            if(secondIter.data.size() == 0 && secondIter.getRight() == null && secondIter.getLeft() == null){
                if (rightFlag)
                    PreviousNode.right = null;
                else
                    PreviousNode.left = null;

            }else { //if tree node doenst empty, go to another child recursively
                KWBinarySearchTree<MaxHeap<E>>.Node<MaxHeap<E> > tempNode = PreviousNode;
                shift_remove(secondIter);
                PreviousNode = tempNode;
            }
        }
        return null;
    }

    /**
     * removes item recursively
     * @param item item will removed
     * @return element count after remove
     * @throws NoSuchElementException
     */
    public int remove(E item) throws NoSuchElementException {
       if (find(item) == 0)
            throw new NoSuchElementException("Item: "+ item +" Don't Exist in whole structure.");
        PreviousNode = heaps.getRoot();
        return remove(heaps.getRoot(), item);
    }

    /**
     * removes item recursively
     * @param localRoot root element of where we are
     * @param target item will removed
     * @return element count after remove
     */
    private int remove(KWBinarySearchTree<MaxHeap<E>>.Node<MaxHeap<E> > localRoot, E target){
        if(localRoot == null){
            return 0;
        }
        int count = 0;
        if (localRoot.data.find(target)>0){
            count =  localRoot.data.remove_item(target);
            //removing item if found

            if (localRoot.data.find(target) > 0){
                return localRoot.data.find(target);
            }

            shift_remove(localRoot);
            //shifts elements

            return count;
        }

        PreviousNode = localRoot;

        int compResult = target.compareTo(localRoot.data.get(0).getData());
        //searching tree
        if (compResult<0 && localRoot.left != null) {
            rightFlag= false;
            return remove(localRoot.left, target);
        }else if (localRoot.right != null){
            rightFlag = true;
            return remove(localRoot.right, target);
        }
        return 0;
    }
}