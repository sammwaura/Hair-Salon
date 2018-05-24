import org.sql2o.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Stylist{

    //private variables for the stylist class i.e their name, phone number and id
    private String stylistName;
    private int phoneNumber;
    private int id;


    //method to overide the equals method
    @Override
    public boolean equals(Object otherStylist){
        if (!(otherStylist instanceof Stylist)) {
            return false;
        }else{
            Stylist newStylist = (Stylist) otherStylist;
            return this.getName().equals(newStylist.getName())&&
            this.getNumber()==newStylist.getNumber();
        }

    }

    //constructor for the Stylist
    public Stylist(String name, int number){
        this.stylistName = name;
        this.phoneNumber = number;
    }

    //getter method for the name;
    public String getName(){
        return stylistName;
    }

    //getter method for the phone number;
    public int getNumber(){
        return phoneNumber;
    }

    //getter method for the id
    public int getId(){
        return id;
    }
    //the method that saves the entries to the database and also gets the key id and returns it;
    public void save(){
        try(Connection con = DB.sql2o.open()){
            String sql = "INSERT INTO stylists (stylistName, phoneNumber) VALUES (:stylistName, :phoneNumber)";
            this.id = (int) con.createQuery(sql, true)
            .addParameter( "stylistName", this.stylistName)
            .addParameter("phoneNumber", this.phoneNumber)
            .executeUpdate()
            .getKey();
        }
    }
    //the method gets all the data from the table stylist and convets it to type object Stylist
    public static List<Stylist>all(){
        String sql = "SELECT * FROM stylists";
        try(Connection con = DB.sql2o.open()){
            return con.createQuery(sql).executeAndFetch(Stylist.class);
        }
    }
    //the find method finds the first id property in the table stylist that matches the id proprty provided then casts it into a Stylist object
    public static Stylist find(int id){
        try(Connection con = DB.sql2o.open()){
        String sql = "SELECT * FROM stylists WHERE id=:id";
        Stylist mystylist = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Stylist.class);
        return mystylist;
    }
    }

    //the method  updates the stylist name and phone number 
    public void update(String stylistName, int phoneNumber){
        try(Connection con = DB.sql2o.open()){
            String sql = "UPDATE stylists SET stylistName= :stylistName, phoneNumber= :phoneNumber WHERE id= :id";
            con.createQuery(sql)
            .addParameter( "stylistName", stylistName)
            .addParameter("phoneNumber", phoneNumber)
            .addParameter("id", id)
            .executeUpdate();
        }

    }
    //the method  deletes the stylist name and phone number 

    public void delete(){
        try(Connection con = DB.sql2o.open()){
            String sql = "DELETE FROM stylists WHERE id=:id";
            con.createQuery(sql)
            .addParameter("id", id)
            .executeUpdate();
        }
    }

    //method that retrieves all the clients that are saved under a specific Stylist
    public List<Client> getClients(){
        try(Connection con = DB.sql2o.open()){
            String sql="SELECT * FROM clients WHERE stylistid=:id";
            return con.createQuery(sql)
            .addParameter("id", this.id)
            .executeAndFetch(Client.class);
        }
    }
}