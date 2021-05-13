package src;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;

public class Evaluator {

    ProfileSLAEMatrix profileMatrix;
    double[] b;

    private void read(BufferedReader br) {

    }

    private void print() {
        double[] d = {1.0, 1.0};
        double[] al = {1.0, 1.0};
        double[] au = {1.0, 1.0};
        int[] ial = {0, 1};
        int[] iau = {0, 1};
        profileMatrix = new ProfileSLAEMatrix(d, al, ial, au, iau);

        b = new double[]{15.0, 4.0};

        System.out.println(Arrays.toString(new LUMethod(profileMatrix, b).findSolutions()));
    }

    public void evaluate(BufferedReader br) throws IOException {
        read(br);
        Generator2 generator2 = new Generator2();
        generator2.generate();
        print();
    }

    public static void main(String[] args) {
        try (BufferedReader br = Files.newBufferedReader(Path.of(args[0]))) {
            Evaluator evaluator = new Evaluator();
            evaluator.evaluate(br);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
