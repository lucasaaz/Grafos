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
            List<String> arestas = gerarArestasAleatorias(numVertices, x);
            // Exibe as arestas geradas (opcional)
            //exibirArestas(arestas);
            // Salva as arestas em um arquivo separado para cada iteração
            salvarArestasEmArquivo("grafo_" + x + ".txt", arestas);
        }

        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;

        System.out.println("Tempo total de execução: " + totalTime + "ms | " + (totalTime/1000) + "s | " + ((totalTime/1000)/60) + " min");
    
    }

    private static List<String> gerarArestasAleatorias(int numVertices, int x) {
        // Calcular quantidades equidistantes
        int menorQuantidade = numVertices - 1;
        int maiorQuantidade = (numVertices * (numVertices - 1)) / 2;
        int intervalo = (maiorQuantidade - menorQuantidade) / 4;
        int[] quantidadesEquidistantes = new int[5];

        for (int i = 0; i < 5; i++) {
            quantidadesEquidistantes[i] = menorQuantidade + i * intervalo;
        }

        // Adicionar arestas aleatórias com pesos e sem repetições
        Random random = new Random();
        Set<Pair<Integer, Integer>> arestasAdicionadas = new HashSet<>();
        List<String> arestas = new ArrayList<>();
        boolean entrar = true;

        for (int i = 0; i < 5; i++) {
            while (arestasAdicionadas.size() < quantidadesEquidistantes[i]) {
                int origem = random.nextInt(numVertices);
                int destino = random.nextInt(numVertices);

                if (origem != destino) {
                    Pair<Integer, Integer> novaAresta = new Pair<>(Math.min(origem, destino), Math.max(origem, destino));
                    if (arestasAdicionadas.add(novaAresta)) {
                        int V = (int) Math.pow(5, x);
                        if (entrar == true) {
                            String v = String.valueOf(V);
                            arestas.add(v);
                            entrar = false;
                        }

                        int peso = random.nextInt(1,10); // Peso aleatório de 0 a 100
                        arestas.add(origem + " " + destino + " " + peso);
                    }
                }
            }
        }

        return arestas;
    }

    private static void exibirArestas(List<String> arestas) {
        for (String aresta : arestas) {
            System.out.println(aresta);
        }
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
