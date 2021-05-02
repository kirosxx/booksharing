package gdut.imis.test;

public class Foo {
    public static void main(String[] args) {
        son s = new son();
    }
}

class father{
    public father(){
        System.out.println("父");
    }
}

class son extends father{
    public son(){
        new father();
        System.out.println(Character.toString('g'));
    }
}

/*    */

class A{
    public void exam(){
        System.out.println("A");
    }
}
