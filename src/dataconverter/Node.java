/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dataconverter;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Skrzypek & Bart
 */
public class Node {
    String key;
        String value;

        public Node(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }

        public void saveNode(String path, String year, String date) {
            System.out.println(path + key + "/" + key + "_" + year + ".csv");
            PrintWriter out = null;
            try {
                out = new PrintWriter(new BufferedWriter(new FileWriter(path + key + "/" + key + "_" + year + ".csv", true)));
                out.println(date + ";" + value);
                out.close();
            } catch (IOException ex) {
                Logger.getLogger(Writer.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                out.close();
            }
        }
}
