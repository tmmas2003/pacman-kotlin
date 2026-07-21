import pt.isel.canvas.*

const val CELL_SIZE = 32
const val GRID_WIDTH = 11
const val GRID_HEIGHT = 11
const val RADIUS = 16
const val CANVAS_WIDTH = CELL_SIZE * GRID_WIDTH
const val CANVAS_HEIGHT = CELL_SIZE * GRID_HEIGHT
const val DELTA = 1

fun main() {
    print("Begin ")
    onStart {
        val arena = Canvas(CELL_SIZE * GRID_WIDTH, CELL_SIZE * GRID_HEIGHT + CELL_SIZE, BLACK)
        var game = createGame()
        arena.drawGame(game)
        arena.onKeyPressed { key: KeyEvent ->
            game = game.movePacman(key.code).moveGhost().activeEscape().step()
            println(game)
            arena.drawGame(game)
        }
        arena.onTimeProgress(20000) {
            game = game.addGhosts(createGame())
        }
    }
    onFinish {  }
}
