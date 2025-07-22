class Bankaccount {
    private int accountNumber;
    private String accountHolder;
    private double balance;


    public Bankaccount(int accountNumber, String accountHolder, double balance){
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        this.balance = balance;

    }

    public void deposit(double amount) {
        balance += amount;
        System.out.println("Deposited: " + amount);
    }
    public void withdraw(double amount){
        if (balance >= amount){
            balance -= amount ;
            System.out.println("withdrawn : " + amount );
        }
        else {
            System.out.println("insufficient balance");
        }
    }

    public   double getBalance(){
        return balance;
    }
}

class savingsaccount extends Bankaccount{
    private double  penalty;
    public savingsaccount(int accountNumber,  String accountHolder, double balance , double penalty){
        super(accountNumber, accountHolder , balance );
        this.penalty = penalty;
    }
    @Override
    public void withdraw(double amount){
        if(getBalance()>=amount) {
            super.withdraw(amount + penalty);
            System.out.println("penaly applied:"  + penalty);}
        else{
            System.out.println("insufficient amount");
        }
    }


}

class Customer{
    public  int customerid;
    public String name;
    public String address;

    public  Customer(int customerid, String name,String address){
        this.customerid =  customerid;
        this.name = name;
        this.address = address;




    }


}

public class jul21_bank {

    public static void main(String[] args) {
        Bankaccount account = new Bankaccount(12345 , "JOHN DOE " , 1000);
        savingsaccount SAVINGSACCOUNT = new savingsaccount(645523, "JOHN DOE  ",  1000 , 10 );
        SAVINGSACCOUNT.deposit(100);
        SAVINGSACCOUNT.withdraw(10);


        Customer customer = new Customer( 1200 , "JOHN DOE" , "DEHRADUN" );

        System.out.println("CUSTOMER ID  :"  +  customer.customerid );
        System.out.println("CUSTOMEr  NAME :"  +  customer.name );
        System.out.println("CUSTOMER ADDRESS : " +  customer. address);


    }
}
