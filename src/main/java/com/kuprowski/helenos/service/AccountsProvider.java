package com.kuprowski.helenos.service;

import com.kuprowski.helenos.dao.model.Account;
import java.util.List;

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

    List<Account> loadAll();
    
    long getAccountsCount();
}
