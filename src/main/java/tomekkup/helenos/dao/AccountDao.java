package tomekkup.helenos.dao;

import org.springframework.security.core.userdetails.UserDetailsService;
import tomekkup.helenos.dao.model.Account;

/**
 * ********************************************************
 * Copyright: 2015 Tomek Kuprowski
 *
 * License: GPLv2: http://www.gnu.org/licences/gpl.html
 *
 * @author Tomek Kuprowski (tomekkuprowski at gmail dot com)
 * *******************************************************
 */
public interface AccountDao extends UserDetailsService, EntityDao<Account, String> {
    
    void encodePasswd(Account user);
    
    String encodePasswd(String username, String passwd);
}
