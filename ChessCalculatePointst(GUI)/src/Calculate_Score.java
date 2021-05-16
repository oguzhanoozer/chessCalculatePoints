import java.io.File;
import java.util.*;

public class Calculate_Score 
{
	private  String[][] square = new String[8][8]; // add element read from file
	private  double[][] points = new double[8][8]; //assign to points for every squares   

	private  void CalculatePoints(String squareNumber,int col,int row)
	{		
		/*
		 * check if square's points is decreased before
		 * if true ,points will be same
		 * else points will be decrease
		 */
		double currentPoint = this.points[col][row];
		if(currentPoint%1==0)
		{
			this.points[col][row] = currentPoint - (currentPoint/2);	
		}
		else {
			this.points[col][row] =currentPoint;
		}
		
	}
	
	private  boolean CheckInSquareKnight(int col,int row,String whiteOrBlack)
	{
		/*
		 * check if move is inside play square for knight
		 * then, check if move is to opposite side
		 * then, call CalculatePoints
		 */
		if((col>=0 && col <=this.square.length-1) && (row>=0 && row <=this.square.length-1))
		{
			String temp_str = this.square[col][row];
			
			if(temp_str.contains("xx"))
			{
				return false;
			}
			else
			{
				if(whiteOrBlack.contains("black"))
				{							
					if(temp_str.contains("s"))
					{	
						CalculatePoints(temp_str,col,row);
						return true;
					}
				}		
				if(whiteOrBlack.contains("white"))
				{							
					if(temp_str.contains("b"))
					{		
						CalculatePoints(temp_str,col,row);					
						return true;
					}
				}		
			}			
		}
		return false;
	}
	
	private  boolean CheckInSquareBishop(int col,int row,String whiteOrBlack,String pr1,String pr2)
	{
		/*
		 * check if move is inside play square for bishop
		 * then, check if move is to opposite side
		 * then, call CalculatePoints
		 * if move is equals empty square then call CheckInSquareBishop recursively
		 * p1,pr2 : are used for direction move, if -,- call CheckInSquareBishop by using col-1,row-1 parameters
		 */
		
		if((col>=0 && col <=this.square.length-1) && (row>=0 && row <=this.square.length-1))
		{
			String temp_str = this.square[col][row];
			if(temp_str.contains("xx"))
			{
				if(pr1.contains("-") && pr2.contains("-"))
				{
					CheckInSquareBishop(col-1,row-1,whiteOrBlack,"-","-");	
				}
				else if(pr1.contains("-") && pr2.contains("+"))
				{
					CheckInSquareBishop(col-1,row+1,whiteOrBlack,"-","+" );					
				}
				else if(pr1.contains("+") && pr2.contains("+"))
				{
					CheckInSquareBishop(col+1,row+1,whiteOrBlack,"+","+");
				}
				else if(pr1.contains("+") && pr2.contains("-"))
				{
					CheckInSquareBishop(col+1,row-1,whiteOrBlack,"+","-");	
				}				
			}
			else
			{				
				if(whiteOrBlack.contains("white"))
				{							
					if(temp_str.contains("b") && !temp_str.contains("s"))
					{		
						CalculatePoints(temp_str,col,row);
						return true;
					}
				}	
				if(whiteOrBlack.contains("black"))
				{							
					if(temp_str.contains("s") && !temp_str.contains("b"))
					{		
						CalculatePoints(temp_str,col,row);
						return true;
					}
				}		
			}			
		}
		return false;
	}
	
	private  void PlayKnight(int col,int row,String whiteOrBlack) 
	{
		/*
		 * knight does has 8 move
		 * check if move is valid
		 */
		 CheckInSquareKnight(col-2,row+1,whiteOrBlack);
		 CheckInSquareKnight(col-1,row+2,whiteOrBlack);
	   	 CheckInSquareKnight(col+1,row+2,whiteOrBlack);
	     CheckInSquareKnight(col+2,row+1,whiteOrBlack);
	   	 CheckInSquareKnight(col+2,row-1,whiteOrBlack);
	   	 CheckInSquareKnight(col+1,row-2,whiteOrBlack);
	   	 CheckInSquareKnight(col-1,row-2,whiteOrBlack);
	     CheckInSquareKnight(col-2,row-1,whiteOrBlack);  	
	}
	
