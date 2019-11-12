package com.example.seguranca.repository;

import com.example.seguranca.modal.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends IGenericRepository<User> {

    Optional<User> findByEmail(String email);

    @Query(nativeQuery = true,
        value = "select distinct u.id from security.tb_control_profile cp " +
                "    inner join security.tb_profile p on cp.id_profile = p.id " +
                "         and cp.id_user = :user and p.key = :role" +
                "    inner join security.tb_control_profile cp2 on cp2.id_application = cp.id_application " +
                "    inner join security.tb_user u on cp2.id_user = u.id ")
    List<Long> findBy(@Param("user") Long idUser, @Param("role") String role);

    @Query("SELECT user FROM User user WHERE user.id IN :ids")
    Page<User> findByIdList(@Param("ids") List<Long> ids, Pageable pageable);

    @Query(nativeQuery = true,
        value = "select u.* from security.tb_control_profile cp" +
                "   inner join security.tb_user u on cp.id_user = u.id" +
                "       and cp.id_application = :app")
    List<User> findByApplication(@Param("app") Long application);
}
