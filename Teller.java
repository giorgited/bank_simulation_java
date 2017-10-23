/*
Giorgi Tediashvili
CS4348 Project 2
10/28/2017
 */
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;

public class Teller implements Runnable{
    public int tellerNumber;
    private int cust_number;

    Teller(int tellerNumber) {
        this.tellerNumber = tellerNumber;
    }
    @Override
    public void run() {
        teller_created();
        while (true) {
            wait(Bank.ready_for_teller);
            prepareForCustomer();
            signal(Bank.ready_for_customer);
            wait(Bank.ProcessTransaction);
            process_transaction();
            signal(Bank.reciept);
            wait(Bank.leave_teller);
            signal(Bank.Teller);
        }
    }
    
    void prepareForCustomer(){  //let customer know whos the teller that will be assisting
        cust_number = Bank.teller_queue.remove();
        Bank.customer_obj[cust_number].tellerNumber = this.tellerNumber;
    }
    void process_transaction() {        
        System.out.println("Teller " + tellerNumber + " begins serving customer " + cust_number);
        
        int transactionType = Bank.customer_obj[cust_number].transactionType;
        int transactionAmount = Bank.customer_obj[cust_number].transactionAmount;
        
        TaskDelay(Bank.TELLER_TASK_TIME); //sleep for 4 minutes 
        
        if (transactionType == 1){  //deposit
            Bank.customer_obj[cust_number].balance += transactionAmount;
            System.out.println("Teller " + tellerNumber + " processes deposit of $ " + transactionAmount +  " for customer: " + cust_number); 
        } else if (transactionType == 2){   //withdrawal
            Bank.customer_obj[cust_number].balance -= transactionAmount;
            System.out.println("Teller " + tellerNumber + " processes withdrawal of $ " + transactionAmount +  " for customer: " + cust_number); 
        }
    }
    void teller_created() { //logger
        System.out.println("Teller " + tellerNumber + " created");
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
