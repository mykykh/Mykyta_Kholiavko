import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Task1 {
    public static List<Integer> getIntegersFromList(List<Object> list){
        return list.stream()
                .filter(x -> x instanceof Integer)
                .map(x -> (Integer) x)
                .collect(Collectors.toList());
    }

    public static void main(String[] args){
        System.out.println(getIntegersFromList(Arrays.asList(1, 2, 'a', 'b')));
        System.out.println(getIntegersFromList(Arrays.asList(1, 2, 'a', 'b', 0, 15)));
        System.out.println(getIntegersFromList(Arrays.asList(1, 2, 'a', 'b', "aasf", '1', "123", 231)));
    }
}
