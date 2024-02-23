package studentSystem;

public class Student {
    // 学生ID
    private String id;
    // 学生姓名
    private String name;
    // 学生年龄
    private int age;
    // 学生家庭住址
    private String address;

    // 空参构造
    public Student() {
    }

    // 有参构造
    public Student(String id, String name, int age, String address) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.address = address;
    }

    // 获取学生ID
    public String getId() {
        return id;
    }

    // 设置学生ID
    public void setId(String id) {
        this.id = id;
    }

    // 获取学生姓名
    public String getName() {
        return name;
    }

    // 设置学生姓名
    public void setName(String name) {
        this.name = name;
    }

    // 获取学生年龄
    public int getAge() {
        return age;
    }

    // 设置学生年龄
    public void setAge(int age) {
        this.age = age;
    }

    // 获取学生家庭住址
    public String getAddress() {
        return address;
    }

    // 设置学生家庭住址
    public void setAddress(String address) {
        this.address = address;
    }
}
