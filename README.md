# 💰 FinApp - Aplicativo Financeiro

O **FinApp** é um aplicativo Android desenvolvido em Kotlin com arquitetura **MVVM + Clean Architecture**, 
cujo objetivo é permitir aos usuários consultar o saldo da conta, visualizar o histórico de transações e realizar transferências.

## ✨ Funcionalidades

- ✅ Exibição de saldo atual
- 📄 Listagem de transações com tipo (crédito/débito)
- 🔁 Realização de transferências bancárias
- ⏳ Feedback visual de carregamento (ProgressBar)
- ❌ Tratamento de erros com mensagens claras
- 🎯 Navegação entre telas com **Navigation Component**
- 📱 Suporte completo a Data Binding e ConstraintLayout

## 🧱 Arquitetura

O app segue os princípios de **Clean Architecture** com camadas bem definidas:

```
data
├── mapper (DTO → Domain)
├── model (Modelos de dados DTO`s)
├── remote (MockInterceptor, TransactionApi)
├── repository (Implementações)
di (Injeção de dependência com Koin)
├── modules (Módulos de injeção)
domain
├── model (Modelos de negócio)
├── repository (Interfaces de repositório)
├── usecase (Casos de uso centralizados)
presentation
├── screens (Acitivities, Fragments e Adapters)
├── uiState (UiState para lidar com estados da UI)
├── viewModels (ViewModel)

```

## 🔧 Tecnologias Utilizadas

- 🟦 **Kotlin**
- 🔄 **Coroutines + ViewModelScope**
- 🧪 **JUnit + MockK + Coroutine Test**
- 📦 **Koin** (Injeção de dependência)
- 🌐 **Retrofit** (Requisições HTTP)
- 📊 **LiveData + UiState** (Controle de estado)
- 🧩 **Navigation Component**
- 🧱 **ConstraintLayout**
- 🧪 Testes unitários com mocks de JSON

### Home
- Mostra o saldo atual

### Histórico
- Lista completa das transações do usuário

### Transferência
- Permite enviar um valor para outro usuário

## 📁 Estrutura dos testes

- **Mock de JSON**: arquivos localizados em `assets/` para simular respostas reais
- Testes para: `Repository`, `UseCase`, `ViewModels`

## ▶️ Como rodar o projeto

## Clone o repositório:
   ```bash
   git clone https://github.com/seu-usuario/finapp.git
   ```

## 🧪 Como rodar os testes

Execute:
```bash
./gradlew testDebugUnitTest
```

```
