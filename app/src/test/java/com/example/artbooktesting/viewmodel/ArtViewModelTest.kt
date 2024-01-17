package com.example.artbooktesting.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.artbooktesting.MainCoroutineRule
import com.example.artbooktesting.getOrAwaitValue
import com.example.artbooktesting.repo.FakeArtRepository
import com.example.artbooktesting.util.Status
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ArtViewModelTest {
    private lateinit var viewModel: ArtViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()


    @Before
    fun setup(){
        //test doubles
        viewModel = ArtViewModel(FakeArtRepository())

    }

    @Test
    fun `insert art without year , returns error` (){
        viewModel.makeArt("Mona lisa","Da vinci","")

        val value = viewModel.insertArtMessage.getOrAwaitValue()
        assertThat(value.status).isEqualTo(Status.ERROR)

    }

    @Test
    fun `insert art without name returns error` (){
        viewModel.makeArt("","Da vinci","1500")

        val value = viewModel.insertArtMessage.getOrAwaitValue()
        assertThat(value.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert art without artist name returns error` (){
        viewModel.makeArt("Mona lisa","","1500")

        val value = viewModel.insertArtMessage.getOrAwaitValue()
        assertThat(value.status).isEqualTo(Status.ERROR)
    }


}