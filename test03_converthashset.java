import java.util.ArrayList;
import java.util.HashSet;

public class test03_converthashset {

    public static void main(String[] args) {

        ArrayList<Integer> numbers = new ArrayList<>();
        numbers.add(12);
        numbers.add(5);
        numbers.add(8);
        numbers.add(5);
        numbers.add(19);
        numbers.add(3);
        numbers.add(8);


        HashSet<Integer> uniqueNumbers = new HashSet<>(numbers);


        System.out.println("Unique elements: " + uniqueNumbers);
    }
}
