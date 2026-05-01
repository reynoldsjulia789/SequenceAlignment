package test;

import main.AlignmentChecker;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AlignmentCheckerTest
{
    @Nested
    @DisplayName("AlignmentTests")
    class AlignmentTests
    {
        @Test
        @DisplayName("finds best alignment of AGCGA and ACGAA")
        public void test1()
        {
            AlignmentChecker         test;
            AlignmentChecker.Results results;

            test    = new AlignmentChecker();
            results = test.checkAlignment("AGCGA", "ACGAA");

            assertEquals(0, results.alignmentScore(), "Alignment Score");
            assertEquals("A-CGAA", results.sequence1(), "Sequence 1");
            assertEquals("AGCGA-", results.sequence2(), "Sequence 2");
        }

        @Test
        @DisplayName("finds best alignment of AGTTA and AGAGA")
        public void test2()
        {
            AlignmentChecker         test;
            AlignmentChecker.Results results;

            test    = new AlignmentChecker();
            results = test.checkAlignment("AGTTA", "AGAGA");

            assertEquals(0, results.alignmentScore(), "Alignment Score");
            assertEquals("AGTTA", results.sequence1(), "Sequence 1");
            assertEquals("AGAGA", results.sequence2(), "Sequence 2");
        }
    }
}
