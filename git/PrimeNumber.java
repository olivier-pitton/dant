public class PrimeNumber {
   
   private static final int MAX = 100_000;
   
   public static void main(String[] args) {
      /**
       * Nb prime number is :
       *  - 1000          --> 168
       *  - 10000         --> 1229
       *  - 100000        --> 9592
       *  - 100 000 000   --> 5 761 455
       *  - 1 000 000 000 --> 50 847 533
       */
      long start = System.nanoTime();
      int nbPrimeNumber = 0;
      
      long[] tableau_premiers = new long[MAX - 1];
      
      for (int i = 0 ; i < tableau_premiers.length ; i++) {
         tableau_premiers[i] = i;
      }
      
      for (int i = 2 ; i <= MAX ; i++) {
         if (tableau_premiers[i - 2] != -1) {
            int j = i + 1;
            while (j <= MAX) {
               if ((j % i) == 0) {
                  tableau_premiers[j - 2] = -1;
               }
               j++;
            }
         }
      }
      for (int i = 0 ; i < tableau_premiers.length ; i++) {
          if (tableau_premiers[i] != -1) {
             nbPrimeNumber++;
          }
      }
      long end = (System.nanoTime() - start) / 1_000_000L;
      System.out.println("There are " + nbPrimeNumber + " prime numbers between 2 and " + MAX);
      System.out.println("Time consumed is " + end + " ms.");
   }
   
}