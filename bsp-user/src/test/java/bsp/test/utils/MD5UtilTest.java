package bsp.test.utils;

import org.junit.Test;

import com.bsp.utils.Cryptography;

public class MD5UtilTest {
	
	@Test
	public void get() {
		System.out.println(Cryptography.MD5Hash("12345678", "358739303@qq.com"));
	}
	
}
