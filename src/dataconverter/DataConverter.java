/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dataconverter;

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
        System.out.println(reader.euro_res + " "+reader.dollar_res + " "+reader.eurostoxx_res 
                + " "+reader.nasdaq_res + " "+reader.dji_res);
//        System.out.println(reader.euro_res);

    }
}
