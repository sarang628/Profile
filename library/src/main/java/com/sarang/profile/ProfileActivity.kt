package com.sarang.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.sryang.torang_core.navigation.ProfileNavigation
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.components.ActivityComponent
import javax.inject.Inject

@AndroidEntryPoint
class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        findViewById<Toolbar>(R.id.toolbar2).apply {
            this?.let {
                setSupportActionBar(it)
            }
        }

        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.container, Profile1Fragment())
            .commit()

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        onBackPressed()
        return super.onOptionsItemSelected(item)
    }

    companion object {
        fun go(context: Context, userId: Int) {
            context.startActivity(Intent(context, ProfileActivity::class.java).apply {
                putExtra("userId", userId)
            })

        }
    }
}

class ProfileNavigationImpl @Inject constructor() : ProfileNavigation {
    override fun go(context: Context, userId: Int) {
        ProfileActivity.go(context, userId)
    }
}

@Module
@InstallIn(ActivityComponent::class)
abstract class ProfileNavigationInject {
    @Binds
    abstract fun bindProfileNavigation(
        profileNavigationImpl: ProfileNavigationImpl
    ): ProfileNavigation
}