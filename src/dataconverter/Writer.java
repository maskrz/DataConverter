/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dataconverter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;
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
    boolean entryExists;

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
        entryExists = false;
//        System.out.println(path);

    }

    public void prepareData() {
        Node n = new Node("djia", reader.djia_res);
        datas.add(n);
        n = new Node("euro", reader.euro_res);
//        System.out.println(n.value);
        datas.add(n);
        n = new Node("dollar", reader.dollar_res);
        datas.add(n);
        n = new Node("eurostoxx50", reader.eurostoxx_res);
        datas.add(n);
        n = new Node("nasdaq", reader.nasdaq_res);
        datas.add(n);
    }

    public void checkEntry() {
        try {
            File f = new File(path + "euro/euro_" + year + ".csv");
            Scanner sc = new Scanner(f);
            String last = "";
            System.out.println(date);
            while (sc.hasNext()) {
                last = sc.nextLine();
//                System.out.println(last);
                if (last.contains(date)) {
                    System.out.println(last);
                    entryExists = true;
                    break;
                }
            }
            sc.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Writer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void save() {
        if (!entryExists) {
            if (true) {
                for (Node n : datas) {
                    n.saveNode(path, year, date);
                }
                for (Node n : indices) {
                    n.saveNode(path + "indices/", year, date);
                }
                for (Node n : companies) {
                    n.saveNode(path + "companies/", year, date);
                }
            }
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
            for (Node node : indices) {
                f = new File(path + "indices/" + node.key + "/" + node.key + year + ".csv");
                f.createNewFile();
            }
            for (Node node : companies) {
                f = new File(path + "companies/" + node.key + "/" + node.key + year + ".csv");
                f.createNewFile();
            }
        } catch (IOException ex) {
            Logger.getLogger(Writer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void cleanDatas() {
        System.out.println("Cleaning datas...");
        System.out.println("Cleaning stocks");
        cleanStocks();
        System.out.println("Cleaning currencies");
        cleanCurrencies();
        System.out.println("Cleaning indices");
        cleanIndices();
        System.out.println("Cleaning companies");
        cleanCompanies();
    }

    private void cleanStocks() {
        File f = new File(path + "djia/djia_" + year + ".csv");
        clean(f);
        f = new File(path + "eurostoxx50/eurostoxx50_" + year + ".csv");
        clean(f);
        f = new File(path + "nasdaq/nasdaq_" + year + ".csv");
        clean(f);
    }
    
    private void cleanCurrencies() {
        File f = new File(path + "dollar/dollar_" + year + ".csv");
        clean(f);
        f = new File(path + "euro/euro_" + year + ".csv");
        clean(f);
    }
    
    private void cleanIndices() {
        File f;
        for(Node node : indices) {
            f = new File(path + "indices/" + node.key + "/" + node.key +"_"+ year + ".csv");
            clean(f);
        }
    }
    
    private void cleanCompanies() {
        File f;
        for(Node node : companies) {
            f = new File(path + "companies/" + node.key + "/" + node.key +"_"+ year + ".csv");
            clean(f);
        }
    }

    private void clean(File f) {
        try {
//            System.out.println(f);
            String result = "";
            Scanner sc = new Scanner(f);
            String lastDate = "";
            while(sc.hasNext()) {
                String line = sc.nextLine();
                String date = line.split(";")[0];
                if(!date.equals(lastDate)) {
                    result+=line+"\r\n";
                }
                lastDate = date;
            }
            PrintWriter out = null;
            try {
                out = new PrintWriter(new BufferedWriter(new FileWriter(f)));
                out.print(result);
                out.close();
            } catch (IOException ex) {
                Logger.getLogger(Writer.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                out.close();
            }
//            System.out.println(result);
        } catch (FileNotFoundException ex) {
            System.out.println("File not found: "+ f);
        }
    }
}
