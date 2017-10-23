/*
Giorgi Tediashvili
CS4348 Project 2
10/28/2017
 */
import java.util.Random;
import java.util.concurrent.Semaphore;

public class Customer implements Runnable{
    //customer related variables
    public int custNumber;
    public int balance;    
    //loan related vraibles
    public int loanAmount;
    //teller related varaibles
    public int transactionType; 
    public int transactionAmount;
    public int tellerNumber;
    
    
    Customer(int custNumber) {
        this.custNumber = custNumber;
        this.balance = 1000;
    }


    @Override
    public void run() {        
        cust_created();
        this.transactionType = RandomizeTransaction();
        for (int i=0; i < 3; i++){
            cust_enterBank();
            switch(transactionType){                
                case 3:              //loan officer      
                    wait(Bank.Loaner);
                    loan_request(); 
                    signal(Bank.ProcessLoan);
                    wait(Bank.reciept);
                    get_cash_and_reciept_and_leave();
                    signal(Bank.leave_loaner);
                    break;
                case 2:            //teller withdrawal
                    enter_in_teller_queue();
                    wait(Bank.Teller);
                    signal(Bank.ready_for_teller);
                    wait(Bank.ready_for_customer);
                    withdraw(GetRandomTransactionAmount()); //add work to queue                    
                    signal(Bank.ProcessTransaction);
                    wait(Bank.reciept);
                    get_cash_and_reciept_and_leave();
                    signal(Bank.leave_teller); 
                    break;
                case 1:            //teller deposit
                    enter_in_teller_queue();
                    wait(Bank.Teller);
                    signal(Bank.ready_for_teller);
                    wait(Bank.ready_for_customer);
                    deposit(GetRandomTransactionAmount()); //add work to queue                    
                    signal(Bank.ProcessTransaction);
                    wait(Bank.reciept);
                    get_reciept_and_leave();
                    signal(Bank.leave_teller); 
                    break;
            }       
        }         
    }
    
    // **** SHARED FUNCTIONS ****
    void cust_created() {
        System.out.println("Customer " + custNumber + " created");        
        this.transactionType = RandomizeTransaction();
    }    
    void cust_enterBank() {        
        System.out.println("Customer " + custNumber + " enters Bank");        
    }
    void get_cash_and_reciept_and_leave() {
        TaskDelay(Bank.CUSTOMER_TASK_TIME);
        System.out.println("Customer " + custNumber + " leaves bank");
    }
    void get_reciept_and_leave() {
        TaskDelay(Bank.CUSTOMER_TASK_TIME);
        System.out.println("Customer " + custNumber + " leaves bank");
    }
    
    //**** TELLER FUNCTIONS ****
    void enter_in_teller_queue(){
        Bank.teller_queue.add(custNumber);
    }
    void deposit(int amount){
        Bank.teller_queue.add(custNumber);
        transactionAmount = amount;
        
        System.out.println("Customer " + custNumber + " requests of teller " + tellerNumber + 
                            " to make a deposit of $" + transactionAmount); 
        
        TaskDelay(Bank.CUSTOMER_TASK_TIME);
    }
    void withdraw(int amount){
        Bank.teller_queue.add(custNumber);
        transactionAmount = amount;
        
        TaskDelay(Bank.CUSTOMER_TASK_TIME);   //sleep for 1 minute
        
        System.out.println("Customer " + custNumber + " requests of teller " + tellerNumber + 
                            " to make a withdraw of $" + transactionAmount);          
    }
   
    //**** LOANER FUNCTIONS ****
    void loan_request() {
        Bank.loan_queue.add(custNumber);
        System.out.println("Customer " + custNumber + " asks Loaner for $" + loanAmount );
        this.loanAmount = GetRandomTransactionAmount();
        TaskDelay(Bank.CUSTOMER_TASK_TIME);
    }
    
    //***** SEMAPHORS *****
    void wait(Semaphore s) {
        try {
            s.acquire();
        } catch (InterruptedException e) {

        }
    }
    void signal(Semaphore s) {
        s.release();
    } 
    
    //***** HELPERS *****
    int RandomizeTransaction(){
        Random r = new Random();
        return r.nextInt(3) + 1;
    }
    int GetRandomTransactionAmount(){
        Random r = new Random();
        return (r.nextInt(5) + 1) * 100;
    }
    void TaskDelay(int amount){
        try        
        {
            Thread.sleep(amount);
        } 
        catch(InterruptedException ex) {}
    }
}
