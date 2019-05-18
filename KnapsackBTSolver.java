/*
Dixita Bhanderi
CSC206 Assignment 4
Spring 2019
OS: Mac
*/
import java.util.*;

// Backtracking solver
public class KnapsackBTSolver extends KnapsackBFSolver
{

	protected KnapsackInstance inst;
	protected KnapsackSolution crntSoln;
	protected KnapsackSolution bestSoln;

	public void FindSolns(int itemNum)
	{
		if (crntSoln.GetWeight() > inst.GetCapacity()) return;

		int itemCnt = inst.GetItemCnt();
    
		if (itemNum == itemCnt + 1)
		{
			CheckCrntSoln();
			return;
		}
		crntSoln.TakeItem(itemNum);
		FindSolns(itemNum + 1);
		crntSoln.UndoTakeItem(itemNum);
		
		crntSoln.DontTakeItem(itemNum);
		FindSolns(itemNum + 1);
		crntSoln.UndoDontTakeItem(itemNum);
		
	}
	public void CheckCrntSoln()
	{
		int crntVal = crntSoln.GetValue();
		if (crntSoln.GetWeight() <= inst.GetCapacity()){
			if (crntVal > bestSoln.GetValue())
			{
				bestSoln.Copy(crntSoln);
			}
		return;
		}
		return;		
	}

	public KnapsackBTSolver()
	{
		crntSoln = null;
	}

	public void close()
	{
		if (crntSoln != null)
		{
			crntSoln = null;
		}
	}
	public void Solve(KnapsackInstance inst_, KnapsackSolution soln_)
	{
		inst = inst_;
		bestSoln = soln_;
		crntSoln = new KnapsackSolution(inst);
		FindSolns(1);
	}
}