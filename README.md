# MoviesTracker

Keep track of upcoming movies on your Android using TMDB API

## Instructions

After cloning the project, just import to Android Studio 2.3.3

To use your own TMDB API key, add the properties

```shell
TMDB_API_KEY_DEV=your_api_key_for_development
TMDB_API_KEY_RELEASE=your_api_key_for_release
```

on `$HOME/.gradle/gradle.properties`

## Dependencies

* Kotlin
* Support Library v7 (AppCompat, Design, Pallete, RecyclerView, CardView, ConstraintLayout)
* RxAndroid - To work with Android and Main (UI) scheduler
* Retrofit 2 - To abstract network layer
* Dagger 2 - To abstract dependecy injection
* Glide - To easily load web images
* GlidePalette - To easily create a color palette from a web image
* PaperParcel - To easily implement Parcelable boilerplate code

## Translations

You can help translate the app [here](https://localise.biz/pietro-caselani/moviestracker)

## Author

Pietro Caselani, pc1992@gmail.com