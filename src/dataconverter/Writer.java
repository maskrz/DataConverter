/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dataconverter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Skrzypek & Bart
 */
public class Writer {

    Properties prop;
    String path;
    Reader reader;
    String year;
    ArrayList<Node> datas;
    ArrayList<Node> indices;
    ArrayList<Node> companies;
    String date;

    public Writer(Reader r) {
        prop = new Properties();
        prop = loadProperties("Writer");
        path = prop.getProperty("path");
        reader = r;
        date = r.date;
        year = date.substring(0, 4);
        if (date.substring(5).equals("01-01")) {
            createNewPaths();
        }
        datas = new ArrayList();
        indices = r.indices;
        companies = r.companies;
//        System.out.println(path);

    }

    public void prepareData() {
        Node n = new Node("djia", reader.djia_res);
        datas.add(n);
        n = new Node("euro", reader.euro_res);
        datas.add(n);
        n = new Node("dollar", reader.dollar_res);
        datas.add(n);
        n = new Node("eurostoxx50", reader.eurostoxx_res);
        datas.add(n);
        n = new Node("nasdaq", reader.nasdaq_res);
        datas.add(n);
    }

    public void save() {
        for (Node n : datas) {
            n.saveNode(path, year, date);
        }
        for (Node n : indices) {
            n.saveNode(path+"indices/", year, date);
        }
        for (Node n : companies) {
            n.saveNode(path+"companies/", year, date);
        }
    }

    private Properties loadProperties(String path) {
        try {
            prop.load(new FileInputStream("src/properties/" + path + ".properties"));
        } catch (IOException ex) {
            Logger.getLogger(Reader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return prop;
    }

    private void createNewPaths() {
        try {
            File f = new File(path + "euro/euro_" + year + ".csv");
            f.createNewFile();
            f = new File(path + "djia/djia_" + year + ".csv");
            f.createNewFile();
            f = new File(path + "dollar/dolalr_" + year + ".csv");
            f.createNewFile();
            f = new File(path + "eurostoxx50/eurostoxx50_" + year + ".csv");
            f.createNewFile();
            f = new File(path + "nasdaq/nasdaq_" + year + ".csv");
            f.createNewFile();
            for(Node node : indices) {
                f = new File(path + "indices/"+node.key+"/"+node.key + year + ".csv");
            f.createNewFile();
            }
            for(Node node : companies) {
                f = new File(path + "companies/"+node.key+"/"+node.key + year + ".csv");
            f.createNewFile();
            }
        } catch (IOException ex) {
            Logger.getLogger(Writer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
