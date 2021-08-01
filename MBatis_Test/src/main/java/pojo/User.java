package pojo;

/**
 * @description：TODO
 * @Author MRyan
 * @Date 2021/7/31 23:06
 * @Version 1.0
 */
public class User {

    private Integer id;

    private String username;

    public User() {
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
