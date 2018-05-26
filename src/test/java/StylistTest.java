import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.Sql2o;
import java.util.Arrays;

public class StylistTest{

    // before test make a connection to the DB 
    @Before    
    @Rule
    public void setUp(){

        DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432//hair_salon_test", null, null);
    }
    // clearing the testdatabase after use
    @After
    public void tearDown(){ Stylist.clear();
    }
    // test to check if Stylist Instantiates with a name
    @Test
    public void Stylist_instantiatesWithName_String(){
        Stylist newStylist = new Stylist("Jones", "Fade");

        assertTrue(newStylist instanceof Stylist);
    }
    // test to check if stylist instantiates with a style
    @Test
    public void StylisytInstantiatesWithStyle_String(){
        Stylist newStylist = new Stylist("Frank","Box");

        assertTrue(newStylist instanceof Stylist);
    }
    // test to check if all insatnces of the stylists are returned
    @Test
    public void StylistInstantiatesWithAllInstancesOfTheStylist_true(){
        Stylist newStylist1 = new Stylist("Jones", "Mo-hawk");

        newStylist1.save();

        Stylist newStylist2 = new Stylist("Frank", "Box");

        newStylist2.save();

        assertTrue(Stylist.allStyle().contains(newStylist2));

    }
    // test to clear array list
    @Test
    public void Clear_EmptiesAllStylistsFromArrayList_0(){
        Stylist newStylist = new Stylist("Jones", "Mo-hawk");

        Stylist.clear();

        assertEquals(0, Stylist.allStyle().size());
    }

    // test to check whether we are receiving all clients
    @Test
    public void getTasks_retrieveAllTasksFromDatabase_tasksList(){
        Stylist myStylist = new Stylist("Jones", "Box");
        myStylist.save();
        Client myClient1 = new Client("Wayne", "Mo-hawk", myStylist.getId());
        Client myClient2 = new Client("Mike", "Clean Cut", myStylist.getId());
        myClient2.save();
        Client[] clients = new Client[] { myClient1, myClient2 };
        assertTrue(myStylist.getClients().containsAll(Arrays.asList(clients)));
    }
    // test to check if stylist instantiates with ID
    @Test
    public void getID_instantiatesWithId_GreaterThan0(){
        Stylist.clear();
       
        Stylist newStylist = new Stylist("Jones", "Mo-hawk");

        newStylist.save();

        assertTrue(newStylist.getId() > 0);
    }
// test to check if Stylist is returned with the same ID
@Test
public void find_FindInstantiatesWithSameId_newStylist2() {
    Stylist newStylist1 = new Stylist ("Jones", "Mo-hawk");

    newStylist1.save();

    Stylist newStylist2 = new Stylist("Frank", "Box");

    newStylist2.save();

    assertEquals(Stylist.find(newStylist2.getId()), newStylist2);
}
// test to check if stylist's details can be updated
@Testpublic void Update_UpdateInstantiatesWithUpdatedInformation_True(){
    Stylist newStylist = new Stylist("Jones", "Mo-hawk");

    newStylist.save();

    newStylist.update("Mike", "fine Cut");

    assertEquals("Mike", Stylist.find(newStylist.getId()).getName());

}

// test to check if stylist can be deleted
@Test
public void Delete_DeleteDeleteInfoOfStylist_True(){

    Stylist newStylist = new Stylist("Jones", "Mo-hawk");

    newStylist.save();

    int newStylistId = newStylist.getId();

    newStylist.delete();

    assertEquals(null, Stylist.find(newStylistId));
}

}