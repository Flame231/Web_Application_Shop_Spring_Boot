package org.example.webApplicationShopSpringBoot.dao.user;


import jakarta.persistence.NoResultException;
import org.example.webApplicationShopSpringBoot.dao.DAOImpl;
import org.example.webApplicationShopSpringBoot.model.Discount;
import org.example.webApplicationShopSpringBoot.model.UserOrder.UserOrder;
import org.example.webApplicationShopSpringBoot.model.user.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
@Qualifier("UserDAOImpl")
public class UserDAOImpl extends DAOImpl<User> implements UserRepository {


    public UserDAOImpl() {
        super(User.class);
    }

    @Override
    public void addUserOrder(User user, UserOrder userOrder) {
        user.getUserOrders().add(userOrder);
        userOrder.setUser(user);
    }

    @Override
    public void addDiscount(User user, Discount discount) {
        user.setDiscount(discount);
        discount.getUsers().add(user);
    }

    @Override
    public User findUser(String login) throws NoResultException{
        getEm().clear();
        User user = null;
            user = getEm().createQuery("from User user where user.login =: login", User.class)
                    .setParameter("login", login).getSingleResult();
        return user;
    }
}
