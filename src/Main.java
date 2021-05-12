import java.util.*;
import java.security.SecureRandom;

public class Main {
    public static void main(String[] args) {

        DADHeap<String> names = new DADHeap<String>();

        names.add("Haþim");
        names.add("Ali");
        names.offer("Mahmut");
        names.add("Alican");
        names.offer("Mustafa");
        names.offer("Deniz");
        names.add("Hakký");
        names.add("Hamdi");
        names.offer("Mehmet");
        names.add("Zeynep");

        System.out.println(names.toString());
        System.out.println("Search: Mehmet : " + names.search("Mehmet"));

        System.out.println("Remove third biggest which is:  " + names.remove_spesific_largest(3));

        System.out.println(names.toString());

        DADHeap.Itr iter = names.iterator();
        int index = 0;

        while (iter.hasNext()){
            String item = (String) iter.next();
            if (index == 2 ){
                System.out.println("After setting third element with iterator, "+ item +" with Gudubet");
                iter.set("Gudubet");
            }
            index+=1;
        }


        System.out.println(names.toString());

        System.out.println("Peek() method will return: " + names.peek());
        System.out.println("Poll() method will return: " + names.poll());



        DADHeap<String> Secondnames = new DADHeap<String>();

        Secondnames.add("Tugba");
        Secondnames.add("Can");
        Secondnames.offer("Berkay");
        Secondnames.add("Halit");
        Secondnames.offer("Kazim");
        Secondnames.offer("Deniz");

        System.out.println("\nFirts name list is: "+ names.toString());
        System.out.println("Second name list is: "+ Secondnames.toString());

        System.out.println("Merging two heap : "+ names.merge(Secondnames));

        System.out.println("\nNew name list is: "+ names.toString());

        System.out.println("\n\nNow I will remove elements but be careful that I am fixing heap after each remove");
        System.out.println("Removing all elements: ");

        iter = names.iterator();

        while (iter.hasNext()){
            System.out.print(iter.next()+ "  ");
            iter.remove();
        }
        System.out.println("\n\n");




        BSTHeapTree<Integer> TESTbsttree = new BSTHeapTree<Integer>();
        ArrayList<Integer> TESTarrayList = new ArrayList<Integer>();

        fill_tree_and_arraylist(TESTbsttree,TESTarrayList);

        TESTarrayList.sort(Comparable::compareTo);

        System.out.println("\n\nArray Elements: \n" + TESTarrayList);
        System.out.println("Tree elements: \n" + TESTbsttree.toString());



        BSTHeapTree_TEST_1_checkNumberOccurances(TESTbsttree,TESTarrayList);

        BSTHeapTree_TEST_2_checkNumberOccurances(TESTbsttree,TESTarrayList);

        BSTHeapTree_TEST_3_CheckMode(TESTbsttree,TESTarrayList);


        BSTHeapTree_TEST_4_Remove_Test(TESTbsttree, TESTarrayList);


    }

    public static void fill_tree_and_arraylist(BSTHeapTree<Integer> bsttree, ArrayList<Integer> array){
        //Random rand = new Random();

        int rand_number = 0;

        SecureRandom rand = new SecureRandom();

        for (int i = 0 ; i < 3000; i++){
            // Generating random integers in range 0 to 4999
            rand_number = rand.nextInt(5000);
            array.add(rand_number);
            bsttree.add(rand_number);
        }
    }

    public static boolean BSTHeapTree_TEST_1_checkNumberOccurances(BSTHeapTree<Integer> bsttree, ArrayList<Integer> array){

        int first, old_i = 0;;
        boolean flag = false;
        int element_count = 0;
        int element_count_tree = 0;

        for (int i = 0; i < array.size(); i++ ){
            flag = false;
            element_count = 0;
            element_count_tree = 0;
            old_i = i;

            for (int j = old_i; j< array.size() ; j++){
                if(array.get(j).equals(array.get(old_i)))
                    element_count++;
                else
                    break;
            }
            i += (element_count-1);

            element_count_tree = bsttree.find(array.get(old_i));

            if (element_count_tree == element_count)
                System.out.println("Element: " + array.get(old_i) + " , Occurance in tree: "+ element_count_tree + " , In array: " + element_count);
            else{
                System.out.println("TEST 1 FAILED: Counts dont match for: " + array.get(old_i));
                System.out.println("Element: " + array.get(old_i) + " , Count array : " + element_count + "  Count tree : "+ element_count_tree);
                return false;
            }
        }
        System.out.println("\n\nTEST 1 IS SUCCESFULL\n\n");

        return true;
    }

