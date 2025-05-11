package com.example.storyboard.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.storyboard.StoryBoardApplication
import com.example.storyboard.data.Panel
import com.example.storyboard.data.StoryBoard
import com.example.storyboard.data.StoryBoardRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

sealed interface BoardState {
    data class Success(val boards: List<StoryBoard>): BoardState
    data object Empty: BoardState
}

class StoryBoardViewModel(
    val storyBoardRepository: StoryBoardRepository
): ViewModel() {
    private var _panels = MutableStateFlow<List<Panel>>(emptyList())
    val panels: StateFlow<List<Panel>> = _panels.asStateFlow()

    val storyBoards = storyBoardRepository.getBoards().mapNotNull { boards ->
        when {
            boards.isNotEmpty() -> BoardState.Success(boards)
            else -> {BoardState.Empty}
        }
    }
    .stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000L),
        initialValue = BoardState.Empty
    )

    private var _selectedBoard = MutableStateFlow<StoryBoard?>(null)
    val selectedBoard: StateFlow<StoryBoard?> = _selectedBoard.asStateFlow()

    private fun resetPanels() {
        _panels.value = emptyList()
    }

    fun addPanel(p: Panel) {
        val list = _panels.value
        val l = mutableListOf<Panel>()
        list.forEach { l.add(it) }
        l.add(p)
        _panels.value = l.toList()
    }

    fun createStoryBoard(sb: StoryBoard) {
        viewModelScope.launch {
            storyBoardRepository.insertBoard(sb = sb)
        }
        resetPanels()
    }

    fun deleteStoryBoard(sb: StoryBoard) {
        viewModelScope.launch {
            storyBoardRepository.deleteBoard(sb)
        }
    }


    fun getStoryBoard(id: Int) {
        viewModelScope.launch {
            storyBoardRepository.getBoard(id).collectLatest { value ->
                _selectedBoard.value = value
            }
        }
    }

    fun updateStoryBoard(sb: StoryBoard) {
        viewModelScope.launch {
            storyBoardRepository.updateBoard(sb)
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val storyBoardRepository =
                    (this[APPLICATION_KEY] as StoryBoardApplication).container.storyBoardRepository
                StoryBoardViewModel(storyBoardRepository)
            }
        }
    }

}