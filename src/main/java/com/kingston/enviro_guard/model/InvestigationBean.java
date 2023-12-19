package com.kingston.enviro_guard.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author Sanjaya Sandakelum
 * @since 2023-12-19
 * TSTPL
 */

@Entity
@Table(name = "investigation_report")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvestigationBean {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String description;
    private Date addedDateTime;
    private String status;
    private String image;

    @OneToOne
    @JoinColumn(name = "complaint_id")
    private ComplaintBean complaint;

}
