/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tomekkup.helenos.dao;

import java.util.List;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import tomekkup.helenos.dao.model.qx.QxAccount;
import tomekkup.helenos.types.qx.QxPasswordChangeRequest;

/**
 *
 * @author developer
 */
public interface AccountDao extends UserDetailsService {

    void createAccount(QxAccount user);

    void delete(String username);

    long getAccountsCount();

    List<QxAccount> loadAll();

    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    void saveNewPassword(QxPasswordChangeRequest pcr) throws IllegalStateException;

    void store(QxAccount user);
    
}
