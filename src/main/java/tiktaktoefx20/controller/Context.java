package tiktaktoefx20.controller;


import tiktaktoefx20.strategies.*;

/**
 * Класс, инициализирующий интерфейс через this и содержащий метод для выполнения хода. Initializes
 * the interface via this and contains a method for making a move.
 */

public class Context {

  private final Strategic strategic;

  public Context(Strategic strategic) {
    this.strategic = strategic;
  }

  public int[] makeMove(char[][] gameField, String selectedLevel) {
    return strategic.makeMove(gameField, selectedLevel);
  }

}