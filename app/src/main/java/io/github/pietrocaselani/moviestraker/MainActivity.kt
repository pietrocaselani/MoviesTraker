package io.github.pietrocaselani.moviestraker

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

class MainActivity : AppCompatActivity(), HasSupportFragmentInjector {

	//region Properties
	@Inject lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>
	@Inject lateinit var navigationController: NavigationController
	//endregion

	//region Lifecycle
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		val toolbar = findViewById(R.id.app_toolbar) as Toolbar
		setSupportActionBar(toolbar)

		supportActionBar?.title = getString(R.string.app_name)

		if (savedInstanceState == null) {
			navigationController.navigateToUpcomingMovies()
		}
	}
	//endregion

	//region Fragment Injector
	override fun supportFragmentInjector(): AndroidInjector<Fragment> {
		return dispatchingAndroidInjector
	}
	//endregion
}
