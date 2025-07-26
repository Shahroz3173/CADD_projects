import java.util.ArrayList;
import java.util.Collections;

public class test03_secMINIMUM {

    public static void main(String[] args) {

        ArrayList<Integer> numbers = new ArrayList<>();
        numbers.add(12);
        numbers.add(5);
        numbers.add(8);
        numbers.add(19);
        numbers.add(3);


        Collections.sort(numbers);
        int secondMin = numbers.get(1);
        System.out.println("Second minimum element is: " + secondMin);



    }
}
