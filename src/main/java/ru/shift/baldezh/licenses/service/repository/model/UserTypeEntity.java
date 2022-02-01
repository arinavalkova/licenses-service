package ru.shift.baldezh.licenses.service.repository.model;

import javax.persistence.*;

@Entity
@Table(name = "user_type")
public class UserTypeEntity {

    @Id
    @GeneratedValue
    private long typeId;

    @Column(name = "type", nullable = false)
    private String type;
}
