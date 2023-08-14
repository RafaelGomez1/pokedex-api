package com.example.heytradepokemontest.shared

interface FakeRepository<T> {
    val elements: MutableList<T>

    fun resetFake() = elements.clear()
    fun contains(resource: T): Boolean = elements.contains(resource)
}
