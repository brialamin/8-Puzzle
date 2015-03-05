/*
 * Author: Samuel Adams
 * Date: October 4, 2014
 * Program 1: Using the A* algorithm to solve the 8 puzzle
 * 
 * NOTES:  The program currently is not working as intended, and I'm not entirely sure why
 * but I've run out of time and need to submit.  It does not accept inputs nor does it
 * print the data out to a file.
 * 
 */
import java.util.*;

public class puzzle{
	public static void main (String[] args){
		if(args.length > 0){
			/*try{
				BufferedReader br = new BufferedReader(new FileReader(args[0]));
			}*/
		}
		else
		{	//initialize variables
			Scanner input = new Scanner(System.in);
			String[] topLine;
			String[] secondLine;
			String[] thirdLine;
			int[] top = new int[3];
			int[] middle = new int[3];
			int[] bottom = new int[3];
			int[] checkPuzzleArray = new int[9];
			int[][] puzzle = new int[3][3];
			String lineRead;
			
			//handle input from user
			System.out.println("Input the 8 puzzle initial state one line at a time, starting with the top row");
			System.out.println("and separating each number with a space, using 0 for the blank: ");
			lineRead = input.nextLine();
			topLine = lineRead.split(" ");
			System.out.println("Second row: ");
			lineRead = input.nextLine();
			secondLine = lineRead.split(" ");
			System.out.println("Third row: ");
			lineRead = input.nextLine();
			thirdLine = (lineRead.split(" "));
			input.close();
			for(int i = 0; i < 3; i++)
			{
				top[i] = Integer.parseInt(topLine[i]);
				middle[i] = Integer.parseInt(secondLine[i]);
				bottom[i] = Integer.parseInt(thirdLine[i]);
			}
			createPuzzleArrays(top, middle, bottom, checkPuzzleArray, puzzle);
			if(isSolvable(checkPuzzleArray))
			{
				System.out.println("Here are the steps to the goal state:");
				printPuzzle(puzzle);
				makeStep(puzzle, 0);
				System.out.println("Given puzzle is SOLVED!");
			}
			else
			{
				System.out.println("Given puzzle:");
				printPuzzle(puzzle);
				System.out.println("is NOT solvable!");
			}
					
		}
	}			
			
		
			//functions
	public static void createPuzzleArrays(int[] top, int[] middle, int[] bottom, int[] solvability, int[][] puzzle)
	{
		//makes two different arrays, one to check the solvability of the puzzle, and the other to actually work with
		//this is for the solvability testing
		for(int i = 0; i < top.length; i++)
		{
			solvability[3*i] = top[i];
			solvability[3*i + 1] = middle[i];
			solvability[3*i + 2] = bottom[i];
		}
		//this is for the array to actually be worked with.
		puzzle[0] = top;
		puzzle[1] = middle;
		puzzle[2] = bottom;
	}
	//given code to check the solvability of a puzzle
	public static boolean isSolvable(int[] puzzle)
	{
		int i, j, n, inversions = 0;
		n = puzzle.length;
		for(i = 0; i < n - 1; i++)
		{
			for(j = i + 1; j < n; j++)
			{
				if(puzzle[i] == 0 || puzzle[j] == 0)
				{
					
				}
				else if(puzzle[i] > puzzle[j])
				{
					inversions++;
				}
			}
		}
		return !((inversions > 0) && (inversions % 2 == 0));
	}
	//calculates h(n)
	public static int h_n(int[][] puzzle)
	{
		int hn = 0;
		int[][] goalState = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
		int[][] twoDimensionalPuzzle = puzzle;
		//the nested 4 loops is to compare each value from the puzzle to each value of the goal state
		for (int i = 0; i < 3; i++)
		{
			for (int j = 0; j < 3; j++)
			{
				for (int k = 0; k < 3; k++)
				{
					for (int l = 0; l < 3; l++)
					{
						if (twoDimensionalPuzzle[k][l] == goalState[i][j])
						{
							hn += Math.abs(k-i) + Math.abs(l-j);
						}
					}
				}
			}
		}
		return hn;
	}
	//calculates g(n)
	public static int g_n(int numberOfSteps)
	{
		int gn = numberOfSteps + 1;
		return gn;
	}
	//calculates f(n)
	public static int f_n(int hn, int gn)
	{
		int fn = hn + gn;
		return fn;
	}
	public static void makeStep(int[][] puzzle, int currentStep)
	{
		String[] stepChoices;
		stepChoices = whichChecks(puzzle);
		for(int i = 0; i < stepChoices.length; i++)
		{
			System.out.println(stepChoices[i]);
		}
		int[][] goalState = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
		int[] heuristicValues = new int[stepChoices.length];
		while(puzzle != goalState)
		{
			for (int i = 0; i < stepChoices.length; i++)
			{
				if(stepChoices[i] == "up")
				{
					heuristicValues[i] = checkUp(puzzle, currentStep);
				}
				else if(stepChoices[i] == "down")
				{
					heuristicValues[i] = checkDown(puzzle, currentStep);
				}
				else if(stepChoices[i] == "left")
				{
					heuristicValues[i] = checkLeft(puzzle, currentStep);
				}
				else if(stepChoices[i] == "right")
				{
					heuristicValues[i] = checkRight(puzzle, currentStep);
				}
			}
			int min = heuristicValues[0];
		    for (int i = 0; i < heuristicValues.length; i++)
		    {
		        if (heuristicValues[i] < min)
		        {
		            min = heuristicValues[i];
		        }
		    }
		    int locationMin = 0;
		    for (int i = 0; i < heuristicValues.length; i++)
		    {
		    	if(heuristicValues[i] == min)
		    	{
		    		locationMin = i;
		    	}
		    }
		    if(stepChoices[locationMin] == "up")
			{
				moveUp(puzzle, currentStep);
				printPuzzle(puzzle);
			}
			else if(stepChoices[locationMin] == "down")
			{
				moveDown(puzzle, currentStep);
				printPuzzle(puzzle);
			}
			else if(stepChoices[locationMin] == "left")
			{
				moveLeft(puzzle, currentStep);
				printPuzzle(puzzle);
			}
			else if(stepChoices[locationMin] == "right")
			{
				moveRight(puzzle, currentStep);
				printPuzzle(puzzle);
			}
		}
		return;
	}
	
