package collections

class MyQueue<E>() {

    private val initSize = 2
    private val increaseFactor = 2
    private var front = 0
    private var rear = -1
    private val size
        get() = rear - front + 1

    private var items = arrayOfNulls<Any>(initSize)

    private val capacity
        get() = items.size

    constructor(initialItems: Iterable<E>) : this() {
        initialItems.forEach { offer(it) }
    }

    fun element(): E {
        if (size == 0)
            throw NoSuchElementException("Empty queue")
        return items[front] as E
    }

    fun remove(): E {
        if (size == 0)
            throw NoSuchElementException("Empty queue")
        val item = items[front] as E
        items[front++] = null
        moveLeft()
        decreaseCapacity()
        return item
    }

    fun peek(): E? {
        if (size == 0)
            return null
        return items[front] as E?
    }

    fun poll(): E? {
        if (size == 0)
            return null
        val item = items[front] as E
        items[front++] = null
        moveLeft()
        decreaseCapacity()
        return item
    }

    fun offer(item: E): Boolean {
        if (rear + 1 == capacity)
            increaseCapacity()
        items[++rear] = item
        return true
    }

    /**
     * Memory optimization.
     *
     * If the first half of [items] is unused, i.e. elements presented in the queue are in the second half,
     * copy them to the very beginning. It happens not too frequent so this is also time efficient.
     */
    private fun moveLeft() {
        if (front >= capacity / 2) {
            items.copyInto(destination = items, destinationOffset = 0, startIndex = front, endIndex = rear + 1)
            items.fill(null, maxOf(front, rear - front + 1), rear + 1)
            rear -= front
            front = 0
        }
    }

    private fun increaseCapacity() {
        items = items.copyOf(capacity * increaseFactor)
    }

    /**
     * Memory optimization.
     *
     * If there are less than [capacity] / ([increaseFactor] * [increaseFactor]) elements in queue,
     * copy them to the very beginning of the array and reduce it's size by [increaseFactor].
     * It happens not too frequent so this is also time efficient.
     */
    private fun decreaseCapacity() {
        if (size <= capacity / (increaseFactor * increaseFactor)) {
            moveLeft()
            items = items.copyOf(capacity / increaseFactor)
        }
    }

}
