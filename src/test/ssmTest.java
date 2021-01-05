package test;

import instance.Classg;
import instance.Student;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * ssm测试类,模拟真实的ssm操作流程
 *
 * 利用run窗口模拟界面,通过0-10数字的输入模拟对应按钮.
 * 1.添加班级
 * 2.查看所有班级（遍历打印）
 * 3.输入名称查询班级
 * 4.创建一个学生对象
 * 5.输入班级名称查询班级所有学生
 * 6.输入班级名称和学生姓名查询学生
 * 7.输入班级名称和学生姓名删除学生
 * 9.输入班级名称获取学生各姓氏个数
 * 10.输入年份，统计全校该年份入学的人数
 * 0.退出
 * 每次运行程序时会反序列化的学校(school),正常退出(通过界面输入0并回车)则序列化更新school到文件"resource\school.txt"中
 * <p>
 * 包含四个私有静态成员变量
 *
 * @version 1.0
 */
public class ssmTest {

    private static ArrayList<Classg> school = new ArrayList<>();//学校:唯一,只有本包可访问
    private static Scanner scanner = new Scanner(System.in);//文本扫描器 唯一
    private static Random random = new Random();//随机生成器
    private static HashSet<String> idSet = new HashSet<>();//学号集合 注意:需初始化,否则为null,调用方法空指针异常

    public static void main(String[] args) {
        // 反序列化"resource\school.txt"获取学校
        getSchoolByResource();
        /**
         * 使用死循环+ switch用数字录入1-9模拟选择按钮
         */
        String i = "";//模拟按钮
        while (true) {
            boolean flag = true;
            while (flag) {//必须输入正确序号,否则循环

                //模拟界面
                System.out.println("=================================");
                System.out.println("1.添加班级");
                System.out.println("2.查看所有班级（遍历打印）");
                System.out.println("3.输入名称查询班级");
                System.out.println("4.创建一个学生对象");
                System.out.println("5.输入班级名称查询班级所有学生");
                System.out.println("6.输入班级名称和学生姓名查询学生");
                System.out.println("7.输入班级名称和学生姓名删除学生");
                System.out.println("9.输入班级名称获取学生各姓氏个数");
                System.out.println("10.输入年份，统计全校该年份入学的人数");
                System.out.println("0.退出");
                System.out.println("请输入对应序号:");
                //System.out.println(school);//测试用
                i = scanner.next();//获取输入字符串,执行相应操作(模拟主菜单按钮)
                String[] iArray = {"0", "1", "2", "3", "4", "5", "6", "7", "9", "10"};
                for (String i1 : iArray) {
                    if (i1.equals(i)) {//如果i1在数组中(模拟按钮),则跳出循环;否则死循环
                        flag = false;
                    }
                }
                if (flag == true) {//输入字符串序号错误,提示用户并重新循环回到主菜单
                    System.out.println("输入序号" + i + "有误,请重新输入!");
                }
            }
            if (i.equals("0")) {//若输入0,则序列化学校到"resource\school.txt",然后跳出主菜单死循环退出程序
                saveSchoolToROM();//序列化学校
                break;//退出程序
            }
            /**
             *功能实现
             *  1.添加班级
             *  2.查看所有班级（遍历打印）
             *  3.输入名称查询班级
             *  4.创建一个学生对象
             *  5.输入班级名称查询班级所有学生
             *  6.输入班级名称和学生姓名查询学生
             *  7.输入班级名称和学生姓名删除学生
             *  9.输入班级名称获取学生各姓氏个数
             *  10.输入年份，统计全校该年份入学的人数
             */
            switch (i) {
                case "1":
                    addClassg();
                    break;//跳出当前循环
                case "2":
                    checkSchool();
                    break;
                case "3":
                    checkClassgByClassgName();
                    break;
                case "4":
                    addAStudent();
                    break;
                case "5":
                    checkStudentByClassgName();
                    break;
                case "6":
                    checkStudentByClassgNameAndStudentName();
                    break;
                case "7":
                    deleteAStudentByClassgNameAndStuName();
                    break;
                case "9":
                    statisticsFirstNameByClassgName();
                    break;
                case "10":
                    statisticsNumByYear();
                    break;
            }
        }
    }

    /**
     * 封装常用方法,方便调用
     */
    /**
     * a.根据班级名在学校查找班级,然后打印班级信息并返回班级类;若班级名字不存在则返回null
     *
     * @param classgName
     * @return Classg or null
     */
    private static Classg getClassgByClassgName(String classgName) {
        for (Classg classg : school) {
            if (classg.getName().equals(classgName)) {
                return classg;
            }
        }
        return null;
    }

    /**
     * b.根据学生姓名在班级查找学生并返回该学生;若不存在则返回null
     *
     * @param studentName
     * @param classg
     * @return Student or null
     */
    private static Student getStudentByNameFromClassg(String studentName, Classg classg) {
        for (Student student : classg.getClassg()) {
            if (student.getName().equals(studentName)) {
                return student;
            }
        }
        return null;
    }

