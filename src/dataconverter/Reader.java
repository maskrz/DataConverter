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
    String euro_res = "";
    String dollar_res = "";
    boolean euro_found;
    boolean dollar_found;

    public Reader() {
        d = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        date = dateFormat.format(d);
        euro_found = false;
        dollar_found = false;
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
                    Matcher m = CURRENCY.matcher(line);
                    if (m.find()) {
                        euro_res = m.group(0);
                    }
                }
                if (line.contains(dollarReg)) {
                    Matcher m = CURRENCY.matcher(line);
                    if (m.find()) {
                        dollar_res = m.group(0);
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
            inStream = new InputStreamReader(urlConnection.getInputStream());
            return new BufferedReader(inStream);
        } catch (Exception ex) {
            Logger.getLogger(Reader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }
}
