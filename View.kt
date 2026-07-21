import pt.isel.canvas.*

const val SPRITE_SIZE = 32

// Função que irá agregar todos os elementos do jogo que iram ser desenhados.

fun Canvas.drawGame(game: Game) {
    erase()
    drawPellets(game.pellets,game.superPellets)
    drawMaze(game.walls)
    drawPacman(game.pacman)
    drawGhost(game.ghosts)
    drawEscapeGhosts(game)
    drawScore(game)
    gameOver(game)

}
// Função que escreve "Game Over" quando o jogo acaba
fun Canvas.gameOver(g : Game) {
    if(g.isOver()) drawText(CANVAS_WIDTH/2 - CELL_SIZE - CELL_SIZE/2, CANVAS_HEIGHT + CELL_SIZE - 5 ,"Game Over",RED,17)
}

// Função que define como é que o pacman deve ser desenhado

fun Canvas.drawPacman(pacMan: PacMan) {
    val x = pacMan.cell.col * CELL_SIZE
    val y = pacMan.cell.line * CELL_SIZE
    val xSprite = when(pacMan.dir) {
        Direction.LEFT -> 0 * SPRITE_SIZE
        Direction.RIGHT -> 1 * SPRITE_SIZE
        Direction.DOWN -> 2 * SPRITE_SIZE
        Direction.UP -> 3 * SPRITE_SIZE
    }
    drawImage("packman|$xSprite,0,32,32",x,y)
}
// Função que define como é que as pastilhas devem ser desenhadas

fun Canvas.drawPellets(p : List <Cell>, s : List<Cell>) {
    p.forEach{
        val x = it.col * CELL_SIZE + CELL_SIZE/2
        val y = it.line * CELL_SIZE + CELL_SIZE/2
        drawCircle(x, y, RADIUS/4, WHITE)}
    s.forEach {
        val x = it.col * CELL_SIZE + CELL_SIZE / 2
        val y = it.line * CELL_SIZE + CELL_SIZE / 2
        drawCircle(x, y, RADIUS / 2, WHITE)
    }
}
// Função que define como é que os ghosts devem ser desenhados

fun Canvas.drawGhost(g : List<Ghost>) {
    g.forEach{
        val x = it.c.col * CELL_SIZE
        val y = it.c.line * CELL_SIZE
        val ySprite = when (it.d) {
            Direction.LEFT -> 4
            Direction.RIGHT -> 5
            Direction.DOWN -> 3
            Direction.UP -> 2
        }
        drawImage("packman|0,${ySprite*SPRITE_SIZE},$SPRITE_SIZE,$SPRITE_SIZE", x, y)
    }
}
// Função que define como é que os fantasmas, quando estão em fuga (Escape ON), devem ser desenhados

fun Canvas.drawEscapeGhosts(g : Game) {
    if(g.escape){
        g.ghosts.forEach {
            val x = it.c.col * CELL_SIZE
            val y = it.c.line * CELL_SIZE
            val ySprite = 3
            val xSprite = 5
            drawImage("packman|${SPRITE_SIZE * xSprite},${SPRITE_SIZE * ySprite},$SPRITE_SIZE,$SPRITE_SIZE",x,y)
        }
    }
}
// Função que define como é que a pontuação deve ser apresentada no canvas

fun Canvas.drawScore(g : Game) {
    drawText(5,CANVAS_HEIGHT + CELL_SIZE - 5 ,"Score:${g.score}",WHITE,17)
}

// Função que define como é que o labirinto deve ser desenhado

fun Canvas.drawMaze(wall : List<Cell>){
    wall.forEach{
        val x = it.col * CELL_SIZE
        val y = it.line * CELL_SIZE
        drawRect(x,y,CELL_SIZE,CELL_SIZE, BLUE)
    }
}