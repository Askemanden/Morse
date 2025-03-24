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
    public void formatDeformat(){
        byte[] encoded = Morse.encode("i am a person du er fed");
        for(Byte b: encoded){
            String binaryString = String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0'); // Convert to binary
            System.out.println(binaryString);

        }
        String formatted = Morse.formatEncoded(encoded);
        System.out.println(formatted);
        byte[] deformatted = Morse.deformatEncoded(formatted);
        for(Byte b: deformatted){
            String binaryString = String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0'); // Convert to binary
            System.out.println(binaryString);
        }
        for (int i = 0; i < encoded.length; i++){
            assertEquals(encoded[i], deformatted[i]);
        }
    }

    @Test
    public void UITest(){
        UserInterface.run();
        
    }

}
