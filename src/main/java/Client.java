import java.util.ArrayList;
import java.util.List;
import org.sql2o.*;

public class Client{

    //private variables for the client class i.e their name, phone number,stylistId and id
    private String clientName;
    private int phoneNumber;
    private int id;
    private int stylistId;

    //constructor for the Client
    public Client(String name, int number, int stylistId){
        this.clientName= name;
        this.phoneNumber = number;
        this.stylistId = stylistId;
    }

    //method to overide the equals method
    @Override

    public boolean equals(Object otherClient){
        if (!(otherClient instanceof Client)) {
            return false;
        }else{
            Client newClient = (Client) otherClient;
            return this.getName().equals(newClient.getName())&&
            this.getId() == newClient.getId()&&
            this.getStylistId() == newClient.getStylistId()&&
            this.getPhoneNumber() == newClient.getPhoneNumber();

        }
    }
    //getter method for the name;
    public String getName(){
        return clientName;
    }
    //getter method for the number
    public int getPhoneNumber(){
        return phoneNumber;
    }
    //getter method for the id
    public int getId(){
        return id;
    }
    //getter method for the sylistId
    public int getStylistId(){
        return stylistId;
    }

    //the method gets all the data from the table clients and convets it to type object Client
    public static List<Client> all(){
        String sql = "SELECT * FROM clients";
        try(Connection con = DB.sql2o.open()){
            return con.createQuery(sql).executeAndFetch(Client.class);
        }
    }
    //the method that saves the entries to the database and also gets the key id and returns it;
    public void save(){
        try(Connection con = DB.sql2o.open()){
            String sql = "INSERT INTO clients(clientName, phoneNumber, stylistId) VALUES(:clientName,:phoneNumber,:stylistId)";
            this.id = (int) con.createQuery(sql, true)
            .addParameter( "clientName", this.clientName)
            .addParameter("phoneNumber", this.phoneNumber)
            .addParameter("stylistId", this.stylistId)
            .executeUpdate()
            .getKey();
        }
    }

    //the find method finds the first id property in the table clients that matches the id proprty provided then casts it into a Client object
    
    public static Client find(int id){
        try(Connection con = DB.sql2o.open()){
            String sql = "SELECT * FROM clients WHERE id=:id";
            Client client = con.createQuery(sql)
            .addParameter("id", id)
            .executeAndFetchFirst(Client.class);
            return client;

        }

    }
    //the method update updates the clients name and phone nummber 
    public void update(String clientName, int clientNumber){
        try(Connection con = DB.sql2o.open()){
            String sql = "UPDATE clients SET clientName= :clientName, phoneNumber=:phoneNumber WHERE id=:id";
            con.createQuery(sql)
            .addParameter( "clientName", clientName)
            .addParameter("phoneNumber", phoneNumber)
            .addParameter("id",id)
            .executeUpdate();
        }

    }

    //the method  deletes the clients name and phone number 
    public void delete(){
        try(Connection con = DB.sql2o.open()){
            String sql = "DELETE FROM clients WHERE id=:id";
            con.createQuery(sql)
            .addParameter("id", id)
            .executeUpdate();
        }
    }

}