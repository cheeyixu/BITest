package javabitest;

import java.math.BigInteger;
import java.time.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class JavaBITest {

	private static final String csvFile = "JavaBigIntegerBenchmark.csv";
	private static final String bench_smallintegers = "bench_smallintegers.csv";
	private static final String bench_mixedintegers = "bench_mixedintegers.csv";
	private static final String bench_bigintegers = "bench_bigintegers.csv";

	private static Instant start, end;
	private static Duration duration;

	private static long toMillis, min = 999999999, max = 0;
	private static double sec, mean, stdev;

	private static Random random = new Random();

	private static volatile BigInteger result = null;

	private static BigInteger cat1 = new BigInteger("2289055645946358156");
	private static BigInteger cat2 = new BigInteger(
			"6033565409114572137454336969572991810648483027731061130188570476685332844974184");
	private static BigInteger cat3 = new BigInteger(
			"6782415135834451130284656972615190022473066784914729414121185719656847441572467291633147838185007376986081093291356905148999810824724649156371868911541253368798504114719547577967971897998763445945860499435727219749524872504399008957332502665772830459117114010396328983359888719386023972966086035158331295016096869490140168767794612834182160866919095091674963045530588732890930341099717584418174768857376276128770646693508385082374399903720439882080639907637722301327533121364582518408499894721156327539743357703813542397");

	public static void main(String[] args) throws Exception {

		FileWriter writer = new FileWriter(csvFile);

		CSVUtils.writeLine(writer,
				Arrays.asList("Operation", "Library", "Mean (s)", "Max (s)", "Min (s)", "STDEV (s)", "Exec 1 (s)",
						"Exec 2 (s)", "Exec 3 (s)", "Exec 4 (s)", "Exec 5 (s)", "Exec 6 (s)", "Exec 7 (s)",
						"Exec 8 (s)", "Exec 9 (s)", "Exec 10 (s)"));

		// TestAddition(writer);
		TestAnd(writer);

		writer.flush();
		writer.close();

	}

	public static void TestAnd(FileWriter writer) throws IOException {

		for (int i = 0; i < 10; i++) {

			start = Instant.now();

			for (int j = 0; j < 100000000; j++) {
				result = cat1.add(cat2);
			}

			end = Instant.now();
			duration = Duration.between(start, end);

			toMillis = duration.toMillis();
			sec = toMillis / 1000.0;
			System.out.println(sec);

		}

	}

	public static void TestAddition(FileWriter writer) throws IOException {

		BigInteger bi1 = new BigInteger(
				"981273987498712983791827391283781278192847381827348234792488723873872372837987023985092384092839482382375981729837120983012983098");
		BigInteger bi2 = new BigInteger(
				"3681723897192837847823794872389472398987928349874988127381729831729928398237892739872398779347598347985779237498789747923487239847987");

		BigInteger bi3 = null;

		ArrayList<String> additionOutput = new ArrayList<>();
		ArrayList<Long> execTime = new ArrayList<>();

		additionOutput.add("+");
		additionOutput.add("Java BigInteger");

		for (int i = 0; i < 10; i++) {

			start = Instant.now();

			for (int j = 0; j < 29384798; j++) {
				bi3 = bi1.add(bi2);
			}
			end = Instant.now();

			System.out.println(start);

			duration = Duration.between(start, end);
			System.out.println(duration);
			toMillis = duration.toMillis();

			System.out.println(toMillis);

			execTime.add(toMillis);

			if (toMillis > max)
				max = toMillis;
			if (toMillis < min)
				min = toMillis;

		}

		mean = listAverageLong(execTime);
		System.out.println(mean);
		stdev = calStdev(execTime, mean);

		additionOutput.add(String.valueOf(mean));
		additionOutput.add(String.valueOf(max));
		additionOutput.add(String.valueOf(min));
		additionOutput.add(String.format("%.2f", stdev));

		for (int i = 0; i < 10; i++) {

			additionOutput.add(execTime.get(i).toString());

		}

		CSVUtils.writeLine(writer, additionOutput);
		resetMinMax();

	}

	private static void resetMinMax() {

		min = 999999999;
		max = 0;

	}

	private static double calStdev(ArrayList<Long> execTime, double mean) {

		ArrayList<Double> squareDistance = new ArrayList<>();
		int size = execTime.size();

		for (int i = 0; i < size; i++) {

			squareDistance.add(Math.pow(execTime.get(i) - mean, 2));

		}

		return Math.sqrt(listAverageDouble(squareDistance));

	}

	private static double listAverageDouble(ArrayList<Double> doubleList) {

		double sum = 0, size = doubleList.size();

		for (int i = 0; i < size; i++) {

			sum += doubleList.get(i);

		}

		return sum / size;

	}

	private static long listAverageLong(ArrayList<Long> longList) {

		long sum = 0, size = longList.size();

		for (int i = 0; i < size; i++) {

			sum += longList.get(i);

		}

		return sum / size;

	}

}