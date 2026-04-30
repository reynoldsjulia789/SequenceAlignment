# Dynamic Programming Justification

## Instructions

Include a brief text justification based on your implementation of why your code runs faster
than with a brute-force top-down approach.
Describe your subproblems and how you are avoiding subproblem overlap.

---
### Why this runs faster than with a brute-force top-down approach
In a brute-force top-down approach, every subproblem would need to be calculated even though there
is overlap between the subproblems. In other words, the overlapping subproblems would have to be
calculated each time they are seen as if they are being seen for the first time.
By storing the solutions to each subproblem as we go, we reduce the amount of work that needs to 
be done to reach a final solution.

---
### Subproblems

There are 3 subproblems to each problem:
1. The cost of a match/mismatch plus the cost of the optimal alignment of the previous letter
   in each DNA sequence
2. The cost of a gap plus cost of the optimal alignment of the previous letter in the first DNA
   sequence and the current letter in the second DNA sequence
3. The cost of a gap plus cost of the optimal alignment of the current letter in the first DNA
   sequence and the previous letter in the second DNA sequence

---
### How subproblem overlap is avoided

Subproblem overlap is avoided by storing the solution to each
subproblem that we encounter as we work towards the final solution.
This greatly reduces the number of calculations we need to perform
along the way. 
