import java.util.Random;
import java.util.Scanner; 
import java.io.*;
public class RBSA
{
   
    public int row,col;//Rows and Column for n x n matrix 
    public int coly=1;
    
     {   
            try
            { 
 
              BufferedReader br  =  new BufferedReader(new InputStreamReader(System.in));
              System.out.print("\n Enter the no of gases:");
              row=Integer.parseInt(br.readLine());
              col=row;
             }
             catch(Exception e)
             {
               System.out.println("Error "+e); 
               System.exit(0);
             }   
    }

    public float Beta[][]=new float[row][coly]; 
    public float Yin[][]=new float[row][col];
    public float Xin[][]=new float[row][col];
    public float Xtemp[][]=new float[row][col];
    public float Xr[][]=new float[row][col];
    public float Xr1[][]=new float[row][col];
    public float X_T[][]=new float[row][col]; 
    public float XI[][]=new float[row][col]; 
 
   
    /* This method find random fraction value */
    double randomVaue()
    {
     Random rand = new Random();
     double prob ;
     return prob =  (100 * rand.nextDouble());
    } 

    /* This method find the multiplication of two Matrix */
    void mat_Mul(float mat1[][],int row1,int col1,float mat2[][],int row2,int col2,float mat_res[][])
    {
	int i, j, k;
	if(col1 == row2)
	{

		for(i =0; i<row1; i++)
			for(j=0; j<col2; j++)
			{
				mat_res[i][j] = 0;
				for(k = 0; k < col1; k ++)
				{
					mat_res[i][j] += mat1[i][k] * mat2[k][j];

				}
			}
	
	}
	else
	  System.out.printf("\n Multiplication is not possible");
	
     }


     /* This method find the transpose of a matix */
     void mat_Trans(float transp[][],int row1,int col1)
     {
	for(int i = 0; i< row1; i++)
	{
		for(int j = 0; j < col1; j++)
		{
			X_T[i][j] = transp[j][i] ;
		}
	}
     }

      
     
     /* This method is a part of Inverse function */
     void swap( int row1,int row2, int col, float mat[][],float mat1[][])
     {
        for(int i = 0; i < col; i++)
	{
		float   temp = mat[row1][i];
		mat[row1][i] = mat[row2][i];
		mat[row2][i] = temp;

		temp = mat1[row1][i];
		mat1[row1][i] = mat1[row2][i];
		mat1[row2][i] = temp;

	 }
      }
     /* This method is a part of Inverse function */
     void tempx(int row,int col)
     {
      for(int i=0;i<row;i++)
      {   
       for(int j=0;j<col;j++)
           XI[i][j]=(float)0.0;
       XI[i][i]=1;
      }
     }

     /* This function find inverse of matrix */
     void mat_Inverse(int row1, int col1, float mat[][],float mat1[][])
     {
         boolean singular = false;
         int i, r, c;
	 for(r = 0;( r < row1)&& !singular;  r++)
	 {

                if((int)mat[r][r]!=0 )  /* Diagonal element is not zero */
                {
			for(c = 0; c < col1; c++)
                        {
			        if( c == r)
				{

					/* Make all the elements above and below the current principal
					 diagonal element zero */

					float ratio =  mat[r][r];
					for( i = 0; i < col1; i++)
					{
						mat[r][i] /= ratio ;
						mat1[r][i] /= ratio;
					}
				}
				else
				{
					float ratio = mat[c][r] / mat[r][r];
					for( i = 0; i < col1;  i++)
					{
						mat[c][i] -= ratio * mat[r][i];
						mat1[c][i] -= ratio * mat1[r][i];
					}
				}
                         } 
                }
		else
		{
			/* If principal diagonal element is zero */
                        singular = true;

			for(c = (r+1); (c < col1) && singular; ++c)
                               if((int)mat[c][r]!=0)
				{
                                        singular = false;
					/* Find non zero elements in the same column */
					swap(r,c,col1, mat, mat1);
					--r;
				}

		}
	}
     
    }
    

    /* This is Displaying the mtrix */
    void display(float d[][],int n,int m)
    {
     
       for(int i=0;i<n;i++)
       {
         for(int j=0;j<m;j++)
            System.out.printf(" %1.3f ",d[i][j]);
         System.out.println();
       }
    }

