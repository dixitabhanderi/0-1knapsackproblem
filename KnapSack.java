/*
Dixita Bhanderi
CSC206 Assignment 4
Spring 2019
OS: Mac
*/
public class KnapSack
{
	public static void main(String[] args)
	{
		long DPTime;
		long BFTime;
		long BTTime;
		long BBTime1;
		long BBTime2;
		long BBTime3;
		long BBTime4;
		long BBTime5;
		long BBTime6;
		float speedup;
		int itemCnt;
		KnapsackInstance inst; //a Knapsack instance object
		KnapsackDPSolver DPSolver = new KnapsackDPSolver(); //dynamic programming solver
		KnapsackBFSolver BFSolver = new KnapsackBFSolver(); //brute-force solver
		KnapsackBTSolver BTSolver = new KnapsackBTSolver(); //backtracking solver
		KnapsackBBSolver BBSolver1 = new KnapsackBBSolver(UPPER_BOUND.UB1); //branch-and-bound solver with UB1
		KnapsackBBSolver BBSolver2 = new KnapsackBBSolver(UPPER_BOUND.UB2); //branch-and-bound solver with UB2
	    KnapsackBBSolver BBSolver3 = new KnapsackBBSolver(UPPER_BOUND.UB3); //branch-and-bound solver with UB3
	    KnapsackBBSolver BBSolver4 = new KnapsackBBSolver(UPPER_BOUND.UB4); //branch-and-bound solver with UB4
	    KnapsackBBSolver BBSolver5 = new KnapsackBBSolver(UPPER_BOUND.UB5); //branch-and-bound solver with UB5
	    KnapsackBBSolver BBSolver6 = new KnapsackBBSolver(UPPER_BOUND.UB6); //branch-and-bound solver with UB6

	    KnapsackSolution DPSoln;
		KnapsackSolution BFSoln;
		KnapsackSolution BTSoln;
		KnapsackSolution BBSoln1;
		KnapsackSolution BBSoln2;
		KnapsackSolution BBSoln3;
		KnapsackSolution BBSoln4;
		KnapsackSolution BBSoln5;
		KnapsackSolution BBSoln6;

		if (args.length != 1)
		{
			System.out.print("Invalid Number of command-line arguments\n");
			System.exit(1);
		}
		itemCnt = Integer.parseInt(args[0]);
		if (itemCnt < 1)
		{
			System.out.print("Invalid number of items\n");
			System.exit(1);
		}

		inst = new KnapsackInstance(itemCnt);
		DPSoln = new KnapsackSolution(inst);
		BFSoln = new KnapsackSolution(inst);
		BTSoln = new KnapsackSolution(inst);
		BBSoln1 = new KnapsackSolution(inst);
		BBSoln2 = new KnapsackSolution(inst);
		BBSoln3 = new KnapsackSolution(inst);
		BBSoln4 = new KnapsackSolution(inst);
		BBSoln5 = new KnapsackSolution(inst);
		BBSoln6 = new KnapsackSolution(inst);

		inst.Generate();
		inst.Print();

		long  startTime = System.nanoTime();
		DPSolver.Solve(inst,DPSoln);
		long elapsed = System.nanoTime()-startTime;
		DPTime =  (Long)(elapsed/1000000);

		System.out.printf("\n\nSolved using dynamic programming (DP) in "+DPTime+"ms Optimal value = "+DPSoln.GetValue());
		if (itemCnt <= DefineConstants.MAX_SIZE_TO_PRINT)
		{
			DPSoln.Print("Dynamic Programming Solution");
		}

		startTime = System.nanoTime();
		BFSolver.Solve(inst,BFSoln);
		elapsed = System.nanoTime()-startTime;
		BFTime = (Long)(elapsed/1000000);

		System.out.printf("\n\nSolved using brute-force enumeration (BF) in "+BFTime+"ms Optimal value = "+ BFSoln.GetValue());
		if (itemCnt <= DefineConstants.MAX_SIZE_TO_PRINT)
		{
			BFSoln.Print("Brute-Force Solution");
		}
		if (DPSoln.equalsTo(BFSoln))
		{
			System.out.print("\nSUCCESS: DP and BF solutions match");
		}
		else
		{
			System.out.print("\nERROR: DP and BF solutions mismatch");
		}

		startTime = System.nanoTime();
		BTSolver.Solve(inst,BTSoln);
		elapsed = System.nanoTime()-startTime;
		BTTime = (Long)(elapsed/1000000);

		System.out.printf("\n\nSolved using Back Tracking enumeration (Bt) in "+BTTime+"ms Optimal value = "+ BTSoln.GetValue());
		if (itemCnt <= DefineConstants.MAX_SIZE_TO_PRINT)
		{
			BTSoln.Print("Backtracking Solution");
		}
		if (BFSoln.equalsTo(BTSoln))
		{
			System.out.print("\nSUCCESS: BF and BT solutions match");
		}
		else
		{
			System.out.print("\nERROR: BF and BT solutions mismatch");
		}
		speedup = (float)(BTTime == 0? 0 : 100.0 * (BFTime - BTTime) / (float)BFTime);
		System.out.printf("\nSpeedup of BT relative to BF is"+speedup+"percent");

		startTime = System.nanoTime();
		BBSolver1.Solve(inst,BBSoln1);
		elapsed = System.nanoTime()-startTime;
		BBTime1 = (Long)(elapsed/1000000);

		System.out.printf("\n\nSolved using Branch and Bound enumeration in "+BBTime1 +"ms Optimal value = "+ BBSoln1.GetValue());
		if (itemCnt <= DefineConstants.MAX_SIZE_TO_PRINT)
		{
			BBSoln1.Print("BB-UB1 Solution");
		}
		if (BFSoln.equalsTo(BBSoln1))
		{
			System.out.print("\nSUCCESS: BF and BB-UB1 solutions match");
		}
		else
		{
			System.out.print("\nERROR: BF and BB-UB1 solutions mismatch");
		}
		speedup = (float)(BBTime1 == 0? 0 : 100.0 * (BFTime - BBTime1) / (float)BFTime);
		System.out.printf("\nSpeedup of BB-UB1 relative to BF is"+speedup+"percent");
		
		startTime = System.nanoTime();
		BBSolver2.Solve(inst,BBSoln2);
		elapsed = System.nanoTime()-startTime;
		BBTime2 = (Long)(elapsed/1000000);

		System.out.printf("\n\nSolved using Branch and Bound enumeration in "+BBTime2 +"ms Optimal value = "+ BBSoln1.GetValue());
		if (itemCnt <= DefineConstants.MAX_SIZE_TO_PRINT)
		{
			BBSoln2.Print("BB-UB2 Solution");
		}
		if (BFSoln.equalsTo(BBSoln2))
		{
			System.out.print("\nSUCCESS: BF and BB-UB2 solutions match");
		}
		else
		{
			System.out.print("\nERROR: BF and BB-UB2 solutions mismatch");
		}
		speedup = (float)(BBTime2 == 0? 0 : 100.0 * (BFTime - BBTime2) / (float)BFTime);
		System.out.printf("\nSpeedup of BB-UB2 relative to BF is"+speedup+"percent");

		startTime = System.nanoTime();
		BBSolver3.Solve(inst,BBSoln3);
		elapsed = System.nanoTime()-startTime;
		BBTime3 = (Long)(elapsed/1000000);

		System.out.printf("\n\nSolved using Branch and Bound enumeration in "+BBTime3 +"ms Optimal value = "+ BBSoln1.GetValue());
		if (itemCnt <= DefineConstants.MAX_SIZE_TO_PRINT)
		{
			BBSoln3.Print("BB-UB3 Solution");
		}
		if (BFSoln.equalsTo(BBSoln3))
		{
			System.out.print("\nSUCCESS: BF and BB-UB3 solutions match");
		}
		else
		{
			System.out.print("\nERROR: BF and BB-UB3 solutions mismatch");
		}
		speedup = (float)(BBTime3 == 0? 0 : 100.0 * (BFTime - BBTime3) / (float)BFTime);
		System.out.printf("\nSpeedup of BB-UB3 relative to BF is"+speedup+"percent");

		//Extra Credit
		startTime = System.nanoTime();
		BBSolver4.Solve(inst,BBSoln4);
		elapsed = System.nanoTime()-startTime;
		BBTime4 = (Long)(elapsed/1000000);

		System.out.printf("\n\nSolved using Branch and Bound enumeration in "+BBTime4 +"ms Optimal value = "+ BBSoln1.GetValue());
		if (itemCnt <= DefineConstants.MAX_SIZE_TO_PRINT)
		{
			BBSoln4.Print("BB-UB4 Extra Credit Solution");
		}
		if (BFSoln.equalsTo(BBSoln4))
		{
			System.out.print("\nSUCCESS: BF and BB-UB4 Extra Credit solutions match");
		}
		else
		{
			System.out.print("\nERROR: BF and BB-UB4 Extra Credit solutions mismatch");
		}
		speedup = (float)(BBTime4 == 0? 0 : 100.0 * (BFTime - BBTime4) / (float)BFTime);
		System.out.printf("\nSpeedup of BB-UB4 Extra Credit relative to BF is"+speedup+"percent");

		startTime = System.nanoTime();
		BBSolver5.Solve(inst,BBSoln5);
		elapsed = System.nanoTime()-startTime;
		BBTime5 = (Long)(elapsed/1000000);

		System.out.printf("\n\nSolved using Branch and Bound enumeration in "+BBTime5 +"ms Optimal value = "+ BBSoln1.GetValue());
		if (itemCnt <= DefineConstants.MAX_SIZE_TO_PRINT)
		{
			BBSoln5.Print("BB-UB5 Extra Credit Fractional Knapsack Upper Bound O(1) Solution");
		}
		if (BFSoln.equalsTo(BBSoln5))
		{
			System.out.print("\nSUCCESS: BF and BB-UB5 Extra Credit Fractional Knapsack Upper Bound O(1) solutions match");
		}
		else
		{
			System.out.print("\nERROR: BF and BB-UB5 Extra Credit Fractional Knapsack Upper Bound O(1) solutions mismatch");
		}
		speedup = (float)(BBTime5 == 0? 0 : 100.0 * (BFTime - BBTime5) / (float)BFTime);
		System.out.printf("\nSpeedup of BB-UB5 Extra Credit relative to BF is"+speedup+"percent");

		startTime = System.nanoTime();
		BBSolver6.Solve(inst,BBSoln6);
		elapsed = System.nanoTime()-startTime;
		BBTime6 = (Long)(elapsed/1000000);

		System.out.printf("\n\nSolved using Branch and Bound enumeration in "+BBTime6 +"ms Optimal value = "+ BBSoln1.GetValue());
		if (itemCnt <= DefineConstants.MAX_SIZE_TO_PRINT)
		{
			BBSoln6.Print("BB-UB6 Extra Credit Solution");
		}
		if (BFSoln.equalsTo(BBSoln6))
		{
			System.out.print("\nSUCCESS: BF and BB-UB6 Extra Credit solutions match");
		}
		else
		{
			System.out.print("\nERROR: BF and BB-UB6 Extra Credit solutions mismatch");
		}
		speedup = (float)(BBTime6 == 0? 0 : 100.0 * (BFTime - BBTime6) / (float)BFTime);
		System.out.printf("\nSpeedup of BB-UB6 Extra Credit relative to BF is"+speedup+"percent");

		inst = null;
		DPSoln = null;
		BFSoln = null;
		BTSoln = null;
		BBSoln1 = null;
		BBSoln2 = null;
		BBSoln3 = null;
		BBSoln4 = null;
		BBSoln6 = null;
		BBSoln6 = null;

		System.out.print("\n\nProgram Completed Successfully\n");

	}
}
