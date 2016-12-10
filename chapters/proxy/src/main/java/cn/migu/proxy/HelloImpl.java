package cn.migu.proxy;

/**
 * Created by Song on 2016/12/3.
 */
public class HelloImpl implements Hello{

    @Override
    public void say(String name) {
        System.out.println("Hello" + name);
    }

    public static void main(String[] args){
        Hello hello = new HelloProxy();
        hello.say("Jack");
    }
}
