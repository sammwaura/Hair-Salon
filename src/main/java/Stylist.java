import org.sql2o.Connection;

import java.util.List;


public class Stylist{

    //private variables for the stylist class i.e their name, style and id
    private String name;
    // name property
    private String style;
    // style property
    private int id;
    // ID property

    // Constructor
    public Stylist(String name, String style) {
        this.name = name;

        this.style = style;
    }
    // method to clear test database
    public static void clear() {
        // try to make a connection to test DB
        try (Connection con = DB.sql2o.open()) {

            String deleteStylistQuery = "DELETE FROM stylist *;";

            con.createQuery(deleteStylistQuery).executeUpdate();
        }
    }
    // method to get the name
    public String getName(){

        return name;
    }

    // method to get style of stylist
    public String getStyle() {

        return style;
    }

    // method to get all inmstances of Stylist from object
    public static List<Stylist> allStyle(){

        String sql = "SELECT * FROM stylist;";

        try(Connection con = DB.sql2o.open()){

        return con.createQuery(sql).executeAndFetch(Stylist.class);

        }
    }
    // method to save all instances of the class
    public void save(){

        try(Connection con = DB.sql2o.open()) {

            String sql = "INSERT INTO stylist (name, style)VALUES(:name, :style)";

            this.id = (int) con.createQuery(sql,true)

            .addParameter("name", this.name)

            .addParameter("style", this.style)

            .executeUpdate()

            .getKey();

        }
    }

    // method to get ID of a stylist
    public int getId() {
        return id;

    }

    // method to find ID
    public static Stylist find(int id) {
        try(Connection con = DB.sql2o.open()) {
            String sql = "SELECT * FROM stylist where id=:id";
            Stylist stylist = con.createQuery(sql)
                    .addParameter("id", id)
                    .executeAndFetchFirst(Stylist.class);
            return stylist;
        }
    }
    // method to retrieve Clients
    public List<Client> getClients() {
        try(Connection con = DB.sql2o.open()) {
            String sql = "SELECT * FROM clients where stylistid=:id";
            return con.createQuery(sql)
            .addParameter("id", this.id)
            .executeAndFetch(Client.class);
        }
    }
    // method to update info on database
    public void update(String name, String style) {

        try(Connection con = DB.sql2o.open()) {

            String sql = "UPDATE stylist SET (name, style) = (:name, :style) WHERE id = :id;";

            con.createQuery(sql)

            .addParameter("name", name)

            .addParameter("style", style)

            .addParameter("id", this.id)

            .executeUpdate();

        }
    }

    // method to delete Stylist from Database
    public void delete(){
        try(Connection con = DB.sql2o.open()) {

            String sql = "DELETE FROM stylist WHERE id = :id";

            con.createQuery(sql)

                    .addParameter("id", this.id)

                    .executeUpdate();

        }
    }

    // method to get all the clients from Database
    public List<Client> all() {

        String sql = "SELECT * FROM clientys WHERE id = :id";

        try(Connection con = DB.sql2o.open()) {

            return con.createQuery(sql)

                       .addParameter("id", this.id)
                       
                       .executeAndFetch(Client.class);
        }
    }
    //method to overide the equals method
    @Override
    public boolean equals(Object otherStylist){

        if (!(otherStylist instanceof Stylist)) {

            return false;

        }else{

            Stylist newStylist = (Stylist) otherStylist;

            return this.getName().equals(newStylist.getName());
        }

    }
}

   