package main.view;

import main.model.AlignmentChecker;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;

/**
 * Handles reading from the input file, calling the AlignmentChecker methods, and writing to the output file
 */
public class FileReader
{
    /**
     * FileReader method.
     * <p>
     * Executes the appropriate methods to determine the optimal alignment of two DNA sequences.
     * </p>
     * <p>
     * The sequences to check must be in a file called input.txt in the following format:
     * Line 1: The characters of the first DNA sequence, with no leading or trailing whitespace. Characters are all
     * uppercase and members of the set {A, C, G, T}
     * Line 2: The characters of the second DNA sequence, with no leading or trailing whitespace. Characters are all
     * uppercase and members of the set {A, C, G, T}
     * Line 3: The exact text "Match %d" where %d is an integer representing the score of a match bonus
     * Line 4: The exact text "Mismatch %d" where %d is an integer representing the score of a mismatch penalty
     * Line 5: The exact text "Gap %d" where %d is an integer representing the score of a gap penalty
     * </p>
     * <p>
     * The results will be output to a file called output.txt in the following format:
     * Line 1: The score of the optimal alignment
     * Line 2: The characters of the first string from the input file, with gaps ("-") added where necessary to
     * create the optimal alignment score
     * Line 3: The characters of the second string from the input file, with gaps ("-") added where necessary to
     * create the optimal alignment score
     * </p>
     * @param args args
     */
    public static void main(String[] args)
    {
        int                      matchBonus, mismatchPenalty, gapPenalty;
        long                     startTime, endTime;
        AlignmentChecker alignmentChecker;
        AlignmentChecker.Results results;
        String                   inputFileName, outputFileName;
        String                   sequence1, sequence2;
        String[]                 inputFile;

        inputFileName  = "src/main/textFiles/input.txt";
        outputFileName = "src/main/textFiles/output.txt";

        System.out.println("Start");

        inputFile = readInputFile(inputFileName);

        System.out.println("Successfully read input file");

        sequence1        = inputFile[0].trim().toUpperCase();
        sequence2        = inputFile[1].trim().toUpperCase();
        matchBonus       = Integer.parseInt(inputFile[2].trim());
        mismatchPenalty  = Integer.parseInt(inputFile[3].trim());
        gapPenalty       = Integer.parseInt(inputFile[4].trim());

        alignmentChecker = new AlignmentChecker(matchBonus, mismatchPenalty, gapPenalty);

        System.out.println("Initialized AlignmentChecker");

        startTime        = System.currentTimeMillis();
        results          = alignmentChecker.checkAlignment(sequence1, sequence2);
        endTime          = System.currentTimeMillis();

        System.out.println("Calculated optimal alignment: " + results.alignmentScore());

        // Print Alignment
        for (AlignmentChecker.Cell[] row : results.alignmentData())
        {
            for (AlignmentChecker.Cell cell : row)
            {
                System.out.print(cell.toString());
                System.out.print("\t");
            }

            System.out.println();
        }

        System.out.println(results.sequence1());
        System.out.println(results.sequence2());

        System.out.println("Time taken (ms):  " + (endTime - startTime));

        printToOutputFile(outputFileName, results);

        System.out.println("Printed results to " + outputFileName);
        System.out.println("End");
    }

    /** TODO Eventually: Verify correct input file formatting while reading
     * Reads DNA sequences and alignment modifiers from an input file, returning the data as a String[]
     * @param inputFileName the name of the input file to read from
     * @return returns the data from the input file as a String[]
     * @throws IllegalArgumentException throws exception if input file was not found or was unable to be accessed
     */
    private static String[] readInputFile(String inputFileName) throws IllegalArgumentException
    {
        String[] result;

        try (Scanner inputFileReader = new Scanner(new File(inputFileName)))
        {
            result = new String[5];

            // DNA sequences
            result[0] = inputFileReader.nextLine();
            result[1] = inputFileReader.nextLine();

            // Modifiers
            result[2] = inputFileReader.nextLine().trim().split("\\s+")[1];
            result[3] = inputFileReader.nextLine().trim().split("\\s+")[1];
            result[4] = inputFileReader.nextLine().trim().split("\\s+")[1];

            return result;
        }
        catch (FileNotFoundException caught)
        {
            throw new IllegalArgumentException("input file was not found or was unable to be accessed", caught);
        }
    }

    /**
     * Writes optimal alignment data to a file of the specified file name
     * @param outputFileName the name of the file to print the results to
     * @param alignmentData a String[] with the optimal alignment data
     * @throws IllegalArgumentException throws exception if output file was not found or was unable to be written to
     */
    private static void printToOutputFile(String outputFileName, AlignmentChecker.Results alignmentData)
            throws IllegalArgumentException
    {
        try (PrintStream outputFileWriter = new PrintStream(outputFileName))
        {
            outputFileWriter.println(alignmentData.alignmentScore());
            outputFileWriter.println(alignmentData.sequence1());
            outputFileWriter.println(alignmentData.sequence2());
        }
        catch (FileNotFoundException caught)
        {
            throw new IllegalArgumentException("output file was not found or was unable to be written to", caught);
        }
    }
}
