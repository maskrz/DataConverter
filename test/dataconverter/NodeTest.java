/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dataconverter;

import java.io.File;
import java.io.FileNotFoundException;
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
public class NodeTest {
    Node node;
    
    public NodeTest() {
        node = new Node("testKey", "testValue");
    }
    
    @BeforeClass
    public static void setUpClass() {
        
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getKey method, of class Node.
     */
    @Test
    public void testGetKey() {
        System.out.println("getKey");
        String k = node.getKey();
        Assert.assertEquals(k, "testKey");
    }

    /**
     * Test of getValue method, of class Node.
     */
    @Test
    public void testGetValue() {
        System.out.println("getValue");
        String v = node.getValue();
        Assert.assertEquals(v, "testValue");
    }

    /**
     * Test of saveNode method, of class Node.
     */
    @Test
    public void testSaveNode() throws FileNotFoundException {
        System.out.println("saveNode");
        File f = new File("src\\datas\\testKey\\testKey_2013.csv");
        Scanner sc = new Scanner(f);
        int counter = 0;
        while(sc.hasNext()) {
            counter ++;
            sc.nextLine();
        }
        node.saveNode("src\\datas\\", "2013", "2013-10-10");
        f = new File("src\\datas\\testKey\\testKey_2013.csv");
        sc = new Scanner(f);
        int counter2 = 0;
        while(sc.hasNext()) {
            counter2 ++;
            sc.nextLine();
        }
        Assert.assertEquals(counter+1, counter2);
    }
}