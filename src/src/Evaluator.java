package src;

import src.generator.*;
import src.matrix.ProfileSLAEMatrix;
import src.matrix.SparseSLAEMatrix;
import src.method.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Evaluator {

    ProfileSLAEMatrix profileMatrix;
    SparseSLAEMatrix sparseSLAEMatrix;
    double[][] denseMatrix;
    double[] b;
    static String contentRoot = "/Users/maria/Desktop/metopt3";
//    String contentRoot = "/home/valrun/IdeaProjects/metopt3"



    LUMethod luMethod;
    GaussMethod gaussMethod;
    ConjugateMethod conjugateMethod;
    int n;

    private static void generateMatrixes() {
        List<AbstractGenerator> generators = List.of(/*new Generator(), new Generator2(), new Generator3(), new Generator52(),*/ new Generator53());
        for (AbstractGenerator generator : generators) {
            generator.generate();
        }
    }

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
        sparseSLAEMatrix = new SparseSLAEMatrix(denseMatrix);

        luMethod = new LUMethod(profileMatrix, b);
        gaussMethod = new GaussMethod(denseMatrix, b, n);
        conjugateMethod = new ConjugateMethod(sparseSLAEMatrix, b);
    }

    private void print(PrintWriter pw) {
        double[] res = gaussMethod.findSolutions();
        for (int i = 0; i < n; i++) {
            pw.print(res[i] + " ");
        }
        pw.close();
    }

    private void printRes(PrintWriter pw, double[] res, String format) {
        if (res != null) {
            double quadSumRes = 0.0;
            double quadSum = 0.0;

            for (int i = 0; i < n; i++) {
                quadSumRes += Math.pow(i + 1.0 - res[i], 2.0);
                quadSum += Math.pow(i + 1.0, 2.0);
            }

            pw.print((String.format(format, Math.sqrt(quadSumRes))) + ", "
                    + (String.format(format, Math.sqrt(quadSumRes) / Math.sqrt(quadSum))));
        }
    }

    private void printSecondThird(PrintWriter pw, String format) {
        printRes(pw, luMethod.findSolutions(), format);
    }

    private void printFourth(PrintWriter pw) {
        double[] res = luMethod.findSolutions();
        printRes(pw, res, "%.16f");
        pw.print(", " + luMethod.getActions() + ", ");
        res = gaussMethod.findSolutions();
        printRes(pw, res, "%.16f");
        pw.print(", " + gaussMethod.getActions());
    }

    private void printFifth1(PrintWriter pw) {
        printRes(pw, conjugateMethod.findSolutions(), "%.1f");
    }

    private void printFifth2(PrintWriter pw, String format) {
        double[] res = conjugateMethod.findSolutions();
        if (res != null) {
            double[] Ax = new Generator().multiplyOnVector(denseMatrix, res);

            double quadSumXRes = 0.0;
            double quadSumX = 0.0;

            double quadSumFRes = 0.0;
            double quadSumF = 0.0;

            for (int i = 0; i < n; i++) {
                quadSumXRes += Math.pow(i + 1.0 - res[i], 2.0);
                quadSumX += Math.pow(i + 1.0, 2.0);

                quadSumFRes += Math.pow(b[i] - Ax[i], 2.0);
                quadSumF += Math.pow(b[i], 2.0);
            }

            pw.print(conjugateMethod.getActions() + ", ");
            pw.print((String.format(format, Math.sqrt(quadSumXRes))) + ", ");
            pw.print((String.format(format, Math.sqrt(quadSumXRes) / Math.sqrt(quadSumX))) + ", ");
            pw.print((String.format(format, (Math.sqrt(quadSumXRes) * Math.sqrt(quadSumF)) / (Math.sqrt(quadSumX) * Math.sqrt(quadSumFRes)))));
        }
    }

    public void evaluate(BufferedReader br, PrintWriter pw) throws IOException {
        read(br);
        print(pw);
    }


    public static void main(String[] args) {
//        generateMatrixes();
//        firstEx();
//        secondEx(true);
//        secondEx(false);
//        fourthEx();
//        fifthEx1();
//        fifthEx234(2);
        fifthEx234(3);
//        fifthEx234(4);
    }

    private static void firstEx() {
        int n = 3;
        List<AbstractGenerator> generators = List.of(new Generator2()/*, new Generator3(), new Generator52(), new Generator53()*/);
        for (AbstractGenerator generator : generators) {
            double[][] matrix = generator.generateMatrix(n);
            System.err.println("A:");
            for (double[] row : matrix) {
                for (double i : row) {
                    System.err.print(i + " " + "\t");
                }
                System.err.println();
            }

            System.err.println("b:");
            double[] b = generator.multiplyOnVectorX(matrix);
            for (double i : b) {
                System.err.print(i + " " + "\t");
            }
            System.err.println();

            Method method = new ConjugateMethod(new SparseSLAEMatrix(matrix), b);

            System.err.println();
            System.err.println();
            double[] res = method.findSolutions();
            for (double i : res) {
                System.err.println(i);
            }
        }

    }

    private static void secondEx(boolean isSecond) {
        String path = isSecond ? contentRoot + "/src/result/second.txt" : contentRoot + "/src/result/third.txt";
        try (PrintWriter pw = new PrintWriter(new FileWriter(path))) {
            for (int n = 15; n < 1000; n += 50) {
                if (!isSecond) {
                    try (BufferedReader br = Files.newBufferedReader(Paths.get(contentRoot + "/src/matrices/3/n" + n + ".txt"))) {
                        pw.print(n + ", ");
                        Evaluator evaluator = new Evaluator();
                        evaluator.read(br);
                        evaluator.printSecondThird(pw, "%.16f");
                        pw.println(";");
                    }
                } else {
                    for (int k = 0; k < 7; k++) {
                        try (BufferedReader br = Files.newBufferedReader(Paths.get(contentRoot + "/src/matrices/2/k" + k + "/n" + n + ".txt"))) {
                            pw.print(n + ", " + k + ", ");
                            Evaluator evaluator = new Evaluator();
                            evaluator.read(br);
                            evaluator.printSecondThird(pw, "%.4f");
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
        try (PrintWriter pw = new PrintWriter(new FileWriter(contentRoot + "/src/result/fourth.txt"))) {
            for (int n = 15; n < 1000; n += 100) {
                for (int k = 0; k < 7; k += 3) {
                    try (BufferedReader br = Files.newBufferedReader(Paths.get(contentRoot + "/src/matrices/2/k" + k + "/n" + n + ".txt"))) {
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

    private static void fifthEx1() {
//        todo(Is it right NaN in result)
        try (PrintWriter pw = new PrintWriter(new FileWriter(contentRoot + "/src/result/fifth1.txt"))) {
            for (int n = 5; n < 55; n += 5) {
                    try (BufferedReader br = Files.newBufferedReader(Paths.get(contentRoot + "/src/matrices/0/n" + n + ".txt"))) {
                        pw.print(n + ", ");
                        Evaluator evaluator = new Evaluator();
                        evaluator.read(br);
                        evaluator.printFifth1(pw);
                        pw.println(";");
                    }
            }
        } catch (IOException e) {
            System.err.println("IO error:" + e);
        }
    }

    private static void fifthEx234(int ex) {
        int folder;
        switch (ex) {
            case 2:
                folder = 52;
                break;
            case 3:
                folder = 53;
                break;
            case 4:
                folder = 3;
                break;
            default:
                return;
        }
        try (PrintWriter pw = new PrintWriter(new FileWriter(contentRoot + "/src/result/fifth" + ex + ".txt"))) {
            int[] sizes = {15, 50, 200, 500, 1000, 2000, 5000, /*(int) 1e4/*, (int) (2.5 * 1e4), 5 * (int) 1e4, (int) (7.5 * 1e4), (int) 1e5*/};
            for (int n : sizes) {
                    try (BufferedReader br = Files.newBufferedReader(Paths.get(contentRoot + "/src/matrices/" + folder + "/n" + n + ".txt"))) {
                        pw.print(n + ", ");
                        Evaluator evaluator = new Evaluator();
                        evaluator.read(br);
                        evaluator.printFifth2(pw, "%.6f");
                        pw.println(";");
                    }
            }
        } catch (IOException e) {
            System.err.println("IO error:" + e);
        }
    }
}
