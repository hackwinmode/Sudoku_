package com.example.sudoku.game

import android.arch.lifecycle.MutableLiveData
import java.util.*
import kotlin.random.Random

class SudokuGame {
    var endGame = false
    var selectedCellLiveData = MutableLiveData<Pair<Int,Int>>()
    var cellsLiveData = MutableLiveData<List<Cell>>()

    val boardQuizze = arrayOf("0,0,4,3,0,0,2,0,9" +
            ",0,0,5,0,0,9,0,0,1" +
            ",0,7,0,0,6,0,0,4,3" +
            ",0,0,6,0,0,2,0,8,7" +
            ",1,9,0,0,0,7,4,0,0" +
            ",0,5,0,0,8,3,0,0,0" +
            ",6,0,0,0,0,0,1,0,5" +
            ",0,0,3,5,0,8,6,9,0" +
            ",0,4,2,9,1,0,3,0,0",

                        "0,4,0,1,0,0,0,5,0" +
                        ",1,0,7,0,0,3,9,6,0" +
                        ",5,2,0,0,0,8,0,0,0" +
                        ",0,0,0,0,0,0,0,1,7" +
                        ",0,0,0,9,0,6,8,0,0" +
                        ",8,0,3,0,5,0,6,2,0" +
                        ",0,9,0,0,6,0,5,4,3" +
                        ",6,0,0,0,8,0,7,0,0" +
                        ",2,5,0,0,9,7,1,0,0")

    val boardSolution:Array<String> = arrayOf(
            "8,6,4,3,7,1,2,5,9" +
            ",3,2,5,8,4,9,7,6,1" +
            ",9,7,1,2,6,5,8,4,3" +
            ",4,3,6,1,9,2,5,8,7" +
            ",1,9,8,6,5,7,4,3,2" +
            ",2,5,7,4,8,3,9,1,6" +
            ",6,8,9,7,3,4,1,2,5" +
            ",7,1,3,5,2,8,6,9,4" +
            ",5,4,2,9,1,6,3,7,8",

        "3,4,6,1,7,9,2,5,8" +
                ",1,8,7,5,2,3,9,6,4" +
                ",5,2,9,6,4,8,3,7,1" +
                ",9,6,5,8,3,2,4,1,7" +
                ",4,7,2,9,1,6,8,3,5" +
                ",8,1,3,7,5,4,6,2,9" +
                ",7,9,8,2,6,1,5,4,3" +
                ",6,3,1,4,8,5,7,9,2" +
                ",2,5,4,3,9,7,1,8,6")

    private var selectedRow = -1
    private var selectedCol = -1

    private val board: Board

    private val index = Random.nextInt(0,1)
    private val quizze = boardQuizze[index].split(",").map{it.toInt()}
    private val solutions  = boardSolution[index].split(",").map{it.toInt()}


    init {

        var cellsQuizze = List(9*9){i -> Cell(i/9, i%9, quizze[i], checkStart(quizze[i]), false)}
        val cellsSolution = List(9*9){i->Cell(i/9,i%9, solutions[i], false, false)}
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
        if(cellQuizze == cellSolution)
            return true
        else
            return false
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