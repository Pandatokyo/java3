package src.chat;

import java.util.Objects;

record User(String login, String password, String nickname) {

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (User) obj;
        return Objects.equals(this.login, that.login) &&
                Objects.equals(this.password, that.password) &&
                Objects.equals(this.nickname, that.nickname);
    }

    @Override
    public String toString() {
        return  "login:" + login + ", " +
                "pass:" + password + ", " +
                "nickname:" + nickname;
    }
}