	public static void moveUp(int[][] puzzle, int currentMove)
	{
		for (int i = 0; i < puzzle.length; i++)
		{
			for (int j = 0; j < puzzle[0].length; j++)
			{
				if(puzzle[i][j] == 0)
				{
					puzzle[i][j] = puzzle[i-1][j];
					puzzle[i-1][j] = 0;
					return;
				}
			}
		}
	}
	
	public static void moveDown(int[][] puzzle, int currentMove)
	{
		for (int i = 0; i < puzzle.length; i++)
		{
			for (int j = 0; j < puzzle[0].length; j++)
			{
				if(puzzle[i][j] == 0)
				{
					puzzle[i][j] = puzzle[i+1][j];
					puzzle[i+1][j] = 0;
					return;
				}
			}
		}
	}
	
	public static void moveLeft(int[][] puzzle, int currentMove)
	{
		for (int i = 0; i < puzzle.length; i++)
		{
			for (int j = 0; j < puzzle[0].length; j++)
			{
				if(puzzle[i][j] == 0)
				{
					puzzle[i][j] = puzzle[i][j+1];
					puzzle[i][j+1] = 0;
					return;
				}
			}
		}
	}
	
	public static void moveRight(int[][] puzzle, int currentMove)
	{
		for (int i = 0; i < puzzle.length; i++)
		{
			for (int j = 0; j < puzzle[0].length; j++)
			{
				if(puzzle[i][j] == 0)
				{
					puzzle[i][j] = puzzle[i][j-1];
					puzzle[i][j-1] = 0;
					return;
				}
			}
		}
	}
	
