package collections

class MyStack<E>() {

    private val initSize = 2
    private val increaseFactor = 2
    private var top = -1

    private var items = arrayOfNulls<Any>(initSize)

    private val size
        get() = top + 1

    private val capacity
        get() = items.size

    constructor(initialItems: Iterable<E>) : this() {
        initialItems.forEach { push(it) }
    }

    fun push(newItem: E) {
        if (size == capacity)
            increaseCapacity()
        items[++top] = newItem
    }

    fun pop(): E? {
        if (size == 0)
            return null
        val item = items[top] as E
        items[top--] = null
        decreaseCapacity()
        return item
    }

    fun peek(): E? {
        if (size == 0)
            return null
        return items[top] as E
    }

    private fun increaseCapacity() {
        items = items.copyOf(capacity * increaseFactor)
    }

    /**
     * Memory optimization.
     *
     * If there are less than [capacity] / ([increaseFactor] * [increaseFactor]) elements in stack,
     * reduce it's size by [increaseFactor].
     * It happens not too frequent so this is also time efficient.
     */
    private fun decreaseCapacity() {
        if (size <= capacity / (increaseFactor * increaseFactor)) {
            items = items.copyOf(capacity / increaseFactor)
        }
    }

}
