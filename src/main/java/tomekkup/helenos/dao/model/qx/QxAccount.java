package tomekkup.helenos.dao.model.qx;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

@Entity
@Table(name = "USERS")
public class QxAccount implements UserDetails {
    
    private Collection<SimpleGrantedAuthority> authorities = new HashSet<SimpleGrantedAuthority>();
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
    
    public QxAccount(String username, String password, SimpleGrantedAuthority authority, boolean enabled) {
        this(username);
        setPassword(password);
        addAuthority(authority);
        setEnabled(enabled);
    }
    
    @Transient
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
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

    @Column(unique = true, nullable = false)
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

    
    public void setAuthorities(Collection<SimpleGrantedAuthority> authorities) {
        this.authorities = authorities;
    }
    
    public Map<String, ?> toParametersMap() {
        Assert.hasLength(username);
        Assert.hasLength(password);
        
        List<String> authToStr = new ArrayList<String>(authorities.size());
        for(SimpleGrantedAuthority sga : authorities) {
            authToStr.add(sga.getAuthority());
        }

        Map<String, Object> map = new HashMap<String, Object>(3);
        map.put("username", username);
        map.put("password", password);
        map.put("authorities", StringUtils.arrayToCommaDelimitedString(authToStr.toArray()));
        map.put("enabled", enabled);
        return map;
    }

    public void addAuthority(SimpleGrantedAuthority authority) {
        this.authorities.add(authority);
    }
}
