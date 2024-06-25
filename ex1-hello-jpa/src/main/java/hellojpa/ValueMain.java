package hellojpa;

public class ValueMain {
    public static void main(String[] args) {

        Integer a = Integer.valueOf(10);
        Integer b = a;

//        int a = 10;
//        int b = a;

        a = Integer.valueOf(20);

        System.out.println("a = " + a);
        System.out.println("b = " + b);
    }
}
