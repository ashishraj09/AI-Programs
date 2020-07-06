package knn;


import java.util.Scanner;
import knn.bean.KNNData;

public class KConfigMenu {

	public static int getKValue(Scanner sc,KNNData data) {

		System.out.println("Enter the K value of KNN ");
		int k = 1;
		String ch = sc.nextLine();
		if(!isNumeric(ch))
		{
			System.out.println("Invalid value !!! Please a digit.");
			return getKValue(sc,data);
		}
		k = Integer.parseInt(ch);
		int idleValue = calculateK (data);

		if (k > 50) {
			
			System.out.println("K Value is out of range."+idleValue);
			return getKValue(sc,data);
		} 
		else
		{
			if(k != calculateK (data))
			System.out.println("Best value of k is "+idleValue+" - Calculated using : \"K = sqrt ( number of samples in dataset ) / 2\".");
			return k;
		}
	}
	
	private static int calculateK (KNNData data) {
	    int size = data.getTestFeatures().size();
	    int k = (int) Math.round(Math.sqrt(size) / 2);
	    if ( k%2 != 0 ) {
	        return k ;
	    }
	    else {
	        return k - 1 ;
	    }
	  
	}
	
	public static int getFormulaChoice(Scanner sc) {
		
		System.out.println("Please choose distance calculation formula : \n"+ "1 - for Euclidean distance formula \n2 - for Manhanttan distance formula");
		
		String ch  =  sc.nextLine();
		int choice = 1;
		if(!isNumeric(ch))
		{
			System.out.println("Invalid choice !!! Please enter 1 or 2");
			return getFormulaChoice(sc);
		}
		choice = Integer.parseInt(ch);
		if (choice == 2 || choice == 1) {
			return choice;
		} 
		else
		{
			System.out.println("Invalid choice !!! Please enter 1 or 2");
			return getFormulaChoice(sc);
		}

	}
	
	public static boolean isNumeric(String str) { 
		  try {  
		    Integer.parseInt(str);  
		    return true;
		  } catch(NumberFormatException e){  
		    return false;  
		  }  
		}
	
}
