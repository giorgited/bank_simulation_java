/*
Giorgi Tediashvili
CS4348 Project 2
10/28/2017
 */

import java.util.LinkedList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Bank {
    // **** LOANERS *** //
    public static Semaphore Loaner = new Semaphore(1, true);
    public static Semaphore leave_loaner = new Semaphore(0, true);    
    public static Semaphore ProcessLoan = new Semaphore(0,true);
    public static final int totalLoaners = 1;
    public static Thread loanerThread = new Thread();
    public static Loaner loaner_obj = new Loaner();
    
    // **** CUSTOMERS *** //
    public static final int totalCustomers = 5;
    public static Customer[] customer_obj = new Customer[totalCustomers];    
    public static Thread[] customerThreads = new Thread[totalCustomers];
    public static Semaphore reciept = new Semaphore(0,true);
    
    // **** TELLERS *** //
    public static Semaphore Teller = new Semaphore(2, true);  
    public static Semaphore leave_teller = new Semaphore(0, true); 
    public static Semaphore ready_for_teller = new Semaphore(0, true);
    public static Semaphore ready_for_customer = new Semaphore(0, true);
    public static Semaphore ProcessTransaction = new Semaphore(0,true); 
    public static final int totalTellers = 2;
    public static Teller[] teller_obj = new Teller[totalTellers];    
    public static Thread[] tellerThreads = new Thread[totalTellers];

    // *** TIMER CONSTANTS ***//
    public static final int TELLER_TASK_TIME = 100; //1 minutes -> 1/10sec;
    public static final int CUSTOMER_TASK_TIME = 400;//4 minutes -> 4/10sec;
    public static final int lOANER_TASK_TIME = 100;//1 minute -> 1/10sec;
    // **** oTHER *** //
    public static Queue <Integer> loan_queue = new ConcurrentLinkedQueue <>();
    public static Queue <Integer> teller_queue = new ConcurrentLinkedQueue <>();

}
