package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;

public class Main
{
    public static void main(String[] args)
    {
        long                startTime, endTime;
        AlignmentChecker    alignmentChecker;
        String              inputFileName, outputFileName;
        String              sequence1, sequence2;
        String[]            inputFile, optimalAlignment;

        inputFileName  = "input.txt";
        outputFileName = "output.txt";

        System.out.println("Start");

        inputFile = readInputFile(inputFileName);

        System.out.println("Successfully read input file");

        sequence1           = inputFile[0].trim().toUpperCase();
        sequence2           = inputFile[1].trim().toUpperCase();
        alignmentChecker    = new AlignmentChecker(
                                                    Integer.parseInt(inputFile[2].trim()),
                                                    Integer.parseInt(inputFile[3].trim()),
                                                    Integer.parseInt(inputFile[4].trim())
                                                  );

        System.out.println("Initialized AlignmentChecker");

        startTime        = System.currentTimeMillis();
        optimalAlignment = alignmentChecker.checkAlignment(sequence1, sequence2);
        endTime          = System.currentTimeMillis();

        System.out.println("Calculated optimal alignment");
        System.out.println("Time taken (ms):  " + (endTime - startTime));

        printToOutputFile(outputFileName, optimalAlignment);

        System.out.println("Printed results to " + outputFileName);
        System.out.println("End");
    }

    /**
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
            result[2] = inputFileReader.nextLine().trim().split("\\s+")[2];
            result[3] = inputFileReader.nextLine().trim().split("\\s+")[2];
            result[4] = inputFileReader.nextLine().trim().split("\\s+")[2];

            return result;
        }
        catch (FileNotFoundException caught)
        {
            throw new IllegalArgumentException("input file was not found or was unable to be accessed");
        }
    }

    /**
     * Writes optimal alignment data to a file of the specified file name
     * @param outputFileName the name of the file to print the results to
     * @param alignmentData a String[] with the optimal alignment data
     * @throws IllegalArgumentException throws exception if output file was not found or was unable to be written to
     */
    private static void printToOutputFile(String outputFileName, String[] alignmentData) throws IllegalArgumentException
    {
        try (PrintStream outputFileWriter = new PrintStream(outputFileName))
        {
            for(String line : alignmentData)
            {
                outputFileWriter.println(line);
            }
        }
        catch (FileNotFoundException caught)
        {
            throw new IllegalArgumentException("output file was not found or was unable to be written to");
        }
    }
}
