/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dataconverter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
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
public class WriterTest {
    Writer writer;
    Reader reader;
    
    public WriterTest() {
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
        reader.setResults();
        writer = new Writer(reader);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of prepareData method, of class Writer.
     */
    @Test
    public void testPrepareData() {
        System.out.println("prepareData");        
        writer.prepareData();
        Assert.assertEquals(5, writer.datas.size());
    }

    /**
     * Test of save method, of class Writer.
     */
    @Test
    public void testSave() throws FileNotFoundException {
        System.out.println("save");
        ArrayList<Node> datas = new ArrayList();
        Node n;
        n = new Node("testKey", "testValue");
        datas.add(n);
        File f = new File("src\\datasCopy\\testKey\\testKey_2013.csv");
        Scanner sc = new Scanner(f);
        int counter = 0;
        while(sc.hasNext()) {
            counter ++;
            sc.nextLine();
        }
        writer.datas = datas;
        writer.save();
        f = new File("src\\datasCopy\\testKey\\testKey_2013.csv");
        sc = new Scanner(f);
        int counter2 = 0;
        while(sc.hasNext()) {
            counter2 ++;
            sc.nextLine();
        }
        Assert.assertEquals(counter+1, counter2);
    }
}