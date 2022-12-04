package day2

import utils.DayInputs

fun main() {
    val inputs = DayInputs(2)
    println("# TEST #")
    inputs.testInput.getTotalScores()
    println("# PUZZLE #")
    inputs.input.getTotalScores()
}

data class Round(
    val myScore: Score? = null,
    val opponentScore: Score? = null,
    val result: Results? = null,
    val globalScore: Int? = null,
)

enum class Score(val score: Int) { ROCK(1), PAPER(2), SCISSORS(3) }

enum class Results(val score: Int) { LOOSE(0), EVEN(3), WIN(6) }

val possibleResultsMap: List<Round> = listOf(
    Round(Score.ROCK, Score.PAPER, Results.LOOSE, Score.ROCK.score + Results.LOOSE.score),
    Round(Score.PAPER, Score.SCISSORS, Results.LOOSE, Score.PAPER.score + Results.LOOSE.score),
    Round(Score.SCISSORS, Score.ROCK, Results.LOOSE, Score.SCISSORS.score + Results.LOOSE.score),
    Round(Score.ROCK, Score.ROCK, Results.EVEN, Score.ROCK.score + Results.EVEN.score),
    Round(Score.PAPER, Score.PAPER, Results.EVEN, Score.PAPER.score + Results.EVEN.score),
    Round(Score.SCISSORS, Score.SCISSORS, Results.EVEN, Score.SCISSORS.score + Results.EVEN.score),
    Round(Score.ROCK, Score.SCISSORS, Results.WIN, Score.ROCK.score + Results.WIN.score),
    Round(Score.PAPER, Score.ROCK, Results.WIN, Score.PAPER.score + Results.WIN.score),
    Round(Score.SCISSORS, Score.PAPER, Results.WIN, Score.SCISSORS.score + Results.WIN.score)
)

fun String.asHandShape(): Score = when {
    equals("A") || equals("X") -> Score.ROCK
    equals("B") || equals("Y") -> Score.PAPER
    equals("C") || equals("Z") -> Score.SCISSORS
    else -> error("Unknown input hand shape")
}

fun String.asResult(opponentScore: Score): Results = when {
    equals("X") -> Results.LOOSE;
    equals("Y") -> Results.EVEN
    equals("Z") -> Results.WIN
    else -> error("Unknown input result")
}

fun List<String>.getTotalScores() {
    val firstResult = this.sumOf { entry ->
        possibleResultsMap.find {
            it.opponentScore == entry.split(" ").first().asHandShape() &&
                    it.myScore == entry.split(" ").last().asHandShape()
        }?.globalScore ?: 0
    }
    println("First guide result is: $firstResult")
    val secondResult = this.sumOf { entry ->
        possibleResultsMap.find {
            it.opponentScore == entry.split(" ").first().asHandShape() &&
                    it.result == entry.split(" ").last().asResult(it.opponentScore)
        }?.globalScore ?: 0
    }
    println("Second guide result is: $secondResult")
}

