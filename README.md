# Sequence Alignment
Algorithms (CSCD 320) Project - 
An algorithm that uses dynamic programming to 
determine the optimal alignment of two 
DNA sequences.

### Input
#### A file called input.txt of the following form:

- **Line 1:** The characters of the first string, 
with no leading or trailing whitespace. Assume 
these characters are all uppercase
and members of the set {A, C, G, T}

- **Line 2:** The characters of the second 
string, with no leading or trailing 
whitespace. Assume these characters are all
uppercase and members of the set {A, C, G, T}

- **Line 3:** The exact text "Match %d" 
where %d is an integer representing the 
score of a match bonus

- **Line 4:** The exact text "Mismatch %d" 
where %d is an integer representing the 
score of a mismatch penalty

- **Line 5:** The exact text "Gap %d" where %d 
is an integer representing the score of a 
gap penalty

### Output 
#### A file called output.txt with the optimal alignment of the two sequences.

- **Line 1:** The score of the optimal alignment

- **Line 2:** The characters of the first 
string from the input file, with gaps 
("-") added where necessary to create 
the optimal alignment score.

- **Line 3:** The characters of the second 
string from the input file, with gaps ("-") 
added where necessary to create the optimal 
alignment score.