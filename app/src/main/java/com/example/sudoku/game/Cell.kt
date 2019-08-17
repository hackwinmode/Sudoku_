package com.example.sudoku.game


//add attribute isStarting to check Cell.

class Cell(val row: Int = 0, val column: Int= 0, var value: Int= 0, var isStarting: Boolean = false, var isCorrect: Boolean = false)