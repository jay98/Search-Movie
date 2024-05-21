# Search-Movie

## Pre-Requisite

Create a new file called `secrets.properties` and add the API key:

```properties
TMDB_API_KEY="{api_key}"
```

### Mini Demo ( Doesn't show all the edge cases )

https://github.com/jay98/Search-Movie/assets/23137381/2feb434e-6a6d-4d00-8091-7f26f3f3096a

## Modifications from Existing Design

1. **Search Results**: Includes a title and part of the overview in addition to the backdrop,
   providing users with more context at a glance.
2. **Loading Indicator**: Added for search operations.
3. **Infinite Scroll**: Implemented for both search results and trending movies with a retry button
   for failed loading attempts.
4. **Swipe to Refresh**: Implemented for trending movies screen

## Potential Future Enhancements

### Product Features

1. Add search filters.
2. Enhance the movie details overview page.
3. Improve opening animations for search and trending list views using SharedElement transitions.
4. Display trending movies for both the day and the month.

### Engineering Improvements
1. Find a better or lower res gif for top image view ( that's taking a perfor mance hit )
2. Improve server configuration handling to dynamically fetch different image sizes based on device
   size.
3. Set up more robust toolbars and better handle insets.
4. Develop a better transformer layer.
5. Centralize image loading using binding adapters for various situations.
6. Replace the failed image load hack with proper Coil GIF loaders.
### Testing Enhancements

1. Add scenario tests.
2. Set up a mock data provider for tests instead of hardcoding data.
3. Make unit tests more comprehensive.




