package com.example.seguranca.repository;

import com.example.seguranca.modal.Application;
import com.example.seguranca.modal.ControlProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationRepository extends IGenericRepository<Application> {

    @Query(nativeQuery = true,
        value = "select app.* from security.tb_control_profile cp " +
                "   inner join security.tb_application app " +
                "       on cp.id_application = app.id " +
                "           and cp.id_user = :user and app.initials <> 'SEC'")
    List<Application> findByUserNonRoot(@Param("user") Long user);

}
