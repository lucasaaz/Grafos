import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Stack;

public class AlgoritmoFloydWarshall {
    static final int INFINITY = Integer.MAX_VALUE;

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        StringBuilder resultado = new StringBuilder();

        try {
            for (int y = 1; y <= 5; y++) {  // Passar pelos 5 arquivos
                Scanner scanner = new Scanner(new File("grafo_" + y + ".txt"));
                int numVertices = scanner.nextInt();
                int[][] matrizAdj = new int[numVertices][numVertices];

                // Inicializar matriz de adjacência com INFINITY
                for (int i = 0; i < numVertices; i++) {
                    Arrays.fill(matrizAdj[i], INFINITY);
                }

                // Preencher matriz de adjacência com os pesos das arestas
                while (scanner.hasNext()) {
                    int source = scanner.nextInt();
                    int destination = scanner.nextInt();
                    int weight = scanner.nextInt();
                    matrizAdj[source][destination] = weight;
                    matrizAdj[destination][source] = weight;  // Adiciona a aresta inversa para grafos não direcionados
                }

                scanner.close();

                floydWarshall(matrizAdj, numVertices, resultado, y);
            }
        } catch (FileNotFoundException e) {
            System.err.println("Erro ao abrir o arquivo.");
            e.printStackTrace();
        }

        // Escrever o resultado no arquivo "FloydWarshall.txt"
        criarArquivoTexto("FloydWarshall.txt", resultado.toString());

        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;

        resultado.append("\nTempo total de execução: " + totalTime + " ms | " + (totalTime / 1000) + " s | " + ((totalTime / 1000) / 60) + " min\n");
        System.out.println("\nTempo total de execução: " + totalTime + " ms | " + (totalTime / 1000) + " s | " + ((totalTime / 1000) / 60) + " min\n");
    }

    public static void criarArquivoTexto(String nomeArquivo, String conteudo) {
        // Criar um objeto File para representar o arquivo
        File arquivo = new File(nomeArquivo);

        try {
            // Criar objetos FileWriter e BufferedWriter para escrever no arquivo
            FileWriter escritor = new FileWriter(arquivo);
            BufferedWriter bufferEscrita = new BufferedWriter(escritor);

            // Escrever o conteúdo no arquivo
            bufferEscrita.write(conteudo);

            // Fechar os recursos (é importante fazer isso para garantir que os dados sejam gravados no arquivo)
            bufferEscrita.close();
            escritor.close();

            System.out.println("Arquivo criado com sucesso: " + arquivo.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Erro ao criar o arquivo: " + e.getMessage());
        }
    }

    public static void floydWarshall(int[][] matrizAdj, int numVertices, StringBuilder resultado, int grafoNum) {
        int[][] distancias = new int[numVertices][numVertices];
        int[][] caminhos = new int[numVertices][numVertices];

        // Inicialização das matrizes de distâncias e caminhos
        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numVertices; j++) {
                distancias[i][j] = matrizAdj[i][j];
                if (i != j && matrizAdj[i][j] != INFINITY) {
                    caminhos[i][j] = i;
                } else {
                    caminhos[i][j] = -1;
                }
            }
        }

        // Algoritmo de Floyd-Warshall
        for (int k = 0; k < numVertices; k++) {
            for (int i = 0; i < numVertices; i++) {
                for (int j = 0; j < numVertices; j++) {
                    if (distancias[i][k] != INFINITY && distancias[k][j] != INFINITY &&
                            distancias[i][k] + distancias[k][j] < distancias[i][j]) {
                        distancias[i][j] = distancias[i][k] + distancias[k][j];
                        caminhos[i][j] = caminhos[k][j];
                    }
                }
            }
        }

        // Construir o resultado
        resultado.append("\nGrafo ").append(grafoNum);
        resultado.append("\nDistâncias mínimas e caminhos mínimos a partir do vértice 0:\n");
        for (int i = 0; i < numVertices; i++) {
            resultado.append("Para o vértice ").append(i).append(": Distância - ");
            if (i == 0) {
                resultado.append("0");  // Para o vértice de origem, a distância é 0
            } else {
                resultado.append(distancias[0][i]);
            }
            resultado.append(", Caminho - ");
            construirCaminho(caminhos, 0, i, resultado);
            resultado.append("\n");
        }
    }

    public static void construirCaminho(int[][] caminhos, int origem, int destino, StringBuilder resultado) {
        if (destino == -1) {
            resultado.append("Caminho indisponível.");
            return;
        }

        Stack<Integer> path = new Stack<>();
        while (destino != origem) {
            path.push(destino);
            destino = caminhos[origem][destino];
        }

        resultado.append(origem);
        while (!path.isEmpty()) {
            resultado.append(" -> ").append(path.pop());
        }
    }
}
