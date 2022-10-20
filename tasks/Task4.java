import java.util.List;
import java.util.stream.IntStream;

public class Task4 {

    public static int countPairs(List<Integer> numbers, final int target){
        int pairs = 0;
        for (int i = 0; i < numbers.size(); i++){
            for (int j = 0; j < numbers.size(); j++){
                if ((i != j) && (numbers.get(i) + numbers.get(j) == target)) pairs++;
            }
        }
        return pairs;
    }

    public static int countPairsStream(List<Integer> numbers, final int target){
        return IntStream.range(0, numbers.size())
                .map(
                        i -> (int) IntStream.range(0, numbers.size())
                        .filter(j -> (i != j) && (numbers.get(i) + numbers.get(j) == target))
                        .count()
                ).sum();
    }

    public static void main(String[] args){
        System.out.println(countPairs(List.of(1, 3, 6, 2, 2, 0, 4, 5), 5));
        System.out.println(countPairsStream(List.of(1, 3, 6, 2, 2, 0, 4, 5), 5));
    }
}
