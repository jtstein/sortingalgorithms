//Jordan Stein
// CSCI 3412 - FALL 2015
// Algorithms - Dr. Williams
// Problem Set 1 - Sorting Algorithms

import java.util.*;
import java.io.*;

public class SortingAlgorithms 
{
	static long comparison = 0; // will keep a count of how many comparisons are done per sort.
	static long assignment = 0; // will keep a count of how many assignments are done per sort.
	
	public static void main(String[] args)
	{	
		Scanner sc2 = new Scanner(System.in); // scanner for user input
		int fchoice = 0; // will represent file choice
		String file = "";
		do
		{
			System.out.println("Which file would you like to use?");
			System.out.println("1. shuffled.txt – the integers 0-99 in random order – i.e., shuffled\n"
						+  "2. sorted.txt – the integers 0-99 in sorted order (ascending order)"
						+  "\n3. nearly-sorted.txt – the integers 0-99 in nearly sorted order"
						+	"\n4. unsorted.txt – the integers 0-99 in unsorted order (descending order)"
						+	"\n5. nearly-unsorted.txt – the integers 0-99 in nearly unsorted order"
						+	"\n6. duplicate.txt – 100 integers 0, 10, 20, …, 90 – i.e., many duplicates – in random order"
						+	"\n7. one-million-randoms – 1,000,000 integers 0-99 in random order");
			
			fchoice = sc2.nextInt();
		} while (fchoice > 7 || fchoice < 1); // error checks if user choses an invalid number.
			
		int arrlength = 0;
		if (fchoice < 7) // used if user choses any file that is not one-million-randoms
			arrlength = 100;
		else // used if user choses one-million-randoms for the file
		{
			System.out.print("Please input the amount of integers from one-million-randoms you would like to sort: ");
			arrlength = sc2.nextInt();
		}
		switch(fchoice) // assigns file based on user choice
		{
			case 1: file = "shuffled.txt";
			    break;
			case 2: file = "sorted.txt";
				break;
			case 3: file = "nearly-sorted.txt";
				break;
			case 4: file = "unsorted.txt";
				break;
			case 5: file = "nearly-unsorted.txt";
				break;
			case 6: file = "duplicate.txt";
				break;
			case 7: file = "one-million-randoms.txt";
				break;
		}
		
		int[] arr = new int[arrlength]; // array we will store integer data in
		
		readData(arr, fchoice, arrlength, file, sc2); // reads data from fileinto array.
	
		System.out.println("Which sort would you like to use?");
		System.out.println("1. Bubble Sort\n2. Merge Sort\n3. Counting Sort");
		
		int choice = sc2.nextInt();
		
		while (choice < 1 || choice > 3) // error checks if user doesnt input 1, 2, or 3
		{
			System.out.println("Which sort would you like to use?");
			System.out.println("1. Bubble Sort\n2. Merge Sort\n3. Counting Sort");
			choice = sc2.nextInt();
		}
		
		sc2.close(); // close file scanner
		
		System.out.println("Before sorting:");
		for (int i=0; i < arr.length; i++)	System.out.print(arr[i] + " ");
		
		switch(choice) // chooses which algorithm to call based on user input
		{
			case 1: bubblesort(arr);
					break;
			case 2: mergesort(arr,0,arr.length-1);
					break;
			case 3: int max, min;
					max = min = arr[0]; // initialize max and min to first element
					assignment +=2;
		      
					for (int i = 1; i < arr.length; ++i) // finds min and max values of array.
					{
						if (arr[i] > max) // finds max value
						{
							max = arr[i];
						}
						else if (arr[i] < min) // finds min value
						{ 
							min = arr[i]; 
						}
						comparison++; // comparison for every iteration
						assignment++; // assignment for every iteration
					}
					countingsort(arr, min, max);
					break;
		}
		
		System.out.println( "\nAfter sorting: ");
		for (int i=0; i < arr.length; i++)	System.out.print(arr[i] + " ");
		
		System.out.println("\nNumber of comparisons: " + comparison);
		System.out.println("Number of assignments: " + assignment);
	} // end main
	
