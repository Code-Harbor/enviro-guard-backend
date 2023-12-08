package com.kingston.enviro_guard.repository;

import com.kingston.enviro_guard.model.InstituteBean;
import com.kingston.enviro_guard.model.UserBean;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Sanjaya Sandakelum
 * @since 2023-12-07
 * TSTPL
 */

public interface InstitureRepository extends JpaRepository<InstituteBean, Integer> {

}
