package com.raj.imageloading.lrucache

class Node(
    val key: Int,
    var value: Int,
    var prev: Node? = null,
    var next: Node? = null
)

class LRUCache(private val capacity: Int) {
    private val head = Node(key = 0, value = 0)//Least recently used
    private val tail = Node(key = 0, value = 0)//Most recently used

    /**
     * This is the actual cache. Head and tail are used to find LRU, MRU
     * hashMap is a cacheMap
     */
    val hashMap = HashMap<Int, Node>()

    init {
        head.next = tail
        tail.prev = head
    }

    fun get(key: Int): Int {
        if (key !in hashMap) {
            return -1
        }

        val nodeTobeOnTail = hashMap.getValue(key = key)
        /**
         * On evey fetch update the position of fetched node as per the LRU policy
         */
        removeAndBreakTheLinkFromCurrentPosition(nodeTobeOnTail)
        insertAtEnd(nodeTobeOnTail)
        return nodeTobeOnTail.value
    }

    fun put(key: Int, value: Int) {
        if (key in hashMap) {//already in cache, just update value and position(no removal)
            val nodeTobeMovedToEnd = hashMap.getValue(key = key)
            nodeTobeMovedToEnd.value = value//Just value is updated and moved to end
            removeAndBreakTheLinkFromCurrentPosition(nodeTobeMovedToEnd)
            insertAtEnd(nodeTobeMovedToEnd)//Became most recently used
            return
        }
        //Cache is Full, apply cache eviction policy (on cacheMap)
        if (capacity == hashMap.size) {
            val nodeToDeleted = head.next!! //Lest recently used is going to be out of memory/cache
            removeAndBreakTheLinkFromCurrentPosition(nodeToDeleted)//
            hashMap.remove(nodeToDeleted.key)//remove from cache
        }
        val newNode = Node(key = key, value = value)
        insertAtEnd(newNode)//Put always at end to maintain the policy
        hashMap[key] = newNode//Finally putting in actual cache
        printCurrentCacheMap(key, value)
    }

    private fun removeAndBreakTheLinkFromCurrentPosition(nodeTobeRemoved: Node) {
        nodeTobeRemoved.prev?.next = nodeTobeRemoved.next
        nodeTobeRemoved.next?.prev = nodeTobeRemoved.prev
    }

    /**
     * insertAtFront Not need for LRU cache
     */
    private fun insertAtFront(nodeToBeAdded: Node) {
        //First Update your self
        nodeToBeAdded.prev = head
        nodeToBeAdded.next = head.next
        //Second update your neighbour
        head.next?.prev = nodeToBeAdded
        head.next = nodeToBeAdded //Must be updated at last
    }

    private fun insertAtEnd(nodeTobeInsertedAtEnd: Node) {
        //Update your self first
        nodeTobeInsertedAtEnd.prev = tail.prev
        nodeTobeInsertedAtEnd.next = tail
        //Updates affected neighbours
        tail.prev?.next = nodeTobeInsertedAtEnd
        tail.prev = nodeTobeInsertedAtEnd//Must be updated at last
    }

    private fun printCurrentCacheMap(key: Int, value: Int) {
        println("-------------------------------------------")
        println("After adding key = $key and value = $value")
        println("Current Status of hashMap")
        hashMap.forEach {
            println("key = ${it.key} and value = ${it.value.value}")
        }
        println("-------------------------------------------")
    }

}

fun main() {
    val cache = LRUCache(3)
    cache.put(1, 10)
    cache.put(2, 20)
    cache.put(3, 30)
    println("*******************************************")
    println("GET value for the key = 2 : ${cache.get(2)}")
    println("*******************************************")
    cache.put(4, 40)
    cache.put(5, 50)
}