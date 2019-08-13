package management.jwt.demo.entity;

/**
 * @Author xiqiuwei
 * @Date Created in 8:42 2019/8/13
 * @Description
 * @Modified By:
 */
public class UserThreadLocal {

    private static ThreadLocal<User> localUser = new ThreadLocal<>();

    public static User getLocalUser() {
        return localUser.get();
    }

    public static void setLocalUser(User user) {
        localUser.set(user);
    }
}
