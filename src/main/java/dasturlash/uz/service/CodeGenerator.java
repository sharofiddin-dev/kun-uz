package dasturlash.uz.service;

import java.security.SecureRandom;

public class CodeGenerator {
    private static final SecureRandom random = new SecureRandom();

    public static String generateNumericCode() {
        int code = random.nextInt(900000) + 100000; // 100000 - 999999
        return String.valueOf(code);
    }
}