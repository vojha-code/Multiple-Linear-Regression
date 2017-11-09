import java.io.*;//import input output
public class RunRBSA
{

 public static void main(String args[]) //Main Method Starts
 {
         
         String yes = "N"; 

         do //Do While Loop 
         {
              System.out.print("\n --------->Welcome to the System for Linear Regression<-----------\n");
            try
            {          
             
              BufferedReader br  =  new BufferedReader(new InputStreamReader(System.in));
              
               RBSA rbsa=new RBSA(); //Creating object for Class RBSA
               rbsa.linear_regression(); //Calling method linear regression in class RBSA   


               
              System.out.print("\n\nPress \" Y \" to Contineue for diffrent Gas Combination:");
              yes = br.readLine();
             }
             catch(Exception e)
             {
                System.out.println("Error "+e);
                System.exit(0); 
             }
           
         }
         while(yes.equals("Y")||yes.equals("y")); //Loop for another env if Y is the answer


      
  
 }
 
}