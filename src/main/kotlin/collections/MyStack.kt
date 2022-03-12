package collections

class MyStack<E>() {

    private val initSize = 2
    private val increaseFactor = 2
    private var top = -1

    private var items = arrayOfNulls<Any>(initSize)

    private val capacity
        get() = items.size

    constructor(initialItems: Iterable<E>) : this() {
        initialItems.forEach { push(it) }
    }

    fun push(newItem: E) {
        if (top + 1 == capacity)
            increaseCapacity()
        items[++top] = newItem
    }

    fun pop(): E? {
        if (top < 0)
            return null
        return items[top--] as E
    }

    fun peek(): E? {
        if (top < 0)
            return null
        return items[top] as E
    }

    private fun increaseCapacity() {
        items = items.copyOf(capacity * increaseFactor)
    }
}