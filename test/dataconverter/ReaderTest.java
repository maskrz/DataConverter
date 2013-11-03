/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dataconverter;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Skrzypek
 */
public class ReaderTest {
    Reader reader;
    
    public ReaderTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        reader = new Reader();
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of readEuroAndDollar method, of class Reader.
     */
    @Test
    public void testReadEuroAndDollar() {
        System.out.println("readEuroAndDollar");
        reader.readEuroAndDollar();
        Pattern p = Pattern.compile("[0-9].[0-9][0-9][0-9][0-9]");
        Matcher m = p.matcher(reader.euro_res);
        Assert.assertTrue(m.find() || reader.euro_res.equals("N/A"));
        m = p.matcher(reader.dollar_res);
        Assert.assertTrue(m.find());
    }

    /**
     * Test of readEurostoxx method, of class Reader.
     */
    @Test
    public void testReadEurostoxx() {
        System.out.println("readEurostoxx");
        reader.readEurostoxx();
        Pattern p = Pattern.compile("[0-9]+.[0-9][0-9]");
        Matcher m = p.matcher(reader.eurostoxx_res);
        Assert.assertTrue(m.find());
    }

    /**
     * Test of readNasdaq method, of class Reader.
     */
    @Test
    public void testReadNasdaq() {
        System.out.println("readNasdaq");
        reader.readNasdaq();
        Pattern p = Pattern.compile("[0-9]+.[0-9][0-9]");
        Matcher m = p.matcher(reader.nasdaq_res);
        Assert.assertTrue(m.find());
    }

    /**
     * Test of readDji method, of class Reader.
     */
    @Test
    public void testReadDji() {
        System.out.println("readDji");
        reader.readDji();
        Pattern p = Pattern.compile("[0-9]+.[0-9][0-9]");
        Matcher m = p.matcher(reader.djia_res);
        Assert.assertTrue(m.find());
    }

    /**
     * Test of setResults method, of class Reader.
     */
    @Test
    public void testLoadProperties() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        System.out.println("loadProperties");
        Method method = Reader.class.getDeclaredMethod("loadProperties", String.class);
        method.setAccessible(true);
        String input = "testProp";
        Properties prop = (Properties) method.invoke(reader, input);
        Assert.assertEquals(prop.getProperty("testUrl"), "http://finance.yahoo.com/q?s=^DJI");
        Assert.assertEquals(prop.getProperty("testIndexLine"), "yfs_l10_^dji");
        Assert.assertEquals(prop.getProperty("pattern"), "[0-9]+,[0-9][0-9][0-9].[0-9][0-9]");
        // TODO review the generated test code and remove the default call to fail.
    }
    
    @Test
    public void testSetStockProperties() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, FileNotFoundException, IOException {
        System.out.println("setStockProperties");
        Method method = Reader.class.getDeclaredMethod("setStockProperties");
        method.setAccessible(true);
        Properties prop = new Properties();
        prop.load(new FileInputStream("src/properties/testProp.properties"));
        reader.prop = prop;
        Pattern pattern = (Pattern) method.invoke(reader);
        Assert.assertEquals(pattern.toString(), "[0-9]+,[0-9][0-9][0-9].[0-9][0-9]");
        // TODO review the generated test code and remove the default call to fail.
    }
   
    @Test
    public void testGetConnection() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, FileNotFoundException, IOException {
        System.out.println("getConnection");
        Method method = Reader.class.getDeclaredMethod("getConnection", String.class);
        method.setAccessible(true);
        BufferedReader bf = (BufferedReader) method.invoke(reader, "http://finance.yahoo.com/q?s=^DJI");
        Assert.assertNotNull(bf);
        // TODO review the generated test code and remove the default call to fail.
    }
   
    @Test
    public void testGetStockResult() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, FileNotFoundException, IOException {
        System.out.println("getStockResult");
        Method method = Reader.class.getDeclaredMethod("getStockResult", Pattern.class);
        method.setAccessible(true);
        reader.pageURL = "http://finance.yahoo.com/q?s=^DJI";
        reader.stockReg = "yfs_l10_^dji";
        String output = (String) method.invoke(reader, Pattern.compile("[0-9]+,[0-9][0-9][0-9].[0-9][0-9]"));
        Assert.assertNotSame("N/A", output);
        // TODO review the generated test code and remove the default call to fail.
    }
    
}