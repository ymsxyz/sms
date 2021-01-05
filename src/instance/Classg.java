package instance;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;

/**
 * 班级实体类,包含两个私有成员属性,班级名称和班级学生集合,重写了Object的toString和hashCode方法.
 *
 * @author ymsxyz
 * @version 1.0
 * @see java.io.Serializable
 */
public class Classg implements Serializable {

    private String name;//名称
    private HashSet<Student> classg;//学生集合  注意:成员变量必须初始化,否则为null,而非空集合[]

    public Classg() {
    }

    public Classg(String name) {
        this.name = name;
    }

    public Classg(String name, HashSet<Student> classg) {
        this.name = name;
        this.classg = classg;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashSet<Student> getClassg() {
        return classg;
    }

    public void setClassg(HashSet<Student> classg) {
        this.classg = classg;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Classg classg1 = (Classg) o;
        return Objects.equals(name, classg1.name) &&
                Objects.equals(classg, classg1.classg);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, classg);
    }

    @Override
    public String toString() {
        return "Classg{" +
                "name='" + name + '\'' +
                ", classg=" + classg +
                '}';
    }

}
