/*
Dixita Bhanderi
CSC206 Assignment 4
Spring 2019
OS: Mac
*/
import java.util.*;

public class KnapsackInstance implements java.io.Closeable
{
	private int itemCnt; //Number of items
	private int cap; //The capacity
	private int totalValue; //The total value of all items
	private int[] weights; //An array of weights
	private int[] values; //An array of values

	public KnapsackInstance(int itemCnt_)
	{
		itemCnt = itemCnt_;

		weights = new int[itemCnt + 1];
		values = new int[itemCnt + 1];
		cap = 0;
	}
	public void close()
	{
		weights = null;
		values = null;
	}

	public void Generate()
	{
		int i;
        int wghtSum;

		weights[0] = 0;
		values[0] = 0;

		wghtSum = 0;
		for(i=1; i<= itemCnt; i++)
		{
			weights[i] = Math.abs(RandomNumbers.nextNumber()%100 + 1); // 
			values[i] = weights[i] + 10;
			wghtSum += weights[i];
			totalValue += values[i];
		}
		cap = wghtSum/2;
	}

	public int GetItemCnt()
	{
		return itemCnt;
	}
	public int GetItemWeight(int itemNum)
	{
		return weights[itemNum];
	}
	public int GetItemValue(int itemNum)
	{
		return values[itemNum];
	}
	public int GetCapacity()
	{
		return cap;
	}
	public int GetTotalValue()
	{
		return totalValue;
	}
	public void SortItemVW()
	{
		//sorting items by cost; 
        int itemCnt=GetItemCnt();
		Double[] fractionalArray = new Double[itemCnt+1];
		for(int i=1;i<=itemCnt;i++){
			Double val = new Double(values[i]);//first way 
			Double w = new Double(weights[i]);//first way 
			fractionalArray[i]=(val/w);
		}
		QuickSort(fractionalArray,1,itemCnt); // try to get index in result array
		int lengh = itemCnt + 2;
		for(int i=1; i<=lengh/2; i++)
		{ 
			int temp = values[i];	
		 	values[i] = values[lengh -i -1];
		 	values[lengh -i -1] = temp; 
		 	int temp2 = weights[i];	
		 	weights[i] = weights[lengh -i -1];
		 	weights[lengh -i -1] = temp2;		 	
		}
	}

	public void QuickSort(Double cost[], int lo, int hi)
	{
		if(lo>=hi) return;

		int mid = Partition(cost, lo, hi);	//Last element as pivot 
		QuickSort(cost,lo,mid-1);
		QuickSort(cost,mid+1,hi);
	}
	public int Partition(Double cost[] , int lo, int hi)
	{
		Double x;
		int i, j; 
		i=lo-1;
		x = cost[hi] ;
		for(j=lo; j<=hi-1; j++){
			if(cost[j] <= x){
				swap(j, i+1, cost);
				swap1(j, i+1, values);
				swap1(j, i+1, weights);
				i++;
			}
		}
		swap(hi, i+1, cost);
		swap1(hi, i+1, values);
		swap1(hi, i+1, weights);
		return i+1;
	}

	public void swap(int x , int y ,Double data[])
	{
		Double temp = data[x];
		data[x] = data[y];
	    data[y] = temp;
	} 

	public void swap1(int x , int y ,int data[])
	{
		int temp = data[x];
		data[x] = data[y];
	    data[y] = temp;
	} 

	public void Print()
	{
		int i;
		System.out.printf("Number of items = %d, Capacity = %d\n",itemCnt, cap);
		System.out.print("Weights: ");
		for (i = 1; i <= itemCnt; i++)
		{
			System.out.printf("%d ",weights[i]);
		}
		System.out.print("\nValues: ");
		for (i = 1; i <= itemCnt; i++)
		{
			System.out.printf("%d ",values[i]);
		}
		System.out.print("\n");
	}
}
