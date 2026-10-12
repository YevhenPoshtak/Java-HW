package task4_6;

import task4_6.pack.MyClass;
import task4_6.pack.SamePackage;

public class Main {

    public static void main(String[] args) {
        MyClass obj = new MyClass();

        System.out.print("Inside package: ");
        SamePackage.test(obj);
        // obj.show(); // compile error: show() has protected access in task4_6.pack.MyClass
        System.out.println("Outside package: obj.show() is not accessible, " + "method is not public");
    }
}
