import matplotlib.pyplot as plt
import numpy as np
import ast

algoritmos = ["BellmanFord", "Dijkstra", "FloydWarshall", "ForestPaths", "Jhonson"]
lista_vertice_5 = []
lista_vertice_25 = []
lista_vertice_125 = []
lista_vertice_625 = []
lista_vertice_3125 = []
lista_time_total = []


# Ler dados de arquivos
for i in algoritmos:
    with open('ResulTime_' + i + '.txt', 'r') as arquivo:
        # Lê todas as linhas do arquivo em uma lista
        linhas = arquivo.read()
        dados= ast.literal_eval(linhas)
        dados_tempo = dados[0]
        dados_arestas = dados[1]
        time_total = sum(dados_tempo)
        lista_time_total.append(time_total)

    lista_vertice_5.append(dados_arestas[0:5])
    lista_vertice_5.append(dados_tempo[0:5])
    lista_vertice_25.append(dados_arestas[5:10])
    lista_vertice_25.append(dados_tempo[5:10])
    lista_vertice_125.append(dados_arestas[10:15])
    lista_vertice_125.append(dados_tempo[10:15])
    lista_vertice_625.append(dados_arestas[15:20])
    lista_vertice_625.append(dados_tempo[15:20]) 
    lista_vertice_3125.append(dados_arestas[20:25])
    lista_vertice_3125.append(dados_tempo[20:25])

#
# Gera um grafico de todos os algoritmos com as arestas aumentando e vertice = 5 
#
# Criar um gráfico de linhas
plt.plot(lista_vertice_5[0],lista_vertice_5[1], marker='', linestyle='-', color='r', label='BellmanFord')
plt.plot(lista_vertice_5[2],lista_vertice_5[3], marker='', linestyle='-', color='y', label='Dijkstra')
plt.plot(lista_vertice_5[4],lista_vertice_5[5], marker='', linestyle='-', color='b', label='FloydWarshall')
plt.plot(lista_vertice_5[6],lista_vertice_5[7], marker='', linestyle='-', color='g', label='ForestPaths')
plt.plot(lista_vertice_5[8],lista_vertice_5[9], marker='', linestyle='-', color='c', label='Jhonson')

# Adicionar rótulos e título
plt.xlabel('Numero de Arestas')
plt.ylabel('Tempo (ms)')
plt.title('RESULTADO POR ALGORITMO')

# Adicionar legenda
plt.legend()

# Exibir o gráfico
plt.show()

#
# Gera um grafico de todos os algoritmos com as arestas aumentando e vertice = 25 
#
# Criar um gráfico de linhas
plt.plot(lista_vertice_25[0],lista_vertice_25[1], marker='', linestyle='-', color='r', label='BellmanFord')
plt.plot(lista_vertice_25[2],lista_vertice_25[3], marker='', linestyle='-', color='y', label='Dijkstra')
plt.plot(lista_vertice_25[4],lista_vertice_25[5], marker='', linestyle='-', color='b', label='FloydWarshall')
plt.plot(lista_vertice_25[6],lista_vertice_25[7], marker='', linestyle='-', color='g', label='ForestPaths')
plt.plot(lista_vertice_25[8],lista_vertice_25[9], marker='', linestyle='-', color='c', label='Jhonson')

# Adicionar rótulos e título
plt.xlabel('Numero de Arestas')
plt.ylabel('Tempo (ms)')
plt.title('RESULTADO POR ALGORITMO')

# Adicionar legenda
plt.legend()

# Exibir o gráfico
plt.show()

#
# Gera um grafico de todos os algoritmos com as arestas aumentando e vertice = 125 
#
# Criar um gráfico de linhas
plt.plot(lista_vertice_125[0],lista_vertice_125[1], marker='', linestyle='-', color='r', label='BellmanFord')
plt.plot(lista_vertice_125[2],lista_vertice_125[3], marker='', linestyle='-', color='y', label='Dijkstra')
plt.plot(lista_vertice_125[4],lista_vertice_125[5], marker='', linestyle='-', color='b', label='FloydWarshall')
plt.plot(lista_vertice_125[6],lista_vertice_125[7], marker='', linestyle='-', color='g', label='ForestPaths')
plt.plot(lista_vertice_125[8],lista_vertice_125[9], marker='', linestyle='-', color='c', label='Jhonson')

