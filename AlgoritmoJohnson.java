import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Stack;

// Classe que representa um grafo
class Graph {
    private int numVertices;
    private List<List<Pair>> adjList;

    // Construtor que inicializa o grafo com o número de vértices especificado
    public Graph(int vertices) {
        this.numVertices = vertices;
        this.adjList = new ArrayList<>(vertices);
        for (int i = 0; i < vertices; i++) {
            this.adjList.add(new ArrayList<>());
        }
    }

    // Método para adicionar uma aresta ao grafo
    public void addEdge(int source, int destination, int weight) {
        // Certifica-se de que a lista tenha capacidade suficiente
        while (this.adjList.size() <= Math.max(source, destination)) {
            this.adjList.add(new ArrayList<>());
        }

        this.adjList.get(source).add(new Pair(destination, weight));
        this.adjList.get(destination).add(new Pair(source, weight));
    }

    // Métodos para obter a lista de adjacência e o número de vértices
    public List<List<Pair>> getAdjList() {
        return this.adjList;
    }

    public int getNumVertices() {
        return this.numVertices;
    }
}

// Classe que representa um par de vértice e peso
class Pair {
    private int vertex;
    private int weight;

    public Pair(int vertex, int weight) {
        this.vertex = vertex;
        this.weight = weight;
    }

    public int getVertex() {
        return this.vertex;
    }

    public int getWeight() {
        return this.weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}

// Classe principal que contém a implementação do algoritmo Jhonson
public class AlgoritmoJohnson {

    // Algoritmo de Johnson
    public static int[] johnson(Graph graph, int startVertex, int[] parents) {
        int numVertices = graph.getNumVertices();

        // Adição do nó fictício
        graph.addEdge(numVertices, 0, 0);

        // Execução do Bellman-Ford para encontrar os menores caminhos do nó fictício para todos os outros nós
        int[] hValues = bellmanFord(graph, numVertices);

        // Verificação de ciclo negativo
        if (hValues == null) {
            System.out.println("O grafo contém um ciclo negativo. O algoritmo de Johnson não pode ser aplicado.");
            return null;
        }

        // Ajuste dos pesos das arestas originais
        for (int u = 0; u < numVertices; u++) {
            for (Pair neighbor : graph.getAdjList().get(u)) {
                int v = neighbor.getVertex();
                int weightUV = neighbor.getWeight();
                neighbor.setWeight(weightUV + hValues[u] - hValues[v]);
            }
        }

        // Remoção do nó fictício
        graph.getAdjList().remove(numVertices);

        // Inicialização do array de resultados
        int[] result = new int[numVertices * numVertices];
        int index = 0;
        for (int i = 0; i < numVertices; i++) {
            int[] distances = dijkstra(graph, i);
            for (int j = 0; j < numVertices; j++) {
                result[index++] = distances[j] - hValues[i] + hValues[j];
            }
        }

        return result;
    }

    // Algoritmo de Bellman-Ford modificado
    public static int[] bellmanFord(Graph graph, int startVertex) {
        int numVertices = graph.getNumVertices();
        int[] distances = new int[numVertices + 1];

        // Inicialização das distâncias
        for (int i = 0; i <= numVertices; i++) {
            distances[i] = Integer.MAX_VALUE;
        }
        distances[startVertex] = 0;

        // Relaxamento das arestas repetidas numVertices vezes
        for (int i = 0; i < numVertices; i++) {
            for (int u = 0; u <= numVertices; u++) {
                for (Pair neighbor : graph.getAdjList().get(u)) {
                    int v = neighbor.getVertex();
                    int weightUV = neighbor.getWeight();

                    if (distances[u] != Integer.MAX_VALUE && distances[v] > distances[u] + weightUV) {
                        distances[v] = distances[u] + weightUV;
                    }
                }
            }
        }

        // Verificação de ciclo negativo
        for (int u = 0; u <= numVertices; u++) {
            for (Pair neighbor : graph.getAdjList().get(u)) {
                int v = neighbor.getVertex();
                int weightUV = neighbor.getWeight();
                if (distances[u] != Integer.MAX_VALUE && distances[v] > distances[u] + weightUV) {
                    return null;  // Ciclo negativo detectado
                }
            }
        }

        return distances;
    }

    // Algoritmo de Dijkstra modificado
    public static int[] dijkstra(Graph graph, int startVertex) {
        int numVertices = graph.getNumVertices();
        int[] distances = new int[numVertices + 1]; // Ajuste aqui para numVertices + 1

        // Inicialização das distâncias
        for (int i = 0; i <= numVertices; i++) {
            distances[i] = Integer.MAX_VALUE;
        }
        distances[startVertex] = 0;

        PriorityQueue<Pair> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(Pair::getWeight));
        priorityQueue.add(new Pair(startVertex, 0));

        while (!priorityQueue.isEmpty()) {
            int u = priorityQueue.poll().getVertex();

            for (Pair neighbor : graph.getAdjList().get(u)) {
                int v = neighbor.getVertex();
                int weightUV = neighbor.getWeight();

                if (v < numVertices && distances[v] > distances[u] + weightUV) {
                    distances[v] = distances[u] + weightUV;
                    priorityQueue.add(new Pair(v, distances[v]));
                }
            }
        }

        return distances;
    }

    

    // Método principal
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        StringBuilder resultado = new StringBuilder();
        List<String> ResulTime = new ArrayList<>();
        List<String> ResultAresta = new ArrayList<>();

