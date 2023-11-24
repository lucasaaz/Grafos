import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class AlgoritmoBellmanFord2 {
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
            Scanner scanner = new Scanner(new File("grafo_1.txt"));

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
        int[] pais = new int[numVertices]; // Armazena o caminho mínimo
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
                    pais[v] = u; // Atualiza o pai para o caminho mínimo
                }
            }
        }

        // Verifica ciclos de peso negativo
        for (Edge aresta : arestas) {
            int u = aresta.source;
            int v = aresta.destination;
            int peso = aresta.weight;

            if (distancias[u] != INFINITY && distancias[u] + peso < distancias[v]) {
                System.out.println("O grafo contém um ciclo de peso negativo.");
                return;
            }
        }

        // Exibe as distâncias mínimas e os caminhos mínimos a partir da origem
        System.out.println("Distâncias mínimas a partir do vértice " + origem + ":");
        for (int i = 0; i < numVertices; ++i) {
            System.out.println("Para o vértice " + i + ": Distância - " + distancias[i] + ", Caminho - " + obterCaminho(pais, origem, i));
        }
    }

    static String obterCaminho(int[] pais, int origem, int destino) {
        if (origem == destino) {
            return String.valueOf(origem);
        }

        StringBuilder caminho = new StringBuilder();
        caminho.append(destino);

        while (destino != origem) {
            destino = pais[destino];
            caminho.insert(0, " -> ").insert(0, destino);
        }

        return caminho.toString();
    }
}
