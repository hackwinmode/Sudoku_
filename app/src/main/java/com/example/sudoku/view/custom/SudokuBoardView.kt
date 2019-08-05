package com.example.sudoku.view.custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.example.sudoku.game.Cell

class SudokuBoardView(context: Context, attributeSet: AttributeSet): View(context, attributeSet){

    private var sqrtSize = 3
    private var size = 9

    private var cellSizePixels = 0F

    private var selectedRow = -1
    private var selectedCol = -1

    private var cells: List<Cell> ?= null

    private var listener: SudokuBoardView.OnTouchListener ?= null

    private val thickLinePaint = Paint().apply {
        style = Paint.Style.STROKE
        color = Color.BLUE
        strokeWidth = 4F
    }

    private val thinLinePaint = Paint().apply {
        style = Paint.Style.STROKE
        color = Color.BLACK
        strokeWidth = 2F
    }

    private val selectedCellPaint = Paint().apply {
        style = Paint.Style.FILL_AND_STROKE
        color = Color.parseColor("#FA8072")
    }

    private val conflictingCellPaint = Paint().apply {
        style = Paint.Style.FILL_AND_STROKE
        color = Color.parseColor("#FFDAB9")
    }

    private val textInputPaint = Paint().apply{
        style = Paint.Style.FILL_AND_STROKE
        color = Color.BLUE
        textSize = 32F
    }

    private val textPaint = Paint().apply {
        style = Paint.Style.FILL_AND_STROKE
        color = Color.BLACK
        textSize = 32F
    }


    private fun fillCells(canvas: Canvas){

        cells?.forEach{
            val row = it.row
            val column = it.column

            if (row == selectedRow && column == selectedCol){
                fillCell(canvas, row, column, selectedCellPaint)
            }else if (row == selectedRow || column == selectedCol){
                fillCell(canvas, row, column, conflictingCellPaint)
            }else if (row/ sqrtSize == selectedRow/sqrtSize && column/sqrtSize == selectedCol/sqrtSize){
                fillCell(canvas, row, column, conflictingCellPaint)
            }
        }
    }

    private fun fillCell(canvas: Canvas, row: Int, column: Int, paint: Paint ){
        canvas.drawRect(
            column* cellSizePixels,
            row* cellSizePixels,
            (column+1) * cellSizePixels,
            (row+1) * cellSizePixels,
            paint
        )
    }

    private fun drawLines(canvas: Canvas){
        canvas.drawRect(0F, 0F, width.toFloat(), height.toFloat(), thickLinePaint)

        for (i in 1 until size ) {
            val paintToUse = when(i % sqrtSize){
                0 -> thickLinePaint
                else -> thinLinePaint
            }

            canvas.drawLine(
                i*cellSizePixels,
                0F,
                i*cellSizePixels,
                height.toFloat(),
                paintToUse)

            canvas.drawLine(
                0F,
                i*cellSizePixels,
                width.toFloat(),
                i*cellSizePixels,
                paintToUse)
        }
    }

    private fun drawText(canvas: Canvas){
        cells?.forEach {
            if (it.value > 0) {
                val row = it.row
                val column = it.column

                val valueString = it.value.toString()
                val textBounds = Rect()

                textPaint.getTextBounds(valueString, 0, valueString.length, textBounds)

                val textWidth = textPaint.measureText(valueString)
                val textHeight = textBounds.height()
                if(it.isStarting) {
                    canvas.drawText(
                        valueString,
                        (column * cellSizePixels) + cellSizePixels / 2 - textWidth / 2,
                        (row * cellSizePixels) + cellSizePixels / 2 - textHeight / 2,
                        textPaint
                    )
                }else{
                    canvas.drawText(
                        valueString,
                        (column * cellSizePixels) + cellSizePixels / 2 - textWidth / 2,
                        (row * cellSizePixels) + cellSizePixels / 2 - textHeight / 2,
                        textInputPaint
                    )
                }
            }
        }
    }
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val sizePixels = Math.min(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(sizePixels, sizePixels)

    }

    override fun onDraw(canvas: Canvas) {
        cellSizePixels = (width/size).toFloat()
        fillCells(canvas)
        drawLines(canvas)
        drawText(canvas)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return when(event!!.action){
            MotionEvent.ACTION_DOWN -> {
                handleTouchEvent(event.x, event.y)
                true
            }
            else -> false
        }
    }

    private fun handleTouchEvent(x: Float, y:Float){
        val possibleSelectedRow = (y/cellSizePixels).toInt()
        val possibleSelectedCol = (x/cellSizePixels).toInt()
        listener?.onCellTouched(possibleSelectedRow,possibleSelectedCol)
    }


    fun updateSelectedCellUI(row: Int, column:Int){
        selectedRow = row
        selectedCol = column
        invalidate()
    }

    fun registerListener(listener: SudokuBoardView.OnTouchListener) {
        this.listener = listener
    }

    fun updateCells(cells: List<Cell>){
        this.cells = cells
        invalidate()
    }

    interface OnTouchListener {
        fun onCellTouched(row: Int, column: Int){}
    }
}