	private  void PlayBishop(int col,int row,String whiteOrBlack)
	{	
		/*
		 * bishop does has 4 move as cross direction
		 * check if move is valid
		 */
		CheckInSquareBishop(col-1,row-1,whiteOrBlack,"-","-");
		CheckInSquareBishop(col-1,row+1,whiteOrBlack,"-","+");
		CheckInSquareBishop(col+1,row+1,whiteOrBlack,"+","+");
		CheckInSquareBishop(col+1,row-1,whiteOrBlack,"+","-");
	}
	
	private  boolean isFileReaded(String filePath)
	{
		 /*
		  * read the selected file from frame
		  * if read file,it will return true
		  * else return false
		  * add the read value to square array
		  * add the points value to points array as a player chess pieces 
		 
		 */
		
		int col=0,row=0;
    	 
         try {
             File myObj = new File(filePath);
             Scanner myReader = new Scanner(myObj);
             
             while (myReader.hasNextLine()) 
             {
               String data = myReader.nextLine();
               String[] tmp = data.split(" ");    //Split space
               
               for(String str: tmp)
               { 
            	    this.square[col][row]=str;
            	    
	               	if(str.contains("p"))
	               		this.points[col][row]=1;	
	               	else if(str.contains("k"))
	               		this.points[col][row]=5;
	                else if(str.contains("a"))
	                	this.points[col][row]=3;
	                else if(str.contains("f"))
	                	this.points[col][row]=3;
	               	else if(str.contains("v"))
	               		this.points[col][row]=9;
	               	else if(str.contains("s"))
	               		this.points[col][row]=100;
	               	else if(str.contains("x"))
	               		this.points[col][row]=0;           
	            	  row++;
               }
               row=0;             
               col++;
             }
             
             myReader.close();
           } catch (Exception e) {
             return false;
           }         
     return true;   
	}
	
	public String getScoreFromCalculator(String filePath) {
	/*
	 * call isFileReaded function to check file is read
	 * then, check if piece is a bishop and knight
	 * then call the related function
	 * then calculate sum white and black pieces' points
	 * return the points
	 */
		if(!isFileReaded(filePath))
		{
			 return "Hata.! Dosya okunamadý,dosya yolu hatalý olabilir.!";
		}
		
         for (int i = 0; i < this.square.length; i++)
         {
             for (int j = 0; j < this.square.length; j++) 
             {            	 
               	 String str = this.square[i][j];
               	
               	 if(str.contains("ab"))
            	 {
               		PlayKnight(i,j, "black") ;      		
            	 } 
               	 else if(str.contains("as"))
	           	 {         		    
               		PlayKnight(i,j, "white") ;  
	           	 }              	     
               	  if(str.contains("fs"))
            	 {
               		PlayBishop(i,j,"white");
            	 }               	
               	  if(str.contains("fb"))
            	 {            		
               		PlayBishop(i,j,"black");
            	 }              	     	   
             }                            
         }
         
         double whiteSum=0.0,blackSum=0.0;
         
         for (int i = 0; i < this.square.length; i++)
         {
             for (int j = 0; j < this.square.length; j++) 
             {            	 
            	 if(this.square[i][j].contains("sb"))
            	 {            		 
            		 whiteSum+=this.points[i][j];
            	 }
            	 else if(this.square[i][j].contains("s") )
            	 {            		 
            		 blackSum+=this.points[i][j];
            	 }
            	 else if(this.square[i][j].contains("b"))
            	 {
            		 whiteSum+=this.points[i][j];
            	 }            	 
             }            
         }
         
         String score = "Black:"+blackSum+" - "+"White:"+whiteSum;         
         return score;
         
	}
	
}