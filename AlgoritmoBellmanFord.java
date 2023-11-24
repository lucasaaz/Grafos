
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class AlgoritmoBellmanFord {
    static class Edge {
        int source, destination, weight;

        public Edge(int source, int destination, int weight) {
            this.source = source;
            this.destination = destination;
            this.weight = weight;
        }
    }

    static int INFINITY = Integer.MAX_VALUE;

    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(new File("grafo.txt"));

            int numVertices = scanner.nextInt();
            List<Edge> arestas = new ArrayList<>();

            while (scanner.hasNext()) {
                int origem = scanner.nextInt();
                int destino = scanner.nextInt();
                int peso = scanner.nextInt();

                arestas.add(new Edge(origem, destino, peso));
            }

            // Escolha um vértice como origem (pode ser qualquer um)
            int origem = 0;

            // Chama o algoritmo de Bellman-Ford
            bellmanFord(arestas, numVertices, origem);

            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    static void bellmanFord(List<Edge> arestas, int numVertices, int origem) {
        int[] distancias = new int[numVertices];
        Arrays.fill(distancias, INFINITY);
        distancias[origem] = 0;

        // Relaxamento de arestas repetidas
        for (int i = 1; i < numVertices; ++i) {
            for (Edge aresta : arestas) {
                int u = aresta.source;
                int v = aresta.destination;
                int peso = aresta.weight;

                if (distancias[u] != INFINITY && distancias[u] + peso < distancias[v]) {
                    distancias[v] = distancias[u] + peso;
                }
            }
        }

        // Exibe as distâncias mínimas a partir da origem
        System.out.println("Distâncias mínimas a partir do vértice " + origem + ":");
        for (int i = 0; i < numVertices; ++i) {
            System.out.println("Para o vértice " + i + ": " + distancias[i]);
        }
    }
}
