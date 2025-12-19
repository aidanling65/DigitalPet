package com.example.tamagotchi.sudoku.domain

import kotlin.random.Random

class SudokuGenerator {private val N = 9
    private val SRN = 3
    private var mat: Array<Array<Int>> = Array(N) { Array(N) { 0 } }

    fun generate(missingDigits: Int): Pair<Array<Array<Int>>, Array<Array<Boolean>>> {
        mat = Array(N) { Array(N) { 0 } }

        fillDiagonal()

        fillRemaining(0, SRN)

        val solvedGrid = Array(N) { i -> mat[i].clone() }

        removeKDigits(missingDigits)

        val startingGrid = Array(N) { r ->
            Array(N) { c ->
                mat[r][c] != 0
            }
        }
        return Pair(solvedGrid, startingGrid)
    }

    private fun fillDiagonal() {
        for (i in 0 until N step SRN) {
            fillBox(i, i)
        }
    }

    private fun fillBox(row: Int, col: Int) {
        var num: Int
        for (i in 0 until SRN) {
            for (j in 0 until SRN) {
                do {
                    num = Random.nextInt(1, N + 1)
                } while (!isSafeInBox(row, col, num))
                mat[row + i][col + j] = num
            }
        }
    }

    private fun isSafeInBox(rowStart: Int, colStart: Int, num: Int): Boolean {
        for (i in 0 until SRN) {
            for (j in 0 until SRN) {
                if (mat[rowStart + i][colStart + j] == num) return false
            }
        }
        return true
    }

    private fun checkIfSafe(i: Int, j: Int, num: Int): Boolean {
        return (unUsedInRow(i, num) &&
                unUsedInCol(j, num) &&
                unUsedInBox(i - i % SRN, j - j % SRN, num))
    }

    private fun unUsedInRow(i: Int, num: Int): Boolean {
        for (j in 0 until N) {
            if (mat[i][j] == num) return false
        }
        return true
    }

    private fun unUsedInCol(j: Int, num: Int): Boolean {
        for (i in 0 until N) {
            if (mat[i][j] == num) return false
        }
        return true
    }

    private fun unUsedInBox(rowStart: Int, colStart: Int, num: Int): Boolean {
        for (i in 0 until SRN) {
            for (j in 0 until SRN) {
                if (mat[rowStart + i][colStart + j] == num) return false
            }
        }
        return true
    }

    private fun fillRemaining(i: Int, j: Int): Boolean {
        var i = i
        var j = j
        if (j >= N && i < N - 1) {
            i += 1
            j = 0
        }
        if (i >= N && j >= N) return true
        if (i < SRN) {
            if (j < SRN) j = SRN
        } else if (i < N - SRN) {
            if (j == (i / SRN) * SRN) j += SRN
        } else {
            if (j == N - SRN) {
                i += 1
                j = 0
                if (i >= N) return true
            }
        }

        for (num in 1..N) {
            if (checkIfSafe(i, j, num)) {
                mat[i][j] = num
                if (fillRemaining(i, j + 1)) return true
                mat[i][j] = 0
            }
        }
        return false
    }

    private fun removeKDigits(k: Int) {
        var count = k
        while (count != 0) {
            val cellId = Random.nextInt(0, N * N)
            val i = cellId / N
            val j = cellId % N
            if (mat[i][j] != 0) {
                count--
                mat[i][j] = 0
            }
        }
    }
}
