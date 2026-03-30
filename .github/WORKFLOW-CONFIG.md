# 🔧 Configuração dos Workflows CI/CD

## Variáveis de Ambiente

### Java
- **Versão:** 21 (LTS)
- **Distribuição:** Temurin
- **Cache:** Maven (habilitado)

### Maven
- **Comando build:** `mvn clean package -DskipTests`
- **Repo:** https://hub.spigotmc.org/nexus/content/repositories/snapshots/

### Versionamento
- **Padrão:** Semântico (MAJOR.MINOR.PATCH)
- **Regra develop:** Incrementa PATCH
- **Regra main:** Incrementa MINOR

---

## Triggers dos Workflows

| Workflow | Branches | Quando Roda |
|----------|----------|-----------|
| Build and Release | main, develop | Push, PR |
| Tests | main, develop | Push, PR |
| Security | main, develop | Push, PR, Weekly |
| Documentation | main | Push, PR |

---

## Outputs de Cada Workflow

### Build
- ✅ JAR em `target/Fulldev-coins-1.0-SNAPSHOT.jar`
- ✅ Tag git criada
- ✅ Release no GitHub
- ✅ Artefato disponível por 30 dias

### Tests
- ✅ Relatório JUnit
- ✅ Cobertura de testes
- ✅ Resumo no Step Summary

### Security
- ✅ Análise de vulnerabilidades
- ✅ OWASP report
- ✅ Artefato de segurança

### Documentation
- ✅ JavaDoc gerado
- ✅ Validação de Markdown
- ✅ Verificação de arquivos

---

## Requisitos para Sucesso

### Código
- [ ] Java 21+ sintaxe válida
- [ ] Maven compila sem erros
- [ ] Sem dependências vulneráveis

### Repositório
- [ ] Branches `main` e `develop` existem
- [ ] Permissões de `write` para contents
- [ ] GitHub Token disponível

### Documentação
- [ ] README.md existe
- [ ] Markdown válido
- [ ] Links funcionam

---

## Como Customizar

### Mudar versão do Java
```yaml
# .github/workflows/build.yml
- uses: actions/setup-java@v4
  with:
    java-version: '21'  # Mude aqui
```

### Adicionar novo trigger
```yaml
on:
  push:
    branches: [ main, develop ]
    paths:           # Adicione isso
      - 'src/**'
      - 'pom.xml'
```

### Adicionar notificação
```yaml
- name: Notify on failure
  if: failure()
  uses: seu-action/notify@v1
  with:
    token: ${{ secrets.SLACK_TOKEN }}
```

---

## Performance

### Tempos Típicos
- Build: 2-3 minutos
- Tests: 1-2 minutos
- Security: 3-5 minutos
- Total: 6-10 minutos

### Otimizações
- ✅ Maven cache ativado
- ✅ DependencyCheck paralelo
- ✅ Uploads apenas de mudanças

---

## Custos (GitHub Free)

- ✅ Unlimited builds
- ✅ 2000 minutos/mês para ações
- ✅ Armazenamento: 500MB artefatos
- ✅ Sem custos adicionais

---

## Checklist de Deployment

Antes de fazer push:
- [ ] Código compila localmente
- [ ] Testes passam
- [ ] Sem warnings Maven
- [ ] Documentação atualizada
- [ ] Commit message clara

---

Última atualização: 2026-03-29
