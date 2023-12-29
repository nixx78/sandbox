package lv.nixx.oop.interfaces;

public interface InterfaceWithDefault {

    static String staticString = "Static.String";
    String d = "DefaultString";
    default void method1(String p) {
        System.out.println("Call method '1' default :" + p + ":" + d);
    }
    void method2(String p);

}
