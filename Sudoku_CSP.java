import java.io.*;
import java.util.*;
public class Sudoku_CSP{
   static char[][] puzzle = new char[16][16];
   static final ArrayList<Character> VALUES = new ArrayList<Character>();
   static int countAssign = 0;
   
   public static void main(String[] args){
      VALUES.add('0');
      VALUES.add('1');
      VALUES.add('2');
      VALUES.add('3');
      VALUES.add('4');
      VALUES.add('5');
      VALUES.add('6');
      VALUES.add('7');
      VALUES.add('8');
      VALUES.add('9');
      VALUES.add('A');
      VALUES.add('B');
      VALUES.add('C');
      VALUES.add('D');
      VALUES.add('E');
      VALUES.add('F');
      int x = 0;
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
      System.out.println("Initial puzzle state...");
      printPuzzle();

      boolean result = solve();
      System.out.println("Final puzzle state...");
      if(result){
         printPuzzle();
         System.out.println("CSP Agent - Success! ");
         System.out.println("Total number of cell assignments: " + countAssign);
      } else {
         printPuzzle();
         System.out.println("CSP Agent - Couldn't be solved");
         System.out.println("Total number of cell assignments: " + countAssign);
      }
   }

   public static boolean solve(){
      
      boolean complete = true;
      
      for (int i=0; i<puzzle.length; i++){
         for(int j=0; j<puzzle[0].length; j++){
            if (puzzle[i][j] == '-'){
               complete = false;
            }
         }
      }
      
      if(complete){
         return true;
      }
      
      
      //Dissassociate the Node reference from the values at this level of recursion...
      Node node = getMRV();
      int x = node.getX();
      int y = node.getY();
      ArrayList<Character> list = new ArrayList<Character>();
      for (int i=0;i<node.getValues().size();i++){
         list.add(node.getValues().get(i));
      }
      
      for(int i=0;i<list.size();i++){
         countAssign++;
         
         puzzle[x][y] = list.get(i);

         boolean check = solve();
         
         if(check==true){
            return true;
         }
         
         else{
            puzzle[x][y] = '-';
         }
      }
      return false;
   }
   
   public static void printPuzzle(){
      for(int i=0;i<puzzle.length;i++){
         String row = new String(puzzle[i]);
         System.out.println(row);
      }
      System.out.println();
      
   }
   
   public static Node getMRV(){
       Node bestNode = new Node(0,0,VALUES);
 
       for(int x=0;x<puzzle.length;x++){
          for(int y=0;y<puzzle[0].length;y++){
             if(puzzle[x][y] == '-'){
                ArrayList<Character> test = validateMRV(x, y);
                if (test.size() == 1){
                   Node tempNode = new Node(x, y, test);
                   return tempNode;
                } 
                else if (test.size() < bestNode.getValues().size()){
                   bestNode.setX(x);
                   bestNode.setY(y);
                   bestNode.setValues(test);
                }
             }
          }  
       }    
       return bestNode;
   } 
   
     
   public static ArrayList<Character> validateMRV(int x, int y){
      ArrayList<Character> mrv = new ArrayList();
      for(int i=0;i<VALUES.size();i++){
         mrv.add(VALUES.get(i));
      }
      
      
      //check horizontal
      for(int i=0; i<puzzle.length; i++){
         if(puzzle[x][i] != '-'){
            int elementIndex = mrv.indexOf(puzzle[x][i]);
            if (elementIndex != -1){
               mrv.remove(elementIndex);
            }
         }
      }
      //check vertical
      for(int i=0; i<puzzle[0].length; i++){
         if(puzzle[i][y] != '-'){
            int elementIndex = mrv.indexOf(puzzle[i][y]);
            if (elementIndex != -1){
               mrv.remove(elementIndex);
            }
         }
      }
            
      //check box
      int startX = 4 * (x/4);
      int startY = 4 * (y/4);
      int endX = startX + 4;
      int endY = startY + 4;
      for (int i = startX; i<endX; i++){
         for(int j= startY; j<endY; j++){
            if(puzzle[i][j] != '-'){
               int elementIndex = mrv.indexOf(puzzle[i][j]);
               if (elementIndex != -1){
                  mrv.remove(elementIndex);
               }
            }
         }
      }
      return mrv;
   }
   
   public static class Node{
      private static int x;
      private static int y;
      private static ArrayList<Character> values = new ArrayList<Character>();
      
      public Node(int xv, int yv, ArrayList<Character> valuesv){
         x = xv;
         y = yv;
         values.clear();
         for(int i=0; i<valuesv.size(); i++){
            values.add(valuesv.get(i));
         }      
      }
      
      public static int getX(){
         return x;
      }
      
      public static int getY(){
         return y;
      }
      
      public static ArrayList<Character> getValues(){
         return values;
      }
      
      public static void setX(int xv){
         x = xv;
      }
      
      public static void setY(int yv){
         y = yv;
      }
      
      public static void setValues(ArrayList<Character> valuesv){
         ArrayList<Character> temp = new ArrayList<Character>();
         for(int i=0; i<valuesv.size(); i++){
            temp.add(valuesv.get(i));
         }
         values.clear();
         values = temp;
      }
   } 
}