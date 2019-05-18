/*
Dixita Bhanderi
CSC206 Assignment 4
Spring 2019
OS: Mac
*/
import java.util.*;
import java.lang.*;

// Dynamic programming solver
public class KnapsackDPSolver implements java.io.Closeable
{
	private KnapsackInstance inst;
	private KnapsackSolution soln;

	public void FindSolns()
	{
		int cap = inst.GetCapacity();
		int n = inst.GetItemCnt();
		int[][] t = new int[n+1][cap+1];

		for (int j = 0; j <= cap ; j++ ) {
			t[0][j] = 0; //fill the first row with 0's
		}
		
		for (int i=1; i<=n; i++ ) {
			for (int j=0; j<=cap; j++ ) {
				if( inst.GetItemWeight(i) > j)
					t[i][j] = t[i-1][j];
				else
					t[i][j] = Math.max( (inst.GetItemValue(i)+t[i-1][j-inst.GetItemWeight(i)]), t[i-1][j] );
			}
		}
		
		int j = cap;
		for (int i=n; i>=1  ; i-- ) {
			if (t[i][j] > t[i-1][j]) {
				soln.TakeItem(i);
				j = j-inst.GetItemWeight(i);
			}
		}
	}
	public KnapsackDPSolver()
	{
		soln = null;
	}
	public void close()
	{
    	if (soln != null)
		{
			soln = null;
		}
	}
	public void Solve(KnapsackInstance inst_, KnapsackSolution soln_)
	{
		inst = inst_;
		soln = soln_;
		FindSolns();
	}
}