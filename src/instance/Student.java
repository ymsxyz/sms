package instance;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * 学生实体类,含5个私有成员属性,重写了Object的equal和hashCode方法,实现了Serializable接口
 *
 * @see java.io.Serializable
 * @author ymsxyz
 * @version 1.0
 */
public class Student implements Serializable {

    private String name;//姓名
    private String id;//学号（格式：yyyy-xxxx == 入学年份-随机数(不能重复)）
    private String sex;//性别
    private Integer age;//年龄
    private Date birthday;//出生日期Date（格式：yyyy-MM-dd）

    public Student() {
    }

    public Student(String name, String id, String sex, Integer age, Date birthday) {

        this.name = name;
        this.id = id;
        this.sex = sex;
        this.age = age;
        this.birthday = birthday;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(name, student.name) &&
                Objects.equals(id, student.id) &&
                Objects.equals(sex, student.sex) &&
                Objects.equals(age, student.age) &&
                Objects.equals(birthday, student.birthday);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, id, sex, age, birthday);
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", sex='" + sex + '\'' +
                ", age=" + age +
                ", birthday=" + birthday +
                '}';
    }
}
