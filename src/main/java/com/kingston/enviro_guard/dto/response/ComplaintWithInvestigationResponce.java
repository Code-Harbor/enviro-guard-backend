package com.kingston.enviro_guard.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.kingston.enviro_guard.model.ComplaintBean;
import com.kingston.enviro_guard.model.InvestigationBean;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Sanjaya Sandakelum
 * @since 2023-12-19
 * TSTPL
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ComplaintWithInvestigationResponce {

    private ComplaintBean complaintBean;
    private InvestigationBean investigationBean;
}
