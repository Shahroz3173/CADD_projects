import java.util.ArrayList;

public class test03_arraylist {

    public static void main(String[] args) {

        ArrayList<String> items = new ArrayList<>();


        items.add("Apple");
        items.add("Banana");
        items.add("Cherry");
        items.add(null);
        System.out.println("-------------------------------------------------");
        System.out.println("After adding elements: " + items);

        System.out.println("-------------------------------------------------");


        // 2. Update( "Banana" to "Blueberry")
        int indexToUpdate = items.indexOf("Banana");

        items.set(indexToUpdate, "Blueberry");

        System.out.println("After updating 'Banana' to 'Blueberry': " + items);

        System.out.println("-------------------------------------------------");

        //  Delete an element-- remove "Apple"
        items.remove("Apple");
        System.out.println("After removing 'Apple': " + items);

        System.out.println("-------------------------------------------------");

        // particular value exists
        String checkItem = "Cherry";
        if (items.contains(checkItem)) {
            System.out.println("'" + checkItem + "' is present in the list.");
        } else {
            System.out.println("'" + checkItem + "' is NOT present in the list.");
        }
        System.out.println("-------------------------------------------------");

        //  null values
        if (items.contains(null)) {
            System.out.println("The list contains a null value.");
        } else {
            System.out.println("The list does not contain any null value.");
        }
        System.out.println("-------------------------------------------------");
    }
}

