package com.kingston.enviro_guard.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author Sanjaya Sandakelum
 * @since 2023-12-18
 * TSTPL
 */

@Entity
@Table(name = "complaint")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComplaintBean {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String complaintTitle;
    private String complaintDescription;
    private String location;
    private Date addedDateTime;
    private String status;
    private String image;

    @ManyToOne
    @JoinColumn(name = "institute_id")
    private InstituteBean institute;

    @ManyToOne
    @JoinColumn(name = "complainer_id")
    private ComplainerBean complainer;

}
