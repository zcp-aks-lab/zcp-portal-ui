package com.skcc.cloudz.zcp.common.security.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class JwtIdToken {
    private String jti;
    private long exp;
    private long nbf;
    private long iat;
    private String iss;
    private String aud;
    private String sub;
    private String typ;
    private String azp;
    private long auth_time;
    private String session_state;
    private String acr;
    private String name;
    private String preferred_username;
    private String given_name;
    private String family_name;
    private String email;
}
