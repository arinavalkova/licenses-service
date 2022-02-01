package ru.shift.baldezh.licenses.service.repository.model;

import javax.persistence.*;

@Entity
@Table(name = "user_info")
public class UserInfoEntity {

    @Id
    @GeneratedValue
    private long userId;

    @Column(name = "mail", nullable = false)
    private String mail;

    @ManyToOne
    @JoinColumn(name = "type", nullable = false)
    private UserTypeEntity type;
}
