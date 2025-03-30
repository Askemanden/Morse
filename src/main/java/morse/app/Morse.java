package morse.app;

import java.util.Map;
import java.util.HashMap;
import java.util.Collections;

public class Morse {

    /* binary representation of morse characters. 
     * 1 indicates start of the sequence. 
     * Every character starts with 00 except space, represented by 11111111 (8 on bits)
    */

    static final Map<Character, Byte> morseMap;
    static final Map<Byte, Character> charMap;


    static{ 
        Map<Character, Byte> tempMorseMap = new HashMap<>();
        tempMorseMap.put('a', (byte) 0b000101);
        tempMorseMap.put('b', (byte) 0b011000);
        tempMorseMap.put('c', (byte) 0b011010);
        tempMorseMap.put('d', (byte) 0b001100);
        tempMorseMap.put('e', (byte) 0b000010);
        tempMorseMap.put('f', (byte) 0b010010);
        tempMorseMap.put('g', (byte) 0b001110);
        tempMorseMap.put('h', (byte) 0b010000);
        tempMorseMap.put('i', (byte) 0b000100);
        tempMorseMap.put('j', (byte) 0b010111);
        tempMorseMap.put('k', (byte) 0b001101);
        tempMorseMap.put('l', (byte) 0b010100);
        tempMorseMap.put('m', (byte) 0b000111);
        tempMorseMap.put('n', (byte) 0b000110);
        tempMorseMap.put('o', (byte) 0b001111);
        tempMorseMap.put('p', (byte) 0b010110);
        tempMorseMap.put('q', (byte) 0b011101);
        tempMorseMap.put('r', (byte) 0b001010);
        tempMorseMap.put('s', (byte) 0b001000);
        tempMorseMap.put('t', (byte) 0b000011);
        tempMorseMap.put('u', (byte) 0b001001);
        tempMorseMap.put('v', (byte) 0b010001);
        tempMorseMap.put('w', (byte) 0b001011);
        tempMorseMap.put('x', (byte) 0b011001);
        tempMorseMap.put('y', (byte) 0b011011);
        tempMorseMap.put('z', (byte) 0b011100);
        tempMorseMap.put('æ', (byte) 0b010101);
        tempMorseMap.put('ø', (byte) 0b011110);
        tempMorseMap.put('å', (byte) 0b101101);
        tempMorseMap.put('1', (byte) 0b101111);
        tempMorseMap.put('2', (byte) 0b100111);
        tempMorseMap.put('3', (byte) 0b100011);
        tempMorseMap.put('4', (byte) 0b100001);
        tempMorseMap.put('5', (byte) 0b100000);
        tempMorseMap.put('6', (byte) 0b110000);
        tempMorseMap.put('7', (byte) 0b111000);
        tempMorseMap.put('8', (byte) 0b111100);
        tempMorseMap.put('9', (byte) 0b111110);
        tempMorseMap.put('0', (byte) 0b111111);
        tempMorseMap.put(' ', (byte) 0b11111111);
        morseMap = Collections.unmodifiableMap(tempMorseMap);
    }
    static {
        Map<Byte, Character> tempCharMap = new HashMap<>();
        tempCharMap.put((byte) 0b000101, 'a');
        tempCharMap.put((byte) 0b011000, 'b');
        tempCharMap.put((byte) 0b011010, 'c');
        tempCharMap.put((byte) 0b001100, 'd');
        tempCharMap.put((byte) 0b000010, 'e');
        tempCharMap.put((byte) 0b010101, 'f');
        tempCharMap.put((byte) 0b001110, 'g');
        tempCharMap.put((byte) 0b010000, 'h');
        tempCharMap.put((byte) 0b000100, 'i');
        tempCharMap.put((byte) 0b010111, 'j');
        tempCharMap.put((byte) 0b001101, 'k');
        tempCharMap.put((byte) 0b010100, 'l');
        tempCharMap.put((byte) 0b000111, 'm');
        tempCharMap.put((byte) 0b000110, 'n');
        tempCharMap.put((byte) 0b001111, 'o');
        tempCharMap.put((byte) 0b010110, 'p');
        tempCharMap.put((byte) 0b011101, 'q');
        tempCharMap.put((byte) 0b001010, 'r');
        tempCharMap.put((byte) 0b001000, 's');
        tempCharMap.put((byte) 0b000011, 't');
        tempCharMap.put((byte) 0b001001, 'u');
        tempCharMap.put((byte) 0b010001, 'v');
        tempCharMap.put((byte) 0b001011, 'w');
        tempCharMap.put((byte) 0b011001, 'x');
        tempCharMap.put((byte) 0b011011, 'y');
        tempCharMap.put((byte) 0b011100, 'z');
        tempCharMap.put((byte) 0b010101, 'æ');
        tempCharMap.put((byte) 0b011110, 'ø');
        tempCharMap.put((byte) 0b101101, 'å');
        tempCharMap.put((byte) 0b101111, '1');
        tempCharMap.put((byte) 0b100111, '2');
        tempCharMap.put((byte) 0b100011, '3');
        tempCharMap.put((byte) 0b100001, '4');
        tempCharMap.put((byte) 0b100000, '5');
        tempCharMap.put((byte) 0b110000, '6');
        tempCharMap.put((byte) 0b111000, '7');
        tempCharMap.put((byte) 0b111100, '8');
        tempCharMap.put((byte) 0b111110, '9');
        tempCharMap.put((byte) 0b111111, '0');
        tempCharMap.put((byte) 0b11111111,' ');
        charMap = Collections.unmodifiableMap(tempCharMap);
    }