 /* This is Displaying the Beta  */
    void displayBeta(float d[][],int n,int m)
    {
      double betaSum=0.0; 
       for(int i=0;i<n;i++)
       {
         for(int j=0;j<m;j++)
         {
            System.out.printf(" Beta %d is %1.3f \n",i,d[i][j]);
            betaSum += d[i][j]; 
         }
       }
       System.out.printf(" Sumation of Beta is %1.3f\n",betaSum );

       System.out.printf("\nPercentage Shared by gases in the Gas Mixture\n\n");
       double share=0.0;
       for(int i=0;i<n;i++)
       {
         for(int j=0;j<m;j++)
         {
            share=(d[i][j]/betaSum)*100;
            System.out.printf(" Share of Gas no. %d is %.2f percent\n",i,share);
         }
       }
    }


    /* This is Input method Xin */
    void mat_X_input(float X[][],int n,int m)
    {
        try
         { 
 
           BufferedReader ibr  =  new BufferedReader(new InputStreamReader(System.in));
           System.out.print("\nEnter The File Name:");
           String FILE_CROSS=ibr.readLine();
           FILE_CROSS = FILE_CROSS+".txt";
           //String FILE_CROSS="CrossMat.txt";

           FileReader  fr = new FileReader(FILE_CROSS);
           BufferedReader br =  new BufferedReader(fr);
           
           Scanner s = null;
           String Data;

           for(int i=0;i<n;i++)
           {
              if( (Data = br.readLine()) != null);
                 s = new Scanner(Data);
              for(int j=0;j<m;j++)
              {
                   if (s.hasNextDouble())
                      X[i][j] =(float) s.nextDouble();          
              }
           }

           /* for(int i=0;i<n;i++)
            {
             for(int j=0;j<m;j++)
             {
               System.out.print("\nEnter x["+i+"]["+j+"]:");
               X[i][j]=(float)Float.parseFloat(br.readLine());
             }
            }*/

          }
          catch(Exception e)
          {
            System.out.println("Error "+e);
            System.exit(0); 
          }   
    } 

    /* This is Input method Yin */
    void mat_Y_input(float Y[][],int n,int m)
    {
        try
         { 
 
           BufferedReader br  =  new BufferedReader(new InputStreamReader(System.in));
            
            for(int i=0;i<n;i++)
            {
             for(int j=0;j<m;j++)
             {
               System.out.print("\nEnter x["+i+"]["+j+"]:");
               Y[i][j]=(float)Float.parseFloat(br.readLine());
             }
            }
          }
          catch(Exception e)
          {
            System.out.println("Error "+e);
            System.exit(0);
          } 
    } 

    void copy_intemp(int n,int m)
    {
      for(int i=0;i<n;i++)
      {
        for(int j=0;j<m;j++)
          Xtemp[i][j]=Xin[i][j];
      }
    }

    void linear_regression()
    {
         try
            {  
              System.out.println("\nInput Cross-Sensitivity Matrix\n"); 
              mat_X_input(Xin,row,col); 
              display(Xin,row,col);

              String yes = "N"; 
              do
                {
                   
                  System.out.println("\nInput Sensor Array's Values for Mixed Gas\n"); 
                  mat_Y_input(Yin,row,coly); 
          
                  System.out.println("\nThe Cross-Sensitivity Matrix is\n");
                  display(Xin,row,col);

                  System.out.println("\nThe Sensor Array's Values for Mixed Gas is\n");
                  display(Yin,row,coly);     

                  copy_intemp(row,col);
                  mat_Trans(Xtemp,row,col);
                  copy_intemp(row,col);
                  mat_Mul(X_T,row,col,Xtemp,row,col,Xr);
                  tempx(row,col);
                  mat_Inverse(row,col,Xr,XI);
                  mat_Mul(XI,row,col,X_T,row,col,Xr1);
                  mat_Mul(Xr1,row,col,Yin,row,coly,Beta);

                  System.out.println("\nThe System Output is\n");
                  displayBeta(Beta,row,coly);
               
                  BufferedReader br  =  new BufferedReader(new InputStreamReader(System.in));

                  System.out.print("\n\nPress \" Y \" to Contineue for another environment:");
                  yes = br.readLine();
               }
               while(yes.equals("Y")||yes.equals("y"));
           }
           catch(Exception e)
           {
              System.out.println("Errpr "+e);
              System.exit(0); 
           }
         
 
    } 

   /* public static void main(String[] args) 
    {
           
        RBSA rbsa=new RBSA();
        rbsa.linear_regression();   
     
    }*/
}