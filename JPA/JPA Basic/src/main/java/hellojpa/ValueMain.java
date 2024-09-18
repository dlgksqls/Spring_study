package hellojpa;

import hellojpa.domain.Address;

public class ValueMain {
    public static void main(String[] args) {
        int a = 10;
        int b = 10;

        System.out.println(a == b); // true

        Address add1 = new Address("1", "2", "3");
        Address add2 = new Address("1", "2", "3");

        System.out.println(add1.equals(add2)); // true .. equals를 override 해서 개체끼리 비교
    }
}
