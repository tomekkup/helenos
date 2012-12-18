package com.kuprowski.helenos.service.impl;

import com.kuprowski.helenos.service.CredentialsProvider;
import org.springframework.stereotype.Component;

/**
 * ********************************************************
 * Copyright: 2012 Tomek Kuprowski
 *
 * License: GPLv2: http://www.gnu.org/licences/gpl.html
 *
 * @author Tomek Kuprowski (tomekkuprowski at gmail dot com)
 * *******************************************************
 */
@Component("credentialsProvider")
public class CredentialsProviderImpl extends AbstractProvider implements CredentialsProvider {

    @Override
    public int checkCredentials() {
        return 1;
    }

    
}
