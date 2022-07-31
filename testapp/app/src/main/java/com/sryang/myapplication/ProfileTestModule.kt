package com.sryang.myapplication

import android.content.Context
import androidx.fragment.app.FragmentManager
import com.example.torang_core.navigation.MainNavigation
import com.example.torang_core.navigation.PicturePageNavigation
import com.example.torang_core.navigation.RestaurantDetailNavigation
import com.example.torang_core.navigation.TimeLineDetailNavigation
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import javax.inject.Inject


class TestNavigation @Inject constructor() : MainNavigation, RestaurantDetailNavigation,
    TimeLineDetailNavigation, PicturePageNavigation {
    override fun goMain(fragmentManager: FragmentManager?) {

    }

    override fun goMain(context: Context) {

    }

    override fun go(context: Context, restaurantId: Int) {

    }

    override fun go(context: Context, reviewId: Int, position: Int) {

    }

}

@Module
@InstallIn(ActivityComponent::class)
abstract class ProfileTestModule {
    @Binds
    abstract fun provideMainNavigation(testNavigation: TestNavigation): MainNavigation

    @Binds
    abstract fun provideRestaurantDetailNavigation(testNavigation: TestNavigation): RestaurantDetailNavigation

    @Binds
    abstract fun provideTimeLineDetailNavigation(testNavigation: TestNavigation): TimeLineDetailNavigation

    @Binds
    abstract fun providePicturePageNavigation(testNavigation: TestNavigation): PicturePageNavigation
}