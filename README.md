# Swing BorderLayout Demo

Pequeno projeto Maven com uma janela `JFrame` usando `BorderLayout` e botões com **ícones**, **tooltips** e **mnemonics**.

## Requisitos
- Java 17+
- Maven 3.8+

## Como executar
```bash
# compilar e empacotar
mvn clean package

# executar via JAR (manifest já aponta para a main)
java -jar target/swing-borderlayout-demo-1.0.0.jar

# ou rodar diretamente (sem empacotar)
mvn -q exec:java
```

Os ícones de exemplo estão em `src/main/resources/icons/`. No seu projeto real, substitua pelos seus próprios arquivos.

## Estrutura
```
src/
  main/
    java/
      com/example/ui/BorderLayoutDemo.java
    resources/
      icons/
        app.png
        center.png
        east.png
        north.png
        south.png
        west.png
pom.xml
```
