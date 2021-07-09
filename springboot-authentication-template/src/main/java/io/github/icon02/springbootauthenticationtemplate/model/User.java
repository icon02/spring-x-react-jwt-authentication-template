package io.github.icon02.springbootauthenticationtemplate.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.github.icon02.springbootauthenticationtemplate.tempStorage.TemporaryStorage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User implements UserDetails, TemporaryStorage.Identifiable<Long> {

    private Long id;
    private String email;
    @JsonIgnore
    private String password;
    @JsonIgnore
    private Collection<Authority> authorities = new ArrayList<>();
    private boolean enabled = true;
    /**
     * Maps the agentId (key) to a generated singleUseToken (value)
     */
    @JsonIgnore
    private Map<String, String> singleUseTokens = new HashMap<>();

    /**
     * @param agentId id of the user browser
     * @param sut single-use-token
     * @return true if match, else false
     */
    public boolean matchAgentAndSUT(String agentId, String sut) {
        return singleUseTokens.get(agentId).equals(sut);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    /**
     * Getter for *username
     * *In this case we do not use a username and therefore return the email
     *
     * @return email as String
     */
    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return enabled;
    }

    @Override
    public boolean isAccountNonLocked() {
        return enabled;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return enabled;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public Long getId() {
        return id;
    }
}
