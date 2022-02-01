package ru.shift.baldezh.licenses.service.repository;

import org.springframework.data.repository.Repository;
import ru.shift.baldezh.licenses.service.repository.model.UserInfoEntity;

public interface UserRepository extends Repository<UserInfoEntity, Long> {
}
