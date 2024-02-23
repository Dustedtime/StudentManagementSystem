package studentSystem;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class StudentSystem {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        // 创建用户集合
        ArrayList<User> user = new ArrayList<>();
        // 创建学生集合（所有用户共同拥有同一个学生集合）
        ArrayList<Student> stu = new ArrayList<>();
        boolean flag = true;
        // 进入登录界面
        while (flag) {
            for (User user1 : user) {
                System.out.println(user1.getName());
            }
            System.out.println("欢迎来到学生管理系统！");
            System.out.println("请选择操作：");
            System.out.println("1.用户登录");
            System.out.println("2.用户注册");
            System.out.println("3.忘记密码");
            System.out.println("4.退出系统");
            String choice = sc.next();
            switch (choice) {
                case "1" -> signIn(user, stu, sc);
                case "2" -> signUp(user, sc);
                case "3" -> forgetPassword(user, sc);
                case "4" -> flag = false;
                default -> System.out.println("没有这个选项！");
            }
        }
    }

    // 用户登录
    public static void signIn(ArrayList<User> user, ArrayList<Student> stu, Scanner sc) {
        // 用户名
        String username;
        // 用户密码
        String password;
        // 输入的验证码
        String codeInput;
        while (true) {
            System.out.print("请输入用户名：");
            username = sc.next();
            System.out.print("请输入密码：");
            password = sc.next();
            // 随机生成验证码
            String codeRandom = randomCode();
            System.out.println("验证码为" + codeRandom);
            System.out.print("请输入验证码：");
            codeInput = sc.next();
            // 获取用户在集合中的索引
            int userIndex = getIndexInUser(user, username);
            if (userIndex == -1) {
                // 用户名不存在
                System.out.println("该用户名未注册，请先注册！");
                break;
            } else if (!codeRandom.equals(codeInput)) {
                // 验证码错误，提示用户重新输入信息
                System.out.println("验证码错误，请重新输入登录信息！");
                continue;
            } else if (user.get(userIndex).getLimitation() == 0) {
                // 用户账号已被锁定
                System.out.println("该用户账号已被锁定，请先使用忘记密码功能解除锁定再进行登录！");
                break;
            } else if (!user.get(userIndex).getPassword().equals(password)) {
                // 用户输入的密码错误，最大允许尝试次数减一
                int limitation = user.get(userIndex).getLimitation();
                user.get(userIndex).setLimitation(--limitation);
                // 提示用户
                if (limitation > 0) {
                    System.out.printf("用户名或密码错误，该用户还剩下%d次机会！\n", limitation);
                } else {
                    System.out.println("用户名或密码错误，该用户账号被锁定！");
                    break;
                }
            } else {
                // 用户输入的密码正确，进入学生管理系统界面
                System.out.println("登录成功！");
                studentManagementSystem(stu, sc);
                // 重置用户允许输错密码次数
                user.get(userIndex).setLimitation(3);
                break;
            }
        }
    }

    // 随机生成验证码
    public static String randomCode() {
        Random r = new Random();
        // 生成验证码中的数字
        int number = r.nextInt(10);
        // 生成验证码中的字母
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            int numberRandom = r.nextInt(52);
            if (numberRandom < 26) {
                code.append((char)('a' + numberRandom));
            } else {
                code.append((char)('A' + numberRandom - 26));
            }
        }
        String letter = code.toString();
        // 随机生成数字在验证码中的位置
        int position = r.nextInt(5);
        // 组合生成最终的验证码并返回
        return letter.substring(0, position) + number + letter.substring(position);
    }

    // 获取用户名在用户集合中的索引值
    public static int getIndexInUser(ArrayList<User> user, String name) {
        for (int i = 0; i < user.size(); i++) {
            if (user.get(i).getName().equals(name)) {
                // 找到用户名，返回该索引值
                return i;
            }
        }
        // 用户集合中没有找到该用户名，返回-1
        return -1;
    }

    // 用户注册
    public static void signUp(ArrayList<User> user, Scanner sc) {
        String name;
        // 用户输入要注册的用户名，并检测其合法性
        do {
            System.out.print("请输入注册的用户名：");
            name = sc.next();
        } while (!checkUsername(user, name));
        // 用户输入用户密码并确认密码
        String password = enterAndEnsurePassword(sc);
        // 用户输入身份证号码并检验身份证号码合法性
        String ID;
        while (true) {
            System.out.println("请输入身份证号码：");
            ID = sc.next();
            if (checkID(ID)) {
                // 输入的身份证号码合法，退出循环
                break;
            } else {
                System.out.println("输入的身份证号码不合法，请重新输入！");
            }
        }
        // 用户输入手机号码并检验手机号码合法性
        String phoneNumber;
        while (true) {
            System.out.println("请输入手机号码：");
            phoneNumber = sc.next();
            if (checkPhoneNumber(phoneNumber)) {
                // 输入的手机号码合法，退出循环
                break;
            } else {
                System.out.println("输入的手机号码不合法，请重新输入！");
            }
        }
        // 将新注册的用户添加到用户集合中，并打印提示信息
        user.add(new User(name, password, ID, phoneNumber));
        System.out.println("用户注册成功！");
    }

    // 检查用户要注册的用户名是否合法
    public static boolean checkUsername(ArrayList<User> user, String name) {
        if (getIndexInUser(user, name) >= 0) {
            // 查询用户名是否已存在
            System.out.println("该用户名已存在，请重新输入！");
            return false;
        } else if (name.length() < 3 || name.length() > 15) {
            // 检查用户名长度
            System.out.println("该用户名长度不在3-15之间，请重新输入！");
            return false;
        }
        // 检查用户名中字母和数字的数量
        int letterCount = 0;
        int numberCount = 0;
        for (int i = 0; i < name.length(); i++) {
            if (name.charAt(i) >= '0' && name.charAt(i) <= '9') {
                numberCount++;
            } else if ((name.charAt(i) >= 'a' && name.charAt(i) <= 'z') ||
                    (name.charAt(i) >= 'A' && name.charAt(i) <= 'Z')) {
                letterCount++;
            }
        }
        if (letterCount + numberCount != name.length()) {
            // 检查用户名是否只包含字母和数字
            System.out.println("用户名必须有字母和数字组成，请重新输入！");
            return false;
        } else if (letterCount == 0) {
            // 检查用户名是否仅包含数字
            System.out.println("用户名不能只由数字组成，请重新输入！");
            return false;
        }
        return true;
    }

    // 获取用户输入并确认的密码
    public static String enterAndEnsurePassword(Scanner sc) {
        String password;
        String passwordCheck;
        while (true) {
            System.out.println("请输入密码：");
            password = sc.next();
            System.out.println("请确认密码：");
            passwordCheck = sc.next();
            if (password.equals(passwordCheck)) {
                // 两次输入的密码一致，退出循环
                break;
            } else {
                System.out.println("两次输入的密码不相同，请重新输入密码！");
            }
        }
        return password;
    }

    // 检查用户输入的身份证号码合法性
    public static boolean checkID(String ID) {
        if (ID.length() != 18 || ID.charAt(0) == '0') {
            // 身份证号码长度必须为18且首位不能为0
            return false;
        }
        // 计算身份证号码中数字的个数
        int numberCount = 0;
        for (int i = 0; i < ID.length(); i++) {
            if (ID.charAt(i) >= '0' && ID.charAt(i) <= '9') {
                numberCount++;
            }
        }
        if (numberCount == 18) {
            // 身份证号码全是数字，合法
            return true;
        } else if (numberCount == 17 && (ID.charAt(17) == 'x' || ID.charAt(17) == 'X')) {
            // 身份证最后一位为x或X，其余为数字，合法
            return true;
        } else {
            // 其余为不合法
            return false;
        }
    }

    // 检查用户输入的手机号码合法性
    public static boolean checkPhoneNumber(String phoneNumber) {
        if (phoneNumber.length() != 11 || phoneNumber.charAt(0) == '0') {
            // 手机号码长度必须为11且首位不能为0
            return false;
        }
        // 计算手机号码中数字的个数
        int numberCount = 0;
        for (int i = 0; i < phoneNumber.length(); i++) {
            if (phoneNumber.charAt(i) >= '0' && phoneNumber.charAt(i) <= '9') {
                numberCount++;
            }
        }
        // 返回数字个数是否等于11的判断结果
        return numberCount == 11;
    }

    // 忘记密码
    public static void forgetPassword(ArrayList<User> user, Scanner sc) {
        // 输入用户名
        System.out.print("请输入要忘记密码的用户名：");
        String username = sc.next();
        // 获取该用户名在集合中的索引
        int userIndex = getIndexInUser(user, username);
        if (userIndex == -1) {
            System.out.println("该用户名不存在！");
            return;
        }
        // 获取该用户的身份证号码以及手机号码
        String userID = user.get(userIndex).getID();
        String userPhoneNumber = user.get(userIndex).getPhoneNumber();
        // 输入用户身份证号以及手机号
        System.out.print("请输入该用户的身份证号码：");
        String ID = sc.next();
        System.out.print("请输入该用户的手机号码：");
        String phoneNumber = sc.next();
        if (userID.equals(ID) && userPhoneNumber.equals(phoneNumber)) {
            // 身份证号以及手机号正确，用户修改密码
            String newPassword = enterAndEnsurePassword(sc);
            user.get(userIndex).setPassword(newPassword);
            // 重置用户允许输错密码次数
            user.get(userIndex).setLimitation(3);
        } else {
            // 身份证号码或手机号不正确，提示用户
            System.out.println("账号信息不匹配，修改失败！");
        }
    }

    // 学生管理系统界面
    public static void studentManagementSystem(ArrayList<Student> stu, Scanner sc) {
        boolean flag = true;
        while (flag) {
            System.out.println("-------------欢迎来到黑马学生管理系统----------------");
            System.out.println("1: 添加学生");
            System.out.println("2: 删除学生");
            System.out.println("3: 修改学生");
            System.out.println("4: 查询学生");
            System.out.println("5: 退出");
            System.out.print("请输入您的选择: ");
            int choice = sc.nextInt();
            switch (choice) {
                // 添加学生信息
                case 1 -> addStudentInfo(stu, sc);
                // 删除学生信息
                case 2 -> deleteStudentInfo(stu, sc);
                // 修改学生信息
                case 3 -> changeStudentInfo(stu, sc);
                // 查询学生信息
                case 4 -> showStudentInfo(stu);
                // 修改flag为false，表示即将退出循环
                case 5 -> flag = false;
                default -> System.out.println("没有这个选项，请重新输入！");
            }
        }
    }

    // 添加学生信息
    public static void addStudentInfo(ArrayList<Student> stu, Scanner sc) {
        System.out.print("请输入新增学生的id: ");
        String id = sc.next();
        // 检查输入的学生id是否已存在
        if (getIndexInStudent(stu, id) != -1) {
            System.out.println("新增学生id已存在，添加失败！");
            return;
        }
        // 输入学生其他信息
        System.out.print("请输入新增学生的姓名: ");
        String name = sc.next();
        System.out.print("请输入新增学生的年龄: ");
        int age = sc.nextInt();
        System.out.print("请输入新增学生的家庭住址: ");
        String address = sc.next();
        // 将该学生信息添加进集合中
        stu.add(new Student(id, name, age, address));
        System.out.println("添加成功！");
    }

    // 获取学生在集合中的索引
    public static int getIndexInStudent(ArrayList<Student> stu, String id) {
        for (int i = 0; i < stu.size(); i++) {
            if (stu.get(i).getId().equals(id)) {
                return i;
            }
        }
        return -1;
    }

    // 删除学生信息
    public static void deleteStudentInfo(ArrayList<Student> stu, Scanner sc) {
        System.out.print("请输入要删除的学生的id: ");
        String id = sc.next();
        // 获取要删除的学生在集合中的索引
        int index = getIndexInStudent(stu, id);
        if (index == -1) {
            System.out.println("要删除的学生不存在，删除失败！");
        } else {
            // 在集合中删除该学生
            stu.remove(index);
            System.out.println("删除成功！");
        }
    }

    // 修改学生信息
    public static void changeStudentInfo(ArrayList<Student> stu, Scanner sc) {
        System.out.print("请输入要修改信息的学生的id: ");
        String id = sc.next();
        // 获取要修改的学生在集合中的索引
        int index = getIndexInStudent(stu, id);
        if (index == -1) {
            System.out.println("要修改信息的学生不存在，修改失败！");
        } else {
            // 输入修改后的该学生其他信息，并修改
            System.out.print("请输入修改后的学生姓名: ");
            stu.get(index).setName(sc.next());
            System.out.print("请输入修改后的学生年龄: ");
            stu.get(index).setAge(sc.nextInt());
            System.out.print("请输入修改后的学生家庭住址: ");
            stu.get(index).setAddress(sc.next());
            System.out.println("修改成功！");
        }
    }

    // 查询学生信息并展示
    public static void showStudentInfo(ArrayList<Student> stu) {
        if (stu.isEmpty()) {
            System.out.println("当前无学生信息，请添加后再查询！");
        } else {
            // 打印所有学生信息
            System.out.println("id\t\t\t姓名\t\t年龄\t\t家庭住址");
            for (Student student : stu) {
                System.out.printf("%s\t\t\t%s\t\t%d\t\t%s\n", student.getId(),
                        student.getName(), student.getAge(), student.getAddress());
            }
        }
    }
}
