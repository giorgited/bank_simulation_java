/*
Giorgi Tediashvili
CS4348 Project 2
10/28/2017
 */

public class Main {
    public static void main(String...args) {
        Bank bank = new Bank();
        
        //CREATE CUSTOMER THREADS (5)
        for (int i = 0; i < Bank.totalCustomers; i++) {
            Bank.customer_obj[i] = new Customer(i);
            Bank.customerThreads[i] = new Thread(Bank.customer_obj[i]);
            Bank.customerThreads[i].start();
        }
        
        //CREATE LOANER THREAD (1)
        Bank.loaner_obj = new Loaner();
        Bank.loanerThread = new Thread(Bank.loaner_obj);
        Bank.loanerThread.start();
        
        //CREATE TELLER THREADS (2)
        for (int i = 0; i < Bank.totalTellers; i++) {
            Bank.teller_obj[i] = new Teller(i);
            Bank.tellerThreads[i] = new Thread(Bank.teller_obj[i]);
            Bank.tellerThreads[i].start();
        }        
        
        //CREAT THREAD JOIN AND FINAL CUSTOMER BALANCE DISPLAY
        for (int i = 0; i < Bank.totalCustomers; i++) {            
            try {
                Bank.customerThreads[i].join();
                System.out.println("Customer " + i + "'s Balance: $" + Bank.customer_obj[i].balance);
            } catch (InterruptedException e) {}                        
        }
        System.exit(0);
    }
}
