package main;

/**
 * Checks the alignment of two DNA sequences
 */
public class AlignmentChecker
{
    private final int m_matchBonus;
    private final int m_mismatchPenalty;
    private final int m_gapPenalty;

    /**
     * Default AlignmentChecker constructor.
     * Uses the following modifiers by default: a match bonus of +2, a mismatch penalty of -1, and a gap penalty of -2.
     * Sequences are null by default
     */
    public AlignmentChecker()
    {
        this(2, -1, -2);
    }

    /**
     * AlignmentChecker constructor.
     * Takes ints representing the match bonus, mismatch penalty, and gap penalty to be used when determining the
     * optimal alignment of two DNA sequences
     * @param matchBonus the bonus to be applied if a letter in one sequence is matched to the same letter in the other
     * @param mismatchPenalty the penalty to be applied if a letter in one sequence is matched to a different letter in
     *                        the other
     * @param gapPenalty the penalty to be applied if a letter in one sequence is matched to a gap in the other sequence
     */
    public AlignmentChecker(int matchBonus, int mismatchPenalty, int gapPenalty)
    {
        m_matchBonus      = matchBonus;
        m_mismatchPenalty = mismatchPenalty;
        m_gapPenalty      = gapPenalty;
    }

    /**
     * Checks the alignment of two DNA sequences
     * @param sequence1 the first DNA sequence
     * @param sequence2 the second DNA sequence
     * @throws IllegalArgumentException throws exception if one or both sequences are null or blank
     * @return returns the alignment score and the two DNA sequences with gaps inserted in the appropriate places to
     * create the alignment score
     */
    public Results checkAlignment(String sequence1, String sequence2) throws IllegalArgumentException
    {
        int      sequence1Length, sequence2Length;
        String[] updatedSequences;
        Cell[][] alignment;

        if (sequence1 == null || sequence2 == null || sequence1.isBlank() || sequence2.isBlank())
        {
            throw new IllegalArgumentException("AlignmentChecker: can't align null or blank sequence");
        }

        sequence1        = sequence1.trim();
        sequence2        = sequence2.trim();

        sequence1Length  = sequence1.length();
        sequence2Length  = sequence2.length();

        alignment        = calculateAlignment(sequence1, sequence2);
        updatedSequences = createAlignedStrings(sequence1, sequence2, alignment);

        return new Results
        (
            alignment[sequence1Length][sequence2Length].value,
            updatedSequences[0],
            updatedSequences[1]
        );
    }

    /**
     * Calculates the optimal global alignment of 2 DNA sequences
     * @param sequence1 the first DNA sequence to be compared
     * @param sequence2 the second DNA sequence to be compared
     * @return returns a 2D array of Cells, where each cell contains the edit distance and an indicator to where the
     * value came from. The columns correspond to the characters in sequence1, and the rows correspond to the characters
     * in sequence2. Both are offset by 1, so [0][0] corresponds to a gap in both sequences
     */
    private Cell[][] calculateAlignment(final String sequence1, final String sequence2)
    {
        char     char1, char2;
        int      seq1Idx, seq2Idx;
        int      seq1SideLength, seq2SideLength;
        int      matchMismatch, gapSeq1, gapSeq2;
        Cell[][] alignment;

        seq1SideLength  = sequence1.length() + 1;
        seq2SideLength  = sequence2.length() + 1;

        alignment       = new Cell[seq2SideLength][seq1SideLength];

        alignment[0][0] = new Cell();

        // fill in the edit distances for the 1st column and 1st row
        for (seq1Idx = 1; seq1Idx < seq1SideLength; seq1Idx++)
        {
            alignment[0][seq1Idx] = new Cell(seq1Idx * m_gapPenalty, Cell.BACKTRACE.LEFT);
        }

        for (seq2Idx = 1; seq2Idx < seq2SideLength; seq2Idx++)
        {
            alignment[seq2Idx][0] = new Cell(seq2Idx * m_gapPenalty, Cell.BACKTRACE.DOWN);
        }

        // fill in the rest of the edit distances
        for (seq1Idx = 1; seq1Idx < seq1SideLength; seq1Idx++)
        {
            for (seq2Idx = 1; seq2Idx < seq2SideLength; seq2Idx++)
            {
                char1         = sequence1.charAt(seq1Idx - 1);
                char2         = sequence2.charAt(seq2Idx - 1);
                matchMismatch = alignment[seq2Idx - 1][seq1Idx - 1].value +
                                cost(char1, char2);
                gapSeq1       = m_gapPenalty + alignment[seq2Idx][seq1Idx - 1].value;
                gapSeq2       = m_gapPenalty + alignment[seq2Idx - 1][seq1Idx].value;

                if ((matchMismatch >= gapSeq1) && (matchMismatch >= gapSeq2))
                {
                    alignment[seq2Idx][seq1Idx] = new Cell(matchMismatch, Cell.BACKTRACE.DIAGONAL);
                }
                else if ((gapSeq1 > matchMismatch) && (gapSeq1 >= gapSeq2))
                {
                    alignment[seq2Idx][seq1Idx] = new Cell(gapSeq1, Cell.BACKTRACE.DOWN);
                }
                else
                {
                    alignment[seq2Idx][seq1Idx] = new Cell(gapSeq2, Cell.BACKTRACE.LEFT);
                }
            }
        }

        return alignment;
    }

