# Othello

## 概要
CLIで遊べるオセロです。
This is Othello that can be played with CLI.

## 使用言語
Javaです。バージョンは以下の通りです。
These programs are written in Java. The versions are as follows:

openjdk version "17.0.6" 2023-01-17
OpenJDK Runtime Environment Temurin-17.0.6+10 (build 17.0.6+10)
OpenJDK 64-Bit Server VM Temurin-17.0.6+10 (build 17.0.6+10, mixed mode)

## 構成例
. (root)
|
|  - bin
|  - src
      |
      | - app
           |
           |  - Board.java
           |  - Stone.java
           |  - Player.java
           |  - Main.java

## 使い方
1. Eclipseで新しいJavaプロジェクトを立ち上げます。
2. src以下にパッケージを作ります(上記ではapp)。
3. 2で作ったパッケージの直下にBoard.Java, Stone.java, Player.java, Main.javaを配置してください。
4. ターミナルでappディレクトリ内に入り、```javac *.java```コマンドでコンパイルします。
5. ```cd ..```コマンドでsrcディレクトリに戻り、```java app/Main```で起動します。
