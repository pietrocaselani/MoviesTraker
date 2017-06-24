package io.github.pietrocaselani.moviestraker.upcoming

import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by pc on 24/06/17.
 */
@Module
abstract class UpcomingFragmentModule {

	@ContributesAndroidInjector
	abstract fun contributeUpcomingFragment(): UpcomingFragment

}