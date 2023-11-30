package algonquin.cst2335.androidfinalproject.song;

import android.os.AsyncTask;

public class SaveSongTask extends AsyncTask<Song, Void, Long> {

    private FavoriteSongDatabase database;
    private OnTaskCompleteListener listener;

    SaveSongTask(FavoriteSongDatabase database, OnTaskCompleteListener listener) {
        this.database = database;
        this.listener = listener;
    }

    @Override
    protected Long doInBackground(Song... songs) {
        // Perform the database operation in the background
        FavoriteSong favoriteSong = new FavoriteSong();
        Song song = songs[0];
        favoriteSong.setTitle(song.getTitle());
        favoriteSong.setDuration(song.getDuration());
        favoriteSong.setAlbumName(song.getAlbumName());
        favoriteSong.setAlbumCoverUrl(song.getAlbumCoverUrl());

        return database.favoriteSongDao().saveFavoriteSong(favoriteSong);
    }

    @Override
    protected void onPostExecute(Long result) {
        // Notify the listener about the task completion
        listener.onTaskComplete(result);
    }

    // Interface to notify the completion of the task
    public interface OnTaskCompleteListener {
        void onTaskComplete(Long result);
    }
}
