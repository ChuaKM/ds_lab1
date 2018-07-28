/* Koon Chua
 * EN 605.202.81
 * Lab 1
 *
 * This class checks the input string against six preset languages.
 */

public class LanguageEval {
    private Stack_LL input_stack;
    private Stack_LL stack_A;
    private Stack_LL stack_B;
    private boolean lang_1, lang_2, lang_3, lang_4, lang_5, lang_6;
    private String output_string;
    private String input_string;
    enum Alphabet {A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W, X, Y, Z}

    /**
     * Class Constructor that initializes several intermediary stacks, and the output string
     * @param   input String that will be analyzed
     */
    public LanguageEval(String input) {
        input_stack = new Stack_LL();
        stack_A = new Stack_LL();
        stack_B = new Stack_LL();
        input_string = input;
        output_string = "";
        toStack(input);
    }

    /**
     * Tests input string against six languages.
     * @return output string of passed language tests
     */
    public String language_match() {
        language1();
        language2();
        language3();
        language4();
        language5();
        language6();
        printoutput_string();
        return output_string;
    }


    /**
     * Test input string against Language 1:
     * Check if the string has the same number of A's and B's in any order, no other characters
     * Sets lang_1 boolean to true if string matches language 1.
     */
    private void language1() {
        stack_A.empty();
        stack_B.empty();
        boolean flag = true; // set flag to true if string contains 'A', 'B' (or null)

        for (int i = 0; i < input_string.length(); i++) {
            char c = input_string.charAt(i);
            if (c == 'A') {
                stack_A.push(c);
            } else if (c == 'B') {
                stack_B.push(c);
            } else {
                flag = false;
            }
        }

        // catches all strings with characters other than 'A', 'B', or are null
        if (flag) {
            if (compareAB()) {
                lang_1 = true;
            }
        }
    }

    /**
     * Test input string against Language 2:
     * Check if the string is of the form A^nB^n n>0
     *Sets lang_2 boolean to true if string matches language 2
     */
    private void language2() {
        stack_A.empty();
        stack_B.empty();
        boolean b_flag = false;
        boolean na_char = false; //character other than A or B

        for (int i = 0; i < input_string.length(); i++) {
            char c = input_string.charAt(i);

            if (c == 'A' && b_flag == false) {
                stack_A.push(c);

            } else if (c == 'B') {
                stack_B.push(c);
                b_flag = true;
            } else {
                na_char = true;
            }
        }

        if (!na_char) {
            if (compareAB()) {
                lang_2 = true;
            }
        }
    }

    /**
     * Test input string against Language 3:
     * Check if the string is of the form A^nB^2n n>0
     * Sets lang_3 boolean to true if string matches language 3
     */
    private void language3() {
        stack_A.empty();
        stack_B.empty();
        boolean b_flag = false;
        boolean na_char = false; //character other than A or B

        for (int i = 0; i < input_string.length(); i++) {
            char c = input_string.charAt(i);
            if (c == 'A' && !b_flag) {
                stack_A.push(c);

            } else if (c == 'B') {
                stack_B.push(c);
                b_flag = true;

            } else {
                na_char = true;
            }
        }
        if (!na_char) {
            if (2 * stack_A.getSize() == stack_B.getSize()) {
                lang_3 = true;
            }
        }
    }

    /**
     * Test input string against Language 4:
     * Check if the string is of the form (A^mB^n)^p n>0
     * Sets lang_4 boolean to true if string matches language 4
     */
    private void language4() {
        Stack_LL temp_stack_a = new Stack_LL();
        Stack_LL temp_stack_b = new Stack_LL();
        boolean temp_lang_4 = true;
        boolean temp_flag = false;  // true after first completed pattern
        boolean b_flag = false;     // true after 'B' is found
        boolean na_char = false;    // true if char other than 'A' is found
        stack_A.empty();
        stack_B.empty();

        for (int i = 0; i < input_string.length(); i++) {
            char c = input_string.charAt(i);

            if (c == 'A' && !b_flag && !temp_flag) { // first 'A'
                stack_A.push(c);

            } else if (c == 'B' && !temp_flag) { // first 'B'
                stack_B.push(c);
                b_flag = true;

            } else if (c == 'A' && b_flag && !temp_flag) { // A^mB^n pattern found
                temp_flag = true;
                b_flag = false;
                temp_stack_a.push(c);

            } else if (c == 'A' && b_flag && temp_flag) {
                temp_stack_a.push(c);

            } else if (c == 'B' && temp_flag) {
                temp_stack_b.push(c);
                b_flag = true;

            } else if (c == 'A' && b_flag && temp_flag) {

                // compare stack_a and stack_b to reference stacks.
                if (temp_stack_a.getSize() == stack_A.getSize() && temp_stack_b.getSize() == stack_B.getSize()) {
                    temp_stack_a.empty();
                    temp_stack_b.empty();
                    temp_lang_4 = temp_lang_4 && true;
                } else {
                    temp_lang_4 = temp_lang_4 && false; // if temp_lang_4 is ever false, will always be false
                }
                temp_stack_a.push(c);
                b_flag = false;

            } else {
                na_char = true; // String contains invalid characters (not 'A' or 'B')
            }
        }

        // Double check that no letters other than 'A', 'B', or null string
        if (!na_char) {
            if (temp_stack_a.isEmpty() && temp_stack_b.isEmpty()) {
                lang_4 = true;

            } else if (temp_stack_a.getSize() == stack_A.getSize() && temp_stack_b.getSize() == stack_B.getSize()) {
                temp_stack_a.empty();
                temp_stack_b.empty();
                lang_4 = temp_lang_4 && true;

            } else {
                lang_4 = temp_lang_4 && false;
            }
        }
    }

