public class 测试 {
    public static void main(String[] args) {
        Object obj = new String("我是一个字符串");
        //真实的子类类型是String，但是此处向下转型为StringBuffer
        StringBuffer str = (StringBuffer) obj;
        System.out.println(str.charAt(0));
    }
}
