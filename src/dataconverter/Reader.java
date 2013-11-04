/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dataconverter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Skrzypek & Bart
 */
public class Reader {

    Date d;
    String date;
    BufferedReader buff;
    InputStreamReader inStream;
    Pattern CURRENCY;
    Pattern EUROSTOXX;
    Pattern NASDAQ;
    Pattern DJIA;
    String euro_res;
    String dollar_res;
    String eurostoxx_res;
    String nasdaq_res;
    String djia_res;
    String pageURL;
    String stockReg;
    boolean euro_found;
    boolean dollar_found;
    Properties prop;
    Matcher m;
    ArrayList<Node> indices;
    ArrayList<Node> companies;

    public Reader() {
        d = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        date = dateFormat.format(d);
        euro_found = false;
        dollar_found = false;
        euro_res = "N/A";
        dollar_res = "N/A";
        eurostoxx_res = "N/A";
        djia_res = "N/A";
        prop = new Properties();
        pageURL = "";
        stockReg = "";
        m = null;
        indices = new ArrayList();
        companies = new ArrayList();
    }

    public void readIndices() {
        try {
            prop = loadProperties("Indices");
            String[] indicesList = readList(prop.getProperty("list"));
            int n = Integer.valueOf(indicesList[0]);
            String url = prop.getProperty("url");
            buff = getConnection(url + date);
            String line = buff.readLine();
            String checkLine = prop.getProperty("checkLine");
            int lineVal = Integer.valueOf(prop.getProperty("lineVal"));
            int lineSales = Integer.valueOf(prop.getProperty("lineSales"));
            while (line != null) {
                //            System.out.println(line);
                if (line.contains(checkLine)) {
                    for (int i = 1; i < n; i++) {
                        String temp = indicesList[i];
                        if (line.contains(temp + "<")) {
                            String key = temp;
                            String value = "";
                            for (int j = 0; j < lineVal; j++) {
                                buff.readLine();
                            }
                            line = buff.readLine();
                            String val = line.replaceAll(
                                    "<td>|<\\/td>|&nbsp;|\\s", "").replace(",", ".");
                            for (int j = 0; j < lineSales; j++) {
                                buff.readLine();
                            }
                            line = buff.readLine();
                            String sales = line.replaceAll(
                                    "<td>|<\\/td>|&nbsp;|\\s", "").replace(",", ".");
                            value += val + ";" + sales;
                            Node node = new Node(key, value);
                            indices.add(node);
                        }
                    }
                }
                line = buff.readLine();
            }
            fillEmptyValues(indices, indicesList, 'i');
//            showList(indices);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Reader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Reader.class.getName()).log(Level.SEVERE, null, ex);
        }


    }
    
    public void readCompanies() {
        try {
            prop = loadProperties("Companies");
            String[] companiesList = readList(prop.getProperty("list"));
            int n = Integer.valueOf(companiesList[0]);
            String url = prop.getProperty("url");
            buff = getConnection(url + date);
            String line = buff.readLine();
            String checkLine = prop.getProperty("checkLine");
            int lineVal = Integer.valueOf(prop.getProperty("lineVal"));
            int lineVolume = Integer.valueOf(prop.getProperty("lineVolume"));
            while (line != null) {
                //            System.out.println(line);
                if (line.contains(checkLine)) {
                    for (int i = 1; i < n; i++) {
                        String temp = companiesList[i];
                        if (line.contains(temp + "<")) {
                            String key = temp;
                            String value = "";
                            for (int j = 0; j < lineVal; j++) {
                                buff.readLine();
                            }
                            line = buff.readLine();
                            String val = line.replaceAll(
                                    "<td>|<\\/td>|&nbsp;|\\s", "").replace(",", ".");
                            for (int j = 0; j < lineVolume; j++) {
                                buff.readLine();
                            }
                            line = buff.readLine();
                            String volume = line.replaceAll(
                                    "<td>|<\\/td>|&nbsp;|\\s", "").replace(",", ".");
                            line = buff.readLine();
                            String transactions = line.replaceAll(
                                    "<td>|<\\/td>|&nbsp;|\\s", "").replace(",", ".");
                            line = buff.readLine();
                            String sales = line.replaceAll(
                                    "<td>|<\\/td>|&nbsp;|\\s", "").replace(",", ".");
                            value += val + ";" + volume+";"+transactions+";"+sales;
                            Node node = new Node(key, value);
                            companies.add(node);
                        }
                    }
                }
                line = buff.readLine();
            }
            fillEmptyValues(companies, companiesList, 'c');
//            showList(companies);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Reader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Reader.class.getName()).log(Level.SEVERE, null, ex);
        }


    }