    /**
     * 功能实现方法1-10,所有方法无参,参数通过方法内部scanner对象next方法获取,以保证代码整洁
     */
    /**
     * 1.通过班级名添加班级到学校,注意:班级成员学生集合的初始化
     */
    private static void addClassg() {
        System.out.println("请输入要创建的班级名:");
        String classgName = scanner.next();//程序必须停止等输入再运行
        school.add(new Classg(classgName, new HashSet<Student>()));//成员变量初始化,否则后面为null,会异常
    }

    /**
     * 2.查看所有班级（遍历打印）
     */
    private static void checkSchool() {
        for (Classg classg : school) {
            System.out.println(classg);
        }
    }

    /**
     * 3.根据名称查询班级并打印
     */
    private static void checkClassgByClassgName() {
        System.out.println("请输入要查找的班级名:");
        String classgName = scanner.next();
        Classg classg = getClassgByClassgName(classgName);
        System.out.println(classg);
    }

    /**
     * 4.接收用户数据,利用学生成员属性生成一个学生,利用班级名调用通用方法a:getClassgByClassgName(String classgName)获取该班级类实体,将该名学生加入班级中
     * private String name;//姓名
     * private String id;//学号（格式：yyyy-xxxx == 入学年份-随机数）
     * private String sex;//性别
     * private Integer age;//年龄
     * private Date birthday;//出生日期Date（格式：yyyy-MM-dd）
     *
     * @throws NumberFormatException 若输入年龄不是数字
     * @throws ParseException        若输入生日Date(yyyy-MM-dd)格式错误
     */
    private static void addAStudent() {
        System.out.println("请输入姓名:");
        String name = scanner.next();//获取姓名
        /**
         * 学号（格式：yyyy-xxxx == 入学年份-随机数 不能重复?利用系统时间
         * 分析:最多9999个不重
         *      相同:
         *          1.一定同一年入学,如2020-
         *
         * 方法1:同一个Random固有方法不生成重复数
         * 方法2:储存已生成数据,对比判断,每年更新...省略
         */
        String id = "";
        //int year = Calendar.getInstance().get(Calendar.YEAR);//获取当前年份--模拟真实
        int year = Calendar.getInstance().get(Calendar.YEAR) - random.nextInt(3);//获取3年内学号--功能设计测试需求
        id += year;
        while (true) {//id后四位随机数若存在,则重新生成
            for (int i = 0; i < 4; i++) {
                int j = random.nextInt(10);
                id += j + "";//问题:000 不显示
            }
            if (!idSet.contains(id)) {//判断若不存在,则放行
                break;
            }
            idSet.add(id.substring(4));//后四位随机数添加入私有静态集合idSet
        }
        System.out.println("请输入性别:");
        String sex = scanner.next();//获取性别
        Integer age = null;
        while (true) {//获取年龄,格式错误则循环
            //若输入正确,则跳出循环
            try {
                System.out.println("请输入年龄:");
                age = Integer.valueOf(scanner.next());//nextInt 和 nextNine两个大坑
                break;
                //若格式错误,则循环继续输入年龄
            } catch (NumberFormatException e) {
                System.out.println("年龄格式不对,重新输入:");
            }
        }
        System.out.println("请输入出生日期：yyyy-MM-dd");
        Date birthdayDate = null;
        while (true) {//输入生日,格式正确则跳出循环,否则重新输入
            String birthday = scanner.next();//字符串转Date格式
            try {
                birthdayDate = new SimpleDateFormat("yyyy-MM-dd").parse(birthday);
                break;//正确跳出死循环
            } catch (ParseException e) {
                System.out.println("格式有误,请重新输入!");
            }
        }
        //创建学生对象
        Student student = new Student(name, id, sex, age, birthdayDate);

        //调试用
        //Student student = new Student("展示", "200000", "男", 22, new Date());

        while (true) {//死循环输入班级名
            System.out.println("请输入班级名:");
            String classgName = scanner.next();
            //班级错误空指针异常循环输入,正确则正常加入
            try {
                Classg classg = getClassgByClassgName(classgName);  //调用方法a查找班级并获取班级实体
                classg.getClassg().add(student);//学生加入班级集合
                break;
            } catch (Exception e) {
                System.out.println("看提示重新输入:");
                checkSchool();//提示用户班级名
            }
        }
    }

    /**
     * 5.调用方法a获得班级实体,遍历学生集合并打印
     */
    private static Student checkStudentByClassgName() {

        System.out.println("请输入班级名:");
        String classgName = scanner.next();
        Classg classg = getClassgByClassgName(classgName);

        Student studentCheck = new Student();
        try {
            for (Student student : classg.getClassg()) {//班级实体null
                studentCheck = student;
                System.out.println(student);
            }
        } catch (Exception e) {
            System.out.println("班级名有误!");//提示班级名错误,并退回主菜单
            return null;
        }
        return studentCheck;//班级名正确,则正常返回
    }

