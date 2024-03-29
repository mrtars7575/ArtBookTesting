package com.example.artbooktesting.repo

import androidx.lifecycle.LiveData
import com.example.artbooktesting.api.RetrofitAPI
import com.example.artbooktesting.model.ImageResponse
import com.example.artbooktesting.roomdb.Art
import com.example.artbooktesting.roomdb.ArtDao
import com.example.artbooktesting.util.Resource
import javax.inject.Inject

class ArtRepository @Inject constructor(
    private val artDao: ArtDao,
    private val retrofitAPI: RetrofitAPI
) : ArtRepositoryInterface {
    override suspend fun insertArt(art: Art) {
        artDao.insertArt(art)
    }

    override suspend fun deleteArt(art: Art) {
        artDao.deleteArt(art)
    }

    override fun getArt(): LiveData<List<Art>> {
        return artDao.observeArts()
    }

    override suspend fun searchImage(imageString: String): Resource<ImageResponse> {
        return try {
            val response = retrofitAPI.imageSearch(imageString)
            if (response.isSuccessful){
                response.body()?.let {
                    print(it)
                    return Resource.success(it)
                } ?: Resource.error("Error",null)
            }else{
                print("error data ")
                Resource.error("Error",null)
            }

        }catch (e : Exception){
            Resource.error("No data",null)
        }
    }

}