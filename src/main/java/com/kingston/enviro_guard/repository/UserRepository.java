package com.kingston.enviro_guard.repository;

import com.kingston.enviro_guard.model.UserBean;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Sanjaya Sandakelum
 * @since 2023-12-05
 * TSTPL
 */

@Repository
@Transactional
public interface UserRepository extends JpaRepository<UserBean, Integer> {

    List<UserBean> findByInstituteId(Integer instituteId);

    UserBean findByEmail(String email);

    // Find all users excluding those with a specific type
    List<UserBean> findByTypeNot(String type);

    // Find users based on instituteId and exclude those with a specific type
    List<UserBean> findByInstituteIdAndTypeNot(Integer instituteId, String type);

}
