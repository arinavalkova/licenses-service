package ru.shift.baldezh.licenses.service.repository.specification;

import ru.shift.baldezh.licenses.service.repository.model.UserInfoEntity;

import java.util.HashMap;

public class UserSpecification extends EntitySpecification<UserInfoEntity> {
    public UserSpecification(UserInfoEntity userInfo) {
        super(new HashMap<String, Object>() {{
            put("mail", userInfo.getMail());
            put("type", userInfo.getType());
        }});
    }
}
