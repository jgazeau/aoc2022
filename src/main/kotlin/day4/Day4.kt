package day4

import utils.DayInputs

fun main() {
    val inputs = DayInputs(4)
    println("# TEST #")
    inputs.testInput.findOverlappingPairs()
    println("# PUZZLE #")
    inputs.input.findOverlappingPairs()
}

data class Assignments(
    var firstElf: IntRange,
    var secondElf: IntRange,
)

fun String.toAssignment(): IntRange =
    this.split("-").let { range -> range.first().toInt().rangeTo(range.last().toInt()) }

fun String.toAssignments(): Assignments = this.split(",").let {
    Assignments(it.first().toAssignment(), it.last().toAssignment())
}

fun List<String>.findOverlappingPairs() {
    val firstResult = this.count {
        it.toAssignments()
            .let { assignments ->
                assignments.secondElf.all { section -> section in assignments.firstElf }
                    .or(assignments.firstElf.all { section -> section in assignments.secondElf })
            }
    }
    println("First result is: $firstResult")
    val secondResult = this.count {
        it.toAssignments()
            .let { assignments ->
                assignments.secondElf.any { section -> section in assignments.firstElf }
                    .or(assignments.firstElf.any { section -> section in assignments.secondElf })
            }
    }
    println("Second result is: $secondResult")
}
