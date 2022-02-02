package ru.shift.baldezh.licenses.service.repository.model;

import ru.shift.baldezh.licenses.service.model.UserType;

import javax.persistence.*;

@Entity
@Table(name = "user_info")
public class UserInfoEntity {

    @Id
    @GeneratedValue
    private long userId;

    @Column(name = "mail", nullable = false)
    private String mail;

    @Column(name = "type", nullable = false)
    private UserType type;

    @Override
    public String toString() {
        return "UserInfoEntity{" +
                "userId=" + userId +
                ", mail='" + mail + '\'' +
                ", type=" + type +
                '}';
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }
}
