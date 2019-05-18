/*
Dixita Bhanderi
CSC206 Assignment 4
Spring 2019
OS: Mac
*/
import java.util.*;

public class KnapsackSolution implements java.io.Closeable
{
	private boolean [] isTaken;
	private int valueT;
	private int valueNT;
	private int wghtT;
	private KnapsackInstance inst;

	public KnapsackSolution(KnapsackInstance inst_)
	{
		int i;
		int itemCnt = inst_.GetItemCnt();
    
		inst = inst_;
		isTaken = new boolean[itemCnt + 1];
		valueT = 0;
		valueNT = 0;
		// valueT = DefineConstants.INVALID_VALUE;
		// valueNT = DefineConstants.INVALID_VALUE;
    
		for (i = 1; i <= itemCnt; i++)
		{
			isTaken[i] = false;
		}
	}
	public void close()
	{
		isTaken = null;
	}

	public void TakeItem(int itemNum)
	{
		valueT += inst.GetItemValue(itemNum);
		wghtT += inst.GetItemWeight(itemNum);
		isTaken[itemNum] = true;
	}
	public void DontTakeItem(int itemNum)
	{
		valueNT += inst.GetItemValue(itemNum);
		isTaken[itemNum] = false;
	}
	public void UndoTakeItem(int itemNum)
	{
		valueT -= inst.GetItemValue(itemNum);
		wghtT -= inst.GetItemWeight(itemNum);
	}
	public void UndoDontTakeItem(int itemNum)
	{
		valueNT -= inst.GetItemValue(itemNum);
	}
	public int ComputeValue()
	{
		int i;
		int itemCnt = inst.GetItemCnt();
		int weight = 0;
    
		valueT = 0;
		for (i = 1; i <= itemCnt; i++)
		{
			if (isTaken[i] == true)
			{
				weight += inst.GetItemWeight(i);
				if (weight > inst.GetCapacity())
				{
					valueT = DefineConstants.INVALID_VALUE;
					break;
				}
				valueT += inst.GetItemValue(i);
			}
		}
		return valueT;
	}

	public int GetValue()
	{
		return valueT;
	}
	public int GetWeight()
	{
		return wghtT;
	}
	public int GetValueNT()
	{
		return valueNT;
	}
	public void Print(String title)
	{
		int i;
		int itemCnt = inst.GetItemCnt();
    
		System.out.printf("\n%s: ",title);
		for (i = 1; i <= itemCnt; i++)
		{
			if (isTaken[i] == true)
			{
				System.out.printf("%d ",i);
			}
		}
		System.out.printf("\nValue = %d\n",valueT);
    
	}
	public void Copy(KnapsackSolution otherSoln)
	{
		int i;
		int itemCnt = inst.GetItemCnt();
    
		for (i = 1; i <= itemCnt; i++)
		{
			isTaken[i] = otherSoln.isTaken[i];
		}
		valueT = otherSoln.valueT;
	}
	public boolean equalsTo (KnapsackSolution otherSoln)
	{
		return valueT == otherSoln.valueT;
	}
	
	public void dispose()
	{
		isTaken = null;
	}	
}