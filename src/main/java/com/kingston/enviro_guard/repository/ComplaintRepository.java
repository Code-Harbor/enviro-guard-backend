package com.kingston.enviro_guard.repository;


import com.kingston.enviro_guard.model.ComplaintBean;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Sanjaya Sandakelum
 * @since 2023-12-18
 * TSTPL
 */

@Repository
@Transactional
public interface ComplaintRepository extends JpaRepository<ComplaintBean, Integer> {

    List<ComplaintBean> findAllByComplainerId(Integer complainerId);

    List<ComplaintBean> findAllByInstituteId(Integer instituteId);

    @Modifying
    @Transactional
    @Query("UPDATE ComplaintBean c SET c.status = :newStatus WHERE c.id = :complaintId")
    void updateStatus(@Param("complaintId") Integer complaintId, @Param("newStatus") String newStatus);

    @Query("SELECT c.status, COUNT(c) FROM ComplaintBean c WHERE c.institute.id = :instituteId GROUP BY c.status")
    List<Object[]> getStatusCountByInstitute(@Param("instituteId") Integer instituteId);

    @Query("SELECT COUNT(c) FROM ComplaintBean c WHERE c.institute.id = :instituteId")
    Long getCountByInstitute(@Param("instituteId") Integer instituteId);


}
