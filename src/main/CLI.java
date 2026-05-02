package main;

import java.util.Scanner;

/**
 * Command Line Interface for Sequence Alignment Calculator
 * Either this or Main can be used.
 */
public class CLI
{
    /**
     * Calculates sequence alignment based on user entries
     * @param args args
     */
    public static void main(String[] args)
    {
        boolean                  running;
        int                      matchBonus, mismatchPenalty, gapPenalty;
        long                     startTime, endTime;
        AlignmentChecker         alignmentChecker;
        AlignmentChecker.Results results;
        String                   sequence1, sequence2, userInput;

        running = true;

        System.out.println("Welcome to the Sequence Alignment Calculator!");
        System.out.println("This program takes two DNA sequences and determines their optimal alignment.");

        try (Scanner keyboard = new Scanner(System.in))
        {
            while (running)
            {
                System.out.println("\nUse the following characters to enter your sequences: A, T, G, & C");

                do
                {
                    System.out.print("Enter sequence 1:  ");
                    sequence1 = keyboard.nextLine().toUpperCase().trim();
                }
                while (sequence1.isBlank());

                do
                {
                    System.out.print("Enter sequence 2:  ");
                    sequence2 = keyboard.nextLine().toUpperCase().trim();
                }
                while (sequence2.isBlank());

                System.out.println("\nIf you would like to use custom modifiers, enter them below");
                System.out.print("Match bonus (default 1):  ");
                userInput       = keyboard.nextLine().trim();
                matchBonus      = userInput.isBlank() ? 1 : Integer.parseInt(userInput);

                System.out.print("Mismatch penalty (default -1):  ");
                userInput       = keyboard.nextLine().trim();
                mismatchPenalty = userInput.isBlank() ? -1 : Integer.parseInt(userInput);

                System.out.print("Gap penalty (default -2):  ");
                userInput       = keyboard.nextLine().trim();
                gapPenalty      = userInput.isBlank() ? -2 : Integer.parseInt(userInput);

                System.out.println();

                alignmentChecker = new AlignmentChecker(matchBonus, mismatchPenalty, gapPenalty);
                startTime        = System.nanoTime();
                results          = alignmentChecker.checkAlignment(sequence1, sequence2);
                endTime          = System.nanoTime();

                System.out.println("Calculated optimal alignment.\n Alignment Table:");

                printAlignmentTable(sequence1, sequence2, results.alignmentData());

                System.out.println();
                System.out.println("Calculation Time (ns):  " + (endTime - startTime));
                printResults(results);
                System.out.println();

                System.out.print("Would you like to check more sequences? (y/n):  ");

                userInput = keyboard.nextLine().trim().toUpperCase();

                if (userInput.isBlank() || userInput.charAt(0) != 'Y') {
                    running = false;
                }
            }
        }
        catch (Exception caught)
        {
            System.out.println("An error has occurred  " + caught.getMessage());
        }

        System.out.println("\nThank you for using this program :)");
    }

    /**
     * prints the alignment table to the terminal
     * @param sequence1 the first sequence
     * @param sequence2 the second sequence
     * @param alignmentData the table of data
     */
    private static void printAlignmentTable(String sequence1, String sequence2, AlignmentChecker.Cell[][] alignmentData)
    {
        int idx;

        System.out.print(" \t\t\t\t");

        for (idx = 0; idx < sequence2.length(); idx++)
        {
            System.out.print(sequence2.charAt(idx) + "\t\t");
        }

        System.out.println();

        idx = -1;

        // Print Alignment
        for (AlignmentChecker.Cell[] row : alignmentData)
        {
            if (idx >= 0)
            {
                System.out.print(sequence1.charAt(idx) + "\t");
            }
            else
            {
                System.out.print(" \t");
            }

            for (AlignmentChecker.Cell cell : row)
            {
                System.out.print(cell.toString());
                System.out.print("\t");
            }

            System.out.println();
            idx++;
        }
    }

    /**
     * prints the results of the alignment check
     * @param results results
     */
    private static void printResults(AlignmentChecker.Results results)
    {
        System.out.println("Alignment Score:  " + results.alignmentScore());
        System.out.println("Sequence 1:  " + results.sequence1());
        System.out.println("Sequence 2:  " + results.sequence2());
    }
}
