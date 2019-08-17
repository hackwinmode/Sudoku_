package com.example.sudoku.game

import android.arch.lifecycle.MutableLiveData
import java.util.*
import kotlin.random.Random

 class SudokuGame {
    var endGame = false
    var selectedCellLiveData = MutableLiveData<Pair<Int,Int>>()
    var cellsLiveData = MutableLiveData<List<Cell>>()


    private var selectedRow = -1
    private var selectedCol = -1

    private val board: Board

    private val index = (1..10).random()
    private val quizze = SudokuData.getQuizze(index).split(",").map{it.toInt()}
    private val solutions  = SudokuData.getSolution(index).split(",").map{it.toInt()}


    init {

        var cellsQuizze = List(9 * 9) { i -> Cell(i / 9, i % 9, quizze[i], checkStart(quizze[i]), false) }
        val cellsSolution = List(9 * 9) { i -> Cell(i / 9, i % 9, solutions[i], false, false) }
        board = Board(9, cellsQuizze, cellsSolution)

        selectedCellLiveData.postValue(Pair(selectedRow, selectedCol))
        cellsLiveData.postValue(board.cells)
    }

    fun handleInput(number: Int){
        if(selectedRow == -1 || selectedCol == -1) return
        if(board.getCell(selectedRow,selectedCol).isCorrect) return
        if(board.getCell(selectedRow,selectedCol).isStarting) return

        board.getCell(selectedRow, selectedCol).value = number

        board.getCell(selectedRow,selectedCol).isCorrect =
            checkCorrectCell(board.getCell(selectedRow,selectedCol).value,
                board.solutions[selectedRow*9 + selectedCol].value)

        endGame = checkEndgame(board.cells, board.solutions)

        cellsLiveData.postValue(board.cells)
    }

    fun updateSelectedCell(row: Int, column: Int){
        selectedRow = row
        selectedCol = column
        selectedCellLiveData.postValue(Pair(row,column))
    }

    private fun checkCorrectCell(cellQuizze: Int, cellSolution: Int): Boolean {
         return (cellQuizze == cellSolution)
    }

    private fun checkStart(value: Int):Boolean {
        if (value == 0) return false
        return true
    }

    private fun checkEndgame(cellsQuizze: List<Cell>, cellSolutions: List<Cell>):Boolean{
        for(i in 0 until 81)
                if (cellsQuizze[i].value != cellSolutions[i].value)
                    return false
        return true
    }

}