    public static String clean(String text){
        return text.replaceAll("[^a-zA-Z0-9\\sæøåÆØÅ]", "").replaceAll("[\\n\\r]", " ");
    }

    public static String cleanFormatted(String text){
        // Remove all non space, non - . | / characters
        StringBuilder cleaned = new StringBuilder(text.replaceAll("[^\\-\\.\\|/\\s]", "").replaceAll("[\\n\\r]", ""));
        // Remove all additional spaces and additional new word characters
        for(int i = 0; i < cleaned.length(); i++){
            if(cleaned.charAt(i) == ' '){
                int j = i+1;
                while(j < cleaned.length() && cleaned.charAt(j) == ' '){
                    cleaned.deleteCharAt(j);
                }
            }
            if(cleaned.charAt(i) == '/' || cleaned.charAt(i) == '|'){
                if(cleaned.charAt(i-1) == ' '){
                    cleaned.deleteCharAt(i-1);
                }
                int j = i+1;
                while(j < cleaned.length() && (cleaned.charAt(j) == '/' || cleaned.charAt(j) == '|' || cleaned.charAt(j) == ' ')){
                    System.out.println("deleting: " + cleaned.charAt(j));
                    cleaned.deleteCharAt(j);
                }
            }
        }
        return cleaned.toString();
    }
    

    public static byte[] encode(String text) {
        text = clean(text);
        text = text.toLowerCase();
        char[] chars = text.toCharArray();
        byte[] converted = new byte[chars.length];

        for (int i = 0; i < chars.length; i++) {

            converted[i] = morseMap.get(chars[i]);
        }

        return converted;
    }

    public static String decode(byte[] morse) {
        char[] chars = new char[morse.length];
        for (int i = 0; i < morse.length; i++){
            chars[i] = charMap.get(morse[i]);
        }
        return new String(chars);
    }
    
    public static String formatEncoded(byte[] morse){
        StringBuilder output = new StringBuilder();
        for (byte character : morse){
            if(character == (byte) 0b11111111){
                output.append('/');
                output.append(' ');
            }
            else{
                boolean started = false;
                for(int i = 5; i >= 0; i--){
                    if(started){
                        char duration = (character & (1 << i)) != 0 ? '-': '.';
                        output.append(duration);
                    }
                    started = started || (((character & (1 << i))!=0));
                }
                output.append(' ');
            }
        }
        return output.toString();
    }

    public static byte[] deformatEncoded(String encoded){
        encoded = cleanFormatted(encoded);
        String[] parts = encoded.split("\\s");
        byte[] morse = new byte[parts.length];

        for (int i = 0; i < parts.length; i++) {
            if(parts[i].equals("|") || parts[i].equals("/")){
                morse[i] = (byte) 0b11111111;
            }
            else{
                byte character = 0;
                for(int j = 0; j < parts[i].length(); j++){
                    char currentSignal = parts[i].charAt(j);
                    if(currentSignal == '-'){
                        character |= 1 << (parts[i].length()-j-1);
                    }
                }
                character |= 1 << (parts[i].length());
                morse[i] = character;
            }
        }
        return morse;
    }
}
