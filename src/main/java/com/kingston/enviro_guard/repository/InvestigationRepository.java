package com.kingston.enviro_guard.repository;

import com.kingston.enviro_guard.model.InvestigationBean;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Sanjaya Sandakelum
 * @since 2023-12-19
 * TSTPL
 */

@Repository
@Transactional
public interface InvestigationRepository extends JpaRepository<InvestigationBean, Integer> {

    Optional<InvestigationBean> findByComplaintId(Integer complaintId);
}
