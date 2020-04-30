package grupo8;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class TestDummyDesing
{
    /**
     * Rigorous Test :-)
     */
    DummyDesign dummyTest = new DummyDesign();

    @Test
    public void testIntegrante2()
    {
        assertEquals(2, dummyTest.integrante2());
    }
    
    @Test
    public void testIntegrante1()
    {
        assertEquals(1, dummyTest.integrante1());
    }
    
    @Test
    public void testIntegrante4()
    {
    	assertEquals(4, dummyTest.integrante4());
    }
    
    @Test
    public void testIntegrante3()
    {
    	assertEquals(3, dummyTest.integrante3());
    }
    
    @Test
    public void testIntegrante5() {
    	
    	assertEquals(5, dummyTest.integrante5());
    }
    
}
