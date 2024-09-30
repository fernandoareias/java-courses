package org.example;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class App {
    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        int dia = 24;
        int mes = 3; // O zero à esquerda foi removido, pois inteiros não suportam isso.
        int ano = 2001;

        System.out.println(dia);
        System.out.println(mes);
        System.out.println(ano);

        // Casting

        // Widening Casting (Implicito)
        byte varByte = 1;
        int varInt = varByte;

        // Narrowing casting (Explicito)
        long varLong = 922337203685775807L;
        int varInt2 = (int) varLong;

        System.out.println(varInt2);

        int numero1 = 9;
        int numero2 = 2;

        float resultadoDaDivisao = (float) numero1 / numero2;

        System.out.println(resultadoDaDivisao);

        System.out.print("Informe um valor: ");
        Scanner scanner = new Scanner(System.in);
        int valorDigitado = scanner.nextInt();
        scanner.close();
        System.out.println(valorDigitado);

        String texto = "Lorem Ipsum"; // Corrigido erro de digitação em "Ipsum"
        System.out.println(texto);

        System.out.println(valorDigitado % 2 == 0 ? "Par" : "Ímpar"); // Corrigido erro de digitação em "Impa"

        char nota;
        if (valorDigitado >= 0 && valorDigitado <= 5)
            nota = 'R';
        else if (valorDigitado > 5 && valorDigitado <= 10) // Ajustado para maior que 5.
            nota = 'A';
        else
            nota = 'E';

        switch (nota) {
            case 'R':
                System.out.println("REPROVADO");
                break;

            case 'A':
                System.out.println("APROVADO");
                break;

            case 'E':
                System.out.println("ERRO");
                break;

            default:
                System.out.println("ops");
                break;
        }

        int x = 0;

        while (x < 5) {
            System.out.println("Executando while");
            x++;
        }

        x = 0;

        do {
            System.out.println("Executando do-while");
            x++;
        } while (x < 5);

        for (int y = 0; y < 5; y++) {
            System.out.println("Executando for");
        }

        int[] lista = new int[]{1, 2, 3, 4, 5};
        for (int l : lista) {
            System.out.println("Percorrendo lista com 'foreach'");
        }

        String[] tarefas = new String[]{"Passear", "Comer", "Estudar"};
        for (int z = 0; z < tarefas.length; z++) {
            System.out.println(String.format("Tarefa pendente: %s", tarefas[z]));
        }

        // Usando 2 for
        int[][] matriz = new int[][]{{1, 2, 3, 4}, {1, 2, 3, 0}};

        for (int i = 0; i < matriz.length; i++) { // Percorre as linhas
            for (int j = 0; j < matriz[i].length; j++) { // Percorre as colunas da linha atual
                System.out.println(String.format("Percorrendo matriz, valor: %d", matriz[i][j]));
            }
        }

        // Usando for each
        for (int[] linha : matriz) {
            for (int valor : linha) {
                System.out.println(String.format("Percorrendo matriz, valor: %d", valor));
            }
        }

        List<String> nomes = new ArrayList<>();
        nomes.add("Bob");
        nomes.add("Horus");

        System.out.println(nomes);

        nomes.remove("Bob");
        System.out.println(nomes);

        List<Integer> listNumeros = new ArrayList<>();
        listNumeros.add(2);
        listNumeros.add(15);
        listNumeros.add(3);
        listNumeros.add(0);
        listNumeros.add(223232);

        System.out.println(listNumeros);
        Collections.sort(listNumeros);
        System.out.println(listNumeros);

        MyClass obj = new MyClass(); // Nome da classe corrigido para começar com maiúscula.
        new Processor().process(obj);
    }

    static class MyClass { // Corrigido para letra maiúscula e estático para ser acessível no main.

        @PrintHello
        public void myMethod() {
            System.out.println("My method");
        }
    }

    static class Processor { // Corrigido para classe estática e método getDeclaredMethods().

        public void process(Object obj) throws InvocationTargetException, IllegalAccessException {
            Method[] methods = obj.getClass().getDeclaredMethods(); // Corrigido para getDeclaredMethods().
            for (Method m : methods) {

                if (m.isAnnotationPresent(PrintHello.class)) {
                    System.out.println("Hello");
                    m.invoke(obj); // O parâmetro null não é necessário ao invocar métodos sem parâmetros.
                }
            }
        }
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    @interface PrintHello {

    }
}
