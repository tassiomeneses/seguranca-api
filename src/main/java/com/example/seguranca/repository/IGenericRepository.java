package com.example.seguranca.repository;


import com.example.seguranca.modal.AbstractModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface IGenericRepository<T extends AbstractModel> extends JpaRepository<T, Long> {
}
