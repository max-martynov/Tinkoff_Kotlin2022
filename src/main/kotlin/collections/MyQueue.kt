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
        return items[front++] as E
    }

    fun peek(): E? {
        if (size == 0)
            return null
        return items[front] as E?
    }

    fun poll(): E? {
        if (size == 0)
            return null
        return items[front++] as E
    }

    fun offer(item: E): Boolean {
        if (rear + 1 == capacity)
            increaseCapacity()
        items[++rear] = item
        return true
    }

    fun clear() {
        while (size > 0)
            remove()
    }

    private fun increaseCapacity() {
        items = items.copyOf(capacity * increaseFactor)
    }

}