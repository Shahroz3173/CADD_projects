import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class test03_typeOfList {

    public static void main(String[] args) {

        List<String> arrayList = new ArrayList<>();
        arrayList.add("Apple");
        arrayList.add("Banana");
        arrayList.add("Cherry");
        System.out.println("ArrayList: " + arrayList);


        List<String> linkedList = new LinkedList<>();
        linkedList.add("Dog");
        linkedList.add("Elephant");
        linkedList.add("Fox");
        System.out.println("LinkedList: " + linkedList);
    }
}

//The Java Collection Framework is a set of classes and interfaces that
// implement commonly reusable collection data structures such as List, Set, Map, etc.
