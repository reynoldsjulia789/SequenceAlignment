package test;

import main.AlignmentChecker;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AlignmentCheckerTest
{
    @Nested
    @DisplayName("AlignmentTests")
    class AlignmentTests
    {
        @Test
        @DisplayName("finds best alignment of AGCGA and ACGAA")
        public void testMatchGap()
        {
            AlignmentChecker         test;
            AlignmentChecker.Results results;

            test    = new AlignmentChecker();
            results = test.checkAlignment("AGCGA", "ACGAA");

            assertEquals(0, results.alignmentScore(), "Alignment Score");
            assertEquals("AGCG-A", results.sequence1(), "Sequence 1");
            assertEquals("A-CGAA", results.sequence2(), "Sequence 2");
        }

        @Test
        @DisplayName("finds best alignment of AGTTA and AGAGA")
        public void testMatchMismatch()
        {
            AlignmentChecker         test;
            AlignmentChecker.Results results;

            test    = new AlignmentChecker();
            results = test.checkAlignment("AGTTA", "AGAGA");

            assertEquals(1, results.alignmentScore(), "Alignment Score");
            assertEquals("AGTTA", results.sequence1(), "Sequence 1");
            assertEquals("AGAGA", results.sequence2(), "Sequence 2");
        }

        @Test
        @DisplayName("finds best alignment of ACGT and GT (trailing match)")
        public void testTrialing()
        {
            AlignmentChecker         test;
            AlignmentChecker.Results results;

            test    = new AlignmentChecker();
            results = test.checkAlignment("ACGT", "GT");

            assertEquals(-2, results.alignmentScore(), "Alignment Score");
            assertEquals("ACGT", results.sequence1(), "Sequence 1");
            assertEquals("--GT", results.sequence2(), "Sequence 2");

            results = test.checkAlignment("GT", "ACGT");

            assertEquals(-2, results.alignmentScore(), "Alignment Score");
            assertEquals("--GT", results.sequence1(), "Sequence 1");
            assertEquals("ACGT", results.sequence2(), "Sequence 2");
        }

        @Test
        @DisplayName("finds best alignment of two long sequences")
        public void test4()
        {
            AlignmentChecker         test;
            AlignmentChecker.Results results;

            test    = new AlignmentChecker();
            results = test.checkAlignment("GTGTGTGTGGGTGCACATTTGTGTGTGTGTGCGCCTGTGTGTGTGGGTGCCTGTGTGTGT", "GTGTGTGTGGAAGTGAGTTCATCTGTGTGTGCACATGTGTGTGCATGCATGCATGTGT");

            assertEquals(22, results.alignmentScore(), "Alignment Score");
            assertEquals("GTGTGTGTGGGTGCACATTTGTGTGTGTGTGCGCCTGTGTGTGTGGGTGCCTGTGTGTGT", results.sequence1(), "Sequence 1");
            assertEquals("GTGTGTGTGGAAGTGAGTTCATCTGTGTGTGC-AC-ATGTGTGTGCATGCATGCATGTGT", results.sequence2(), "Sequence 2");
        }

        @Test
        @DisplayName("finds best alignment of ACGT and AC (leading match)")
        public void testleadingmatch()
        {
            AlignmentChecker         test;
            AlignmentChecker.Results results;

            test    = new AlignmentChecker();
            results = test.checkAlignment("ACGT", "AC");

            assertEquals(-2, results.alignmentScore(), "Alignment Score");
            assertEquals("ACGT", results.sequence1(), "Sequence 1");
            assertEquals("AC--", results.sequence2(), "Sequence 2");

            results = test.checkAlignment("AC", "ACGT");

            assertEquals(-2, results.alignmentScore(), "Alignment Score");
            assertEquals("AC--", results.sequence1(), "Sequence 1");
            assertEquals("ACGT", results.sequence2(), "Sequence 2");
        }

        @Test
        @DisplayName("finds best alignment of AT and CG (no match)")
        public void testnomatch()
        {
            AlignmentChecker         test;
            AlignmentChecker.Results results;

            test    = new AlignmentChecker();
            results = test.checkAlignment("AT", "CG");

            assertEquals(-2, results.alignmentScore(), "Alignment Score");
            assertEquals("AT", results.sequence1(), "Sequence 1");
            assertEquals("CG", results.sequence2(), "Sequence 2");
        }

        @Test
        @DisplayName("finds best alignment of ACGT and CG (middle match)")
        public void testmiddlematch()
        {
            AlignmentChecker         test;
            AlignmentChecker.Results results;

            test    = new AlignmentChecker();
            results = test.checkAlignment("ACGT", "CG");

            assertEquals(-2, results.alignmentScore(), "Alignment Score");
            assertEquals("ACGT", results.sequence1(), "Sequence 1");
            assertEquals("-CG-", results.sequence2(), "Sequence 2");

            results = test.checkAlignment("CG", "ACGT");

            assertEquals(-2, results.alignmentScore(), "Alignment Score");
            assertEquals("-CG-", results.sequence1(), "Sequence 1");
            assertEquals("ACGT", results.sequence2(), "Sequence 2");
        }

        @Test
        @DisplayName("finds best alignment of GAATTC & GATTA")
        public void testMatchMismatchGap()
        {
            AlignmentChecker         test;
            AlignmentChecker.Results results;

            test    = new AlignmentChecker();
            results = test.checkAlignment("GAATTC", "GATTA");

            assertEquals("GAATTC", results.sequence1(), "Sequence 1");
            assertEquals("G-ATTA", results.sequence2(), "Sequence 2");
        }

        @Test
        @DisplayName("throws exception on empty sequence")
        public void testEmpty()
        {
            AlignmentChecker         test;

            test    = new AlignmentChecker();

            assertThrows(Exception.class, () -> test.checkAlignment("ACGT", " "));
            assertThrows(Exception.class, () -> test.checkAlignment(" ", "ACGT"));
        }

        @Test
        @DisplayName("trims spaces from the outer edges of sequences")
        public void testSpace()
        {
            AlignmentChecker         test;
            AlignmentChecker.Results results;

            test    = new AlignmentChecker();
            results = test.checkAlignment("ACGT", " ACGT ");

            assertEquals("ACGT", results.sequence2(), "Sequence 2");

            results = test.checkAlignment(" ACGT ", "ACGT");

            assertEquals("ACGT", results.sequence1(), "Sequence 1");
        }
    }
}
