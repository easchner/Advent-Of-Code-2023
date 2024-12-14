package utils

class GridUtils2d<T> (grid: List<List<T>>) {
    val working = grid.map { it.toMutableList() }.toMutableList()

    fun expand(buffer: Int, fill: T) {
        val newGrid = MutableList(working.size + buffer * 2) { MutableList(working[0].size + buffer * 2) { fill } }
        for (i in 0 until working.size) {
            for (j in 0 until working[0].size) {
                newGrid[i + buffer][j + buffer] = working[i][j]
            }
        }
        working.clear()
        working.addAll(newGrid)
    }

    fun floodFill(fill: T, ignore: T, startX: Int = 0, startY: Int = 0) {
        val visited = MutableList(working.size) { MutableList(working[0].size) { false } }
        val queue = mutableListOf(Pair(startX, startY))

        while (queue.isNotEmpty()) {
            val (x, y) = queue.removeAt(0)
            if (x < 0 || x >= working.size || y < 0 || y >= working[0].size || visited[x][y] || working[x][y] == ignore) {
                continue
            }
            visited[x][y] = true
            working[x][y] = fill
            queue.add(Pair(x + 1, y))
            queue.add(Pair(x - 1, y))
            queue.add(Pair(x, y + 1))
            queue.add(Pair(x, y - 1))
        }
    }
}