package src;

import src.generator.*;
import src.method.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Evaluator {

    ProfileSLAEMatrix profileMatrix;
    double[][] denseMatrix;
    double[] b;
    LUMethod luMethod;
    GaussMethod gaussMethod;
    int n;

    private void read(BufferedReader br) throws IOException {
        n = Integer.parseInt(br.readLine());
        denseMatrix = new double[n][n];
        for (int i = 0; i < n; i++) {
            String[] input = br.readLine().split(" ");
            for (int j = 0; j < n; j++) {
                if (input[j] != null && input[j].length() > 0) {
                    denseMatrix[i][j] = Double.parseDouble(input[j]);
                }
            }
        }
        br.readLine();
        b = new double[n];
        String[] input = br.readLine().split(" ");
        for (int i = 0; i < n; i++) {
            if (input[i] != null && input[i].length() > 0) {
                b[i] = Double.parseDouble(input[i]);
            }
        }

        profileMatrix = new ProfileSLAEMatrix(denseMatrix);
        luMethod = new LUMethod(profileMatrix, b);
        gaussMethod = new GaussMethod(denseMatrix, b, n);
    }

    private void print(PrintWriter pw) {
        double[] res = gaussMethod.findSolutions();
        for (int i = 0; i < n; i++) {
            pw.print(res[i] + " ");
        }
        pw.close();
    }

    private void printSecond(PrintWriter pw) {
        double[] res = gaussMethod.findSolutions();
        double quadSumRes = 0.0;
        double quadSum = 0.0;

        for (int i = 0; i < n; i++) {
            quadSumRes += Math.pow(i + 1.0 - res[i], 2.0);
            quadSum += Math.pow(i + 1.0, 2.0);
        }

        pw.println((String.format("%.14f", Math.sqrt(quadSumRes))) + ", "
                + (String.format("%.16f", Math.sqrt(quadSumRes) / Math.sqrt(quadSum))) + ";");
    }

    public void evaluate(BufferedReader br, PrintWriter pw) throws IOException {
        read(br);
//        Generator2 generator2 = new Generator2();
//        Generator3 generator3 = new Generator3();
//        generator3.generate();
//        generator2.generate();
        printSecond(pw);
    }


    public static void main(String[] args) {
        thirdEx();
    }

    private static void firstEx() {
        double[][] matrix = (new Generator2()).generateMatrix(3, 0);
        System.err.println("A:");
        for (double[] row : matrix) {
            for (double i : row) {
                System.err.print(i + " " + "\t");
            }
            System.err.println();
        }

        System.err.println("b:");
        double[] b = (new Generator2()).multiplyOnVectorX(matrix);
        for (double i : b) {
            System.err.print(i + " " + "\t");
        }
        System.err.println();

        ProfileSLAEMatrix prof = new ProfileSLAEMatrix(matrix);
        LUMethod method = new LUMethod(prof, b);

        System.err.println();
        System.err.println();
        double[] res = method.findSolutions();
        for (double i : res) {
            System.err.println(i);
        }
    }

    private static void secondEx() {
        try (PrintWriter pw = new PrintWriter(new FileWriter("/home/valrun/IdeaProjects/metopt3/out/production/metopt3/second.txt"))) {
            for (int n = 15; n < 1000; n += 50) {
                for (int k = 0; k < 7; k++) {
                    try (BufferedReader br = Files.newBufferedReader(Paths.get("/home/valrun/IdeaProjects/metopt3/src/matrices/2/k" + k + "/n" + n + ".txt"))) {
                        pw.print(n + ", " + k + ", ");
                        (new Evaluator()).evaluate(br, pw);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("IO error");
        }
    }

    private static void thirdEx() {
        try (PrintWriter pw = new PrintWriter(new FileWriter("/home/valrun/IdeaProjects/metopt3/out/production/metopt3/third.txt"))) {
            for (int n = 15; n < 1000; n += 50) {
                for (int k = 0; k < 7; k++) {
                    try (BufferedReader br = Files.newBufferedReader(Paths.get("/home/valrun/IdeaProjects/metopt3/src/matrices/2/k" + k + "/n" + n + ".txt"))) {
                        pw.print(n + "," + k + ",");
                        (new Evaluator()).evaluate(br, pw);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("IO error");
        }
    }
}
