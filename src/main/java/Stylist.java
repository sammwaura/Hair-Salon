import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;

public class StylistTest{

    //rule that cleans the tests in the DB before and after tests    
    @Rule
    public DatabaseRule database = new DatabaseRule();

    
    @Test
    public void stylist_instantiatesCorrectly_true(){
        Stylist myStylist = new Stylist("jones", 11111);
        assertTrue(myStylist instanceof Stylist);
    }

    @Test
    public void getName_instanciatesNameCorrectly_jones(){
        Stylist myStylist = new Stylist("jones", 11111);
        assertEquals("jones", myStylist.getName());

    }

    @Test
    public void getNumber_instanciatesNumberCorrectly_11111(){
        Stylist myStylist = new Stylist("jones", 11111);
        assertEquals(11111, myStylist.getNumber());

    }
    @Test
    public void save_savesToTheDatabase_true(){
        Stylist myStylist = new Stylist("jones", 11111);
        myStylist.save();
        assertTrue(Stylist.all().get(0).equals(myStylist));
    }
    @Test
    public void all_returnsAllInstancesOfStylist_true(){
        Stylist firstStylist = new Stylist("sam", 5678);
        firstStylist.save();
        Stylist secondStylist = new Stylist("John", 12567);
        secondStylist.save();
        assertTrue(Stylist.all().get(0).equals(firstStylist));
        assertTrue(Stylist.all().get(1).equals(secondStylist));
    }


}