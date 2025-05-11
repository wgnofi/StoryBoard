package com.example.storyboard.data

import kotlinx.coroutines.flow.Flow

interface StoryBoardRepository {
    suspend fun insertBoard(sb: StoryBoard)
    fun getBoards(): Flow<List<StoryBoard>>
    suspend fun deleteBoard(sb: StoryBoard)
    fun getBoard(id: Int): Flow<StoryBoard?>
    suspend fun updateBoard(sb: StoryBoard)
}


class OfflineStoryBoardRepository(private val dao: StoryBoardDao): StoryBoardRepository {
    override suspend fun insertBoard(sb: StoryBoard) = dao.insertSB(sb)

    override fun getBoards(): Flow<List<StoryBoard>> = dao.getAllBoards()

    override suspend fun deleteBoard(sb: StoryBoard) = dao.deleteBoard(sb)

    override fun getBoard(id: Int): Flow<StoryBoard?> = dao.getBoard(id)

    override suspend fun updateBoard(sb: StoryBoard) = dao.updateBoard(sb)
}