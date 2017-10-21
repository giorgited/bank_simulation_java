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
                    leave_bank();
                    signal(Bank.leave_loaner);
                    break;
                default:            //teller
                    enter_in_teller_queue();
                    wait(Bank.Teller);
                    signal(Bank.ready_for_teller);
                    wait(Bank.ready_for_customer);
                    transaction_request(); //add work to queue                    
                    signal(Bank.ProcessTransaction);
                    wait(Bank.reciept);
                    leave_bank();
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
    void leave_bank() {
        System.out.println("Customer " + custNumber + " leaves bank");
    }
    
    //**** TELLER FUNCTIONS ****
    void enter_in_teller_queue(){
        Bank.teller_queue.add(custNumber);
    }
    void transaction_request(){
        Bank.teller_queue.add(custNumber);
        
        transactionAmount = GetRandomTransactionAmount();
        String transactionDescription = "";
        
        if (transactionType == 1) transactionDescription = "Deposit";
        else transactionDescription = "Withdrawal";
        
        System.out.println("Customer " + custNumber + " requests of teller " + tellerNumber + 
                            " to make a " + transactionDescription + " of $" + transactionAmount);
        
    }
   
    //**** LOANER FUNCTIONS ****
    void loan_request() {
        Bank.loan_queue.add(custNumber);
        System.out.println("Customer " + custNumber + " asks Loaner for $" + loanAmount );
        this.loanAmount = GetRandomTransactionAmount();
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
}
