package tomekkup.helenos.service;

import tomekkup.helenos.types.qx.QxAccount;
import java.util.List;
import tomekkup.helenos.types.qx.QxPasswordChangeRequest;

/**
 * ********************************************************
 * Copyright: 2012 Tomek Kuprowski
 *
 * License: GPLv2: http://www.gnu.org/licences/gpl.html
 *
 * @author Tomek Kuprowski (tomekkuprowski at gmail dot com)
 * *******************************************************
 */
public interface AccountsProvider {

    List<QxAccount> loadAll();
    
    long getAccountsCount();
    
    void delete(String username);
    
    void store(QxAccount account);
    
    void createAccount(QxAccount account) throws IllegalStateException;
    
    void saveNewPassword(QxPasswordChangeRequest pcr) throws IllegalStateException;
    
    QxAccount loadUserByUsername(String username);
}
