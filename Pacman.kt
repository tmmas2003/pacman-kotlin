import pt.isel.canvas.*
import kotlin.time.times

data class PacMan(val cell: Cell, val dir: Direction)

// Função que determina que teclas é que devem ser pressionadas e para que direção é que elas estão associadas,
// para o pacman andar.
fun Game.moveByKey(key : Int) : PacMan {
    val dir : Direction = key.toDir() ?: return pacman
    return moveToDir(dir)
}

// Função que determina para que sitio é que o pacman deve ir caso este passe as barreiras do canvas, sem nunca
// tocar nas paredes do labirinto

fun Game.moveToDir(dir: Direction) : PacMan {
    val cell2 = pacman.cell + dir
    return when{
        cell2 !in walls && cell2.col < 0 -> PacMan(Cell(10,cell2.line),dir)
        cell2 !in walls && cell2.col > 10 -> PacMan(Cell(0,cell2.line),dir)
        cell2 !in walls && cell2.line < 0 -> PacMan(Cell(cell2.col,10),dir)
        cell2 !in walls && cell2.line > 10 -> PacMan(Cell(cell2.col,0),dir)
        cell2.inArena() && cell2 !in walls -> PacMan(cell2,dir)
        else -> PacMan(pacman.cell,dir)
    }
}