    public static boolean BSTHeapTree_TEST_2_checkNumberOccurances(BSTHeapTree<Integer> bsttree, ArrayList<Integer> array){

        int first, old_i = 0;;
        boolean flag = false;
        int element_count = 0;
        int element_count_tree = 0;

        int element_count_will_searched = 0; //It will go to 100

        for (int i = 0; i < array.size(); i++ ){
            flag = false;
            element_count = 0;
            element_count_tree = 0;
            old_i = i;

            for (int j = old_i; j< array.size() ; j++){
                if(array.get(j).equals(array.get(old_i)))
                    element_count++;
                else
                    break;
            }
            i += (element_count-1);
            element_count_will_searched +=1;

            element_count_tree = bsttree.find(array.get(old_i));

            if (element_count_tree == element_count)
                System.out.println("Element: " + array.get(old_i) + " , Occurance in tree: "+ element_count_tree + " , In array: " + element_count);
            else{
                System.out.println("TEST 2 FAILED: Counts dont match for: " + array.get(old_i));
                return false;
            }
            if (element_count_will_searched == 100){
                System.out.println("\n100 EXIST ELEMENT TEST IS SUCCESFULL\n");

                ArrayList<Integer> array2 = new ArrayList<Integer>();

                array2.add(5005);
                array2.add(9000);
                array2.add(40000);
                array2.add(5995);
                array2.add(9999);
                array2.add(40300);
                array2.add(6666);
                array2.add(9091);
                array2.add(40044);
                array2.add(33300);

                for (int k = 0; k < array2.size(); k++ ){
                    element_count_tree = bsttree.find(array2.get(k));

                    if (element_count_tree == 0)  // We can write 0 because item must not be in tree
                        System.out.println("Element: " + array2.get(k) + " , Occurance in tree: "+ element_count_tree + " , In array: 1" );
                    else{
                        System.out.println("TEST 2: 10 DONT EXÝST ELEMENT TEST IS FAÝLED");
                        System.out.println("Counts dont match for: " + array2.get(k));
                        return false;
                    }
                }

                System.out.println("\nTEST 2: 10 DONT EXÝST ELEMENT TEST IS SUCCESSFULL");
                break;
            }
        }
        System.out.println("\nTEST 2 IS SUCCESFULL");
        return true;
    }

    public static boolean BSTHeapTree_TEST_3_CheckMode(BSTHeapTree<Integer> bsttree, ArrayList<Integer> array){

        Integer bstModeElement = bsttree.find_mode();

        ArrayList<Integer> AllModes = new ArrayList<Integer>();

        int maxCount=0;
        int count = 0;

        for (int i = 0; i < array.size(); i++ ){
            count = 0;
            for (int j = 0; j < array.size(); j++ ){
                if (array.get(i).equals(array.get(j)))
                    count+=1;
            }
            if (count > maxCount){
                maxCount = count;
                AllModes.clear(); //Clearing arraylis because we found new mode
                AllModes.add( array.get(i) );      //Adding new mode in list
            } else if ( count == maxCount ){
                AllModes.add( array.get(i) );   //Adding modes in list until new mode found
            }
        }

        for (int i = 0; i < AllModes.size(); i++ ){     //Checking All modes one by one
            if (bstModeElement.equals(AllModes.get(i))){
                System.out.println("\n\nTEST 3: SUCCESSFULL, MODE FOUND: Mode: " + bstModeElement +"\n");
                return true;
            }
        }

        System.out.println("\n\nTEST 3: FAILED, MODE DOESNT FOUND\n");
        return false;
    }

    public static boolean BSTHeapTree_TEST_4_Remove_Test(BSTHeapTree<Integer> bsttree, ArrayList<Integer> array){

        int rand_index = 0;

        Random rand = new SecureRandom();

        for (int i = 0 ; i < 2900; i++){
            // Generating random integers in range 0 to 2999 then removing from both structures
            rand_index = rand.nextInt(99);
                System.out.print("Removing :"+ array.get(rand_index) + " , Old Count: " + bsttree.find(array.get(rand_index)));
                try {
                    System.out.println(" , New count on tree : "+ bsttree.remove(array.get(rand_index)));
                }catch (NoSuchElementException e){
                    System.out.println(e.toString() );
                }
            System.out.println("Removed from array: " + array.remove(rand_index));
        }

        //System.out.println(bsttree.toString());

        ArrayList<Integer> array2 = new ArrayList<Integer>();
        System.out.println("\n\nRemoving 10 element doesnt exist:\n");
        array2.add(5005);
        array2.add(9000);
        array2.add(40000);
        array2.add(5995);
        array2.add(9999);
        array2.add(40300);
        array2.add(6666);
        array2.add(9091);
        array2.add(40044);
        array2.add(33300);


        for (int i = 0 ; i < array2.size(); i++){
            try {
                bsttree.remove(array2.get(i));
            }catch (NoSuchElementException e){
                System.out.println(e.toString() );
            }

        }

        if (BSTHeapTree_TEST_1_checkNumberOccurances(bsttree,array)){
            System.out.println("TEST 4 IS SUCCESSFULL TOO BECAUSE OF TEST 1 AFTER REMOVE");
        }else {
            System.out.println("TEST 4 FAILED BECAUSE OF TEST 1 AFTER REMOVE");
        }


        return true;
    }

}
