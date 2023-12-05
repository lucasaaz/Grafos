import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;
import java.util.LinkedList;
import java.util.Queue;

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
        // Garantir que a lista interna tenha o tamanho correto
        while (this.adjList.size() <= Math.max(source, destination)) {
            this.adjList.add(new ArrayList<>());
        }

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

// Classe principal que contém a implementação do algoritmo de Floresta de Caminhos Otimo
public class AlgoritmoFlorestaCaminhosOtimo {

    // Implementação do algoritmo de Floresta de Caminhos Otimo
    public static int[] forestPathsAlgorithm(Graph graph, int startVertex, int[] parents) {
        int[] distances = new int[graph.getNumVertices()];
        for (int i = 0; i < graph.getNumVertices(); i++) {
            distances[i] = Integer.MAX_VALUE;
        }
        distances[startVertex] = 0;

        Queue<Integer> queue = new LinkedList<>();
        queue.offer(startVertex);

        while (!queue.isEmpty()) {
            int u = queue.poll();

            for (Pair neighbor : graph.getAdjList().get(u)) {
                int v = neighbor.getVertex();
                int weightUV = neighbor.getWeight();

                if (distances[v] > distances[u] + weightUV) {
                    distances[v] = distances[u] + weightUV;
                    parents[v] = u;  // Atualiza o pai para o caminho mínimo
                    queue.offer(v);
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
                for (int repeat = 0; repeat < 5; repeat++) {  // Repetir a leitura do arquivo 5 vezes

                    // Inicio - Tempo de execucao do grafo atual por vertice
                    long startTimeGrafo = System.currentTimeMillis();

                    // Resetar o grafo para cada iteração
                    int numLinhas = qa[repeat];  // Número de linhas a serem lidas
                    Graph graph = lerGrafo("grafo_" + y + ".txt", numLinhas);

                    int startVertex = 0; // Você pode escolher outro vértice como ponto de origem
                    int[] parents = new int[graph.getNumVertices()];
                    for (int i = 0; i < graph.getNumVertices(); i++) {
                        parents[i] = -1;
                    }

                    int[] distances = forestPathsAlgorithm(graph, startVertex, parents);

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
                    // Fim - Tempo de execucao do grafo atual por vertice
                    // Calcula o tempo gasto no processamento do grafo atual 
                    long endTimeGrafo = System.currentTimeMillis();
                    long totalTimeGrafo = endTimeGrafo - startTimeGrafo;
                    ResulTime.add(totalTimeGrafo+"");
                    ResultAresta.add(""+numLinhas);
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
            resultado.append("\nTempo total de execução do grafo_" + y + ": " + totalTimeFile + " ms | " + (totalTimeFile / 1000) + " s | " + ((totalTimeFile / 1000) / 60) + " min\n");
            System.out.println("\nTempo total de execução do grafo_" + y + ": " + totalTimeFile + " ms | " + (totalTimeFile / 1000) + " s | " + ((totalTimeFile / 1000) / 60) + " min\n");
        }

        // Calcula o tempo total gasto no Arquivo Completo
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;

        // Registra o tempo total gasto
        resultado.append("\nTempo total de execução: " + totalTime + " ms | " + (totalTime / 1000) + " s | " + ((totalTime / 1000) / 60) + " min\n");
        System.out.println("\nTempo total de execução: " + totalTime + " ms | " + (totalTime / 1000) + " s | " + ((totalTime / 1000) / 60) + " min\n");

        // Escrever o resultado no arquivo "ForestPaths.txt"
        criarArquivoTexto("ForestPaths.txt", resultado.toString());
        List<List<String>> ResultFinal = new ArrayList<>();
        ResultFinal.add(ResulTime);
        ResultFinal.add(ResultAresta);
        criarArquivoTexto("ResulTime_ForestPaths.txt", ResultFinal.toString());
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
