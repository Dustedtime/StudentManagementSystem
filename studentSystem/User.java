package studentSystem;

public class User {
    // 用户名
    private String name;
    // 用户密码
    private String password;
    // 用户身份证号
    private String ID;
    // 用户手机号码
    private String phoneNumber;
    // 用户账号允许输错密码次数限制，初始化为3，减为0时表示用户账号被锁定
    private int limitation = 3;

    // 空参构造
    public User() {
    }

    // 有参构造
    public User(String name, String password, String ID, String phoneNumber) {
        this.name = name;
        this.password = password;
        this.ID = ID;
        this.phoneNumber = phoneNumber;
    }

    // 获取用户名
    public String getName() {
        return name;
    }

    // 设置用户名
    public void setName(String name) {
        this.name = name;
    }

    // 获取用户密码
    public String getPassword() {
        return password;
    }

    // 设置用户密码
    public void setPassword(String password) {
        this.password = password;
    }

    // 获取用户身份证号码
    public String getID() {
        return ID;
    }

    // 设置用户身份证号码
    public void setID(String ID) {
        this.ID = ID;
    }

    // 获取用户手机号码
    public String getPhoneNumber() {
        return phoneNumber;
    }

    // 设置用户手机号码
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    // 获取用户锁定状态
    public int getLimitation() {
        return this.limitation;
    }

    // 设置用户锁定状态
    public void setLimitation(int limitation) {
        this.limitation = limitation;
    }
}
