package com.example.seguranca.repository;


import com.example.seguranca.modal.ControlProfile;
import com.example.seguranca.modal.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ControlProfileRepository  extends IGenericRepository<ControlProfile>  {

    @Query(nativeQuery = true, value = "select cp.* from security.tb_control_profile cp where cp.id_user = :user")
    List<ControlProfile> findByUser(@Param("user") Long user);

    @Query(nativeQuery = true, value = "select cp.id from security.tb_control_profile cp where cp.id_user = :user")
    List<Long> getByUser(@Param("user") Long user);

    @Query(nativeQuery = true,
        value = "select cp.* from security.tb_control_profile cp " +
                "   where cp.id_user = :user and cp.id_application = :app")
    List<ControlProfile> findByUserAndApplication(@Param("user") Long user, @Param("app") Long app);

    @Modifying
    @Query(nativeQuery = true, value = "delete from security.tb_control_profile where id in :ids")
    void deleteByIdIn(List<Long> ids);

}
