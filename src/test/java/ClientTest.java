import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.Sql2o;

public class ClientTest{
    // before test make a connection to test database
    @Before
    public void setUp() {

        DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/hair_salon_test", null, null);
    }
     //rule that cleans the tests in the DB  after use  

     @After
     public void tearDown(){ Client.clear(); };
    // test to check if Client instantiates with a name    
     @Test
     public void StylistInstantiatesWithName_String(){
         Stylist myStylist = new Stylist("Jones", "Fade");

         myStylist.save();

         Client newClient = new Client("Jones", "Fade", myStylist.getId());
         
         assertTrue(newClient instanceof Client);
     }
    //  test to check if Client instantiates with a style
     @Test
     public void StylistInstantiatesWithStyle_String(){
        Stylist mStylist = new Stylist("Jones", "Fade");

        mStylist.save();
           
         Client newClient = new Client("Frank", "Box",    mStylist.getId());

         assertTrue(newClient instanceof Client);
 
     }
    //Test to check if all instances of the client are returned 

     @Test

     public void StylistInstantiatesWithAllInstancesOfTheStylist_true(){
        Stylist myStylist = new Stylist("Jones", "Fade");

        myStylist.save();

        Client newClient1 = new Client("Jones","Mo-hawk", myStylist.getId());
        
        newClient1.save();

        Client newClient2 = new Client("Frank","Box", myStylist.getId());

        newClient2.save();
        
        assertTrue(Client.all().contains(newClient2));
     }
    //  test to clear array list
     @Test
     public void Clear_EmptiesAllStylistsFromArrayList_0(){
        Stylist mStylist = new Stylist("Jones", "Fade");

        mStylist.save();

         Client newClient = new Client("Jones", "Mo-hawk", mStylist.getId());

         Client.clear();

         assertEquals(0, Client.all().size());
 
     }
     @Test
     public void save_savesCategoryIdIntoDB_true(){
        Stylist mStylist = new Stylist("Jones", "Box");

        mStylist.save();

         Client myClient = new Client("Wayne", 
         "Mo-Hawk", mStylist.getId());
         
         myClient.save();

         Client savedClient = Client.find(myClient.getId());

         assertEquals(savedClient.getStylistId(), mStylist.getId());
     }

     @Test
     public void getID_InstantiatesWithId_GreaterThan0(){
         Client.clear();

         Stylist mStylist = new Stylist("Jones", "Fade");

         mStylist.save();

         Client newClient = new Client("Jones", "Mo-Hawk", mStylist.getId());

         newClient.save();

         assertTrue(newClient.getId() > 0);
         }
    //  test to check if Stylist is returned with the sameID
    @Ignore
     @Test
     public void find_FindInstantiatesWithSameId_newStylist2() {
       Stylist mStylist = new Stylist("Jones", "Fade");
         
       mStylist.save();

       Client newClient = new Client("Jones", "Mo-hawk", mStylist.getId());

       newClient.save();

       Client newClient2 = new Client("Frank", "Box", mStylist.getId());

       assertEquals(Client.find(newClient2.getId()), newClient2);
     }

    //  test to check if stylist can be updated
    @Ignore
     @Test
     public void Update_UpdateInstantiatesWithUpdatedInformation_True() {
       Stylist mStylist = new Stylist("Jones", "Fade");

       mStylist.save();

       Client newClient = new Client("Jones", "Mo-hawk", mStylist.getId());

       newClient.save();

       newClient.update("Mike", "Fine Cut");

       assertEquals("Mike", Client.find(newClient.getId()).getClientName());
     }

    //  test to check if stylist can be deleted
     @Ignore
     @Test
     public void Delete_DeletesInfoOfStylist_True(){
         Stylist mStylist = new Stylist("Jones", "Fade");

         mStylist.save();

         Client newClient = new Client("Jones", "Mo-hawk",  mStylist.getId());

         newClient.save();

         int newClientId = newClient.getId();

         newClient.deleteClient();

         assertEquals(null, Client.find(newClientId));
     }
    }


 
