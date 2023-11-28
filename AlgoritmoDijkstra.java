
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

class Graph {
    private int numVertices;
    private List<List<Pair>> adjList;

    public Graph(int vertices) {
        this.numVertices = vertices;
        this.adjList = new ArrayList<>(vertices);
        for (int i = 0; i < vertices; i++) {
            this.adjList.add(new ArrayList<>());
        }
    }

    public void addEdge(int source, int destination, int weight) {
        this.adjList.get(source).add(new Pair(destination, weight));
        // A aresta inversa não é adicionada automaticamente para um grafo direcionado
        this.adjList.get(destination).add(new Pair(source, weight));

    }

    public List<List<Pair>> getAdjList() {
        return this.adjList;
    }

    public int getNumVertices() {
        return this.numVertices;
    }
}

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
}

class Dijkstra {
    public int[] dijkstraAlgorithm(Graph graph, int startVertex, int[] parents) {
        int[] distances = new int[graph.getNumVertices()];
        for (int i = 0; i < graph.getNumVertices(); i++) {
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

                if (distances[v] > distances[u] + weightUV) {
                    distances[v] = distances[u] + weightUV;
                    priorityQueue.add(new Pair(v, distances[v]));
                    parents[v] = u;  // Atualiza o pai para o caminho mínimo
                }
            }
        }

        return distances;
    }
}


public class AlgoritmoDijkstra {
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        StringBuilder resultado = new StringBuilder();

        try {
            for(int y = 1; y<=5; y++){  // Passar pelos 5 arquivos
                Scanner scanner = new Scanner(new File("grafo_" + y + ".txt"));
                int numVertices = scanner.nextInt();
                Graph graph = new Graph(numVertices);
                    
                while (scanner.hasNext()) {
                    int source = scanner.nextInt();
                    int destination = scanner.nextInt();
                    int weight = scanner.nextInt();
                    graph.addEdge(source, destination, weight);
                    // Remova a adição automática da aresta inversa para um grafo direcionado
                }

                scanner.close();     

                Dijkstra dijkstra = new Dijkstra();
                int startVertex = 0;  // Você pode escolher outro vértice como ponto de origem
                int[] parents = new int[numVertices];
                for (int i = 0; i < numVertices; i++) {
                    parents[i] = -1;
                }

                int[] distances = dijkstra.dijkstraAlgorithm(graph, startVertex, parents);

                // Exibindo as distâncias mínimas e os caminhos mínimos a partir do ponto de origem
                resultado.append("\nGrafo " + y );
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
        } catch (FileNotFoundException e) {
            System.err.println("Erro ao abrir o arquivo.");
            e.printStackTrace();
        }

        // Calcular o tempo gasto
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;

        resultado.append("\nTempo total de execução: " + totalTime + " ms | " + (totalTime / 1000) + " s | " + ((totalTime / 1000) / 60) + " min\n");
        System.out.println("\nTempo total de execução: " + totalTime + " ms | " + (totalTime / 1000) + " s | " + ((totalTime / 1000) / 60) + " min\n");
        
        // Escrever o resultado no arquivo "ForestPaths.txt"
        criarArquivoTexto("Dijkstra.txt", resultado.toString());
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
}