	public static int checkUp(int[][] puzzle, int currentMove)
	{
		int hn = 0;
		int gn = 0;
		int fn = 0;
		int[][] testPuzzle = puzzle;
		outerloop:
		for (int i = 0; i < puzzle.length; i++)
		{
			for (int j = 0; j < puzzle[0].length; j++)
			{
				if(testPuzzle[i][j] == 0)
				{
					testPuzzle[i][j] = testPuzzle[i-1][j];
					testPuzzle[i-1][j] = 0;
					break outerloop;
				}
			}
		}
		hn = h_n(testPuzzle);
		//gn = g_n(currentMove);
		//fn = f_n(hn, gn);
		System.out.println(hn);
		return hn;
	}
	
	public static int checkDown(int[][] puzzle, int currentMove)
	{
		int hn = 0;
		int gn = 0;
		int fn = 0;
		int[][] testPuzzle = puzzle;
		outerloop:
		for (int i = 0; i < puzzle.length; i++)
		{
			for (int j = 0; j < puzzle[0].length; j++)
			{
				if(testPuzzle[i][j] == 0)
				{
					testPuzzle[i][j] = testPuzzle[i+1][j];
					testPuzzle[i+1][j] = 0;
					break outerloop;
				}
			}
		}
		hn = h_n(testPuzzle);
		//gn = g_n(currentMove);
		//fn = f_n(hn, gn);
		System.out.println(hn);
		return hn;
	}
	
	public static int checkRight(int[][] puzzle, int currentMove)
	{
		int hn = 0;
		int gn = 0;
		int fn = 0;
		int[][] testPuzzle = puzzle;
		outerloop:
		for (int i = 0; i < puzzle.length; i++)
		{
			for (int j = 0; j < puzzle[0].length; j++)
			{
				if(testPuzzle[i][j] == 0)
				{
					testPuzzle[i][j] = testPuzzle[i][j+1];
					testPuzzle[i][j+1] = 0;
					break outerloop;
				}
			}
		}
		hn = h_n(testPuzzle);
		//gn = g_n(currentMove);
		//fn = f_n(hn, gn);
		System.out.println(hn);
		return hn;
	}
	
	public static int checkLeft(int[][] puzzle, int currentMove)
	{
		int hn = 0;
		int gn = 0;
		int fn = 0;
		int[][] testPuzzle = puzzle;
		outerloop:
		for (int i = 0; i < puzzle.length; i++)
		{
			for (int j = 0; j < puzzle[0].length; j++)
			{
				if(testPuzzle[i][j] == 0)
				{
					testPuzzle[i][j] = testPuzzle[i][j-1];
					testPuzzle[i][j-1] = 0;
					break outerloop;
				}
			}
		}
		hn = h_n(testPuzzle);
		//gn = g_n(currentMove);
		//fn = f_n(hn, gn);
		System.out.println(hn);
		return hn;
	}
	
	public static String[] whichChecks(int[][] puzzle)
	{
		String checks[] = new String[4];
		for (int i = 0; i < puzzle.length; i++)
		{
			for (int j = 0; j < puzzle[0].length; j++)
			{
				if(puzzle[i][j] == 0)
				{
					if (j == 0 && i == 0)
					{
						checks = new String[] {"right", "down"};
					}
					else if (j == 0 && i == 1)
					{
						checks = new String[] {"up", "right", "down"};
					}
					else if (j == 0 && i == 2)
					{
						checks = new String[] {"up", "right"};
					}
					else if (j == 1 && i == 0)
					{
						checks = new String[] {"left", "right", "down"};
					}
					else if (j == 1 && i == 1)
					{
						checks = new String[] {"up", "left", "right", "down"};
					}
					else if (j == 1 && i == 2)
					{
						checks = new String[] {"up", "left", "right"};
					}
					else if (j == 2 && i == 0)
					{
						checks = new String[] {"left", "down"};
					}
					else if (j == 2 && i == 1)
					{
						checks = new String[] {"left", "up", "down"};
					}
					else if (j == 2 && i == 2)
					{
						checks = new String[] {"left", "up"};
					}
				}
			}
		}
		return checks;
	}
	
	public static void printPuzzle(int[][] puzzle)
	{
		for (int i = 0; i < 3; i++)
		{
			for (int j = 0; j < 3; j++)
			{
				if(j == 2)
				{
					System.out.println(puzzle[i][j]);
				}
				else
				{
					System.out.print(puzzle[i][j] + " ");
				}
			}
		}
	}
}