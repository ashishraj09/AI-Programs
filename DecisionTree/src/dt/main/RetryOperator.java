package dt.main;

import java.util.Scanner;

public class RetryOperator {

	public static void retry(Scanner sc) {
		
		System.out.println("Please (Y) to retry or (N) exist program :");
		String option=sc.nextLine();
		if (option.equalsIgnoreCase("y") || option.equalsIgnoreCase("yes"))
			{
			DecisionTreeMain.menu();
			}
		else
		{
			System.out.println("Existing program.");
			sc.close();
			System.exit(0);
		}
	}
	
}
