package tomekkup.helenos.dao.model.qx;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import tomekkup.helenos.dao.model.Authority;

/**
 * ********************************************************
 * Copyright: 2015 Tomek Kuprowski
 *
 * License: GPLv2: http://www.gnu.org/licences/gpl.html
 *
 * @author Tomek Kuprowski (tomekkuprowski at gmail dot com)
 * *******************************************************
 */
public class QxAccount implements Serializable {
    
    private Collection<String> authorities = new HashSet<String>();
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
        addAuthority(authority.getAuthority());
        setEnabled(enabled);
    }
    
    public Collection<String> getAuthorities() {
        return authorities;
    }
    
    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }
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
    
    public void setAuthorities(Collection<String> authorities) {
        this.authorities = authorities;
    }
    
    public void addAuthority(String authority) {
        this.authorities.add(authority);
    }
}
