import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class GeradorDeGrafos {

    public static void main(String[] args) throws InterruptedException {
        long startTime = System.currentTimeMillis();

        // Gera 5 grafos distintos
        for (int x = 1; x <= 5; x++) {
            int numVertices = (int) Math.pow(5, x);
            System.out.println("Grafo " + x + ":");
            List<String> arestas = gerarGrafoConexo(numVertices, x);
            // Salva as arestas em um arquivo separado para cada iteração
            salvarArestasEmArquivo("grafo_" + x + ".txt", arestas);
        }

        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;

        System.out.println("Tempo total de execução: " + totalTime + "ms | " + (totalTime / 1000) + "s | " + ((totalTime / 1000) / 60) + " min");
    }

    private static List<String> gerarGrafoConexo(int numVertices, int x) {
        List<String> arestas = new ArrayList<>();
        Random random = new Random();

        Set<Pair<Integer, Integer>> arestasUsadas = new HashSet<>();
        Set<Integer> verticesUsados = new HashSet<>();
        int origen = 3;

        // Adiciona arestas aleatórias até que o grafo seja conexo
        for (int i = 0; i < primeirasLinhas(numVertices); i++) {
            int destino;
            verticesUsados.add(origen);
            do {
                origen = random.nextInt(numVertices);
            } while (verticesUsados.contains(origen));

            if(origen<12 && origen%2==1){
                destino = (origen + 2) % numVertices; // Conecta cada vértice ao próximo
            }else if(origen>12 && origen%2==1){
                destino = (origen + 1) % numVertices; // Conecta cada vértice ao próximo
            }else if(origen<12 && origen%2==0){
                destino = (origen + 1) % numVertices; // Conecta cada vértice ao próximo
            }else{
                destino = (origen + 2) % numVertices; // Conecta cada vértice ao próximo
            }
            // destino = (origen + 1) % numVertices; // Conecta cada vértice ao próximo
            Pair<Integer, Integer> novaAresta = new Pair<>(Math.min(origen, destino), Math.max(origen, destino));
            if (arestasUsadas.add(novaAresta)) {
                int peso = random.nextInt(1, 10); // Peso aleatório de 0 a 10
                arestas.add(origen + " " + destino + " " + peso);
            }
        }

        // Adiciona a primeira linha com o número total de vértices
        int V = (int) Math.pow(5, x);
        arestas.add(0, String.valueOf(V));

        // Adiciona arestas aleatórias adicionais após o grafo conexo
        while (arestas.size() < linhasMaximas(numVertices)) {
            int origem = random.nextInt(numVertices);
            int destino = random.nextInt(numVertices);

            if (origem != destino) {
                Pair<Integer, Integer> novaAresta = new Pair<>(Math.min(origem, destino), Math.max(origem, destino));
                if (arestasUsadas.add(novaAresta)) {
                    int peso = random.nextInt(1, 10); // Peso aleatório de 0 a 10
                    arestas.add(origem + " " + destino + " " + peso);
                }
            }
        }

        return arestas;
    }

    private static int primeirasLinhas(int numVertices) {
        return numVertices - 1;
    }

    private static int linhasMaximas(int numVertices) {
        return (numVertices * (numVertices - 1)) / 2;
    }

    private static void salvarArestasEmArquivo(String nomeArquivo, List<String> arestas) {
        File arquivo = new File(nomeArquivo);

        try (BufferedWriter bufferEscrita = new BufferedWriter(new FileWriter(arquivo))) {
            for (String aresta : arestas) {
                bufferEscrita.write(aresta + "\n");
            }
            System.out.println("Arquivo criado com sucesso: " + arquivo);
        } catch (IOException e) {
            System.err.println("Erro ao criar o arquivo: " + e.getMessage());
        }
    }

    static class Pair<K, V> {
        private final K key;
        private final V value;

        public Pair(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Pair<?, ?> pair = (Pair<?, ?>) obj;
            return Objects.equals(key, pair.key) && Objects.equals(value, pair.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(key, value);
        }
    }
}
