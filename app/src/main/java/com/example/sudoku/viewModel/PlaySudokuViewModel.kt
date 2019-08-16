package com.example.sudoku.viewModel

import android.arch.lifecycle.ViewModel

import com.example.sudoku.game.SudokuGame

class PlaySudokuViewModel:ViewModel(){

    val sudokuGame = SudokuGame()
}