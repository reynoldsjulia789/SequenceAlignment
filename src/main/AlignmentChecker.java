package main;

/**
 * Checks the alignment of DNA sequences
 */
public class AlignmentChecker
{
    private final int matchBonus;
    private final int mismatchPenalty;
    private final int gapPenalty;
    private int[][]   editDistance;
    private String    sequence1;
    private String    sequence2;

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
        this.matchBonus      = matchBonus;
        this.mismatchPenalty = mismatchPenalty;
        this.gapPenalty      = gapPenalty;
    }

    /**
     * Checks the alignment of two DNA sequences
     * @param sequence1 the first DNA sequence
     * @param sequence2 the second DNA sequence
     * @throws IllegalArgumentException throws exception if one or both sequences are null or blank
     */
    public String[] checkAlignment(String sequence1, String sequence2) throws IllegalArgumentException
    {
        int sequence1Length, sequence2Length;

        if (sequence1 == null || sequence2 == null || sequence1.isBlank() || sequence2.isBlank())
        {
            throw new IllegalArgumentException("AlignmentChecker: can't align null sequence");
        }

        this.sequence1  = sequence1.trim();
        this.sequence2  = sequence2.trim();

        sequence1Length = this.sequence1.length();
        sequence2Length = this.sequence2.length();

        calculateEditDistances();
        createAlignedStrings();

        return new String[] {
                                "Alignment Score: " + this.editDistance[sequence1Length][sequence2Length],
                                this.sequence1,
                                this.sequence2
                            };
    }

    /** TODO: add a way to track where answer for each cell came from
     * Fills out the 2D edit distance array.
     */
    private void calculateEditDistances()
    {
        char    char1, char2;
        int     seq1Idx, seq2Idx;
        int     seq1SideLength, seq2SideLength;
        int     matchMismatch, gapSeq1, gapSeq2;
        int[][] editDistance;

        seq1SideLength = this.sequence1.length() + 1;
        seq2SideLength = this.sequence2.length() + 1;

        editDistance = new int[seq1SideLength][seq2SideLength];

        // fill in the edit distances for the 1st column and 1st row
        for (seq1Idx = 1; seq1Idx < seq1SideLength; seq1Idx++)
        {
            editDistance[0][seq1Idx] = seq1Idx * this.gapPenalty;
        }

        for (seq2Idx = 1; seq2Idx < seq2SideLength; seq2Idx++)
        {
            editDistance[seq2Idx][0] = seq2Idx * this.gapPenalty;
        }

        // fill in the rest of the edit distances
        for (seq1Idx = 1; seq1Idx < seq1SideLength; seq1Idx++)
        {
            for (seq2Idx = 1; seq2Idx < seq2SideLength; seq2Idx++)
            {
                char1         = this.sequence1.charAt(seq1Idx - 1);
                char2         = this.sequence2.charAt(seq2Idx - 1);
                matchMismatch = editDistance[seq2Idx - 1][seq1Idx - 1] +
                                cost(char1, char2);
                gapSeq1       = this.gapPenalty + editDistance[seq2Idx][seq1Idx - 1];
                gapSeq2       = this.gapPenalty + editDistance[seq2Idx - 1][seq1Idx];

                if ((matchMismatch >= gapSeq1) && (matchMismatch >= gapSeq2))
                {
                    editDistance[seq2Idx][seq1Idx] = matchMismatch;
                    // TODO: store that we are going with match/mismatch
                }
                else if ((gapSeq1 > matchMismatch) && (gapSeq1 >= gapSeq2))
                {
                    editDistance[seq2Idx][seq1Idx] = gapSeq1;
                    // TODO: store that we are going with gapSeq1
                }
                else
                {
                    editDistance[seq2Idx][seq1Idx] = gapSeq2;
                    // TODO: store that we are going with gapSeq2
                }
            }
        }

        this.editDistance = editDistance;
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
            return this.matchBonus;
        }

        return this.mismatchPenalty;
    }

    /** TODO: finish createAlignedStrings
     * Takes a calculated edit distance 2D array and the original sequence and adds gaps where necessary to create
     * the optimal alignment score.
     */
    private void createAlignedStrings()
    {
        // save the characters of the strings with gaps ("-") added where necessary to create the optimal alignment score

    }

    @Override
    public String toString()
    {
        return "AlignmentChecker.  Match Bonus: " + this.matchBonus +
                ",  Mismatch Penalty: " + this.mismatchPenalty +
                ",  Gap Penalty: " + this.gapPenalty;
    }
}
