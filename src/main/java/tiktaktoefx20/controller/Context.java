package tiktaktoefx20.controller;


import tiktaktoefx20.strategies.*;

public class Context {

  private final Strategic strategic;

  public Context(Strategic strategic) {
    this.strategic = strategic;
  }

  public int[] makeMove(char[][] gameField, String selectedLevel) {
    return strategic.makeMove(gameField, selectedLevel);
  }

}