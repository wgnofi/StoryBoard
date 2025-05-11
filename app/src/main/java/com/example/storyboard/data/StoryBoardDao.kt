package com.example.storyboard.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface StoryBoardDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertSB(sb: StoryBoard)

    @Query("SELECT * FROM storyboard")
    fun getAllBoards(): Flow<List<StoryBoard>>

    @Delete
    suspend fun deleteBoard(sb: StoryBoard)

    @Query("SELECT * FROM storyboard WHERE id = :id")
    fun getBoard(id: Int): Flow<StoryBoard?>

    @Update
    suspend fun updateBoard(storyBoard: StoryBoard)
}