package src;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Evaluator {

    ProfileSLAEMatrix profileMatrix;
    double[] b;
    LUMethod luMethod;
    int n;

    private void read(BufferedReader br) throws IOException {
        int n = Integer.parseInt(br.readLine());
        double[][] doubles = new double[n][n];
        for (int i = 0; i < n; i++) {
            String[] input = br.readLine().split(" ");
            for (int j = 0; j < n; j++) {
                if(input[j] != null && input[j].length() > 0){
                    doubles[i][j] = Double.parseDouble(input[j]);
                }
            }
        }
        br.readLine();
        b = new double[n];
        String[] input = br.readLine().split(" ");
        for (int i = 0; i < n; i++) {
            if(input[i] != null && input[i].length() > 0){
                b[i] = Double.parseDouble(input[i]);
            }
        }

        profileMatrix = new ProfileSLAEMatrix(doubles);
        luMethod = new LUMethod(profileMatrix, b);
    }

    private void print(BufferedWriter bw) throws IOException {
        double[] res = luMethod.findSolutions();

        for (int i = 0; i < n; i++) {
            bw.write(res[i] + " ");
        }
    }

    public void evaluate(BufferedReader br, BufferedWriter bw) throws IOException {
        read(br);
        Generator2 generator2 = new Generator2();
//        generator2.generate();
        print(bw);
    }

    public static void main(String[] args) {
        try (BufferedReader br = Files.newBufferedReader(Paths.get(args[0]))) {
            try (BufferedWriter bw = Files.newBufferedWriter(Paths.get(args[1]))) {
                Evaluator evaluator = new Evaluator();
                evaluator.evaluate(br, bw);
            }
        } catch (IOException e) {
            System.err.println("IO error");
        }
    }
}
