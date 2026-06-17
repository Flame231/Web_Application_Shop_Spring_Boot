package org.example.webApplicationShopSpringBoot.dao.user;

import org.example.webApplicationShopSpringBoot.dao.DAO;
import org.example.webApplicationShopSpringBoot.model.Discount;
import org.example.webApplicationShopSpringBoot.model.UserOrder.UserOrder;
import org.example.webApplicationShopSpringBoot.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface UserRepository extends DAO<User> {

    User findUser(String login);
}
