package isN_Prime;

import java.util.Scanner;
import java.lang.Math;

public class main {
	
	public static void main(String[] args) {
		
		Scanner scnr = new Scanner(System.in);
		
		double n = 0, i = 2; 
		
		System.out.println("Enter n:");
		
		n = scnr.nextDouble();
		
		boolean isPrime = true;
		while(i < n){
			if(n % i == 0){
				isPrime = false;
				break;
			}
			
			i++;
		}
		
		// - 9,223,372,036,854,775,808
		// * 9,223,372,036,854,775,807
		
		if (isPrime) {
			System.out.println("Prime");
		} else {
			System.out.println("Not Prime");
		}
	}
}