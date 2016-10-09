package javabitest;

import java.math.BigInteger;
import java.time.*;

public class JavaBITest {

	public static void main(String[] args) {

		Instant start = Instant.now();

		BigInteger bi1 = new BigInteger(
				"98127398749871298379182739128378127819284738192487023985092384092839482382375981729837120983012983098");
		BigInteger bi2 = new BigInteger("368172389719283784782379487238947239847923487239847987");

		BigInteger bi3 = bi1.add(bi2);

		for (long i = 0; i < (int)Math.pow(2, 64) - 1; i++) {

			bi3.add(bi3);

		}

		Instant end = Instant.now();

		Duration duration = Duration.between(start, end);

		System.out.println(bi3);
		System.out.println(duration.toMillis());

	}

}