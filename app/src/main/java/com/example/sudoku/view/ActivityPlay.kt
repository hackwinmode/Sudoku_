package com.example.sudoku.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import android.support.v7.app.AlertDialog
import com.example.sudoku.R
import com.example.sudoku.game.Cell

import com.example.sudoku.view.custom.SudokuBoardView
import com.example.sudoku.viewModel.PlaySudokuViewModel
import kotlinx.android.synthetic.main.activity_play2.*

class ActivityPlay : AppCompatActivity(), SudokuBoardView.OnTouchListener {

    private lateinit var viewModel: PlaySudokuViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play2)
        BoardView.registerListener(this)

            viewModel = ViewModelProviders.of(this).get(PlaySudokuViewModel::class.java)

            viewModel.sudokuGame.selectedCellLiveData.observe(this, Observer {updateSelectedCellUI(it)})
            viewModel.sudokuGame.cellsLiveData.observe(this, Observer { updateCells(it) })

        val buttons = listOf(btnClear,button_one,button_two,button_three,button_four,
            button_five,button_six,button_seven,button_eight,button_nine)

        buttons.forEachIndexed { index, button ->
            button.setOnClickListener {
                viewModel.sudokuGame.handleInput(index)
            }
        }

        btnSolve.setOnClickListener {
            if(viewModel.sudokuGame.endGame){
                val dialogBuilder = AlertDialog.Builder(this)

                dialogBuilder.setMessage("You've solved the sudoku quizze!")
                    .setCancelable(false)
                    .setPositiveButton("OK",({_, _-> finish()}))

                val alert = dialogBuilder.create()
                alert.setTitle("Congratulation!")
                alert.show()
            }
        }

        btnBack.setOnClickListener {

            super.onBackPressed()
        }
    }

    private fun updateCells(cells: List<Cell>?) = cells?.let{
        BoardView.updateCells(cells)
    }

    private fun updateSelectedCellUI(cell: Pair<Int, Int>?) = cell?.let {
        BoardView.updateSelectedCellUI(cell.first, cell.second)
    }

    override fun onCellTouched(row: Int, column: Int){
        viewModel.sudokuGame.updateSelectedCell(row, column)
    }
}
