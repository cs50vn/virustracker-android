package com.cs50vn.virustracker.app;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import android.view.View;

import com.cs50vn.virustracker.app.utils.AppUtils;

@RunWith(JUnit4.class)
public class AppUtilsTest {

    public static void init() {

    }


    @Test
    public void FirstTest() {
        Assert.assertEquals("", 1 , 1);
        Assert.assertEquals(true, AppUtils.compareDate(1580886645, 1580886645));

    }

    @Test
    public void testCompareDate() {
        //Case 1: equal
        Assert.assertEquals("", 1 , 1);
        //Case 2: not equal



    }

}
