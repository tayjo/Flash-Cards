/**
 * 
 */
package flashCards;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * @author David Matuszek, Martha Trevino & Josh Taylor
 */
public class StudyListTest {

    Item flashCard;
    StudyList flashCards;
    
    @Before
    public void setUp() throws Exception {
    	flashCards = new StudyList();
    	flashCard = new Item("hello", "hola");
    	flashCards.add(flashCard);
    }

    /**
     * Test method for constructor.
     */
    @Test
    public final void testStudyList() {
        fail("Not yet implemented");
    }

    /**
     * Test method for add(Item).
     */
    @Test
    public final void testAdd() {
        assertEquals(null, flashCards.find("kitchen"));
        flashCards.add(new Item("kitchen", "cocina"));
        assertFalse(null == flashCards.find("kitchen"));
    }

    /**
     * Test method for find(String).
     */
    @Test
    public final void testFind() {
    	assertEquals(flashCard, flashCards.find("hello"));
    	assertEquals(flashCard, flashCards.find("hola"));
    }

    /**
     * Test method for delete(Item).
     */
    @Test
    public final void testDelete() {
        flashCards.delete(flashCard);
        assertEquals(null, flashCards.find("hello"));
    }

    /**
     * Test method for modify(Item, String, String).
     */
    @Test
    public final void testModify() {
        fail("Not yet implemented");
    }

}
