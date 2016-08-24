package model;

public class User {
    private final String userId;
    private final String userToken;

    public User(String userId, String userToken) {
        this.userId = userId;
        this.userToken = userToken;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserToken() {
        return userToken;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User that = (User) o;
        if (!userId.equals(that.userId)) return false;
        return true;
    }

    @Override
    public int hashCode() {
        return userId.hashCode();
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", userToken='" + userToken + '\'' +
                '}';
    }
}
