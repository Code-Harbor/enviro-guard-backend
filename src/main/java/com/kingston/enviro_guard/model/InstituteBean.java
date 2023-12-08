package com.kingston.enviro_guard.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Sanjaya Sandakelum
 * @since 2023-12-07
 * TSTPL
 */

@Entity
@Table(name = "institute")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InstituteBean {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    public InstituteBean(Integer institute_id) {
        this.id = institute_id;
    }
}