        for (int y = 1; y <= 5; y++) { // Itera sobre os 5 grafos

            // Início - Tempo de execução do grafo atual
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
                qa[i] = intervalo;
                intervalo--;
            }

            try {
                for (int repeat = 0; repeat < 5; repeat++) { // Repetir a leitura do arquivo 5 vezes

                    // Resetar o grafo para cada iteração
                    int numLinhas = qa[repeat];  // Número de linhas a serem lidas
                    Graph graph = lerGrafo("grafo_" + y + ".txt", numLinhas);

                    int startVertex = 0; // Você pode escolher outro vértice como ponto de origem
                    int[] parents = new int[graph.getNumVertices()];
                    for (int i = 0; i < graph.getNumVertices(); i++) {
                        parents[i] = -1;
                    }

                    // Inicio - Tempo de execucao do Jhonson no grafo atual
                    long startTimeAlgoritmo = System.currentTimeMillis();
                    // Executa o algoritmo de Jhonson
                    int[] distances = johnson(graph, startVertex, parents);
                    // Fim - Tempo de execucao do Jhonson no grafo atual
                    long endTimeAlgoritmo = System.currentTimeMillis();
                    // Calcula o tempo gasto no processamento do grafo atual
                    long totalTimeAlgoritmo = endTimeAlgoritmo - startTimeAlgoritmo;
                    System.out.println("Tempo gasto pelo algoritmo Jhonson no grafo_" + y + " - Arestas " + numLinhas + ": " + totalTimeAlgoritmo + " ms | " + (totalTimeAlgoritmo / 1000) + " s | " + ((totalTimeAlgoritmo / 1000) / 60) + " min");
                    resultado.append("\nTempo gasto pelo algoritmo Jhonson no grafo_" + y + " - Arestas " + numLinhas + ": " + totalTimeAlgoritmo + " ms | " + (totalTimeAlgoritmo / 1000) + " s | " + ((totalTimeAlgoritmo / 1000) / 60) + " min");
                    ResulTime.add(totalTimeAlgoritmo+"");
                    ResultAresta.add(""+numLinhas);

                    // Exibindo as distâncias mínimas e os caminhos mínimos a partir do ponto de origem
                    resultado.append("\nGrafo ").append(y).append(" - Iteração ").append(repeat + 1).append(" - Arestas " + numLinhas);
                    resultado.append("\nDistâncias mínimas e caminhos mínimos a partir do vértice ").append(startVertex).append(":\n");
                    for (int i = 0; i < graph.getNumVertices(); i++) {
                        resultado.append("Para o vértice ").append(i).append(": Distância - ").append(distances[i]).append(", Caminho - ");
                        Stack<Integer> path = new Stack<>();
                        int current = i;
                        while (current != -1) {
                            path.push(current);
                            current = parents[current];
                        }
                        while (!path.isEmpty()) {
                            resultado.append(path.pop());
                            if (!path.isEmpty()) {
                                resultado.append(" -> ");
                            }
                        }
                        resultado.append("\n");
                    }
                }
            } catch (FileNotFoundException e) {
                System.err.println("Erro ao abrir o arquivo.");
                e.printStackTrace();
            }

            // Fim - Tempo de execução do grafo atual
            // Calcula o tempo gasto no processamento do grafo atual
            long endTimeFile = System.currentTimeMillis();
            long totalTimeFile = endTimeFile - startTimeFile;

            // Registra o tempo gasto em cada grafo
            resultado.append("\nTempo total de execução do Arquivo grafo_" + y + ": " + totalTimeFile + " ms | " + (totalTimeFile / 1000) + " s | " + ((totalTimeFile / 1000) / 60) + " min\n");
            System.out.println("Tempo total de execução do Arquivo grafo_" + y + ": " + totalTimeFile + " ms | " + (totalTimeFile / 1000) + " s | " + ((totalTimeFile / 1000) / 60) + " min\n");
        }

        // Calcular o tempo gasto
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;

        // Registra o tempo total gasto
        resultado.append("\nTempo total de execução de todos os arquivos: " + totalTime + " ms | " + (totalTime / 1000) + " s | " + ((totalTime / 1000) / 60) + " min\n");
        System.out.println("\nTempo total de execução de todos os arquivos: " + totalTime + " ms | " + (totalTime / 1000) + " s | " + ((totalTime / 1000) / 60) + " min\n");
        
        // Escrever o resultado no arquivo "Johnson.txt"
        criarArquivoTexto("Johnson.txt", resultado.toString());
        List<List<String>> ResultFinal = new ArrayList<>();
        ResultFinal.add(ResulTime);
        ResultFinal.add(ResultAresta);
        criarArquivoTexto("ResulTime_Jhonson.txt", ResultFinal.toString());
    }

    // Função para ler o grafo de um arquivo
    public static Graph lerGrafo(String nomeArquivo, int numLinhas) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(nomeArquivo));
        int numVertices = scanner.nextInt();
        Graph graph = new Graph(numVertices);

        int linhaAtual = 0;
        while (linhaAtual < numLinhas && scanner.hasNext()) {
            int source = scanner.nextInt();
            int destination = scanner.nextInt();
            int weight = scanner.nextInt();
            graph.addEdge(source, destination, weight);
            linhaAtual++;
        }

        scanner.close();
        return graph;
    }

    // Função para criar um arquivo de texto com o conteúdo fornecido
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
}