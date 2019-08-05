package com.example.sudoku.game

import kotlin.random.Random

class Board (val size: Int, val cells: List<Cell>){

    fun getCell(row: Int, column: Int) = cells[row*size + column]

}