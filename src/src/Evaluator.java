package src;

import src.generator.*;
import src.matrix.ProfileSLAEMatrix;
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

    private void printRes(PrintWriter pw, double[] res) {
        if (res != null) {
            double quadSumRes = 0.0;
            double quadSum = 0.0;

            for (int i = 0; i < n; i++) {
                quadSumRes += Math.pow(i + 1.0 - res[i], 2.0);
                quadSum += Math.pow(i + 1.0, 2.0);
            }

            pw.print((String.format("%.14f", Math.sqrt(quadSumRes))) + ", "
                    + (String.format("%.16f", Math.sqrt(quadSumRes) / Math.sqrt(quadSum))));
        }
    }

    private void printSecondThird(PrintWriter pw) {
        printRes(pw, luMethod.findSolutions());
    }

    private void printFourth(PrintWriter pw) {
        double[] res = luMethod.findSolutions();
        printRes(pw, res);
        pw.print(", " + luMethod.getActions() + ", ");
        res = gaussMethod.findSolutions();
        printRes(pw, res);
        pw.print(", " + gaussMethod.getActions());
    }

    public void evaluate(BufferedReader br, PrintWriter pw) throws IOException {
        read(br);
//        Generator2 generator2 = new Generator2();
//        Generator3 generator3 = new Generator3();
//        generator3.generate();
//        generator2.generate();
        print(pw);
    }


    public static void main(String[] args) {
        firstEx();
//        secondEx(true);
//        secondEx(false);
//        fourthEx();
    }

    private static void firstEx() {
        int n = 3;
        double[][] matrix = (new Generator3()).generateMatrix(n);
        System.err.println("A:");
        for (double[] row : matrix) {
            for (double i : row) {
                System.err.print(i + " " + "\t");
            }
            System.err.println();
        }

        System.err.println("b:");
        double[] b = (new Generator3()).multiplyOnVectorX(matrix);
        for (double i : b) {
            System.err.print(i + " " + "\t");
        }
        System.err.println();

        Method method = new LUMethod(new ProfileSLAEMatrix(matrix), b);

        System.err.println();
        System.err.println();
        double[] res = method.findSolutions();
        for (double i : res) {
            System.err.println(i);
        }
    }

    private static void secondEx(boolean isSecond) {
        String path = isSecond ? "/home/valrun/IdeaProjects/metopt3/out/production/metopt3/second.txt" : "/home/valrun/IdeaProjects/metopt3/out/production/metopt3/third.txt";
        try (PrintWriter pw = new PrintWriter(new FileWriter(path))) {
            for (int n = 15; n < 1000; n += 50) {
                if (!isSecond) {
                    try (BufferedReader br = Files.newBufferedReader(Paths.get("/home/valrun/IdeaProjects/metopt3/src/matrices/3/n" + n + ".txt"))) {
                        pw.print(n + ", ");
                        Evaluator evaluator = new Evaluator();
                        evaluator.read(br);
                        evaluator.printSecondThird(pw);
                        pw.println(";");
                    }
                } else {
                    for (int k = 0; k < 7; k++) {
                        try (BufferedReader br = Files.newBufferedReader(Paths.get("/home/valrun/IdeaProjects/metopt3/src/matrices/2/k" + k + "/n" + n + ".txt"))) {
                            pw.print(n + ", " + k + ", ");
                            Evaluator evaluator = new Evaluator();
                            evaluator.read(br);
                            evaluator.printSecondThird(pw);
                            pw.println(";");
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("IO error:" + e);
        }
    }

    private static void fourthEx() {
        try (PrintWriter pw = new PrintWriter(new FileWriter("/home/valrun/IdeaProjects/metopt3/out/production/metopt3/fourth.txt"))) {
            for (int n = 15; n < 1000; n += 100) {
                for (int k = 0; k < 7; k += 3) {
                    try (BufferedReader br = Files.newBufferedReader(Paths.get("/home/valrun/IdeaProjects/metopt3/src/matrices/2/k" + k + "/n" + n + ".txt"))) {
                        pw.print(n + ", " + k + ", ");
                        Evaluator evaluator = new Evaluator();
                        evaluator.read(br);
                        evaluator.printFourth(pw);
                        pw.println(";");
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("IO error:" + e);
        }
    }
}
