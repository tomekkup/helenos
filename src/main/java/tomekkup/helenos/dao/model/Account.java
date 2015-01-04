package tomekkup.helenos.dao.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * ********************************************************
 * Copyright: 2015 Tomek Kuprowski
 *
 * License: GPLv2: http://www.gnu.org/licences/gpl.html
 *
 * @author Tomek Kuprowski (tomekkuprowski at gmail dot com)
 * *******************************************************
 */
@Entity
@Table(name="ACCOUNT")
public class Account implements UserDetails, Serializable, Identifiable<String> {
    
    private Collection<Authority> authorities = new HashSet<Authority>();
    private String username;
    private String password;
    private boolean enabled;
    
    public Account() {
        super();
    }
    
    public Account(String username) {
        this();
        setUsername(username);
    }
    
    public Account(String username, String password, Authority authority, boolean enabled) {
        this(username);
        setPassword(password);
        addAuthority(authority);
        setEnabled(enabled);
    }
    
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="ACCOUNT_ID")
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

    @Transient
    @Override
    public String getId() {
        return getUsername();
    }

    @Override
    public void setId(String id) {
        setUsername(id);
    }
}
