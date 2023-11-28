import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

class Graph {
    private int numVertices;
    private List<List<Edge>> adjList;

    public Graph(int vertices) {
        this.numVertices = vertices;
        this.adjList = new ArrayList<>(vertices);
        for (int i = 0; i < vertices; i++) {
            this.adjList.add(new ArrayList<>());
        }
    }

    public void addEdge(int source, int destination, int weight) {
        this.adjList.get(source).add(new Edge(source, destination, weight));
        this.adjList.get(destination).add(new Edge(destination, source, weight));  // Adiciona a aresta inversa
    }

    public List<List<Edge>> getAdjList() {
        return this.adjList;
    }

    public int getNumVertices() {
        return this.numVertices;
    }
}

class Edge {
    int source, destination, weight;

    public Edge(int source, int destination, int weight) {
        this.source = source;
        this.destination = destination;
        this.weight = weight;
    }
}

class AlgoritmoBellmanFord {
    static int INFINITY = Integer.MAX_VALUE;

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        StringBuilder resultado = new StringBuilder();

        try {
            for (int y = 1; y <= 5; y++) {  // Passar pelos 5 arquivos
                Scanner scanner = new Scanner(new File("grafo_" + y + ".txt"));
                int numVertices = scanner.nextInt();
                Graph graph = new Graph(numVertices);

                while (scanner.hasNext()) {
                    int source = scanner.nextInt();
                    int destination = scanner.nextInt();
                    int weight = scanner.nextInt();
                    graph.addEdge(source, destination, weight);
                }

                scanner.close();

                int startVertex = 0;
                int[] parents = new int[numVertices];
                Arrays.fill(parents, -1);

                int[] distances = bellmanFord(graph, startVertex, parents);

                resultado.append("\nGrafo " + y);
                resultado.append("\nDistâncias mínimas e caminhos mínimos a partir do vértice " + startVertex + ":\n");
                for (int i = 0; i < graph.getNumVertices(); i++) {
                    resultado.append("Para o vértice " + i + ": Distância - " + distances[i] + ", Caminho - ");
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
        } catch (IOException e) {
            System.err.println("Erro ao abrir o arquivo.");
            e.printStackTrace();
        }

        // Calcular o tempo gasto
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;

        resultado.append("\nTempo total de execução: " + totalTime + " ms | " + (totalTime / 1000) + " s | " + ((totalTime / 1000) / 60) + " min\n");
        System.out.println("\nTempo total de execução: " + totalTime + " ms | " + (totalTime / 1000) + " s | " + ((totalTime / 1000) / 60) + " min\n");
        
        // Escrever o resultado no arquivo "ForestPaths.txt"
        criarArquivoTexto("BellmanFord.txt", resultado.toString());    
    }

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

    public static int[] bellmanFord(Graph graph, int startVertex, int[] parents) {
        int[] distances = new int[graph.getNumVertices()];
        Arrays.fill(distances, INFINITY);
        distances[startVertex] = 0;

        for (int i = 1; i < graph.getNumVertices(); ++i) {
            for (List<Edge> edges : graph.getAdjList()) {
                for (Edge edge : edges) {
                    int u = edge.source;
                    int v = edge.destination;
                    int weight = edge.weight;

                    if (distances[u] != INFINITY && distances[u] + weight < distances[v]) {
                        distances[v] = distances[u] + weight;
                        parents[v] = u;
                    }
                }
            }
        }

        return distances;
    }
}
