import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Task5 {

    public static String filterNames(final String names){
        List<List<String>> namesSorted = Arrays.stream(names.toUpperCase().split(";"))
                .map(x -> {
                    List<String> temp = new ArrayList<>(Arrays.stream(x.split(":")).toList());
                    Collections.reverse(temp);
                    return temp;
                })
                .sorted((p1, p2) -> {
                    if (p1.get(0).compareTo(p2.get(0)) != 0)
                        return p1.get(0).compareTo(p2.get(0));
                    else
                        return p1.get(1).compareTo(p2.get(1));
                }).toList();

        StringBuilder namesSortedString = new StringBuilder();
        for (List<String> personNames : namesSorted) {
            namesSortedString.append("(%s, %s)".formatted(personNames.get(0), personNames.get(1)));
        }

        return namesSortedString.toString();
    }

    public static void main(String[] args){
        System.out.println(filterNames("Fred:Corwill;Wilfred:Corwill;Barney:Tornbull;Betty:Tornbull;Bjon:Tornbull;Raphael:Corwill;Alfred:Corwill"));
    }
}
