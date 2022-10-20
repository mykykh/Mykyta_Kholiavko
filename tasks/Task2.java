import java.util.HashSet;
import java.util.Set;

public class Task2 {

    public static String first_non_repeating_letter(final String string){
        String result = "";
        String tempString = string.toLowerCase();

        Set<Character> characters = new HashSet<>();

        for (int i = 0; i < tempString.length(); i++){
            if (!characters.contains(tempString.charAt(i))){
                characters.add(tempString.charAt(i));

                boolean repeated = false;

                for (int j = i + 1; j < tempString.length(); j++){
                    if (tempString.charAt(i) == tempString.charAt(j)){
                        repeated = true;
                        break;
                    }
                }

                if (!repeated){
                    result = String.valueOf(string.charAt(i));
                    break;
                }
            }
        }
        return result;
    }

    public static void main(String[] args){
        System.out.println(first_non_repeating_letter("stress"));
        System.out.println(first_non_repeating_letter("sTreSS"));
        System.out.println(first_non_repeating_letter("stressstress"));
    }
}
