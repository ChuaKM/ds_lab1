/* Koon Chua
 * EN 605.202.81
 * Lab 1
 *
 * ReadWrite class reads parses input file and writes output
 */

import java.io.*;
import java.util.*;

public class ReadWrite{
    private String input = new String();
    private String output = new String();
    private File input_file;
    private File output_file;
    private Scanner scanner;
    private PrintWriter printer;

    /**
     * Constructor Method
     * Opens input file and output file
     * @param read input file
     * @param write output file
     */
    public ReadWrite(String read, String write) {
        input_file = new File(read);
        output_file = new File(write);

        // In case of invalid input/output file
        try {
            scanner = new Scanner(input_file); // read input
            printer = new PrintWriter(output_file); // write output
        } catch (Exception e) {
            System.out.println("Invalid input/output parameters");
        }
    }

    /**
     * Method returns the next line(string) of the input file
     * If empty or passed last line, return null
     * @return next line of input file
     */
    public String nextInput() {
        if (scanner.hasNextLine()) {
            input = scanner.nextLine();
            return input;
        } else {
            return null;
        }
    }

    /**
     * Method that writes a single line (string) to the output file
     * @param output single line (string)
     */
    public void writeOutput(String output) {
        printer.println(output);
    }

    /**
     * Method that closes the output file once the rogram is complete
     */
     public void closeOutput() {
        printer.close();
     }
}