    /**
     * Determines if two chars match or not and calculates the associated cost
     * @param char1 the first character to compare
     * @param char2 the second character to compare
     * @return returns the cost of considering a match/mutation between the two characters
     */
    private int cost(char char1, char char2)
    {
        if (char1 == char2)
        {
            return m_matchBonus;
        }

        return m_mismatchPenalty;
    }

    /**
     * Takes a calculated edit distance 2D array and the original sequence and adds gaps where necessary to create
     * the optimal alignment score.
     * @param sequence1 the 1st DNA sequence
     * @param sequence2 the 2nd DNA sequence
     * @param alignment the 2D array of cells with the edit distance values and backtrace enums
     * @return returns a String[] with the two updated sequences
     * @throws IllegalArgumentException thrown if a backtrace element is null unexpectedly, indicating an error may have
     * occurred when filling out the alignment 2D array.
     */
    private String[] createAlignedStrings(String sequence1, String sequence2, Cell[][] alignment)
            throws IllegalArgumentException
    {
        int            idx1, idx2;
        Cell.BACKTRACE backtrace;
        StringBuilder  builder1, builder2;

        builder1 = new StringBuilder(sequence1);
        builder2 = new StringBuilder(sequence2);

        idx1     = sequence1.length();
        idx2     = sequence2.length();

        while ((idx1 != 0) && (idx2 != 0))
        {
            backtrace = alignment[idx1][idx2].backtrace;

            if (backtrace == Cell.BACKTRACE.DIAGONAL)
            {
                idx1 -= 1;
                idx2 -= 1;
            }
            else if (backtrace == Cell.BACKTRACE.DOWN)
            {
                builder1.insert(idx1, "-");
                idx2 -= 1;
            }
            else if (backtrace == Cell.BACKTRACE.LEFT)
            {
                builder2.insert(idx2, "-");
                idx1 -= 1;
            }
            else // backtrace is null
            {
                throw new IllegalArgumentException("missing backtrace enum. " +
                        "an error may have occurred in the alignment calculation");
            }
        }

        return new String[] {builder1.toString(), builder2.toString()};
    }

    @Override
    public String toString()
    {
        return "AlignmentChecker.  Match Bonus: " + m_matchBonus +
                ",  Mismatch Penalty: "           + m_mismatchPenalty +
                ",  Gap Penalty: "                + m_gapPenalty;
    }

    /**
     * A class to be used as the cells in a 2D array grid to store the value of the cell and where that value came from
     */
    private static class Cell
    {
        int value;
        BACKTRACE backtrace;

        /**
         * enum denoting the options for where the cell calculation came from
         */
        enum BACKTRACE
        {
            DOWN,               // gap sequence 1
            DIAGONAL,           // match/mismatch
            LEFT                // gap sequence 2
        }

        /**
         * Default constructor for cell.
         * Constructs an empty cell - value = 0, backtrace = null
         */
        Cell()
        {
            this(0, null);
        }

        /**
         * Constructor for cell.
         * @param value the alignment score
         * @param backtrace an enum indicating where the score was calculated from
         */
        Cell(int value, BACKTRACE backtrace)
        {
            this.value     = value;
            this.backtrace = backtrace;
        }
    }

    /**
     * Used to return the results of an alignment calculation to the caller. Contains the int alignment score and
     * sequence 1 & 2 with gaps inserted as needed to get that score.
     * @param alignmentScore the score of the optimal alignment of sequence1 and sequence2
     * @param sequence1 the first DNA sequence with gaps inserted as needed to get the associated score
     * @param sequence2 the second DNA sequence with gaps inserted as needed to get the associated score
     */
    public record Results(int alignmentScore, String sequence1, String sequence2) {}
}
