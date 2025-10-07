package Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Client  {
    private UUID id ;
    private String  firstName  ;
    private String lastName  ;
    private String CIN ;
    private String phoneNumber ;
    private  String email ;
    private List<Account> accounts = new ArrayList<>();
    private double salaire;
    public Client(String firstName, String lastName, String cin, String phoneNumber, String email,double salaire) {
        this.firstName = firstName ;
        this.lastName = lastName;
        this.CIN = cin ;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.salaire = salaire;
    }

    public double getSalaire() {
        return salaire;
    }

    public void setSalaire(double salaire) {
        this.salaire = salaire;
    }

    public Client() {

    }

    public UUID getId() {
         return id ;

    }
     public String getFirstName(){return firstName;}
     public String getLastName(){return this.lastName;};
     public String getCIN(){return CIN;};
     public String getPhoneNumber(){return phoneNumber;};
     public String getEmail(){return email ;};
     public void setId( UUID id) {this.id = id;}
     public void setFirstName(String firstName){this.firstName = firstName;};
     public void setLastName(String lastName){this.lastName = lastName;};
     public void setCIN(String CIN){this.CIN = CIN;};
     public void setEmail (String email){this.email = email;};
     public String setPhoneNumber(String phoneNumber){this.phoneNumber = phoneNumber;
         return phoneNumber;
     };
    public List<Account> getAccounts() { return accounts; }
    public void  setAccounts(List<Account> accounts){
        this.accounts = accounts;

    }
     public String toString(){
         return "client{" +
                 "id" +id+
            "firstName" +firstName +
                 "lastName" +lastName+
                 "CIN" +CIN+
                 "phoneNumber" +email+ '}';

     }



}
