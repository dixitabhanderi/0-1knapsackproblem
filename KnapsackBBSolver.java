/*
Dixita Bhanderi
CSC206 Assignment 4
Spring 2019
OS: Mac
*/
// Branch-and-Bound solver
public class KnapsackBBSolver extends KnapsackBFSolver
{
	protected UPPER_BOUND ub;
	protected KnapsackInstance inst;
	protected KnapsackSolution crntSoln;
	protected KnapsackSolution bestSoln;
	protected int slackPointer;
	protected int slackRemainingWeight;
	protected double fullKnapsackSolution;

	public void FindSolnsUB1(int itemNum)
	{
		if (crntSoln.GetWeight() > inst.GetCapacity()) return;

		int itemCnt = inst.GetItemCnt();
    
		if (itemNum == itemCnt + 1)
		{
			CheckCrntSoln();
			return;
		}
		if (inst.GetTotalValue() - crntSoln.GetValueNT() <= bestSoln.GetValue() ) return; // Taken values + Undecided
		
		crntSoln.TakeItem(itemNum);
		FindSolnsUB1(itemNum + 1);
		crntSoln.UndoTakeItem(itemNum);

		crntSoln.DontTakeItem(itemNum);
		FindSolnsUB1(itemNum + 1);
		crntSoln.UndoDontTakeItem(itemNum);
	}

	public void FindSolnsUB2(int itemNum)
	{
		if (crntSoln.GetWeight() > inst.GetCapacity()) return;

		int itemCnt = inst.GetItemCnt();
    
		if (itemNum == itemCnt + 1)
		{
			CheckCrntSoln();
			return;
		}
		if ((crntSoln.GetValue()+ UndecidedFitItemValueUB2(itemNum, itemCnt)) <= bestSoln.GetValue() ) return; // Taken Values + Undecided items that fits
		
		crntSoln.TakeItem(itemNum);
		FindSolnsUB2(itemNum + 1);
		crntSoln.UndoTakeItem(itemNum);

		crntSoln.DontTakeItem(itemNum);
		FindSolnsUB2(itemNum + 1);
		crntSoln.UndoDontTakeItem(itemNum);
	}

	public void FindSolnsUB3(int itemNum)
	{
		if (crntSoln.GetWeight() > inst.GetCapacity()) return;

		int itemCnt = inst.GetItemCnt();
    
		if (itemNum == itemCnt + 1)
		{
			CheckCrntSoln();
			return;
		}
		if ((crntSoln.GetValue()+ FractionalKnapsackUB3(itemNum, itemCnt)) <= bestSoln.GetValue() ) return; //Fractional knapsack solution for remaining items sorted by v/w
		
		crntSoln.TakeItem(itemNum);
		FindSolnsUB3(itemNum + 1);
		crntSoln.UndoTakeItem(itemNum);

		crntSoln.DontTakeItem(itemNum);
		FindSolnsUB3(itemNum + 1);
		crntSoln.UndoDontTakeItem(itemNum);
	}

	public void FindSolnsExtraCreditUB4(int itemNum)
	{
		if (crntSoln.GetWeight() > inst.GetCapacity()) return;

		int itemCnt = inst.GetItemCnt();
    
		if (itemNum == itemCnt + 1)
		{
			CheckCrntSoln();
			return;
		}
		if ((crntSoln.GetValue()+ FractionalKnapsackExtraCreditUB3(itemNum, itemCnt)) <= bestSoln.GetValue() ) return; //Fractional knapsack solution for remaining items sorted by v/w
		
		crntSoln.TakeItem(itemNum);
		FindSolnsExtraCreditUB4(itemNum + 1);
		crntSoln.UndoTakeItem(itemNum);
		
		if (inst.GetTotalValue() - crntSoln.GetValueNT() < bestSoln.GetValue() ) return; // Taken values + Undecided
		crntSoln.DontTakeItem(itemNum);
		FindSolnsExtraCreditUB4(itemNum + 1);
		crntSoln.UndoDontTakeItem(itemNum);
	}

