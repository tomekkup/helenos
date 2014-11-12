package tomekkup.helenos.dao.model.qx;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import tomekkup.helenos.dao.model.Authority;

@Entity
public class QxAccount implements UserDetails {
    
    private Collection<Authority> authorities = new HashSet<Authority>();
    private String username;
    private String password;
    private boolean enabled;
    
    public QxAccount() {
        super();
    }
    
    public QxAccount(String username) {
        this();
        setUsername(username);
    }
    
    public QxAccount(String username, String password, Authority authority, boolean enabled) {
        this(username);
        setPassword(password);
        addAuthority(authority);
        setEnabled(enabled);
    }
    
    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
    @Override
    public Collection<Authority> getAuthorities() {
        return authorities;
    }
    
    @Column(name="PASSWORD", nullable = false)
    @Override
    public String getPassword() {
        return password;
    }

    @Id()
    @Column(name = "USERNAME")
    @Override
    public String getUsername() {
        return username;
    }
    
    @Transient
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Transient
    @Override
    public boolean isAccountNonLocked() {
        return isEnabled();
    }

    @Transient
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Column(nullable = false)
    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public void setAuthorities(Collection<Authority> authorities) {
        this.authorities = authorities;
    }
    
    public void addAuthority(Authority authority) {
        this.authorities.add(authority);
    }
}
