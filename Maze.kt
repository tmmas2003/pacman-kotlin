/**
 * Source file to use in the development of PG (Programação) work.
 * Good job, Pedro Pereira.
 */

/**
 * Maze map textually described.
 * List of strings where each string is a map line.
 *
 * '#' -> Wall ; '.' -> Pellet ; '*' -> power pellet ;
 * 'S' -> Start of pac-man ; * 'G' -> Start of ghosts ;
 * 'F' -> Where fruit appears
 */
val maze = listOf(
    "##### #####",
    "#*..#.#..*#",
    "#.#.#.#.#.#",
    "#... G ...#",
    "###.###.###",
    "   .#F#.   ",
    "###.# #.###",
    "#....S....#",
    "#.#######.#",
    "#*.......*#",
    "##### #####",
)

/**
 * Type each cell of a maze.
 */
enum class CellType{ WALL, PELLET, PACMAN_START, POWER_PELLET, GHOST_START, FRUIT_START }

/**
 * Column and line of one position.
 * @property col Number of column (0 until width)
 * @property line Numer of line (0 until height)
 */
data class Cell(val col:Int, val line:Int)

/**
 * Represents the position and type of each cell.
 * @property cell Position of cell
 * @property type The type of cell
 */
data class Element(val cell: Cell, val type: CellType)


/**
 * Represents the maze information.
 * @property width Total width of maze.
 * @property height Total height of maze.
 * @property cells Position and type of each non-empty cell.
 */
data class Maze(val width: Int, val height:Int, val cells: List<Element>)

/**
 * Get the position of the first cell of a given [type].
 * @receiver Maze with the cells to find.
 * @param type The type of cell to find.
 * @return Found cell position.
 */
fun Maze.cellOfType(type: CellType): Cell = cells.first{ it.type==type }.cell

/**
 * Convert a symbol from a cell in the map to its cell type.
 * @receiver Symbol of the cell.
 * @return The cell type.
 */
fun Char.toCellType(): CellType? = when (this) {
    '#' -> CellType.WALL
    '.' -> CellType.PELLET
    'S' -> CellType.PACMAN_START
    '*' -> CellType.POWER_PELLET
    'G' -> CellType.GHOST_START
    'F' -> CellType.FRUIT_START
    else -> null
}

/**
 * Load a textually described map and return its representation.
 * @param maze Textually described map.
 * @return Representation of the map.
 */
fun loadMap(maze: List<String>): Maze {
    val cells = mutableListOf<Element>()
    maze.forEachIndexed { idxLine, line ->
        line.forEachIndexed { idxCol, char ->
            char.toCellType()?.let { cells.add(Element(Cell(idxCol,idxLine),it)) }
        }
    }
    return Maze(maze.maxOf{ it.length }, maze.size, cells)
}
