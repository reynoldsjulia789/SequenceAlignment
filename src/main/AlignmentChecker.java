package main;

/**
 * Checks the alignment of DNA sequences
 */
public class AlignmentChecker
{
    // modifiers are public so they can be accessed and changed from outside the class
    public  int    matchBonus, mismatchPenalty, gapPenalty;
    private String sequence1, sequence2;

    /**
     * Default AlignmentChecker constructor.
     * Uses the following modifiers by default: a match bonus of +2, a mismatch penalty of -1, and a gap penalty of -2.
     * Sequences are null by default
     */
    public AlignmentChecker()
    {
        this.matchBonus      =  2;
        this.mismatchPenalty = -1;
        this.gapPenalty      = -2;
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
     */
    public String[] checkAlignment(String sequence1, String sequence2)
    {
        setSequences(sequence1, sequence2);

        //TODO: finish checking alignment

        // return The score of the optimal alignment
        //        The characters of the first string from the input file, with gaps ("-") added where necessary to
        //            create the optimal alignment score.
        //        The characters of the second string from the input file, with gaps ("-") added where necessary to
        //            create the optimal alignment score.
        return new String[] {null, null, null};
    }

    /**
     * Sets the two DNA sequences to be checked
     * @param sequence1 the first DNA sequence
     * @param sequence2 the second DNA sequence
     */
    private void setSequences(String sequence1, String sequence2)
    {
        if (sequence1 == null)
        {
            throw new IllegalArgumentException("AlignmentChecker: can't set null sequence");
        }

        if (sequence2 == null)
        {
            throw new IllegalArgumentException("AlignmentChecker: can't set null sequence");
        }

        this.sequence1 = sequence1.trim();
        this.sequence2 = sequence2.trim();
    }

    @Override
    public String toString()
    {
        return "AlignmentChecker.  Match Bonus: " + this.matchBonus +
                ",  Mismatch Penalty: " + this.mismatchPenalty +
                ",  Gap Penalty: " + this.gapPenalty;
    }
}
