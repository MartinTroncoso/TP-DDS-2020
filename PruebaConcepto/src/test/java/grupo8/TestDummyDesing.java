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
    public void integrante2Devuelve2()
    {
        assertEquals( 2 , dummyTest.integrante2() );
    }
}
