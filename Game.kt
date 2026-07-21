data class Game(val dim: Dimension, val pacman: PacMan,
                val pellets : List <Cell>, val superPellets : List<Cell>,
                val score: Int, val walls : List<Cell>,
                val ghosts : List<Ghost>,
                val escape : Boolean, val step : Int)

// Função que cria tudo no seu estado inicial

fun createGame() : Game {
    val m = loadMap(maze)
    val dim = Dimension(m.width,m.height)
    val pacPos = m.cellOfType(CellType.PACMAN_START)
    val pelletsPos = m.cells.filter{it.type == CellType.PELLET}.map{it.cell}
    val supperPelletsPos = m.cells.filter{it.type == CellType.POWER_PELLET}.map{it.cell}
    val walls = m.cells.filter{it.type == CellType.WALL}.map{it.cell}
    val ghostsPos = m.cellOfType(CellType.GHOST_START)
    val ghost = listOf(Ghost(ghostsPos,Direction.DOWN))
    return Game(dim, PacMan(pacPos,Direction.RIGHT),pelletsPos,supperPelletsPos,0, walls,
        ghost, escape = false, 0)
}
// Funcão que determina o decorrer do jogo

fun Game.step() : Game {
    val newPellets = pellets.filterNot { it == pacman.cell }
    val newSuperPellets = superPellets.filterNot { it == pacman.cell }
    val newScore = when {
        pellets.any { it == pacman.cell } -> score + 10
        superPellets.any {it == pacman.cell } -> score + 30
        else -> score - 1
    }
    val moveGhosts = this.moveGhost().ghosts.filterNot { it.c == pacman.cell && escape == true }
    val newEscape = this.activeEscape().escape
    val steps = this.countSteps().step
    val confirmEscape = if(newEscape && step != 30) copy(escape = true,step = steps) else
        if (step == 30) copy(escape = false, step = 0) else this
    return if(isOver()) this else
        Game(dim, pacman, newPellets,newSuperPellets, newScore,walls, moveGhosts,confirmEscape.escape,confirmEscape.step)
}
// Função que permite o pacman andar

fun Game.movePacman(key : Int) : Game {
    val pacman = moveByKey(key)
    return if (isOver()) this else
        copy(dim, pacman, pellets)
}
// Função que permite os ghosts andarem

fun Game.moveGhost() : Game{
    val ghost = ghosts.map{it.moveRandom(this)}
    ghost.forEach{
        return when {
            it.c.withoutWalls(this) && it.c.col < 0 -> copy(ghosts = ghosts.map {
                Ghost(Cell(10, it.c.line),it.d)})
            it.c.withoutWalls(this) && it.c.col > 10 -> copy(ghosts = ghosts.map{
                Ghost(Cell(0, it.c.line), it.d)})
            it.c.withoutWalls(this) && it.c.line < 0 -> copy(ghosts = ghosts.map{
                Ghost(Cell(it.c.col, 10), it.d)})
            it.c.withoutWalls(this) && it.c.line > 10 -> copy(ghosts = ghosts.map{
                Ghost(Cell(it.c.col, 0), it.d)})
            it.c.inArena() && it.c.withoutWalls(this) -> copy(ghosts = ghosts.map{
                Ghost(Cell(it.c.col, it.c.line),it.d)})
            else -> this
        }
    }
    return if(isOver()) this else
        return copy(ghosts = ghost)
}
// Função que adiciona os ghosts na posição determinada.

fun Game.addGhosts(game : Game) : Game {
    val initialGhost = game.ghosts
    return if(initialGhost.isNotEmpty() && isOver())
        copy(ghosts = ghosts + initialGhost)
    else this
}
// Função que conta quantas vezes foi pressionada qualquer tecla de movimento quando o modo escape está ON.
fun Game.countSteps() : Game = if(escape == true) copy(step = step + 1) else this

// Função que ativa o modo Escape quando o pacman come uma super pellet (power pellet)

fun Game.activeEscape() : Game {
    return if(pacman.cell in superPellets)
        copy(escape = true, step = 0)
    else this
}

// Função que determina os casos em que o jogo acaba
fun Game.isOver() =
    pacman.cell in ghosts.map { it.c } && escape == false || pellets.isEmpty() && superPellets.isEmpty()