package morse.app;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldEncodeAndDecode()
    {
        byte[] encoded = Morse.encode("i åæam,. a pe;rson\ndu er fed");
        String decoded = Morse.decode(encoded);
        assertEquals("i am a person du er fed", decoded);
        SoundPlayer.playSequence(encoded);
    }
    @Test
    public void UITest(){
        UserInterface.run();
    }

}
