package com.afzaln.pgshoutbox.util;

import org.simpleframework.xml.core.Persister;

import java.io.BufferedReader;
import java.io.Reader;

/**
 * Created by afzal on 2016-11-20.
 */
public class UtfBomSerialized extends Persister {
    @Override
    public <T> T read(Class<? extends T> type, Reader source, boolean strict) throws Exception {
        BufferedReader wrapper = new BufferedReader(source);
        wrapper.mark(6);
        char[] buf = new char[6];
        wrapper.read(buf);
        if (buf[0] != 239) // Reset if not have BOM.
            wrapper.reset();
        return super.read(type, wrapper, strict);
    }

}
