package com.kingston.enviro_guard.repository;

import com.kingston.enviro_guard.model.ComplainerBean;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface ComplainerRepository extends JpaRepository<ComplainerBean, Integer> {
    ComplainerBean findByEmail(String email);

}
