package com.cabletech.commons.web;

import java.io.*;
import java.util.*;

import org.apache.struts.util.*;

/**
 * <p>Provides access to MedRec messages in a properties file.</p>
 *
 * @author Copyright (c) 2003 by BEA Systems. All Rights Reserved.
 */
public class MessageProperties
    implements Serializable{
    private MessageResources messageResources = null;
    private Locale locale = null;

    // Private constructor
    private MessageProperties( Locale pLocale,
        MessageResources pMessageResources ){
        locale = pLocale;
        messageResources = pMessageResources;
    }


    /**
     * <p>Get instance.</p>
     *
     * @param locale
     * @return MedRecMessageProperties
     */
    public static MessageProperties getInstance( Locale locale,
        MessageResources pMessageResources ){
        return new MessageProperties( locale, pMessageResources );
    }


    /**
     * <p>Retrieves a localized message based on a String key.</p>
     *
     * @param pKey
     * @return String
     * @throws MissingResourceException
     */
    public String getMessage( String pKey ) throws MissingResourceException{
        return messageResources.getMessage( locale, pKey );
    }
}
