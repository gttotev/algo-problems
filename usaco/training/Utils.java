public abstract class Utils {
  
  public static void printArr(int[] arr) {
    System.out.print("int[]: { ");
    for (int i = 0; i < arr.length; i++) {
      System.out.print(arr[i] + (i != arr.length - 1 ? ", " : " "));
    }
    System.out.println("}");
  }
  
  public static void printArr(double[] arr) {
    System.out.print("double[]: { ");
    for (int i = 0; i < arr.length; i++) {
      System.out.print(arr[i] + (i != arr.length - 1 ? ", " : " "));
    }
    System.out.println("}");
  }
  
  public static void printArr(String[] arr) {
    System.out.print("String[]: { ");
    for (int i = 0; i < arr.length; i++) {
      System.out.print(arr[i] + (i != arr.length - 1 ? ", " : " "));
    }
    System.out.println("}");
  }
  
  public static int findLinearArr(String[] arr, String value) {
    for (int i = 0; i < arr.length; i++) {
      if (arr[i].equals(value))
        return i;
    }
    return -1;
  }
  
  public static void print2DArr(int[][] arr) {
    for (int i = 0; i < arr.length; i++) {
      for (int e = 0; e < arr[0].length; e++) {
        System.out.print(arr[i][e] + " ");
      }
      System.out.println();
    }
  }
  
  public static void print2DArr(double[][] arr) {
    for (int i = 0; i < arr.length; i++) {
      for (int e = 0; e < arr[0].length; e++) {
        System.out.print(arr[i][e] + " ");
      }
      System.out.println();
    }
  }
  
  public static void print2DArr(String[][] arr) {
    for (int i = 0; i < arr.length; i++) {
      for (int e = 0; e < arr[0].length; e++) {
        System.out.print(arr[i][e] + " ");
      }
      System.out.println();
    }
  }
  
  public static void print2DArr(char[][] arr) {
    for (int i = 0; i < arr.length; i++) {
      for (int e = 0; e < arr[0].length; e++) {
        System.out.print(arr[i][e] + " ");
      }
      System.out.println();
    }
  }
  
}