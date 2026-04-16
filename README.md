# Sistema de Delivery de Comida

## Tema
Gerencie restaurantes, entregadores, pedidos e acompanhe entregas em tempo real.

## Integrantes do grupo
- Ingrid Ferreira de Sousa - RGM: 45999279
- Jefferson Aparecido Faria de Alencar - RGM: 46100440
- Guilherme Leite Tavares - RGM: 45871884
- Stephanie Vitoria Bessa dos Santos - RGM: 
- Matheus Caetano Rocha - RGM: 45998400

## Objetivo do sistema
O objetivo do sistema é oferecer uma plataforma de gerenciamento de delivery que integra restaurantes, entregadores e clientes em um único ambiente. O sistema deve permitir a administração de cardápios, o cadastro de entregadores e o acompanhamento em tempo real do status de pedidos.

A solução visa automatizar todo o processo de entrega de comida, desde a criação do pedido até a finalização da entrega. Com isso, espera-se reduzir o tempo de espera dos clientes, melhorar a organização interna dos restaurantes e garantir mais controle para a equipe de delivery.

## Funcionalidades principais
1. Cadastro e gerenciamento de restaurantes.
2. Cadastro e gerenciamento de entregadores.
3. Criação, atualização e acompanhamento de pedidos.
4. Atribuição automática de entregadores para pedidos disponíveis.
5. Rastreamento em tempo real do status da entrega.
6. Visualização de histórico de pedidos e entregas.
7. Controle de disponibilidade de entregadores.

## Estrutura de classes planejada
- `Restaurante`
  - `id`
  - `nome`
  - `endereco`
  - `cardapio`
  - `avaliacao`

- `Entregador`
  - `id`
  - `nome`
  - `telefone`
  - `statusDisponibilidade`
  - `localizacaoAtual`

- `Pedido`
  - `id`
  - `cliente`
  - `restaurante`
  - `itens`
  - `valorTotal`
  - `status`
  - `entregador`

- `ItemPedido`
  - `nome`
  - `quantidade`
  - `precoUnitario`

- `SistemaDelivery`
  - métodos de cadastro, listagem, atribuição de entregador e atualização de status.

## Regra de negócio complexa
Ao finalizar um pedido, o sistema deve:
- Calcular o subtotal somando o preço de todos os produtos multiplicados por suas quantidades.
- Aplicar descontos progressivos com base no subtotal:
  - 5% de desconto para pedidos acima de R$ 100,00
  - 10% de desconto para pedidos acima de R$ 200,00
  - 15% de desconto para pedidos acima de R$ 300,00
- Adicionar taxa de entrega fixa de R$ 8,00.
- Exibir um resumo detalhado com subtotal dos produtos, desconto aplicado, taxa de entrega e valor final.

Para a atribuição de entregador, o sistema deve verificar se o entregador está com status "disponível" antes de vinculá-lo ao pedido. Após atribuir, o status do entregador deve ser atualizado para "em entrega".

O sistema também deve atribuir automaticamente o entregador mais próximo e disponível para cada novo pedido, com base na localização atual do entregador e no tempo estimado de entrega. Se o entregador selecionado não confirmar o pedido em até 2 minutos, o sistema deve buscar o próximo melhor entregador disponível e reatribuí-lo.

Além disso, se um restaurante atualizar o status de um pedido para "pronto" e não houver entregadores disponíveis no momento, o pedido deve ficar em fila de espera priorizada por tempo de criação e por proximidade futura estimada, garantindo que entregadores recém-disponíveis sejam atribuídos conforme prioridade.

# Documento de Requisitos de Negócios

## 1. Introdução

### 1.1 Propósito
O propósito deste documento é definir os requisitos para o Sistema de Gerenciamento de Delivery de Comida. Este sistema permitirá que os operadores gerenciem restaurantes parceiros, entregadores, pedidos e acompanhem o status das entregas.

### 1.2 Escopo
O Sistema de Gerenciamento de Delivery de Comida será uma aplicação de linha de comando desenvolvida em Java e utilizará PostgreSQL para persistência de dados. O sistema será desenvolvido por grupos de alunos como parte de um trabalho interdisciplinar para as matérias de Programação Orientada a Objetos em Java e Banco de Dados com PostgreSQL.

## 2. Descrição Geral

### 2.1 Perspectiva do Produto
O sistema será usado em um ambiente de plataforma de delivery para gerenciar todo o fluxo de pedidos, desde a criação até a entrega. Ele permitirá que os operadores controlem restaurantes, produtos, clientes, entregadores e o ciclo completo de vida de um pedido.

### 2.2 Funções do Produto
O sistema terá as seguintes funções:

- Gerenciamento de Restaurantes: Os operadores poderão inserir, atualizar e excluir informações dos restaurantes parceiros.
- Gerenciamento de Produtos: Os operadores poderão inserir, atualizar e excluir produtos do cardápio de cada restaurante.
- Gerenciamento de Clientes: Os operadores poderão inserir, atualizar e excluir informações dos clientes.
- Gerenciamento de Entregadores: Os operadores poderão inserir, atualizar e excluir informações dos entregadores, além de visualizar sua disponibilidade.
- Gerenciamento de Pedidos: Os operadores poderão inserir novos pedidos, atualizar o status dos pedidos, atribuir entregadores e excluir pedidos.
- Cálculo de Valores: O sistema calculará automaticamente o valor total do pedido aplicando descontos progressivos e incluindo taxa de entrega.
- Relatórios: O sistema permitirá visualizar o total de pedidos e valor total vendido por restaurante.

### 2.3 Usuários e Stakeholders
Os principais usuários do sistema serão os operadores da plataforma de delivery. Os stakeholders incluem os proprietários da plataforma, restaurantes parceiros, entregadores e clientes.

## 3. Requisitos Específicos

### 3.1 Requisitos de Interface de Usuário
O sistema será uma aplicação de linha de comando. Os usuários interagirão com o sistema através de prompts de texto e menus organizados por funcionalidade.

### 3.2 Requisitos de Banco de Dados
O sistema utilizará PostgreSQL para persistência de dados. O banco de dados incluirá tabelas para restaurantes, produtos, clientes, entregadores, pedidos e uma tabela intermediária para a relação muitos-para-muitos entre pedidos e produtos (`ItemPedido`).
