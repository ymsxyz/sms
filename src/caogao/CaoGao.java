package caogao;

import instance.Student;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

/**
 * 草稿类,用来做测试
 */
public class CaoGao {

    public static void main(String[] args) {

        //测试
        System.out.println("version 4.0");

        HashSet<String> hashSet = new HashSet<>();
        Collections.addAll(hashSet, "a", "b", "c", "d");

        boolean a = hashSet.remove("a");

        boolean a1 = hashSet.contains("b");
        boolean a2 = hashSet.contains("a2");

        System.out.println(hashSet);

        HashMap<Integer, Integer> map = new HashMap<>();
        Integer put = map.put(1, 1);
        Integer put1 = map.put(2, 4);
        Integer put2 = map.put(2, 2);

        HashSet<Student> set = new HashSet<>();
        Student student5 = new Student("张示", "200000", "男", 22, new Date());
        Student student1 = new Student("张示", "200000", "男", 23, new Date());
        Student student2 = new Student("李示", "200000", "男", 24, new Date());
        Student student3 = new Student("李示", "200000", "男", 25, new Date());
        Student student4 = new Student("展示", "200000", "男", 26, new Date());

        Collections.addAll(set, student5, student1, student2, student3, student4);
        HashMap<String, Integer> map1 = new HashMap<>();
        Integer num = 1;
        int i = 1;
        //?
        var student6 = new Student();
        for (Student student : set) {
            //如果第一次出现,则num=null,否则返回上一次value值
            num = map1.put(student.getName(), i);//如果key值未存在,则返回null;否则,返回上一个key
            if (num != null) {//不是第一次
                map1.put(student.getName(), ++num);
            }
            System.out.println(map1);
        }
        System.out.println(map1);

        //测试clone方法复制的对象是否为新对象
        HashSet clone = (HashSet) set.clone();
        HashSet<Student> set1 = set;
        System.out.println(set);
        System.out.println(clone);

    }

}
