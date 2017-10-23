/*
Giorgi Tediashvili
CS4348 Project 2
10/28/2017
 */

public class Project2{
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
                System.out.println("Customer " + Bank.customer_obj[i].custNumber + " is joined by main");
            } catch (InterruptedException e) {}                        
        }
        
        display_results();        
        System.exit(0);
    }
    
    static void display_results(){
        String title = "Bank Simulation Summary";
        String column_1_header = "Ending Balance";        
        String column_2_header = "Loan Amount";
        
        System.out.print(String.format("%1$15s", ""));
        System.out.println(String.format("%1$15s", title));        
        
        System.out.print(String.format("%1$15s", ""));
        System.out.print(String.format("%1$15s", column_1_header));
        System.out.println(String.format("%1$15s", column_2_header));
        
        int total_ending_balance = 0;
        int total_loan_amount = 0;
        for (int i = 0; i < Bank.totalCustomers; i++) {  
            String cust = "Customer " + i;
            
            System.out.print(String.format("%1$15s", cust));
            System.out.print(String.format("%1$15s", Bank.customer_obj[i].balance));
            System.out.println(String.format("%1$15s", Bank.customer_obj[i].loanAmount));
            
            total_ending_balance += Bank.customer_obj[i].balance;
            total_loan_amount += Bank.customer_obj[i].loanAmount;
        }
        System.out.println();
        System.out.print(String.format("%1$15s", "Totals"));
        System.out.print(String.format("%1$15s", total_ending_balance));
        System.out.println(String.format("%1$15s", total_loan_amount));
    }
}
