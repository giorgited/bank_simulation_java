import java.util.LinkedList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;

public class Bank {
    public static Semaphore teller = new Semaphore(2, true);
    public static Semaphore coord = new Semaphore(2, true);
    public static Semaphore loaner = new Semaphore(1, true);
    public static Semaphore ready_for_teller = new Semaphore(0, true);
    public static Semaphore ready_for_loaner = new Semaphore(0, true);
    public static Semaphore leave_teller = new Semaphore(0, true);
    public static Semaphore leave_loaner = new Semaphore(1, true);
    public static Queue < Integer > queue = new LinkedList < > ();

    public static Customer objCust = new Customer;
    public static BankTeller objWork = new BankTeller;
    public static Loaner Loaner = new Loaner;
}
