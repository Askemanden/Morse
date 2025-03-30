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
    public void testCleanFormatted(){
        String testString = "i am a person du er fed";
        byte[] encoded = Morse.encode(testString);
        String formatted = Morse.formatEncoded(encoded);
        StringBuilder toClean = new StringBuilder(formatted);
        toClean.append(" 1  23\n  ");
        toClean.insert(3, "jeg er en abe  \n");
        toClean.insert(2, ",,,");
        String cleanedFormatted = Morse.cleanFormatted(toClean.toString());
        assertEquals(formatted, cleanedFormatted);
    }

    @Test
    public void testSoundPlayer(){
        SoundPlayer.playSequence(new byte[]{(byte) 0b10101010, (byte) 0b01010101});
    }

    @Test
    public void UITest(){
        UserInterface.run();
        
    }

}
