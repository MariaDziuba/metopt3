package src;

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
       // double[] res = gaussMethod.solve();
        double[] res = luMethod.findSolutions();
        for (int i = 0; i < n; i++) {
            pw.print(res[i] + " ");
        }
        pw.close();
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
        try (BufferedReader br = Files.newBufferedReader(Paths.get(args[0]))) {
            try(PrintWriter pw = new PrintWriter(new FileWriter(args[1]))) {
                Evaluator evaluator = new Evaluator();
                evaluator.evaluate(br, pw);
            }
        } catch (IOException e) {
            System.err.println("IO error");
        }
    }

    private static void trySolutionFor1Ex() {
        double[][] matrix = (new Generator2()).generateMatrix(3, 0);
        for (double[] row : matrix) {
            for (double i : row) {
                System.err.print(i + " " + "\t");
            }
            System.err.println();
        }

        ProfileSLAEMatrix prof = new ProfileSLAEMatrix(matrix);
        LUMethod method = new LUMethod(prof, (new Generator2()).multiplyOnVectorX(matrix));

        System.err.println();
        for (double i : method.findSolutions()) {
            System.err.println(i);
        }
    }
}
