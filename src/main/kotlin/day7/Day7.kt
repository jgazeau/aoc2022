package day7

import day7.NodeType.DIR
import day7.NodeType.FILE
import utils.DayInputs
import java.nio.file.Path
import kotlin.io.path.Path

fun main() {
    val inputs = DayInputs(7)
    println("# TEST #")
    inputs.testInput.process()
    println("# PUZZLE #")
    inputs.input.process()
}

data class TreeNode(
    val path: Path?,
    val type: NodeType = DIR,
    var size: Int = 0,
    val children: MutableList<TreeNode> = mutableListOf(),
) {

    fun search(path: Path): TreeNode? {
        return takeIf { it.path == path } ?: children.find { it.path == path }
    }

    fun propagateFileSize(
        currentPath: Path,
        fileSize: Int,
    ) {
        currentPath.fold(Path("/")) { acc, path ->
            this.search(acc)?.let { it.size += fileSize }
            acc.resolve(path)
        }
    }
}

enum class NodeType {
    DIR,
    FILE,
}

fun TreeNode.sumDir(threshold: Int): Int {
    val currentDirSize = takeIf { (size <= threshold) && type == DIR }?.let { size } ?: 0
    return currentDirSize + children.sumOf { it.sumDir(threshold) }
}

fun TreeNode.allDir(): List<TreeNode> = children.filter { it.type == DIR } + children.map { it.allDir() }.flatten()

fun List<String>.process() {
    var currentPath = Path("/")
    val treeNode = TreeNode(currentPath)
    for (i in this.indices) {
        this[i].split(" ")
            .let { cmd ->
                cmd.takeIf { it.first() == "$" }?.drop(1)?.processCommand(currentPath, treeNode)
                    ?: cmd.processNode(currentPath, treeNode)
            }.let { currentPath = it }
    }
    println("First result is: ${treeNode.sumDir(100000)}")
    val unusedSpace = 70000000 - treeNode.size
    println(
        "Second result is: ${
            treeNode.allDir().filter { it.size + unusedSpace >= 30000000 }.minOf { it.size }
        }"
    )
}

fun List<String>.processCommand(
    currentPath: Path,
    treeNode: TreeNode?,
): Path {
    return when (first()) {
        "cd" -> drop(1).processCd(currentPath, treeNode)
        "ls" -> currentPath
        else -> error("Unknown command")
    }
}

fun List<String>.processCd(
    currentPath: Path,
    treeNode: TreeNode?,
): Path {
    val newPath = currentPath.resolve(first()).normalize()
    treeNode?.search(newPath) ?: treeNode?.children?.add(TreeNode(newPath))
    return newPath
}

fun List<String>.processNode(
    currentPath: Path,
    treeNode: TreeNode,
): Path {
    when (first()) {
        "dir" -> drop(1).processDir(currentPath, treeNode)
        else -> processFile(currentPath, treeNode)
    }
    return currentPath
}

fun List<String>.processDir(
    currentPath: Path,
    treeNode: TreeNode?,
) {
    val newPath = currentPath.resolve(first()).normalize()
    treeNode?.search(newPath) ?: treeNode?.children?.add(TreeNode(newPath))
}

fun List<String>.processFile(
    currentPath: Path,
    treeNode: TreeNode,
) {
    val newPath = currentPath.resolve(last()).normalize()
    treeNode.search(currentPath)?.children?.add(TreeNode(newPath, FILE, first().toInt()))
    treeNode.propagateFileSize(newPath, first().toInt())
}
