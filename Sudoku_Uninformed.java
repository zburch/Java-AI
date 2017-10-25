import java.io.*;
import java.util.*;
public class Sudoku_Uninformed{
   static char[][] puzzle = new char[16][16];
   static final char[] VALUES = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
   static int countAssign = 0;
   
   public static void main(String[] args){
      final int SIZE = 16;
      int x = 0;
      int y = 0;
      try {
         File file = new File("Sudoku_test_puzzles/SudokuPuzzle1.txt");
         Scanner input = new Scanner(file);
         
         while(input.hasNextLine() && x<16){
            String line = input.nextLine();
            char[] chars = line.toCharArray();
            for(int i=0;i<chars.length;i++){
               puzzle[x][i] = chars[i];
            }
            x++;
         }
      } catch (FileNotFoundException e){
         e.printStackTrace();
      }
      System.out.println("Initial state of the puzzle...");
      printPuzzle();
      
      boolean result = solve(0,0);
      
      System.out.println("Final state of the puzzle...");
      
      if(result){
         printPuzzle();
         System.out.println("Uninformed agent - Success! ");
         System.out.println("Total number of cell assignments: "+ countAssign);
      } else {
         printPuzzle();
         System.out.println("Uninformed agent - Couldn't be solved");
         System.out.println("Total number of cell assignments: "+ countAssign);
      }
   }
   
   
   
   public static boolean solve(int x, int y){
   
      if(x>15){
         return true;
      }
      
      if(puzzle[x][y] == '-'){
         for(int i=0;i<VALUES.length;i++){
            countAssign++;
   
            if(!validate(x,y,VALUES[i])){
               continue;
            }
            
            puzzle[x][y] = VALUES[i];
            
            int newX;
            int newY;
            if (y<15){
               newY = y+1;
               newX = x;
            }
            else{
               newY = 0;
               newX = x+1;
            }
            boolean check = solve(newX,newY);
            if(check==true){
               return true;
            }
            else{
               puzzle[x][y] = '-';
            }
         }
      }
      else{
         int newX;
         int newY;
         if (y<15){
            newY = y+1;
            newX = x;
         }
         else{
            newY = 0;
            newX = x+1;
         }
         
         return solve(newX,newY);
      }
      return false;
   }
   
   public static boolean validate(int x, int y, char value){
      //check horizontal
      for(int i=0; i<puzzle.length; i++){
         if(puzzle[x][i] == value){
            return false;
         }
      }
      //check vertical
      for(int i=0; i<puzzle[0].length; i++){
         if(puzzle[i][y] == value){
            return false;
         }
      }
      
      //check box
      int startX = 4 * (x/4);
      int startY = 4 * (y/4);
      int endX = startX + 4;
      int endY = startY + 4;
      for (int i = startX; i<endX; i++){
         for(int j = startY; j<endY; j++){
            if(puzzle[i][j] == value){
               return false;
            }
         }
      }
      return true;
   }
   
   public static void printPuzzle(){
      for(int i=0;i<puzzle.length;i++){
         String row = new String(puzzle[i]);
         System.out.println(row);
      }
      System.out.println();
      
   }
}