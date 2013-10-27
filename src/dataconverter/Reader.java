/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dataconverter;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Skrzypek
 */
public class Reader {

    Date d;
    String date;
    BufferedReader buff;
    InputStreamReader inStream;
    Pattern CURRENCY;
    Pattern EUROSTOXX;
    Pattern NASDAQ;
    Pattern DJI;
    String euro_res;
    String dollar_res;
    String eurostoxx_res;
    String nasdaq_res;
    String dji_res;
    String pageURL;
    String stockReg;
    boolean euro_found;
    boolean dollar_found;
    Properties prop;
    Matcher m;

    public Reader() {
        d = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        date = dateFormat.format(d);
        euro_found = false;
        dollar_found = false;
        euro_res = "N/A";
        dollar_res = "N/A";
        eurostoxx_res = "N/A";
        dji_res = "N/A";
        prop = new Properties();
        pageURL = "";
        stockReg = "";
        m = null;
    }

    private void readIndices() {
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
        
        prop = loadProperties("Dji");        
        DJI = setStockProperties();
        dji_res = getStockResult(DJI);
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
            prop.load(new FileInputStream("src/properties/"+path+".properties"));
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
    
}
