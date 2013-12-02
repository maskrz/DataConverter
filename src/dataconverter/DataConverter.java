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
        System.out.println("Program has started");

        Reader reader = new Reader();
        Writer w = new Writer(reader);

        System.out.println("Checking entry");
        w.checkEntry();
        if (!w.entryExists) {
            System.out.println("No entry");
//        if (true) {
            reader.readIndices();
            System.out.println("indices done");
            reader.readCompanies();
            System.out.println("companies done");
            reader.readEuroAndDollar();
            System.out.println("euro dollar done");
            reader.readEurostoxx();
            System.out.println("eurostoxx done");
            reader.readNasdaq();
            System.out.println("nasdaq done");
            reader.readDji();
            System.out.println("dji done");
            String save = reader.euro_res + " " + reader.dollar_res + " " + reader.eurostoxx_res
                    + " " + reader.nasdaq_res + " " + reader.djia_res;
            System.out.println(save);


            w.prepareData();
            w.save();
            w.cleanDatas();
        }
        else {
            System.out.println("Entry exists!");
        }

    }
}
