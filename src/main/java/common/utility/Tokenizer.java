package common.utility;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Tokenizer {
    private String hashing(String string, String type) throws NoSuchAlgorithmException {
        var bytes= MessageDigest.getInstance(type).digest(string.getBytes());
        StringBuilder SB = new StringBuilder();
        for(int i=0; i<bytes.length;i++){
            SB.append(String.format("%02X",bytes[i]));
        }


        return SB.toString();
    }
    public String SHA384(String input) throws NoSuchAlgorithmException{
        return hashing(input, "SHA-384");
    }
}
