package com.skcc.cloudz.zcp.domain.vo;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

import com.skcc.cloudz.zcp.common.domain.vo.CommonVo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Administrator
 *
 */
@Getter @Setter @NoArgsConstructor
public class OpenIdConnectUserDetailsVo extends CommonVo implements UserDetails {
    private static final long serialVersionUID = -899359145312156965L;
    
    private String userId;
    private String email;
    private String group;
    private OAuth2AccessToken token;

    public OpenIdConnectUserDetailsVo(Map<String, String> userInfo, OAuth2AccessToken token) {
        this.userId = userInfo.get("sub");
        this.email = userInfo.get("email");
        this.group = userInfo.get("group");
        this.token = token;
    }
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getUsername() {
        return this.email;
    }
    
}
