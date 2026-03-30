# 🔄 GitHub Actions Workflows - FulldevCoins

Documentação completa dos workflows de CI/CD configurados para o projeto FulldevCoins.

## 📋 Workflows Disponíveis

### 1. 🏗️ Build and Release (`build.yml`)

**Trigger:** Push em `main` ou `develop`, Pull Requests

**O que faz:**
- ✅ Faz checkout do código
- ✅ Configura Java 21
- ✅ Compila o projeto com Maven
- ✅ Calcula nova versão semântica
- ✅ Cria tag git automaticamente
- ✅ Gera release no GitHub com o JAR
- ✅ Faz upload do artefato

**Outputs:**
- JAR compilado em `target/Fulldev-coins-1.0-SNAPSHOT.jar`
- Nova tag criada (ex: `v1.2.3`)
- Release no GitHub com download do JAR

**Versionamento:**
- `main`: Incrementa MINOR (1.1.0 → 1.2.0)
- `develop`: Incrementa PATCH (1.2.0 → 1.2.1)

**Arquivos de entrada:**
- `pom.xml`
- `src/main/java/**/*.java`
- `src/main/resources/**`

**Exemplo de Release gerado:**
```
FulldevCoins v1.2.3
- Compilado com Java 21
- JAR disponível para download
- Links para documentação
- Requisitos do sistema
```

---

### 2. 🧪 Tests and Code Quality (`tests.yml`)

**Trigger:** Push em `main` ou `develop`, Pull Requests

**O que faz:**
- ✅ Faz checkout do código
- ✅ Configura Java 21
- ✅ Executa testes Maven (`mvn test`)
- ✅ Gera relatório de testes
- ✅ Relata cobertura de código

**Outputs:**
- Relatório de testes em formato JUnit
- Resumo no GitHub Step Summary

**Como ver os resultados:**
1. Vá para a aba "Actions" no GitHub
2. Clique no workflow "Tests and Code Quality"
3. Clique na execução específica
4. Veja o resumo e logs

---

### 3. 🔒 Security Scan (`security.yml`)

**Trigger:** Push em `main` ou `develop`, Pull Requests, Weekly (domingo 00:00)

**O que faz:**
- ✅ Verifica dependências vulneráveis
- ✅ Faz análise OWASP Dependency Check
- ✅ Gera relatório de segurança
- ✅ Armazena relatório como artefato

**Outputs:**
- Relatório de segurança em JSON
- Lista de dependências
- Vulnerabilidades conhecidas (CVEs)

**Como ver os resultados:**
1. Vá para "Actions" → "Security Scan"
2. Clique na execução
3. Download do artefato "dependency-check-report"

---

### 4. 📚 Documentation (`docs.yml`)

**Trigger:** Push em `main`, Pull Requests para `main`

**O que faz:**
- ✅ Verifica se arquivos de documentação existem
- ✅ Gera JavaDoc da API
- ✅ Valida sintaxe Markdown
- ✅ Faz upload de JavaDoc como artefato

**Outputs:**
- JavaDoc HTML gerado
- Resumo de documentação
- Validação de Markdown

**Arquivos verificados:**
- README.md
- INSTALACAO.md
- CUSTOMIZACAO.md
- FAQ.md
- RESUMO.md

---

## 🚀 Como Usar

### Começar um novo Build
1. Faça commit e push em `develop` ou `main`
2. O workflow é acionado automaticamente
3. Acompanhe em "Actions" no GitHub

### Ver resultados do Build
```
GitHub → Seu Repositório → Actions → Build and Release → Clique na execução
```

### Download do JAR
```
GitHub → Releases → Clique em uma release → Download do JAR
```

### Monitorar Testes
```
GitHub → Actions → Tests and Code Quality → Resultados
```

### Verificar Segurança
```
GitHub → Actions → Security Scan → Artefatos
```

---

## 📊 Versionamento Semântico

O projeto usa versionamento semântico automático:

```
MAJOR.MINOR.PATCH
  ↑      ↑      ↑
  |      |      └─ Incrementa com push em develop (bugfixes)
  |      └─────── Incrementa com push em main (features)
  └───────────── Incrementa manualmente em caso de breaking changes
```

**Exemplos:**
- v0.0.0 → v0.0.1 (push em develop)
- v0.0.1 → v0.1.0 (push em main)
- v0.1.0 → v1.0.0 (release major manual)

---

## 🔑 Permissões Necessárias

Os workflows necessitam destas permissões no repositório:

```yaml
permissions:
  contents: write        # Criar tags e releases
  packages: write        # Publicar pacotes
```

Estas são ativadas por padrão em repositórios públicos.

---

## 📧 Notificações

### Como receber notificações
1. **GitHub Notifications**
   - Settings → Notifications → Email
   
2. **Customizar alertas**
   - Watch → Custom → Selecionar eventos

### Tipos de notificação
- ✅ Build sucesso
- ❌ Build falha
- ⚠️ Alertas de segurança
- 📝 Pull requests

---

## 🐛 Troubleshooting

### Build falha com erro de compilação
1. Verifique os logs do workflow
2. Corrija o erro no código local
3. Faça commit e push novamente

### Não consegue fazer release
1. Verifique permissões do repositório
2. Garanta que `main` ou `develop` está protegido corretamente
3. Confira se `secrets.GITHUB_TOKEN` está disponível

### Tags não estão sendo criadas
1. Verifique se o push é para `main` ou `develop`
2. Garanta que fetch-depth é 0 no checkout
3. Verifique logs do step "Create git tag"

### Testes não estão rodando
1. Verifique se existem testes em `src/test/java/`
2. Confira se tests.yml está habilitado
3. Veja logs detalhados em "Run Maven tests"

---

## 📈 Métricas

### Acompanhar métricas do projeto

**Build Success Rate:**
```
GitHub → Actions → Build and Release → Ver histórico
```

**Test Coverage:**
```
GitHub → Actions → Tests → Artefato "test-results"
```

**Security Issues:**
```
GitHub → Security → Dependabot (se habilitado)
```

---

## 🎯 Próximas Melhorias

- [ ] Integração com SonarQube para análise de código
- [ ] Deploy automático para servidor de teste
- [ ] Notificações no Discord/Slack
- [ ] Análise de cobertura de testes (Jacoco)
- [ ] Geração automática de changelog

---

## 📝 Exemplo de Workflow Completo

```
1. Dev faz commit em develop
   ↓
2. Workflow "Tests and Code Quality" roda
   - Testa código
   - Verifica segurança
   - ✅ Sucesso
   ↓
3. Workflow "Build and Release" roda
   - Compila JAR
   - Calcula v1.2.3
   - Cria tag v1.2.3
   - Cria Release no GitHub
   ↓
4. JAR disponível para download em GitHub Releases
   ↓
5. Usuários baixam e instalam o plugin
```

---

## 💡 Dicas

### Forçar um novo build
```bash
git commit --allow-empty -m "Trigger build"
git push
```

### Ver logs detalhados
1. Actions → Workflow → Execução → Expandir seções
2. Ou clicar em "View raw logs"

### Usar artifacts localmente
1. Actions → Execução → Artefatos
2. Download e use localmente

---

## 📞 Suporte

Para problemas com workflows:
1. Verifique GitHub Actions documentation
2. Confira logs do workflow
3. Revise este documento
4. Procure issue similar no repositório

---

**Workflows configurados em:** 2026-03-29
**Versão do GitHub Actions:** v4
**Status:** ✅ Ativo e funcionando

👉 **Veja mais em:** [GitHub Actions Documentation](https://docs.github.com/en/actions)
