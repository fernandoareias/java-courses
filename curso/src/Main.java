import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Scanner;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        int[] valores = {10, 15,  30, 50, 20};

        Arrays.sort(valores);

        System.out.println(Arrays.toString(valores));
        System.out.println(valores.length);

        String[] carros = { "BMW", "Tesla"};

        System.out.println(Arrays.toString(carros));


        int[][] valoresMatrix = {{0, 1, 2}, {3, 4, 5}};

        System.out.println(Arrays.deepToString(valoresMatrix));

        NumberFormat pound = NumberFormat.getCurrencyInstance();
        String productValue = pound.format(120.00);
        System.out.println(productValue);


        System.out.println("Write your product name: ");
        Scanner input = new Scanner(System.in);

        String productName = input.next();

        System.out.println("Your write " + productName);

        switch (productName){
            case "batata":
                System.out.println("switch batatao");
                break;
            default:
                System.out.println("Nao conhece isso parca");
                break;
        }

        for (int i = 1; i <= 10; i++){
            System.out.println(i);
        }

    }
}