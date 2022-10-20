
public class Task7 {

    public static String intToIP(final long number){
        return (number >> 24 & 255) + "." +
                (number >> 16 & 255) + "." +
                (number >> 8 & 255) + "." +
                (number & 255);
    }

    public static void main(String[] args){
        System.out.println(intToIP(2149583361L));
        System.out.println(intToIP(32));
        System.out.println(intToIP(0));
    }
}
