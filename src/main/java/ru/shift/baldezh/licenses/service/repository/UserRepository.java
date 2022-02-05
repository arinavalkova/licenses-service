package ru.shift.baldezh.licenses.service.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import ru.shift.baldezh.licenses.service.repository.model.UserInfoEntity;

public interface UserRepository extends CrudRepository<UserInfoEntity, Long>, JpaSpecificationExecutor<UserInfoEntity> {
}
