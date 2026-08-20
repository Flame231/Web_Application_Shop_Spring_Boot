package org.example.webApplicationShopSpringBoot.repository.user;


/*
@Repository
@Qualifier("UserDAOImpl")
public class UserDAOImpl extends DAOImpl<User> implements UserRepository {

    public UserDAOImpl() {
        super(User.class);
    }

    @Override
    public User findUser(String login) throws NoResultException{
        getEm().clear();
        User user = null;
            user = getEm().createQuery("from User user where user.login =: login", User.class)
                    .setParameter("login", login).getSingleResult();
        return user;
    }
}*/
