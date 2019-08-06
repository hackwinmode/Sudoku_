package com.example.sudoku.game


class Board (val size: Int, val cells: List<Cell>, val solutions: List<Cell>){

    fun getCell(row: Int, column: Int) = cells[row*size + column]

}