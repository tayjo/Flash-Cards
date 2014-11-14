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
public class StudyListTest {

    Item flashCard;
    StudyList flashCards;
    
    @Before
    public void setUp() throws Exception {
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
        fail("Not yet implemented");
    }

    /**
     * Test method for find(String).
     */
    @Test
    public final void testFind() {
        fail("Not yet implemented");
    }

    /**
     * Test method for delete(Item).
     */
    @Test
    public final void testDelete() {
        fail("Not yet implemented");
    }

    /**
     * Test method for modify(Item, String, String).
     */
    @Test
    public final void testModify() {
        fail("Not yet implemented");
    }

}
