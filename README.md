# 🚚 Sistema de Delivery

Um sistema completo de gerenciamento de delivery em Java, com implementação de interfaces, CRUD funcional, e regra de negócio complexa com cálculo de desconto progressivo e taxa de entrega variável.

## ✅ Funcionalidades Implementadas

### 1. **Interfaces com Contratos de Comportamento**

#### 📋 `Auditavel`
- Registra logs de ações em pedidos
- Mantém histórico completo com timestamps
- Método `registrarLog(String acao)`: Registra uma ação
- Método `obterHistorico()`: Retorna histórico formatado

**Implementada por:** `Pedido`

#### 🧮 `Calculavel`
- Define contrato para cálculos de valores
- Método `calcularSubtotal()`: Calcula subtotal dos itens
- Método `calcularDesconto()`: Calcula desconto aplicável
- Método `calcularValorTotal()`: Calcula valor final

**Implementada por:** `Pedido`

#### 📊 `Relatorio`
- Define contrato para geração de relatórios
- Método `gerarRelatorio()`: Gera relatório formatado
- Método `exportarDados(String formato)`: Exporta em CSV, JSON ou XML

**Implementada por:** `Restaurante`

---

### 2. **CRUD Completo (100% Funcional)**

#### Clientes
- ✅ **Create:** Cadastrar novo cliente
- ✅ **Read:** Listar todos ou buscar por ID
- ✅ **Update:** Atualizar dados de cliente
- ✅ **Delete:** Remover cliente

#### Restaurantes
- ✅ **Create:** Cadastrar novo restaurante
- ✅ **Read:** Listar todos ou buscar por ID
- ✅ **Update:** Atualizar dados de restaurante
- ✅ **Delete:** Remover restaurante
- ✅ **Relatórios:** Gerar e exportar dados em múltiplos formatos

#### Entregadores
- ✅ **Create:** Cadastrar novo entregador
- ✅ **Read:** Listar todos ou buscar por ID
- ✅ **Update:** Atualizar dados de entregador
- ✅ **Delete:** Remover entregador

#### Pedidos
- ✅ **Create:** Criar pedido com validações
- ✅ **Read:** Listar pedidos ou buscar por ID
- ✅ **Update:** Atualizar status e atribuir entregador
- ✅ **Delete:** Remover pedido

---

### 3. **Regra de Negócio Complexa: Cálculo de Valor Final**

#### 📊 Desconto Progressivo (baseado no subtotal):
```
Subtotal > R$300    → 15% de desconto
Subtotal > R$200    → 10% de desconto
Subtotal > R$100    → 5% de desconto
Subtotal ≤ R$100    → Sem desconto
```

#### 🚚 Taxa de Entrega Variável (baseada no valor com desconto):
```
Valor Final ≥ R$150 → Taxa R$5.00
Valor Final ≥ R$100 → Taxa R$7.00
Valor Final < R$100  → Taxa R$10.00
```

#### 💰 Exemplo de Cálculo:
```
Subtotal: R$250.00
Desconto (10%): R$25.00
Valor com desconto: R$225.00 ✓ Qualifica para taxa reduzida
Taxa de Entrega: R$5.00
VALOR FINAL: R$230.00
```

---

### 4. **Banco de Dados Integrado (PostgreSQL)**

#### Tabelas Criadas:
- `cliente` - Dados de clientes
- `restaurante` - Dados de restaurantes
- `entregador` - Dados de entregadores
- `pedido` - Pedidos com referências às entidades
- `item_pedido` - Itens de cada pedido
- `log_auditoria` - Histórico de ações (para auditoria)

#### Integração JDBC:
- Conexão via `ConexaoBD.java`
- Preparado para PostgreSQL localhost:5432
- Suporta transactions e cascata de deleção

---

## 📁 Estrutura do Projeto

```
sistema-delivery/
├── src/
│   ├── br/com/[seuprojeto]/
│   │   ├── model/              (Entidades)
│   │   │   ├── Cliente.java
│   │   │   ├── Restaurante.java
│   │   │   ├── Entregador.java
│   │   │   ├── Pedido.java (implements Auditavel, Calculavel)
│   │   │   ├── ItemPedido.java
│   │   │   ├── Usuario.java
│   │   │   └── SistemaMain.java
│   │   │
│   │   ├── dao/                (Data Access Object - CRUD)
│   │   │   ├── ClienteDAO.java
│   │   │   ├── RestauranteDAO.java
│   │   │   ├── EntregadorDAO.java
│   │   │   └── PedidoDAO.java
│   │   │
│   │   ├── service/            (Regras de Negócio)
│   │   │   ├── PedidoService.java
│   │   │   └── ClienteService.java
│   │   │
│   │   └── util/               (Utilitários e Interfaces)
│   │       ├── ConexaoBD.java
│   │       ├── Auditavel.java (interface)
│   │       ├── Calculavel.java (interface)
│   │       └── Relatorio.java (interface)
│   │
│   └── schema.sql              (Criação das tabelas)
│
└── README.md                   (Este arquivo)
```

