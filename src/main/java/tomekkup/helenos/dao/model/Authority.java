/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tomekkup.helenos.dao.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import org.springframework.security.core.GrantedAuthority;
import tomekkup.helenos.dao.model.qx.QxAccount;

@Entity
public class Authority extends AbstractEntity implements GrantedAuthority {

    private QxAccount account;
    private String authority;

    public Authority() {
    }

    public Authority(QxAccount account, String authority) {
        this.account = account;
        this.authority = authority;
    }
    
    @ManyToOne(cascade=CascadeType.REFRESH, fetch=FetchType.LAZY)
    @JoinColumn(name="USERNAME")
    public QxAccount getAccount() {
        return account;
    }
    
    @Override
    @Column(length = 16, nullable = false)
    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public void setAccount(QxAccount account) {
        this.account = account;
    }
}
