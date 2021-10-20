package com.ubaya.todoapp.model

import androidx.room.*

@Dao
interface TodoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg todo:Todo)

    @Query("Select * From todo ORDER BY priority DESC")
    suspend fun selectAllTodo():List<Todo>

    @Query("Select * From todo Where uuid = :id")
    suspend fun selectTodo(id:Int):Todo

    @Query("UPDATE todo SET title=:title, notes=:notes,priority=:priority WHERE uuid=:uuid")
    suspend fun update(title:String,notes:String,priority:Int,uuid:Int)

    @Delete
    suspend fun deleteTodo(todo:Todo)
}