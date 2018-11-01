package test.java;
import java.util.Scanner;

public class NaiveStringMatching {

    public static void main(String[] args) {
       Scanner in = new Scanner (System.in); 
       System.out.println("Type Main String : ");
       String str = in.nextLine();
       
       System.out.println("Type String Pattern to search : ");
       String P = in.nextLine();
       in.close();
       
     
      int i,j; 
      char s[] = str.toCharArray();
      char p[] = P.toCharArray(); 
       
      Integer limit = s.length - p.length;
      
         
           for (i = 0 ; i <= limit ; i++) 
           {
                for (j = 0 ; j < p.length ; j++)
                    {
                     if (p[j] != s[i+j])
                        {break;} 
                    }
                    
            if (j == p.length )
               { 
                System.out.println("pattern found at index : " + i );
               }
            }
        }
    }