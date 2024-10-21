package com.itclopedia.cources.dao.user;

import com.itclopedia.cources.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByName(String name);

    @Modifying
    @Query("UPDATE User u SET u.password = :password WHERE u.id = :id")
    void updateUserPassword(@Param("id") int id, @Param("password") String password);

    @Modifying
    @Query("UPDATE User u SET u.email = :email WHERE u.id = :id")
    void updateUserEmail(int id, String email);

    @Modifying
    @Query("UPDATE User u SET u.name = :name WHERE u.id = :id")
    void updateUserName(int id, String name);

}
