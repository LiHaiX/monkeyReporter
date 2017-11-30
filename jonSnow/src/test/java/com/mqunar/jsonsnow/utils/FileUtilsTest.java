package com.mqunar.jsonsnow.utils;

import com.mqunar.jonsnow.utils.FileUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;

/**
 * Created by ironman.li on 2016/7/13.
 */
public class FileUtilsTest {


    @Test
    public void testGetHost() {
        File file = FileUtils.getHostFolder();
        System.out.println(file.toString());
        Assert.assertNotNull(file);
    }
}
