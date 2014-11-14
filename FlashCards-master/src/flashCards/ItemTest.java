/**
 * 
 */
package flashCards;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * @author David Matuszek
 */
public class ItemTest {


    Item flashCard;
	
    @Before
    public void setUp() throws Exception {
    	flashCard = new Item("hello", "hola");
    }

    /**
     * Test method for (String, String) constructor.
     */
    @Test
    public final void testItemStringString() {
    	flashCard = new Item("hello", "hola");
        assertTrue(flashCard instanceof Item);
        assertEquals("hello", flashCard.getStimulus());
        assertEquals("hola", flashCard.getResponse());
        assertEquals(0, flashCard.getTimesCorrect());
    }

    /**
     * Test method for (String, String, int) constructor.
     */
    @Test
    public final void testItemStringStringInt() {
    	flashCard = new Item("hello", "hola", 5);
        assertTrue(flashCard instanceof Item);
        assertEquals("hello", flashCard.getStimulus());
        assertEquals("hola", flashCard.getResponse());
        assertEquals(5, flashCard.getTimesCorrect());
    }
    
    /**
     * Test method for setStimulus(String) and
     * getStimulus() (combined).
     */
    @Test
    public final void testSetAndGetStimulus() {
    	flashCard.setStimulus("kitchen");
        assertEquals("kitchen", flashCard.getStimulus());
    }

    /**
     * Test method for setResponse(String) and
     * getResponse() (combined).
     */
    @Test
    public final void testSetAndGetResponse() {
    	flashCard.setResponse("cocina");
    	assertEquals("cocina", flashCard.getResponse());
    }

    /**
     * Test method for setTimesCorrect(int) and
     * getTimesCorrect() (combined).
     */
    @Test
    public final void testSetAndGetTimesCorrect() {
        flashCard.setTimesCorrect(3);
        assertEquals(3, flashCard.getTimesCorrect());
    }

    @Test(expected=IllegalArgumentException.class)
    public final void testSetTimesCorrectException() {
        flashCard.setTimesCorrect(-3);
    }
    
}