    private void showList(ArrayList<Node> list) {
        for (Node node : list) {
            System.out.println(node.key + " " + node.value);
        }
    }

    public void readEuroAndDollar() {

        prop = loadProperties("EuroAndDollar");
        String url = prop.getProperty("url");
        String euroLine = prop.getProperty("euroLine");
        String dollarLine = prop.getProperty("dollarLine");
        CURRENCY = Pattern.compile(prop.getProperty("pattern"));

        try {

            buff = getConnection(url);
            String line = buff.readLine();

            while (line != null && !(euro_found && dollar_found)) {
                if (line.contains(euroLine)) {
                    euro_found = true;
                    m = CURRENCY.matcher(line);
                    if (m.find()) {
                        euro_res = m.group(0).replace(",", ".");
                    }
                }
                if (line.contains(dollarLine)) {
                    Matcher m = CURRENCY.matcher(line);
                    dollar_found = true;
                    if (m.find()) {
                        dollar_res = m.group(0).replace(",", ".");
                    }
                }
                line = buff.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        };

    }

    public void readEurostoxx() {

        prop = loadProperties("Eurostoxx50");
        EUROSTOXX = setStockProperties();
        eurostoxx_res = getStockResult(EUROSTOXX);
    }

    public void readNasdaq() {

        prop = loadProperties("Nasdaq");
        NASDAQ = setStockProperties();
        nasdaq_res = getStockResult(NASDAQ);
    }

    public void readDji() {

        prop = loadProperties("Djia");
        DJIA = setStockProperties();
        djia_res = getStockResult(DJIA);
    }

    private BufferedReader getConnection(String url_a) {
        URL url;
        try {
            url = new URL(url_a);
            URLConnection urlConnection = (URLConnection) url.openConnection();
            urlConnection.addRequestProperty("User-Agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
            inStream = new InputStreamReader(urlConnection.getInputStream());
            return new BufferedReader(inStream);
        } catch (Exception ex) {
            Logger.getLogger(Reader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }

    private Properties loadProperties(String path) {
        try {
            prop.load(new FileInputStream("src/properties/" + path + ".properties"));
        } catch (IOException ex) {
            Logger.getLogger(Reader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return prop;
    }

    private Pattern setStockProperties() {
        pageURL = prop.getProperty("url");
        stockReg = prop.getProperty("indexLine");
        return Pattern.compile(prop.getProperty("pattern"));
    }

    private String getStockResult(Pattern PATTERN) {
        try {
            buff = getConnection(pageURL);
            String line = buff.readLine();
            while (line != null) {
                if (line.contains(stockReg)) {
                    m = PATTERN.matcher(line);
                    if (m.find()) {
                        return m.group(0).replace(",", "");
                    }
                }
                line = buff.readLine();
            }
        } catch (Exception e) {
        };
        return "N/A";
    }

    void setResults() {
        euro_res = "4.1893";
        dollar_res = "3.0436";
        eurostoxx_res = "3040.67";
        nasdaq_res = "3943.52";
        djia_res = "15619.64";
    }

    private String[] readList(String list) {
        return list.toUpperCase().split(";");
    }

    private void fillEmptyValues(ArrayList<Node> nodesList, String[] list, char type) {
        int n = Integer.valueOf(list[0]);
        for(int i = 1; i < n; i++) {
            String temp = list[i];
            boolean found = false;
            for(Node node : nodesList) {
                if(node.key.equals(temp)) {
                    found = true;
                }
            }
            if(!found) {
                String val = (type == 'i')? "N/A;N/A" : "N/A;N/A;N/A;N/A";
                nodesList.add(new Node(temp, val));
            }
        }
    }
}
