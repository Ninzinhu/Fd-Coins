#!/bin/bash
# Script de build para FulldevCoins
# Use: ./build.sh (Linux/Mac) ou ./build.bat (Windows)

# Limpar builds anteriores
echo "Limpando builds anteriores..."
mvn clean

# Compilar o projeto
echo "Compilando o projeto..."
mvn compile

# Empacotar o JAR
echo "Empacotar JAR..."
mvn package

# Verificar se foi bem-sucedido
if [ -f "target/Fulldev-coins-1.0-SNAPSHOT.jar" ]; then
    echo "✅ Build realizado com sucesso!"
    echo "📦 JAR gerado: target/Fulldev-coins-1.0-SNAPSHOT.jar"
    echo ""
    echo "🚀 Para instalar, copie o JAR para plugins/ do seu servidor Spigot"
else
    echo "❌ Erro na compilação!"
    exit 1
fi
