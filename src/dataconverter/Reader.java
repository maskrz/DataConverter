/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dataconverter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
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
    boolean euro_found;
    boolean dollar_found;

    public Reader() {
        d = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        date = dateFormat.format(d);
        euro_found = false;
        dollar_found = false;
        euro_res = "N/A";
        dollar_res = "N/A";
        eurostoxx_res = "N/A";
        //System.out.println(date);
    }

    private void readIndices() {
    }

    public void readEuroAndDollar() {
        File conf = new File("src/conf_files/EuroAndDollar.conf");
        Scanner sc = null;
        try {
            sc = new Scanner(conf);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Reader.class.getName()).log(Level.SEVERE, null, ex);
        }
        String url = sc.nextLine();
        String euroReg = sc.nextLine();
        String dollarReg = sc.nextLine();
        CURRENCY = Pattern.compile(sc.nextLine());
        try {
            buff = getConnection(url);
            String line = buff.readLine();

            while (line != null && !euro_found && !dollar_found) {
                if (line.contains(euroReg)) {
                    euro_found = true;
                    Matcher m = CURRENCY.matcher(line);
                    if (m.find()) {
                        euro_res = m.group(0).replace(",", ".");
                    }
                }
                if (line.contains(dollarReg)) {
                    Matcher m = CURRENCY.matcher(line);
                    dollar_found = true;
                    if (m.find()) {
                        dollar_res = m.group(0).replace(",", ".");
                    }
                }
                line = buff.readLine();
            }
        } catch (Exception e) {
        };

    }

    public void readEurostoxx() {
        File conf = new File("src/conf_files/EuroStoxx50.conf");
        Scanner sc = null;
        try {
            sc = new Scanner(conf);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Reader.class.getName()).log(Level.SEVERE, null, ex);
        }
        String url = sc.nextLine();
        String stockReg = sc.nextLine();
        EUROSTOXX = Pattern.compile(sc.nextLine());
        Matcher m;

        try {
            buff = getConnection(url);
            String line = buff.readLine();

            while (line != null) {
                if (line.contains(stockReg)) {
                    m = EUROSTOXX.matcher(line);

                    if (m.find()) {
                        eurostoxx_res = m.group(0).replace(",", "");
                    }
                }
                line = buff.readLine();
            }
        } catch (Exception e) {
        };
    }
    
    public void readNasdaq() {
        File conf = new File("src/conf_files/Nasdaq.conf");
        Scanner sc = null;
        try {
            sc = new Scanner(conf);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Reader.class.getName()).log(Level.SEVERE, null, ex);
        }
        String url = sc.nextLine();
        String stockReg = sc.nextLine();
        NASDAQ = Pattern.compile(sc.nextLine());
        Matcher m;

        try {
            buff = getConnection(url);
            String line = buff.readLine();

            while (line != null) {
                if (line.contains(stockReg)) {
                    m = NASDAQ.matcher(line);

                    if (m.find()) {
                        nasdaq_res = m.group(0).replace(",", "");
                    }
                }
                line = buff.readLine();
            }
        } catch (Exception e) {
        };
    }
    
    public void readDji() {
        File conf = new File("src/conf_files/Dji.conf");
        Scanner sc = null;
        try {
            sc = new Scanner(conf);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Reader.class.getName()).log(Level.SEVERE, null, ex);
        }
        String url = sc.nextLine();
        String stockReg = sc.nextLine();
        DJI = Pattern.compile(sc.nextLine());
        Matcher m;

        try {
            buff = getConnection(url);
            String line = buff.readLine();

            while (line != null) {
                if (line.contains(stockReg)) {
                    m = DJI.matcher(line);

                    if (m.find()) {
                        dji_res = m.group(0).replace(",", "");
                        System.out.println(dji_res);
                    }
                }
                line = buff.readLine();
            }
        } catch (Exception e) {
        };
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
}
