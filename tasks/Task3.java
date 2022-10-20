
public class Task3 {
    public static int digital_root(final int number){
        int sum = String.valueOf(number)
                .chars()
                .map(Character::getNumericValue)
                .sum();
        if (sum > 9){
            return digital_root(sum);
        }
        return sum;
    }

    public static void main(String[] args){
        System.out.println(digital_root(16));
        System.out.println(digital_root(942));
        System.out.println(digital_root(132189));
        System.out.println(digital_root(493193));
    }
}
