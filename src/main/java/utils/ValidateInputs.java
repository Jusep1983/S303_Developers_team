package utils;

import exceptions.EmptyInputException;
import exceptions.ValueOutOfRangeException;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class ValidateInputs {

    private static final Scanner SC = new Scanner(System.in);

    public static void validateFieldNotEmpty(String input) throws EmptyInputException {
        if (input.isEmpty()) {
            throw new EmptyInputException("the field cannot be empty");
        }
    }

    public static String readInput() {
        return SC.nextLine().trim();
    }

    public static int validateInteger(String message) {
        while (true) {
            System.out.print(message);
            try {
                String input = readInput();
                validateFieldNotEmpty(input);
                return Integer.parseInt(input);
            } catch (NullPointerException | NumberFormatException e) {
                System.err.println("Format error");
            } catch (EmptyInputException | NoSuchElementException | IllegalStateException e) {
                System.err.println("Error, " + e.getMessage());
            }
        }
    }

    public static String checkString() throws EmptyInputException {
        String inputStr = readInput();
        if (inputStr.isEmpty()) {
            throw new EmptyInputException("the field cannot be empty");
        } else {
            return inputStr;
        }
    }

    public static String validateString(String message) {
        while (true) {
            try {
                System.out.print(message);
                return checkString();
            } catch (EmptyInputException | NoSuchElementException | IllegalStateException e) {
                System.out.println("Error, " + e.getMessage());
            }
        }
    }

    public static void checkRangeNumber(String input, int minimum, int maximum) throws ValueOutOfRangeException {
        int number = Integer.parseInt(input);
        if (number < minimum || number > maximum) {
            throw new ValueOutOfRangeException(
                    "The value entered must be between " + minimum + " and " + maximum + ". "
            );
        }
    }

    public static int readIntegerBetweenOnRange(String message, int minimum, int maximum) {
        while (true) {
            try {
                System.out.print(message);
                String input = readInput();
                validateFieldNotEmpty(input);
                checkRangeNumber(input, minimum, maximum);
                return Integer.parseInt(input);
            } catch (NullPointerException | NumberFormatException e) {
                System.out.println("Format error");
            } catch (EmptyInputException | NoSuchElementException | IllegalStateException |
                     ValueOutOfRangeException e) {
                System.out.println("Error, " + e.getMessage());
            }
        }
    }

}
