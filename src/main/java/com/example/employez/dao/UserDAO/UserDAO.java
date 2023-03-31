package com.example.employez.dao.UserDAO;

import com.example.employez.domain.entity_class.User;

public interface UserDAO {
    User getByMail(String mail);
}
