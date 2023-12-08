package com.kingston.enviro_guard.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Sanjaya Sandakelum
 * @since 2023-12-08
 * TSTPL
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserLoginRequest {
    private String email;
    private String password;
}