    /**
     * 6.根据班级名称调用方法a返回班级实体,，再通过该班级实体和学生姓名查询学生a2方法返回学生实体,然后打印
     *
     * @return 获取的Student对象
     */

    private static Student checkStudentByClassgNameAndStudentName() {
        System.out.println("请输入班级名:");
        String classgName = scanner.next();
        System.out.println("请输入学生姓名:");
        String studentName = scanner.next();

        Classg classg = getClassgByClassgName(classgName);//classg可能为null,注意空指针异常
        Student student = null;
        try {
            student = getStudentByNameFromClassg(studentName, classg);
        } catch (Exception e) {
            System.out.println("输入有误!请看提示:");
            checkSchool();
            return null;
        }
        System.out.println(student);
        return student;
    }

    /**
     * 7.调用通用方法a获得班级,方法b获得学生, 该班级删除该学生, 并返回该学生
     *
     * @return the deleted Student
     */
    private static Student deleteAStudentByClassgNameAndStuName() {

        System.out.println("请输入要删除学生的班级名:");
        String classgName = scanner.next();
        Classg classg = getClassgByClassgName(classgName);
        if (classg == null) {
            System.out.println("班级不存在!");
            return null;//
        }

        System.out.println("请输入要删除学生的姓名:");
        String studentName = scanner.next();
        Student student = getStudentByNameFromClassg(studentName, classg);
        if (student == null) {
            System.out.println("学生不存在!");
            return null;
        }

        //删除学生  注意:迭代器遍历是不可改动迭代对象-->记录,出去在搞它
        Student studentRemove = new Student();
        HashSet<Student> hashSet = classg.getClassg();//获取包含该学生的班级集合
        for (Student student1 : classg.getClassg()) {
            if (student1.getName().equals(studentName)) {//同名则删除第一个
                studentRemove = student1;//
                classg.setClassg(hashSet);//将新集合设置回班级
            }
        }
        hashSet.remove(studentRemove);
        return student;
    }

    /**
     * 9.调用方法a得到班级,遍历班级集合,用put方法添加,根据班级名称统计当前班级不同姓氏出现的次数
     *   关键:参数的传递
     *
     */
    private static HashMap<String, Integer> statisticsFirstNameByClassgName() {
        System.out.println("请输入班级名:");
        String classgName = scanner.next();//获取输入班级名称
        Classg classg = getClassgByClassgName(classgName);//通过名称获取班级实体
        if (classg == null) {
            System.out.println("班级不存在!");
            return null;//不存在则提示并返回null
        }
        //统计姓氏
        HashMap<String, Integer> map = new HashMap<>();
        Integer num;
        for (Student student : classg.getClassg()) {
            //如果第一次出现,则出现次数赋值1,num=null
            num = map.put(student.getName().substring(0, 1), 1);//put方法:如果key值不存在,则返回null;否则,返回上一个key
            if (num != null) {//不是第一次,则map的put方法将本次的姓对应的value值+1
                map.put(student.getName().substring(0, 1), ++num);
            }
        }
        System.out.println(map);
        return map;
    }

    /**
     * 10 根据输入年份，统计全校该年份入学的人数
     * int statisticsNumByYear()
     * 步骤:遍历学校,班级的班级集合,对每个学生的学号判断并计数--同9
     */
    private static HashMap<String, Integer> statisticsNumByYear() {
        //统计年份:年份("2000"),人数(Integer1)
        HashMap<String, Integer> map = new HashMap<>();
        int i = 1;
        Integer num;
        for (Classg classg : school) {//遍历学校得到每一个班级
            for (Student student : classg.getClassg()) {//遍历班级集合得到每一个学生
                //如果第一次出现,则num=null,否则返回上一次value值
                num = map.put(student.getId().substring(0, 4), i);//如果key值未存在,则返回null;否则,返回上一个key
                if (num != null) {//不是第一次
                    map.put(student.getId().substring(0, 4), ++num);
                }
            }
        }
        System.out.println(map);
        return map;
    }

    /**
     * 11.将学校信息序列化到资源文件夹下的学校文本中,每次更新文件
     */
    private static void saveSchoolToROM() {
        try {
            File file = new File("resource\\school.txt");
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
            oos.writeObject(school);
            oos.close();//要不要到finally?否则会不会出现关闭不了?
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 12.反序列化获取文件中的学校信息
     */
    private static void getSchoolByResource() {
        try {
            File file = new File("resource", "school.txt");
            ObjectInputStream oos = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)));
            try {
                school = (ArrayList<Classg>) oos.readObject();
            } catch (ClassNotFoundException e) {
                System.out.println("无可序列化学校资源!");
            }
            oos.close();//要不要到finally?否则会不会出现关闭不了?
        } catch (IOException e) {
            //e.printStackTrace();  EOF异常
        }
    }

}