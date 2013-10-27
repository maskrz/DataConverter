/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dataconverter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Skrzypek
 */
public class DataConverter {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Reader reader = new Reader();
        reader.readEuroAndDollar();
        reader.readEurostoxx();
        reader.readNasdaq();
        reader.readDji();
        String save = reader.euro_res + " "+reader.dollar_res + " "+reader.eurostoxx_res 
                + " "+reader.nasdaq_res + " "+reader.dji_res;
        System.out.println(save);
        
    }
}
