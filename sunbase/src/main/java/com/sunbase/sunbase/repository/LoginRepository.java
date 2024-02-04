package com.sunbase.sunbase.repository;

import com.sunbase.sunbase.entity.Login;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

@Repository
public interface LoginRepository extends JpaRepository<Login,String> {
}
