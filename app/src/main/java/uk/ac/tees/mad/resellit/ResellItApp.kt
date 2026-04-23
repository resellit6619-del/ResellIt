package uk.ac.tees.mad.resellit

import android.app.Application
import coil.ImageLoader
import coil.request.CachePolicy
import uk.ac.tees.mad.resellit.di.Container

class ResellItApp : Application() {
    lateinit var container: Container
    lateinit var imageLoader: ImageLoader

    override fun onCreate() {
        super.onCreate()
        imageLoader = ImageLoader.Builder(this)
            .diskCachePolicy(CachePolicy.ENABLED)
            .memoryCachePolicy(CachePolicy.ENABLED)
            .build()
        container = Container(this)
    }
}


/**
 *late init -- stands for late initialisation and initialise it before using it
 * those values about which are assured that they will not be containing the null values
 * we make them late init
 */