// TokenStream.java

// Implementation of the Scanner for JAY

// This code DOES NOT implement a scanner for JAY yet. You have to complete
// the code and also make sure it implements a scanner for JAY - not something
// else.

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class TokenStream {

    private boolean isEof = false;
    private char nextChar = ' ';
    private BufferedReader input;

    public TokenStream(String fileName) {
        try {
            input = new BufferedReader(new FileReader(fileName));
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + fileName);
            isEof = true;
        }
    }

    public Token nextToken() {
        Token t = new Token();
        t.setType("Other");
        t.setValue("");

        skipWhiteSpace();

        // Handle end of file
        if (isEof) {
            t.setType("EOF");
            return t;
        }

        // Handle comments
        while (nextChar == '/') {
            nextChar = readChar();
            if (nextChar == '/') {
                while (!isEndOfLine(nextChar)) {
                    nextChar = readChar();
                }
                skipWhiteSpace();
            } else {
                t.setType("Operator");
                t.setValue("/");
                return t;
            }
        }

        // Handle operators
        if (isOperator(nextChar)) {
            t.setType("Operator");
            t.setValue("" + nextChar);
            char currentChar = nextChar;
            nextChar = readChar();
            if (currentChar == ':' && nextChar == '=') {
                t.setValue(":=");
                nextChar = readChar();
            }
            return t;
        }

        // Handle separators
        if (isSeparator(nextChar)) {
            t.setType("Separator");
            t.setValue("" + nextChar);
            nextChar = readChar();
            return t;
        }

        // Handle identifiers, keywords, and literals
        if (isLetter(nextChar)) {
            StringBuilder sb = new StringBuilder();
            while (isLetter(nextChar) || isDigit(nextChar)) {
                sb.append(nextChar);
                nextChar = readChar();
            }
            String value = sb.toString();
            if (isKeyword(value)) {
                t.setType("Keyword");
            } else if (value.equals("True") || value.equals("False")) {
                t.setType("Literal");
            } else {
                t.setType("Identifier");
            }
            t.setValue(value);
            return t;
        }

        // Handle numeric literals
        if (isDigit(nextChar)) {
            StringBuilder sb = new StringBuilder();
            while (isDigit(nextChar)) {
                sb.append(nextChar);
                nextChar = readChar();
            }
            t.setType("Literal");
            t.setValue(sb.toString());
            return t;
        }

        // Handle unknown characters
        t.setType("Other");
        t.setValue("" + nextChar);
        nextChar = readChar();

        return t;
    }

    private char readChar() {
        try {
            int i = input.read();
            if (i == -1) {
                isEof = true; // Properly set EOF flag
                return (char) 0; // Return a null character
            }
            return (char) i;
        } catch (IOException e) {
            isEof = true; // On error, mark as EOF
            return (char) 0;
        }
    }

    private boolean isKeyword(String s) {
        return s.equals("bool") || s.equals("integer");
    }

    private boolean isWhiteSpace(char c) {
        return (c == ' ' || c == '\t' || c == '\r' || c == '\n' || c == '\f');
    }

    private boolean isEndOfLine(char c) {
        return (c == '\r' || c == '\n' || c == '\f');
    }

    private void skipWhiteSpace() {
        while (!isEof && isWhiteSpace(nextChar)) {
            nextChar = readChar();
        }
    }

    private boolean isSeparator(char c) {
        return c == '(' || c == ')' || c == '{' || c == '}' || c == ';' || c == ',';
    }

    private boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/' || c == '<' || c == '>' || c == '=' || c == '!' || c == ':';
    }

    private boolean isLetter(char c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
    }

    private boolean isDigit(char c) {
        return (c >= '0' && c <= '9');
    }

    public boolean isEndofFile() {
        return isEof;
    }
}
