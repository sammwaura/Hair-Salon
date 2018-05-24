
import java.util.List;

import org.sql2o.Connection;

public class Client{

    //private variables for the client class i.e their name, phone number,stylistId and id
    private String name;
    // name property
    private String style;
    // Style property
    private int id;
    // id property
    private int stylistId;
    
    //constructor for the Client
    public Client(String name, String style, int stylistId){
        this.name= name;

        this.style = style;

        this.stylistId = stylistId;
    }
    // method to clear Test Database
    public static void clear(){
        // try to make connection to test DB
        try (Connection con = DB.sql20.open()){

            String deleteClientQuery = DELETE FROM clients *;

            con.createQuery(deleteClientQuery).executeUpdate();

            }
        }
        
        //method to get the name
         public String getClientName() {

            return name;
         }
        //  method to get Style of client
        public String getClientStyle() {

            return style;
        }

        // method to get all instances of Client from object
        public static List<Client> all() {
            String sql = "SELECT id, name, stylistId FROM clients";
            try(Connection con = DB.sql2o.open()) {
                return con.createQuery(sql).executeAndFetch(Client.class);
            }
        }
        // method to get Stylists Id
        public int getStylistId() {
            return stylistId;
        }
        // method to get client's Id
        public int getId() {

            return id;
        }
        // method that saves all instances of class
        public void save(){

            try(Connection con = DB.sql20.open()) {

                String sql = "INSERT INTO clients(name, style, stylistId)VALUES (:name, :style, :stylistId)";

                this.id = (int) con.createQuery(sql, true)

                .addParameter("name", this.name)

                .addParameter("style", this.style)

                .addParameter("stylistId", this.stylistId)

                .executeUpdate()

                .getKey();

            }
        }
        // method to find ID
        public static Client find(int id) {
            try(Connection con = DB.sql2o.open()) {
                String sql = "SELECT * FROM clients where id=:id";
                Client client = con.createQuery(sql)
                        .addParameter("id", id)
                        .executeAndFetchFirst(Client.class);
                return client;
            }
        }
        // method to update info on the databse
        public void update(String name, String style) {
            try(Connection con = DB.sql2o.open()) {
                String sql = "UPDATE clients SET (name, style) = (:name, :style) WHERE id= :id,";
                con.createQuery(sql)
                           .addParameter("name", name)
                           .addParameter("style", style)
                           .addParameter("id", id)
                           .executeUpdate();

            }
        }
        // method to delete Client from database
        public void daleteClient(){
            try (Connection con = DB.sql2o.open()) {
                String sql = "DELETE FROM clients WHERE id = :id";
                con.createQuery(sql)
                         .addParameter("id", id)
                         .executeUpdate();
            }
        }
    //method to overide the equals method
    @Override

    public boolean equals(Object otherClient){

        if (!(otherClient instanceof Client)) {

            return false;

        }else{

            Client newClient = (Client) otherClient;

            return this.getClientName().equals(newClient.getClientName())&&
            this.getId() == newClient.getId()&&
            this.getStylistId() == newClient.getStylistId();
            

        }
    }

}
    