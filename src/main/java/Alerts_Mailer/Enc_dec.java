package Alerts_Mailer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class Enc_dec {
	private static String mysecKey = "2546Magic53434secret24354";

	public static final String alertLaKey = "2546Magic53434secret24354prop875638476";

	protected static String KEYGEN_STR = "23435356677";

	public static String encrypt(String sourceStr) {

		try {

			// Get secret key

			Key key = getKey();

			byte[] enc;

//			synchronized (Cipher.class)

//			{

			final Cipher ecipher = Cipher.getInstance("DES/ECB/PKCS5Padding");

			ecipher.init(Cipher.ENCRYPT_MODE, key);

			enc = ecipher.doFinal((new String(sourceStr)).getBytes("UTF-8"));

//			}

			// Encode bytes to base64 to get a string

			Pattern REMOVE_WHITE_SPACES = Pattern.compile("\\s+", Pattern.MULTILINE);

			String encryptedString = java.util.Base64.getEncoder().encodeToString(enc);

			return REMOVE_WHITE_SPACES.matcher(encryptedString).replaceAll("");

		} catch (Exception ex) {

			System.out.println("[Exception [EncDec : while encrypt the string]] : {}");

		}

		return null;

	}

	public static String decrypt(String sourceStr) {

		try {

			// Get secret key

			sourceStr = sourceStr.replace(' ', '+');

			sourceStr = sourceStr.replace("%20", "+");

			sourceStr = sourceStr.replaceAll("\n", "");

			Key key = getKey();

			byte[] dec;

//			synchronized (Cipher.class) {

			final Cipher dcipher = Cipher.getInstance("DES/ECB/PKCS5Padding");

			dcipher.init(Cipher.DECRYPT_MODE, key);

			// Decode base64 to get bytes

			dec = java.util.Base64.getDecoder().decode(sourceStr);

//			}

			// Decrypt data in a single step

			byte[] utf8 = dcipher.doFinal(dec);

			// Decode using utf-8

			return new String(utf8, "UTF-8");

		} catch (Exception ex) {

			// ex.printStackTrace();

			System.out.println("Can not decrypt the string :: {} : Exception thrown :" + sourceStr);

		}

		return null;

	}

	public static SecretKeySpec generateMySQLAESKey(final String key, final String encoding) {

		try {

			final byte[] finalKey = new byte[16];

			int i = 0;

			for (byte b : key.getBytes(encoding))

				finalKey[i++ % 16] ^= b;

			return new SecretKeySpec(finalKey, "AES");

		} catch (UnsupportedEncodingException e) {

			throw new RuntimeException(e);

		}

	}

	public static String formatDateTimeNew(Date date, String format) {

		SimpleDateFormat simpleFormat = new SimpleDateFormat(format);

		return simpleFormat.format(date);

	}

	public static Date getDateWithoutTime(Date date) {

		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

		String convertedDate = null;

		Date dt = null;

		try {

			convertedDate = dateFormat.format(date);

			dt = dateFormat.parse(convertedDate);

		} catch (ParseException e) {

		}

		Calendar c = Calendar.getInstance();

		c.setTime(dt);

		return c.getTime();

	}


	public static Date convertStringToDate(String dateStr, String format) throws ParseException {

		return new SimpleDateFormat(format).parse(dateStr);

	}

	public static long getDaysDiffFromCurrentDate(Date fromDate) {

		long diff = -1;

		try {

			Date currentDate = new Date();

			diff = currentDate.getTime() - fromDate.getTime();

			diff = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);

		} catch (Exception e) {

			e.printStackTrace();

		}

		return diff;

	}

	private static Key getKey() {

		try {

			byte[] bytes = getbytes(KEYGEN_STR);

			DESKeySpec pass = new DESKeySpec(bytes);

			SecretKeyFactory sKeyFactory = SecretKeyFactory.getInstance("DES");

			SecretKey sKey = sKeyFactory.generateSecret(pass);

			return sKey;

		} catch (Exception ex) {

			ex.printStackTrace();

		}

		return null;

	}

	private static byte[] getbytes(String str) {

		ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();

		StringTokenizer sTokenizer = new StringTokenizer(str, "-", false);

		while (sTokenizer.hasMoreTokens()) {

			try {

				byteOutputStream.write(sTokenizer.nextToken().getBytes());

			} catch (IOException ex) {

				System.out.println("[Exception [EncDec : while getbytes : {}");

			}

		}

		byteOutputStream.toByteArray();

		return byteOutputStream.toByteArray();

	}

}
//	public static void main(String[] args) {

//		System.out.println(getContactRequiredToken("7303358511", "119abhi@gmail.com"));

//	}