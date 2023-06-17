package FitxerDeEnCrypter;

//scan for optimization 


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Scanner;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class FitxerEnDeCrypter {
    private static final String ALGORITHM = "AES/CBC/PKCS5Padding";

    private static final byte[] ivParameter = {0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0A, 0x0B,
            0x0C, 0x0D, 0x0E, 0x0F};

    public static void main(String[] args) {
        boolean conitnue = true;
        while (conitnue) {

            Scanner scanner = new Scanner(System.in);
            System.out.println("Write file path: ");
            String path = scanner.nextLine();
            System.out.println("Wirite password: ");
            String password = scanner.nextLine();
            fileManager(path, password);

            System.out.println("Do you want to continue? (Y/N)");
            String answer = scanner.nextLine();
            if (answer.equals("N") || answer.equals("n")) {
                conitnue = false;
            }

        }
    }

    public static void encryptOrDecrypt(File inputFile, File outputFile, SecretKey key, byte[] ivParameter,
                                        int cipherMode) throws Exception {
        IvParameterSpec ivSpec = new IvParameterSpec(ivParameter);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(cipherMode, key, ivSpec);
        FileInputStream inputStream = new FileInputStream(inputFile);

        FileOutputStream outputStream = new FileOutputStream(outputFile);

        byte[] buffer = new byte[32 * 1024];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            byte[] output = cipher.update(buffer, 0, bytesRead);
            if (output != null) {
                outputStream.write(output);
            }
        }

        byte[] output = cipher.doFinal();
        if (output != null) {
            outputStream.write(output);
        }

        Arrays.fill(buffer, (byte) 0);
        FileOutputStream inputFileStream = new FileOutputStream(inputFile);
        inputFileStream.write(buffer);
        inputFileStream.close();
        inputStream.close();
        outputStream.close();
        inputFile.delete();
    }

    public static SecretKey passwordKeyGeneration192(String password) {

        SecretKey sKey = null;
        try {
            byte[] data = password.getBytes(StandardCharsets.UTF_8);

            MessageDigest md = MessageDigest.getInstance("SHA-256");

            byte[] hash = md.digest(data);

            byte[] key = Arrays.copyOf(hash, 192 / 8);
            sKey = new SecretKeySpec(key, "AES");
        } catch (Exception ex) {
            System.err.println("Could not generate the AES key:" + ex);
        }

        return sKey;
    }

    public static void fileManager(String path, String password) {
        SecretKey skey = passwordKeyGeneration192(password);
        if (path.endsWith(".aes")) {
            String substring = path.substring(0, path.length() - 4);
            try {
                encryptOrDecrypt(new File(path), new File(substring), skey, ivParameter,
                        Cipher.DECRYPT_MODE);
            } catch (Exception e) {
                System.out.println("Error,the file could not be found in the indicated path.");

                return;
            }

            System.out.println("File decrypted at:  " + substring);
        } else {
            try {
                encryptOrDecrypt(new File(path), new File(path + ".aes"), skey, ivParameter, Cipher.ENCRYPT_MODE);

            } catch (Exception e) {
                System.out.println("Error,the file could not be found in the indicated path.");
                return;
            }
            {
                System.out.println("File encrypted at: " + path + ".aes");
            }

        }

    }

}
