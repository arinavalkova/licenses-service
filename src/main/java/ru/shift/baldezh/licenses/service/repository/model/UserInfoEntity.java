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
}
