import pt.isel.canvas.DOWN_CODE
import pt.isel.canvas.LEFT_CODE
import pt.isel.canvas.RIGHT_CODE
import pt.isel.canvas.UP_CODE

data class Ghost (val c : Cell, val d : Direction)

// Função que dá as instruções de como será o movimento dos ghosts
fun Ghost.moveRandom(g : Game) : Ghost {
    val randomDirection = all_dir.random().toDir() ?: return this
    if(this.c + randomDirection !in g.walls && randomDirection != changeRandomDir(this.d))
        return moveToGhostDir(g,randomDirection)
    return moveToGhostDir(g,this.d)
}

//Função que determina para que sitio é que os ghosts devem ir caso passem as barreiras do canvas, sem nunca
// tocar nas paredes do labirinto

private fun Ghost.moveToGhostDir(g : Game, dir: Direction): Ghost {
    val ghostCell = this.c + dir
    return if(ghostCell.withoutWalls(g)) Ghost(ghostCell,dir) else copy(d = dir)
}

val all_dir = listOf(UP_CODE, DOWN_CODE, RIGHT_CODE, LEFT_CODE)

// Função que diz para que direção é que o pacman deve ir caso este fique preso
fun changeRandomDir(dir : Direction): Direction {
    return when(dir) {
        Direction.LEFT -> Direction.RIGHT
        Direction.RIGHT -> Direction.LEFT
        Direction.UP -> Direction.DOWN
        Direction.DOWN -> Direction.UP
    }
}