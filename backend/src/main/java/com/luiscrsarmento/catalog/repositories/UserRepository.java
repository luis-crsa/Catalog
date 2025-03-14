package com.luiscrsarmento.catalog.repositories;

import com.luiscrsarmento.catalog.entities.User;
import com.luiscrsarmento.catalog.projections.UserDetailsProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    @Query(nativeQuery = true, value = """
            SELECT tb_user.email AS username, tb_user.password, td_role.id AS roleId, tb_role.authority
            FROM tb_user
            INNER JOIN tb_user_role ON td_user.id = tb_user_role.user_id
            INNER JOIN tb_role ON tb_role.id = tb_user_role.role_id
            WHERE tb_user.email =:email
            """)
    List<UserDetailsProjection> searchUserAndRolesByEmail(String email);
}
