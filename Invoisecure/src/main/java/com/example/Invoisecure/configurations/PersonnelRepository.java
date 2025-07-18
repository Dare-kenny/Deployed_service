package com.example.Invoisecure.configurations;

import com.example.Invoisecure.models.Personnel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonnelRepository extends JpaRepository<Personnel,Long>{
    Personnel findByUsername(String username);
}