	public void FindSolnsExtraCreditUB5(int itemNum)
	{
		if (crntSoln.GetWeight() > inst.GetCapacity()) return;

		int itemCnt = inst.GetItemCnt();
    
		if (itemNum == itemCnt + 1)
		{
			CheckCrntSoln();
			return;
		}
		if (fullKnapsackSolution <= bestSoln.GetValue() ) return; //Fractional knapsack solution for remaining items sorted by v/w
		crntSoln.TakeItem(itemNum);
		FindSolnsExtraCreditUB5(itemNum + 1);
		crntSoln.UndoTakeItem(itemNum);

		ModifyFractionalSolution(itemNum); // item is not taken so update fractional knapsack solution exist
		crntSoln.DontTakeItem(itemNum);
		FindSolnsExtraCreditUB5(itemNum + 1);
		crntSoln.UndoDontTakeItem(itemNum);
	}

	public void FindSolnsExtraCreditUB6(int itemNum)
	{
		if (crntSoln.GetWeight() > inst.GetCapacity()) return;

		int itemCnt = inst.GetItemCnt();
    
		if (itemNum == itemCnt + 1)
		{
			CheckCrntSoln();
			return;
		}
		if ((crntSoln.GetValue()+ UndecidedFitItemValueUB2(itemNum, itemCnt)) <= bestSoln.GetValue() ) return; // Taken Values + Undecided items that fits
		
		crntSoln.TakeItem(itemNum);
		FindSolnsExtraCreditUB6(itemNum + 1);
		crntSoln.UndoTakeItem(itemNum);

		if (inst.GetTotalValue() - crntSoln.GetValueNT() < bestSoln.GetValue() ) return; // Taken values + Undecided
		crntSoln.DontTakeItem(itemNum);
		FindSolnsExtraCreditUB6(itemNum + 1);
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
		} 
		return;		
	}
	public void ModifyFractionalSolution(int curNode){ //with amortized linear time
		int newRemainingCap = inst.GetItemWeight(curNode);
		int itemCnt = inst.GetItemCnt();
		double fraction = 0.0;
		double newSlackValue = 0.0;
		// System.out.printf(" node not to take i= %d\n", curNode);
		// System.out.printf(" node not to take slackPointer i= %d\n", slackPointer);
		if(curNode != slackPointer){
			if( slackRemainingWeight >= newRemainingCap ){
				fraction = newRemainingCap / inst.GetItemWeight(slackPointer);
				fraction = fraction * inst.GetItemValue(slackPointer);
				newSlackValue = newSlackValue +  fraction;
				slackRemainingWeight -= newRemainingCap; // updating global
				newRemainingCap -= newRemainingCap;
			}else{
				
				fraction = slackRemainingWeight / inst.GetItemWeight(slackPointer);
				fraction = fraction * inst.GetItemValue(slackPointer);
				newRemainingCap -= slackRemainingWeight;
				// slackPointer++; // updating global
				for (int i = slackPointer; i <= itemCnt; i++ ) { // this loop will execute aggragate of O(n) time only
				// System.out.printf(" loop\n");

					slackPointer = i; // updating global
					if(inst.GetItemWeight(i) <= newRemainingCap){
						newSlackValue += inst.GetItemValue(i);
						newRemainingCap -= inst.GetItemWeight(i);
					}
					else{
						fraction = (double)(inst.GetItemValue(i)/inst.GetItemWeight(i));
						fraction = newRemainingCap*fraction;
						newSlackValue += fraction;
						slackRemainingWeight -= newRemainingCap; // updating global
						newRemainingCap -= newRemainingCap;
						break;
					}
				}

			}
		fullKnapsackSolution = fullKnapsackSolution - inst.GetItemValue(curNode) + newSlackValue; //updating global
		}else{
			fraction = inst.GetItemValue(slackPointer)/ inst.GetItemWeight(slackPointer);
			fraction = fraction*(inst.GetItemWeight(slackPointer) - slackRemainingWeight);
			fullKnapsackSolution = fullKnapsackSolution - fraction; //updating global
			slackRemainingWeight = 0;
		}
	}
	public int UndecidedFitItemValueUB2(int curNode, int totalItem)
	{
		int rC = inst.GetCapacity() - crntSoln.GetWeight();
		int valueUB2 = 0;
		for (int i = curNode; i<= totalItem; i++) {
			if(inst.GetItemWeight(i) <= rC){
				valueUB2 += inst.GetItemValue(i);
			}
		}
		return valueUB2;
	}
	public double FractionalKnapsackUB3(int curNode, int totalItem)
	{
		int rC = inst.GetCapacity() - crntSoln.GetWeight(); //remaining capacity
		double valueUB3 = 0.0;
		double fraction = 0.0;
		for (int i = curNode; i<= totalItem; i++) {
			if(inst.GetItemWeight(i) <= rC){
				valueUB3 += inst.GetItemValue(i);
				rC -= inst.GetItemWeight(i);
			}
			else{
				fraction = (double)(inst.GetItemValue(i)/inst.GetItemWeight(i));
				fraction = rC*fraction;
				valueUB3 += fraction;
				slackRemainingWeight = inst.GetItemWeight(i) - rC;
				rC -= rC;
				slackPointer = i;
				break;
			}
		}
		return valueUB3;
	}

	public double FractionalKnapsackExtraCreditUB3(int curNode, int totalItem)
	{
		int i = 0;
		double fraction = 0.0, valueUB4 = 0.0;
		int rC = inst.GetCapacity();
		for(i = curNode;i <= totalItem; i++){
			if(inst.GetItemWeight(i) <= rC - crntSoln.GetWeight()){
				valueUB4 = valueUB4 + inst.GetItemValue(i);
			}else{
				fraction = (rC - crntSoln.GetWeight()) / inst.GetItemWeight(i);
				fraction = fraction * inst.GetItemValue(i);
				valueUB4 = valueUB4 +  fraction;
				break;
			}
		}
		return valueUB4;
	}
	public KnapsackBBSolver(UPPER_BOUND ub_)
	{
		ub = ub_;
		crntSoln = null;
		slackPointer = 1;
		slackRemainingWeight = 0;
		fullKnapsackSolution = 0.0;
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
		// FindSolns(1);
		if(ub==UPPER_BOUND.UB1){
			FindSolnsUB1(1);
		}else if(ub==UPPER_BOUND.UB2){
			FindSolnsUB2(1);
		}else if(ub==UPPER_BOUND.UB3){
			inst.SortItemVW();
			FindSolnsUB3(1);
		}else if(ub==UPPER_BOUND.UB4){
			FindSolnsExtraCreditUB4(1);
		}else if(ub==UPPER_BOUND.UB5){
			// inst.SortItemVW(); //instance is already sorted in UB3 hence no need
			int itemCnt = inst.GetItemCnt();
			fullKnapsackSolution = FractionalKnapsackUB3(1, itemCnt);
			// System.out.printf("fullKnapsackSolution = %f ", fullKnapsackSolution);
			// System.out.printf("slackPointer = %d ", slackPointer);
			// for (int i = 1; i <= inst.GetItemCnt(); i++)
			// {
			// 	System.out.printf("%d ", inst.GetItemWeight(i));
			// }
			// System.out.print("\nValues: ");
			// for (int i = 1; i <= inst.GetItemCnt(); i++)
			// {
			// 	System.out.printf("%d ",inst.GetItemValue(i));
			// }
			FindSolnsExtraCreditUB5(1);
		}else if(ub==UPPER_BOUND.UB6){
			// inst.SortItemVW(); //instance is already sorted in UB3 hence no need
			FindSolnsExtraCreditUB6(1);
		}
	}
}