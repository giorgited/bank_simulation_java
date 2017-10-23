/*
Giorgi Tediashvili
CS4348 Project 2
10/28/2017
 */

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;

public class Loaner implements Runnable{
    public int Num;
    private int cust_number;

    @Override
    public void run() {
        loaner_created();
        while (true) {
            wait(Bank.ProcessLoan);
            process_loan();
            signal(Bank.reciept);
            wait(Bank.leave_loaner);
            signal(Bank.Loaner);
        }
    }
    
    void process_loan() {
        cust_number = Bank.loan_queue.remove();
        System.out.println("Loan Officer serving customer " + cust_number);
        int loan_amount = Bank.customer_obj[cust_number].loanAmount;
        TaskDelay(Bank.lOANER_TASK_TIME); //sleep for 4 minutes 
        System.out.println("Loan Officer " + " processes loan of $ " + loan_amount +  " for customer: " + cust_number);    
        Bank.customer_obj[cust_number].balance -= loan_amount;        
    }
    void loaner_created() { //logger
        System.out.println("Loan Officer " + " created");
    }
    void signal(Semaphore s) {
        s.release();
    }
    void wait(Semaphore s) {
        try {
            s.acquire();
        } catch (InterruptedException e) {

        }
    }
    
    void TaskDelay(int amount){
        try        
        {
            Thread.sleep(amount);
        } 
        catch(InterruptedException ex) {}
    }
}
