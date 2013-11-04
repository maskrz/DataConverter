/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dataconverter;

/**
 *
 * @author Skrzypek & Bart
 */
public class DataConverter {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Reader reader = new Reader();
        reader.readIndices();
        reader.readCompanies();
        reader.readEuroAndDollar();
        reader.readEurostoxx();
        reader.readNasdaq();
        reader.readDji();
//        String save = reader.euro_res + " "+reader.dollar_res + " "+reader.eurostoxx_res 
//                + " "+reader.nasdaq_res + " "+reader.djia_res;
//        System.out.println(save);
        reader.setResults();
        Writer w = new Writer(reader);
        w.prepareData();
//        w.save();
        
    }
}
