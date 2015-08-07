package ����;

import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import Decoder.BASE64Encoder;
public class DesUtil {
	 
    private final static String DES = "DES";

     
    /**
     * Description ���ݼ�ֵ���м���
     * @param data 
     * @param key  ���ܼ�byte����
     * @return
     * @throws Exception
     */
    public static  String encrypt(String data, String key) throws Exception {
        byte[] bt = encrypt(data.getBytes(), key.getBytes());
        String strs = new BASE64Encoder().encode(bt);
        return strs;
    }
    /**
     * Description ���ݼ�ֵ���м���
     * @param data
     * @param key  ���ܼ�byte����
     * @return
     * @throws Exception
     */
    private static byte[] encrypt(byte[] data1, byte[] key) throws Exception {
        // ����һ�������ε������Դ
        SecureRandom sr = new SecureRandom();
        // ��ԭʼ��Կ���ݴ���DESKeySpec����
        DESKeySpec dks = new DESKeySpec(key);
        // ����һ����Կ������Ȼ��������DESKeySpecת����SecretKey����
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        SecretKey securekey = keyFactory.generateSecret(dks);
        // Cipher����ʵ����ɼ��ܲ���
        Cipher cipher = Cipher.getInstance(DES);
        // ����Կ��ʼ��Cipher����
        cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);
 
        return cipher.doFinal(data1);
    }
}