---

## 🚀 Como Executar

### Pré-requisitos:
- Java 11+
- PostgreSQL instalado e rodando
- Driver PostgreSQL JDBC

### Passos:
1. **Criar banco de dados:**
   ```sql
   createdb delivery
   psql -U postgres -d delivery -f schema.sql
   ```

2. **Compilar:**
   ```bash
   javac -d bin src/model/*.java src/dao/*.java src/service/*.java src/util/*.java
   ```

3. **Executar:**
   ```bash
   java -cp bin model.SistemaMain
   ```

---

## 🎯 Exemplo de Uso

### Criar um Pedido:
```
1. Selecionar: "14 - Criar Pedido"
2. Inserir ID do Cliente: 1
3. Inserir ID do Restaurante: 1
4. Adicionar itens:
   - Pizza Grande: 1x R$50.00
   - Refrigerante 2L: 2x R$10.00
   - Sobremesa: 1x R$25.00
5. Sistema calcula automaticamente o valor final com desconto e taxa
```

### Visualizar Relatório:
```
1. Selecionar: "16 - Ver Detalhes do Pedido"
2. Inserir ID: 1
3. Sistema exibe:
   - Itens do pedido
   - Cálculos de desconto e taxa
   - Histórico de auditoria completo
```

---

## 📝 Anotações Importantes

- ✅ Todas as classes implementam `@Override` nos métodos de interface
- ✅ Validações robustas em todos os DAOs e Services
- ✅ Histórico de auditoria registrado automaticamente
- ✅ Transições de status validadas
- ✅ Suporte a múltiplos formatos de exportação (CSV, JSON, XML)
- ✅ Código organizado e documentado

---

## 👨‍💻 Autor
Sistema desenvolvido como projeto acadêmico de Sistema de Delivery em Java.

**Data:** Maio de 2026
 de Comida - FOODFLY

## Tema
Gerencia restaurantes, entregadores, pedidos e acompanhe entregas em tempo real.

## Integrantes do grupo
- Ingrid Ferreira de Sousa - RGM: 45999279
- Jefferson Aparecido Faria de Alencar - RGM: 46100440
- Guilherme Leite Tavares - RGM: 45871884
- Stephanie Vitoria Bessa dos Santos - RGM: 45619930
- Matheus Caetano Rocha - RGM: 45998400

## Objetivo do sistema
O objetivo do sistema é oferecer uma plataforma de gerenciamento de delivery que integra restaurantes, entregadores e clientes em um único ambiente. O sistema deve permitir a administração de cardápios, o cadastro de entregadores e o acompanhamento em tempo real do status de pedidos.

A solução visa automatizar todo o processo de entrega de comida, desde a criação do pedido até a finalização da entrega. Com isso, espera-se reduzir o tempo de espera dos clientes, melhorar a organização interna dos restaurantes e garantir mais controle para a equipe de delivery.

## Funcionalidades Principais

1. Cadastro de clientes com nome, telefone e endereço.
2. Cadastro de restaurantes com nome, endereço e avaliação.
3. Cadastro de entregadores com controle de status (disponível, em entrega, indisponível).
4. Criação de pedidos com múltiplos itens, incluindo cálculo automático do valor total.
5. Atribuição automática de entregadores disponíveis aos pedidos, com atualização de status.


## Estrutura de classes planejada
- `Restaurante`
  - `id`
  - `nome`
  - `endereco`
  - `avaliacao`

- `Entregador`
  - `id`
  - `nome`
  - `telefone`
  - `statusDisponibilidade`
  - `localizacaoAtual`

- `Cliente`
  - `id`
  - `nome`
  - `telefone`
  - `endereco`

- `ItemPedido`
  - `nome`
  - `quantidade`
  - `precoUnitario`

- `Pedido`
  - `id`
  - `cliente`
  - `restaurante`
  - `itens`
  - `valorTotal`
  - `status`
  - `entregador`

- `Main`
  - exemplo de execução com cadastro inicial e cálculo de valores.

- `SistemaDelivery`
  - métodos de cadastro, listagem, atribuição de entregador e atualização de status (planejado).
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
