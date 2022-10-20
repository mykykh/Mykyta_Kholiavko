import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Task6 {
    public static int nextBigger(final int number){
        int result = -1;

        List<Integer> digits = new ArrayList<>(
                String.valueOf(number)
                        .chars()
                        .map(Character::getNumericValue)
                        .boxed()
                        .toList()
        );

        for (int i = digits.size() - 2; i >= 0; i--){
            if (digits.get(i) < digits.get(i + 1)){

                int minGraterPos = i + 1;
                for (int j = i + 2; j < digits.size(); j++){
                    if (digits.get(minGraterPos) > digits.get(j)) minGraterPos = j;
                }

                int temp = digits.get(i);
                digits.set(i, digits.get(minGraterPos));
                digits.set(minGraterPos, temp);

                digits.subList(i + 1, digits.size()).sort(Comparator.naturalOrder());

                result = Integer.parseInt(
                        digits.stream()
                                .map(String::valueOf)
                                .reduce("", (s, digit) -> s + digit)
                );

                break;
            }
        }

        return result;
    }

    public static void main(String[] args){
        System.out.println(nextBigger(12));
        System.out.println(nextBigger(513));
        System.out.println(nextBigger(2017));
        System.out.println(nextBigger(9));
        System.out.println(nextBigger(111));
        System.out.println(nextBigger(531));
    }
}
