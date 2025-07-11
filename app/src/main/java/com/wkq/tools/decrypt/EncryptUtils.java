package com.wkq.tools.decrypt;

/**
 * @Author: wkq
 * @Time: 2025/7/11 15:37
 * @Desc:  加密字符串
 */
public class EncryptUtils {
    /**
     * 使用XOR算法加密字符串
     * @param originalString 原始字符串
     * @param xorKey 加密密钥字节数组
     * @return 加密后的字节数组
     */
    public static byte[] encrypt(String originalString, byte[] xorKey) {
        byte[] encryptedBytes = new byte[originalString.length()];
        for (int i = 0; i < originalString.length(); i++) {
            // 获取字符的Unicode码点并与密钥字节进行XOR
            int charCode = originalString.charAt(i);
            encryptedBytes[i] = (byte) (charCode ^ xorKey[i % xorKey.length]);
        }
        return encryptedBytes;
    }

    /**
     * 使用XOR算法解密字节数组
     * @param encryptedBytes 加密后的字节数组
     * @param xorKey 解密密钥字节数组（需与加密时相同）
     * @return 解密后的原始字符串
     */
    public static String decrypt(byte[] encryptedBytes, byte[] xorKey) {
        StringBuilder decrypted = new StringBuilder();
        for (int i = 0; i < encryptedBytes.length; i++) {
            // 将加密字节与密钥字节进行XOR还原字符
            char decryptedChar = (char) (encryptedBytes[i] ^ xorKey[i % xorKey.length]);
            decrypted.append(decryptedChar);
        }
        return decrypted.toString();
    }

    /**
     * 将字节数组转换为C++格式的静态数组声明
     * @param bytes 字节数组
     * @param variableName 变量名
     * @return C++格式的字符串
     */
    public static String toCPlusPlusFormat(byte[] bytes, String variableName) {
        StringBuilder sb = new StringBuilder();
        sb.append("static const unsigned char ").append(variableName).append("[] = {\n    ");

        for (int i = 0; i < bytes.length; i++) {
            // 转换为无符号整数
            int unsignedByte = bytes[i] & 0xFF;
            sb.append(unsignedByte);

            if (i != bytes.length - 1) {
                sb.append(", ");
            }

            if ((i + 1) % 8 == 0) {
                sb.append("\n    ");
            }
        }

        sb.append("\n};");
        return sb.toString();
    }
}