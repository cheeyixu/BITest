package javabitest;

import java.math.BigInteger;
import java.time.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class JavaBITest {

	public static final String csvFile = "JavaBigIntegerBenchmark.csv";

	public static void main(String[] args) throws Exception {

		FileWriter writer = new FileWriter(csvFile);

		CSVUtils.writeLine(writer,
				Arrays.asList("Operation", "Library", "Mean (s)", "Max (s)", "Min (s)", "STDEV (s)", "Exec 1 (s)",
						"Exec 2 (s)", "Exec 3 (s)", "Exec 4 (s)", "Exec 5 (s)", "Exec 6 (s)", "Exec 7 (s)",
						"Exec 8 (s)", "Exec 9 (s)", "Exec 10 (s)"));

		TestAddition(writer);

		writer.flush();
		writer.close();

	}

	public static void TestAddition(FileWriter writer) throws IOException {

		Instant start, end;
		Duration duration;

		long toSec, min = 999999999, max = 0;
		double mean, stdev;

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
			toSec = duration.toMillis();

			System.out.println(toSec);

			execTime.add(toSec);

			if (toSec > max)
				max = toSec;
			if (toSec < min)
				min = toSec;

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

	}

	public static double calStdev(ArrayList<Long> execTime, double mean) {

		ArrayList<Double> squareDistance = new ArrayList<>();
		int size = execTime.size();

		for (int i = 0; i < size; i++) {

			squareDistance.add(Math.pow(execTime.get(i) - mean, 2));

		}

		return Math.sqrt(listAverageDouble(squareDistance));

	}

	public static double listAverageDouble(ArrayList<Double> doubleList) {

		double sum = 0, size = doubleList.size();

		for (int i = 0; i < size; i++) {

			sum += doubleList.get(i);

		}

		return sum / size;

	}

	public static long listAverageLong(ArrayList<Long> LongList) {

		long sum = 0, size = LongList.size();

		for (int i = 0; i < size; i++) {

			sum += LongList.get(i);

		}

		return sum / size;

	}

}