# Adicionar rótulos e título
plt.xlabel('Numero de Arestas')
plt.ylabel('Tempo (ms)')
plt.title('RESULTADO POR ALGORITMO')

# Adicionar legenda
plt.legend()

# Exibir o gráfico
plt.show()

#
# Gera um grafico de todos os algoritmos com as arestas aumentando e vertice = 625 
#
# Criar um gráfico de linhas
plt.plot(lista_vertice_625[0],lista_vertice_625[1], marker='', linestyle='-', color='r', label='BellmanFord')
plt.plot(lista_vertice_625[2],lista_vertice_625[3], marker='', linestyle='-', color='y', label='Dijkstra')
plt.plot(lista_vertice_625[4],lista_vertice_625[5], marker='', linestyle='-', color='b', label='FloydWarshall')
plt.plot(lista_vertice_625[6],lista_vertice_625[7], marker='', linestyle='-', color='g', label='ForestPaths')
plt.plot(lista_vertice_625[8],lista_vertice_625[9], marker='', linestyle='-', color='c', label='Jhonson')

# Adicionar rótulos e título
plt.xlabel('Numero de Arestas')
plt.ylabel('Tempo (ms)')
plt.title('RESULTADO POR ALGORITMO')

# Adicionar legenda
plt.legend()

# Exibir o gráfico
plt.show()

#
# Gera um grafico de todos os algoritmos com as arestas aumentando e vertice = 3125 
#
# Criar um gráfico de linhas
plt.plot(lista_vertice_3125[0],lista_vertice_3125[1], marker='', linestyle='-', color='r', label='BellmanFord')
plt.plot(lista_vertice_3125[2],lista_vertice_3125[3], marker='', linestyle='-', color='y', label='Dijkstra')
plt.plot(lista_vertice_3125[4],lista_vertice_3125[5], marker='', linestyle='-', color='b', label='FloydWarshall')
plt.plot(lista_vertice_3125[6],lista_vertice_3125[7], marker='', linestyle='-', color='g', label='ForestPaths')
plt.plot(lista_vertice_3125[8],lista_vertice_3125[9], marker='', linestyle='-', color='c', label='Jhonson')

# Adicionar rótulos e título
plt.xlabel('Numero de Arestas')
plt.ylabel('Tempo (ms)')
plt.title('RESULTADO POR ALGORITMO')

# Adicionar legenda
plt.legend()

# Exibir o gráfico
plt.show()


#
# Dados Gerais
#
dicionario = {"Johnson": lista_time_total[4], "FloydWarshall": lista_time_total[2], "ForestPaths": lista_time_total[3], "BellmanFord": lista_time_total[0], "Dijkstra": lista_time_total[1]}
# Criar um novo dicionário ordena  do por valores
dicionario_ordenado_por_valores = {chave: valor for chave, valor in sorted(dicionario.items(), key=lambda item: item[1])}
# Exibir resultados
print("Dicionário original:", dicionario)
print("Dicionário ordenado por valores:", dicionario_ordenado_por_valores)
# Obter chaves e valores do dicionário
algoritmo = list(dicionario_ordenado_por_valores.keys())
tempo = list(dicionario_ordenado_por_valores.values())
# Criar um gráfico de linhas
plt.plot(algoritmo, tempo, marker='o', linestyle='-', color='r', label='Grafico Geral')
plt.plot(algoritmo, tempo, marker='o', linestyle='-', color='r', label='')
plt.plot(algoritmo, tempo, marker='o', linestyle='-', color='r', label='')
plt.plot(algoritmo, tempo, marker='o', linestyle='-', color='r', label='')
plt.plot(algoritmo, tempo, marker='o', linestyle='-', color='r', label='')

# Adicionar rótulos e título
plt.xlabel('Algoritmos')
plt.ylabel('Tempo (ms)')
plt.title('RESULTADO GERAL DE CADA ALGORITMO')

# Adicionar legenda
plt.legend()

# Exibir o gráfico
plt.show()