    /**
     * Test input string against Language 5:
     * Check if the string is a palindrome i.e. read the same forward and backward
     * i.e MOM, ANNA, EVE, RACECAR, RADAR
     * null and single character strings also return true
     * Sets lang_5 boolean to true if string matches language 5
     */
    private void language5() {
        Stack_LL temp_stack = input_stack;
        char alpha;
        boolean p_flag = true;

        for (int i = 0; i < input_string.length(); i++) {
            char c = input_string.charAt(i);
            alpha = temp_stack.pop();
            if (c != alpha) {
                p_flag = false;
            }
        }
        lang_5 = p_flag;
    }

    /**
     * Test input string against Language 6:
     * Check if the string is in alphabetical order
     * Duplicate letters are allowed i.e. ABBCDE returns true
     * Skipped letters are allowed i.e. ADZ returns true
     * Sets lang_6 boolean to true if string matches language 6
     */
    private void language6() {
        input_stack.empty();
        toStack(input_string);
        Alphabet input;
        Alphabet next_input;
        char alpha;
        char next_alpha;
        boolean na_char = false;

        while (!input_stack.isEmpty() && !na_char) {
            alpha = input_stack.pop();

            if (alpha == 'A' ||
                alpha == 'B' ||
                alpha == 'C' ||
                alpha == 'D' ||
                alpha == 'E' ||
                alpha == 'F' ||
                alpha == 'G' ||
                alpha == 'H' ||
                alpha == 'I' ||
                alpha == 'J' ||
                alpha == 'K' ||
                alpha == 'L' ||
                alpha == 'M' ||
                alpha == 'N' ||
                alpha == 'O' ||
                alpha == 'P' ||
                alpha == 'Q' ||
                alpha == 'R' ||
                alpha == 'S' ||
                alpha == 'T' ||
                alpha == 'U' ||
                alpha == 'V' ||
                alpha == 'W' ||
                alpha == 'X' ||
                alpha == 'Y' ||
                alpha == 'Z' ) {

                input = enumAlphabet(alpha);
                if (!input_stack.isEmpty()) {
                    next_alpha = input_stack.peek();
                    next_input = enumAlphabet(next_alpha);

                    if (next_input.ordinal() > input.ordinal()) {
                        na_char = true;
                    } else {
                        na_char = false;
                    }
                }
            } else {
                na_char = true;
            }
        }
        lang_6 = !na_char;
    }

    /**
     * Converts string into a stack
     * @param   input String to be converted to stack
     */
    private void toStack(String input) {
        try {
            for (int i = 0; i < input.length(); i++) {
                char c = input.charAt(i);
                input_stack.push(c);
            }
        } catch (Exception e) {
        }
    }

    /**
     * Check to see if Stack_A and Stack_B are the same size
     * Pop each stack until one or both are empty
     * Used in language1 and language2 test
     * @return  true if both stacks are empty, false otherwise
     */
    private boolean compareAB() {
        while (!stack_A.isEmpty() && !stack_B.isEmpty()) {
            stack_A.pop();
            stack_B.pop();
        }
        if (stack_A.isEmpty() && stack_B.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Enumeration property for every character in the alphabet
     * @param   input character
     * @return  Alphabet enumeration for the given character
     */
    private Alphabet enumAlphabet(char input) {
        Alphabet a = Alphabet.A;
        switch (input) {
            case 'A':
                a = Alphabet.A;
                break;
            case 'B':
                a = Alphabet.B;
                break;
            case 'C':
                a = Alphabet.C;
                break;
            case 'D':
                a = Alphabet.D;
                break;
            case 'E':
                a = Alphabet.E;
                break;
            case 'F':
                a = Alphabet.F;
                break;
            case 'G':
                a = Alphabet.G;
                break;
            case 'H':
                a = Alphabet.H;
                break;
            case 'I':
                a = Alphabet.I;
                break;
            case 'J':
                a = Alphabet.J;
                break;
            case 'K':
                a = Alphabet.K;
                break;
            case 'L':
                a = Alphabet.L;
                break;
            case 'M':
                a = Alphabet.M;
                break;
            case 'N':
                a = Alphabet.N;
                break;
            case 'O':
                a = Alphabet.O;
                break;
            case 'P':
                a = Alphabet.P;
                break;
            case 'Q':
                a = Alphabet.Q;
                break;
            case 'R':
                a = Alphabet.R;
                break;
            case 'S':
                a = Alphabet.S;
                break;
            case 'T':
                a = Alphabet.T;
                break;
            case 'U':
                a = Alphabet.U;
                break;
            case 'V':
                a = Alphabet.V;
                break;
            case 'W':
                a = Alphabet.W;
                break;
            case 'X':
                a = Alphabet.X;
                break;
            case 'Y':
                a = Alphabet.Y;
                break;
            case 'Z':
                a = Alphabet.Z;
                break;
        }
        return a;
    }

    /**
     * Creates output string with the language match results
     * Each language test has a corresponding boolean
     * If string passes multiple languages, concatenate existing results
     */
    private void printoutput_string() {
        if (lang_1) {
            output_string = output_string + " lang_1 ";
        }
        if (lang_2) {
            output_string = output_string + " lang_2 ";
        }
        if (lang_3) {
            output_string = output_string + " lang_3 ";
        }
        if (lang_4) {
            output_string = output_string + " lang_4 ";
        }
        if (lang_5) {
            output_string = output_string + " lang_5 ";
        }
        if (lang_6) {
            output_string = output_string + " lang_6 ";
        }
    }
}