import pt.isel.canvas.DOWN_CODE
import pt.isel.canvas.LEFT_CODE
import pt.isel.canvas.RIGHT_CODE
import pt.isel.canvas.UP_CODE

enum class Direction(val dx:Int, val dy: Int) {
    LEFT(-DELTA,0), RIGHT(+DELTA,0),
    UP(0,-DELTA), DOWN(0,+DELTA)
}

data class Dimension (val width : Int, val height : Int) // numero de celulas do game

// Funçao que traduz o código de uma tecla numa direção

fun Int.toDir() : Direction? = when(this) {
    UP_CODE -> Direction.UP
    DOWN_CODE -> Direction.DOWN
    LEFT_CODE -> Direction.LEFT
    RIGHT_CODE -> Direction.RIGHT
    else -> null
}
// Função que possibilita a operação "cell + dir"

operator fun Cell.plus(direction: Direction) =
    Cell(col + direction.dx, line + direction.dy)

// Função que possibilita a operação "cell - dir"

operator fun Cell.minus(direction: Direction) =
    Cell(col - direction.dx, line - direction.dy)

// Função que verifica se tanto a posição dos ghosts, quando a do pacman, quanto a das pellets e super Pellets estão nos limites do canvas.

fun Cell.inArena() = col in 0 until CANVAS_WIDTH && line in 0 until CANVAS_HEIGHT

// Função que verifica se o pacman e os ghosts não estão dentro das paredes do labirinto

fun Cell.withoutWalls(g : Game) : Boolean = this !in g.walls
