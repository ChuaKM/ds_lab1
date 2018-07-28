/* Koon Chua
 * EN 605.202.81
 * Lab 1
 *
 * Lab 1 Driver
 * This class contains the main function of the program
 * Uses ReadWrite to open an input file, and write to an output file
 * Run LanguageEval to check each line against six different language rules
 * The output file is written with the input string and language result concatenated to the end
 */

public class Lab1Main {

    public static void main(String[] args) {
        String input = null;
        String output;

        // In case of invalid input file
        try {
            ReadWrite readWrite = new ReadWrite(args[0], args[1]);
            input = readWrite.nextInput();

            // line by line check each string in the input file
            while(input != null) {
                LanguageEval languageEval = new LanguageEval(input);
                output = languageEval.language_match();
                readWrite.writeOutput(input + output);
                input = readWrite.nextInput();
            }

            readWrite.closeOutput();

        } catch (Exception e) {
            System.out.println("Invalid input/output arguments");
        }
    }
}