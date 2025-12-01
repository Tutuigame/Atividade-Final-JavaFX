
# FinanceFX — JavaFX + SQLite + JUnit

Aplicativo de **finanças pessoais** em JavaFX, seguindo MVC, com 3+ telas, persistência em SQLite, **gráfico interativo** e **testes automatizados**.

## ✨ Features
- Login simples (usuário padrão `admin`/`admin`)
- Dashboard com **gráfico de pizza** por categoria (mês atual)
- Tela de **Despesas** (listar/adicionar/editar/remover)
- Persistência local **SQLite** (`finance.db`)
- 2 testes com **JUnit 5**

## 🧱 Stack
- Java 21, Gradle
- JavaFX (FXML, Scene Builder)
- SQLite (JDBC)
- JUnit 5

## ▶️ Como rodar (IntelliJ IDEA)
1. `File > New > Project from Existing Sources...` e selecione esta pasta (Gradle).
2. Aguarde baixar dependências.
3. (Opcional) Abra os FXML no **Scene Builder** para ajustes visuais.
4. Rode a classe **`MainLauncher`**.
5. Login: `admin` / `admin`.

## 🧪 Rodar testes
- No IntelliJ: clique com direito no diretório `test` > **Run 'Tests in ...'**.

## 📁 Estrutura
```
financefx/
  build.gradle
  settings.gradle
  src/main/java/com/example/financefx/... (App, controllers, dao, model)
  src/main/resources/com/example/financefx/view/... (FXML)
  src/main/resources/com/example/financefx/css/styles.css
  src/test/java/com/example/financefx/... (JUnit)
```

## 📌 Requisitos atendidos
- MVC, pacotes organizados
- Múltiplas telas (Login, Dashboard, Despesas)
- Navegação via `FXMLLoader` (`App.setRoot(...)`)
- Persistência (SQLite)
- Gráfico interativo (`PieChart`)
- 2 testes (JUnit)
- README/documentação
- Estilo/CSS

## 🔒 Observação de segurança
Senha sem hash apenas para fins didáticos.
