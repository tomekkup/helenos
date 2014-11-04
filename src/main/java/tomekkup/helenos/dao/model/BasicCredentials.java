/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tomekkup.helenos.dao.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * ********************************************************
 * Copyright: 2012 Tomek Kuprowski
 *
 * License: GPLv2: http://www.gnu.org/licences/gpl.html
 *
 * @author Tomek Kuprowski (tomekkuprowski at gmail dot com)
 * *******************************************************
 */
@Embeddable
public class BasicCredentials {
    private String username;
    private String password;

    public BasicCredentials() {
    }

    public BasicCredentials(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Column(name = "USERNAME", nullable = false, length = 32)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(name = "PASSWORD", nullable = false, length = 64)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    } 
}
