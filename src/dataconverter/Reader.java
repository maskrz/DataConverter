/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dataconverter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Skrzypek
 */
public class Reader {
    Date d;
    String date;
    public Reader() {
        d = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");        
        date = dateFormat.format(d);
        System.out.println(date);
    }    
    
    private void readIndices() {
    
    }
    
}
