package com.example.taskmobile.data.repositories

interface Repository<T, K> {

    suspend fun create(t: T): T

    suspend fun findAll(): List<T>

    suspend fun findOne(id: K): T

    suspend fun update(t: T): T

    suspend fun delete(id: K): T

}