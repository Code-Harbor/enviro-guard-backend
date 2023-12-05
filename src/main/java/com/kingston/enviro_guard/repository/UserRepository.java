package com.kingston.enviro_guard.repository;

import com.kingston.enviro_guard.model.UserBean;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Sanjaya Sandakelum
 * @since 2023-12-05
 * TSTPL
 */

@Repository
@Transactional
public interface UserRepository extends JpaRepository<UserBean, Integer> {

}