	public static int[] bubblesort(int arr[])
	{	
		int temp = 0; // will hold temporary array value for swapping
		assignment++;
		for (int i=0; i < arr.length-1; ++i)
		{
			for (int j = 1; j < arr.length; ++j)
			{
				if (arr[j] < arr[j-1])
				{
					temp = arr[j];		// temporarly stores arr[j] value
					arr[j] = arr[j-1];  // swaps arr[j] and arr[j]'s value
					arr[j-1] = temp;	// restores arr[j-1]'s value with arr[j]s initial value
					assignment += 3; // 3 assignments for this sort
				}
				comparison++; // comparison done every iteration of this loop
			}
		}
		return arr;
	}	
	
    public static void mergesort(int[] arr, int p, int r) // p-> low, q-> mid, r-> high
    {
        if (p < r) 
        {
        	comparison++;
            int q = p + ((r - p)/2); // calculates middle of array
            assignment++;
            mergesort(arr, p, q); // sorts left side of array
            mergesort(arr, q + 1, r); // sorts right side of array
            merge(arr, p, q, r); // merges left and right side together
        }
    }
 
    public static void merge(int arr[], int p, int q, int r) 
    {
        int tempArr[] = arr.clone(); // copies arr into tempArr
        int i, j, k;
        i = k = p; // initializes i and k to low
        j = q+1;  // initializes j to mid+1
        assignment +=3;
        
        while (i <= q && j <= r)  // merges right side
        {
            if (tempArr[i] <= tempArr[j]) 
            {
                arr[k] = tempArr[i];
                i++;
            } 
            else 
            {
                arr[k] = tempArr[j];
                j++;
            }
            k++;
            comparison++; // tempArr comparison for every iteration of this loop
            assignment++; // assignment for every iteration of this loop
        }
        while (i <= q) // merges left side
        {
            arr[k] = tempArr[i];
            assignment++;
            k++;
            i++;
        }
    }

    public static int[] countingsort(int[] arr, int min, int max)
	{	
	      int range = max - min + 1; // calculates the range of values
	 
	      int[] count = new int[range]; // arr2 will be the counting array
	      
	      for (int i = 0; i < arr.length; i++)
	      {
	            count[arr[i] - min]++;
	      }
	      for (int i = 1; i < range; i++)
	      {
	            count[i] += count[i - 1];
	            assignment++;
	      }

	      int j = 0; // index in while loop
	      for (int i = 0; i < range; i++)
	      {
	          while (j < count[i]) // restores initial array
	          {
	              arr[j] = i + min;
	              j++;
	              assignment++;
	          }
	      }
	      return arr;
	   }    
    
    
	public static void readData(int[] arr, int fchoice, int arrlength, String file, Scanner sc2) // reads data from file into array.
	{
		try{
			String js = ""; // will hold temporary inputs from scanner for error handling in reading into array.
			Scanner sc = new Scanner(new File(file)); // throws file into scanner
			
			if (fchoice == 1 || fchoice == 2 || fchoice == 4 || fchoice == 6) // formats input stream for reading numbers per file
				for (int i=0; i < 4; i++) js = sc.next();
			else if (fchoice == 3 || fchoice == 5)
				for (int i=0; i < 5; i++) js = sc.next();
			else if (fchoice == 7)
				for (int i=0; i < 6; i++) js = sc.next();
			if (fchoice < 7) // used if user picks file that is not one-million-randoms
			{	int i, j;
				i = j = 0;
				while (sc.hasNext())
				{
					arr[j] = sc.nextInt();
					if (j < arrlength)
						j++;
					i++;
				}
			}
			else // used if user picks file that is one-million-randoms
			{
				for (int i=0; i < arrlength; i++)
				{
					arr[i] = sc.nextInt();
				}
			}
		}
		catch(FileNotFoundException e)
		{
			System.out.println("File not found");
		}
	}
}