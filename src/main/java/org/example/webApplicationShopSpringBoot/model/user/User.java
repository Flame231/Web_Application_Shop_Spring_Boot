package org.example.webApplicationShopSpringBoot.model.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import jakarta.persistence.*;
import org.example.webApplicationShopSpringBoot.model.Bag;
import org.example.webApplicationShopSpringBoot.model.Discount;
import org.example.webApplicationShopSpringBoot.model.OrderPoint;
import org.example.webApplicationShopSpringBoot.model.userOrder.UserOrder;
import org.example.webApplicationShopSpringBoot.model.additional.DataEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name = "User")
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class User extends DataEntity implements UserDetails {

    @Column
    private String name;

    @Column(unique = true, nullable = false)
    private String login;

    @Column
    private String passwordHash;

    @Column(nullable = false)
    private LocalDate birthday;

    @Column
    private String paymentMethods;

    @Column
    private BigDecimal sumOfPurchases;

    @OneToMany(mappedBy = "user")
    private Set<UserOrder> userOrders;

    @ManyToOne
    @JoinColumn(name = "discount_id")
    private Discount discount;

    @OneToMany(mappedBy = "user")
    private Set<Bag> bags = new HashSet<>();

    @Column(updatable = false)
    @Enumerated(EnumType.STRING)
    private Role role = Role.CLIENT;

    @ManyToOne
    @JoinColumn(name = "orderPoint_id")
    private OrderPoint orderPoint;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    @Override
    public String getPassword() {
        return passwordHash;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        return "User{" +
                "sumOfPurchases=" + sumOfPurchases +
                ", paymentMethods='" + paymentMethods + '\'' +
                ", birthday=" + birthday +
                ", passwordHash='" + passwordHash + '\'' +
                ", login='" + login + '\'' +
                ", name='" + name + '\'' +
                ", role=" + role +
                '}';
    }
}
