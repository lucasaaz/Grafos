import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

// Classe principal que contém a implementação do algoritmo de FFloyd Warshall
public class AlgoritmoFloydWarshall {
    static final int INFINITY = Integer.MAX_VALUE;

    // Implementação do algoritmo de Floyd-Warshall
    public static void floydWarshall(int[][] matrizAdj, int numVertices, StringBuilder resultado, int grafoNum, int iteracao, int numLinhas) {
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
        resultado.append("\nGrafo ").append(grafoNum).append(" - Iteração ").append(iteracao).append(" - Arestas ").append(numLinhas);
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

    // Método principal
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        StringBuilder resultado = new StringBuilder();
        List<String> ResulTime = new ArrayList<>();
        List<String> ResultAresta = new ArrayList<>();

        try {
            for (int y = 1; y <= 5; y++) { // Itera sobre os 5 grafos

                // Inicio - Tempo de execucao do grafo atual
                long startTimeFile = System.currentTimeMillis();

                int numVertices = (int) Math.pow(5, y);

                // Array para armazenar informações sobre as quantidades de arestas em cada iteração
                int[] qa = new int[5];
                qa[0] = numVertices - 1;
                qa[4] = (numVertices * (numVertices - 1)) / 2;
                int intervalo = numVertices - 1;

                // Calcula intervalos de arestas com base no número de vértices
                for (int i = 1; i <= 3; i++) {
                    intervalo = (int) (intervalo * 1.5);
                    qa[i] = intervalo * y;
                    intervalo--;
                }

                for (int repeat = 0; repeat < 5; repeat++) { // Repetir a leitura do arquivo 5 vezes

                    // Resetar o grafo para cada iteração
                    int numLinhas = qa[repeat];  // Número de linhas a serem lidas
                    int[][] matrizAdj = lerGrafo("grafo_" + y + ".txt", numLinhas);

                    // Inicio - Tempo de execucao do Floyd no grafo atual
                    long startTimeAlgoritmo = System.currentTimeMillis();
                    // Executa o algoritmo de Floyd-Warshall
                    floydWarshall(matrizAdj, matrizAdj.length, resultado, y, repeat + 1, numLinhas);
                    // Fim - Tempo de execucao do Floyd no grafo atual
                    long endTimeAlgoritmo = System.currentTimeMillis();
                    // Calcula o tempo gasto no processamento do grafo atual
                    long totalTimeAlgoritmo = endTimeAlgoritmo - startTimeAlgoritmo;
                    System.out.println("Tempo gasto pelo algoritmo Floyd-Warshall no grafo_" + y + " - Arestas " + numLinhas + ": " + totalTimeAlgoritmo + " ms | " + (totalTimeAlgoritmo / 1000) + " s | " + ((totalTimeAlgoritmo / 1000) / 60) + " min");
                    resultado.append("\nTempo gasto pelo algoritmo Floyd-Warshall no grafo_" + y + " - Arestas " + numLinhas + ": " + totalTimeAlgoritmo + " ms | " + (totalTimeAlgoritmo / 1000) + " s | " + ((totalTimeAlgoritmo / 1000) / 60) + " min");
                    ResulTime.add(totalTimeAlgoritmo+"");
                    ResultAresta.add(""+numLinhas);
                }

                // Fim - Tempo de execução do grafo atual
                // Calcula o tempo gasto no processamento do grafo atual
                long endTimeFile = System.currentTimeMillis();
                long totalTimeFile = endTimeFile - startTimeFile;

                // Registra o tempo gasto em cada grafo
                resultado.append("\nTempo total de execução do Arquivo grafo_" + y + ": " + totalTimeFile + " ms | " + (totalTimeFile / 1000) + " s | " + ((totalTimeFile / 1000) / 60) + " min\n");
                System.out.println("Tempo total de execução do Arquivo grafo_" + y + ": " + totalTimeFile + " ms | " + (totalTimeFile / 1000) + " s | " + ((totalTimeFile / 1000) / 60) + " min\n");
        }
        } catch (FileNotFoundException e) {
            System.err.println("Erro ao abrir o arquivo.");
            e.printStackTrace();
        }

        // Calcula o tempo total gasto no Arquivo Completo
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;

        // Registra o tempo total gasto
        resultado.append("\nTempo total de execução de todos os arquivos: " + totalTime + " ms | " + (totalTime / 1000) + " s | " + ((totalTime / 1000) / 60) + " min\n");
        System.out.println("\nTempo total de execução de todos os arquivos: " + totalTime + " ms | " + (totalTime / 1000) + " s | " + ((totalTime / 1000) / 60) + " min\n");
        
        // Escrever o resultado no arquivo "FloydWarshall.txt"
        criarArquivoTexto("FloydWarshall.txt", resultado.toString());
        List<List<String>> ResultFinal = new ArrayList<>();
        ResultFinal.add(ResulTime);
        ResultFinal.add(ResultAresta);
        criarArquivoTexto("ResulTime_FloydWarshall.txt", ResultFinal.toString());
    }

    // Função para ler o grafo de um arquivo
    public static int[][] lerGrafo(String nomeArquivo, int numLinhas) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(nomeArquivo));
        int numVertices = scanner.nextInt();
        int[][] matrizAdj = new int[numVertices][numVertices];

        // Inicializa a matriz de adjacência com INFINITY
        for (int i = 0; i < numVertices; i++) {
            Arrays.fill(matrizAdj[i], INFINITY);
        }

        // Preenche a matriz de adjacência com os pesos das arestas
        for (int linhaAtual = 0; linhaAtual < numLinhas && scanner.hasNext(); linhaAtual++) {
            int source = scanner.nextInt();
            int destination = scanner.nextInt();
            int weight = scanner.nextInt();
            matrizAdj[source][destination] = weight;
            matrizAdj[destination][source] = weight;  // Adiciona a aresta inversa para grafos não direcionados
        }

        scanner.close();
        return matrizAdj;
    }

    // Função para criar um arquivo de texto com o conteúdo fornecido
    public static void criarArquivoTexto(String nomeArquivo, String conteudo) {
        File arquivo = new File(nomeArquivo);

        try {
            FileWriter escritor = new FileWriter(arquivo);
            BufferedWriter bufferEscrita = new BufferedWriter(escritor);

            bufferEscrita.write(conteudo);

            bufferEscrita.close();
            escritor.close();

            System.out.println("Arquivo criado com sucesso: " + arquivo.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Erro ao criar o arquivo: " + e.getMessage());
        }
    }


    // Função para construir o caminho mínimo a partir das matrizes de caminhos
    public static void construirCaminho(int[][] caminhos, int origem, int destino, StringBuilder resultado) {
        if (destino < 0 || destino >= caminhos.length) {
            resultado.append("Destino inválido.");
            return;
        }

        Stack<Integer> path = new Stack<>();
        while (destino != origem) {
            path.push(destino);
            destino = caminhos[origem][destino];

            // Adicionado verificação de destino válido dentro do loop
            if (destino < 0 || destino >= caminhos.length) {
                resultado.append("Destino inválido.");
                return;
            }
        }

        resultado.append(origem);
        while (!path.isEmpty()) {
            resultado.append(" -> ").append(path.pop());
        }
    }
}