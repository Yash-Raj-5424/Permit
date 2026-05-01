package com.btsl.permit.domain;

import android.util.Log;
import androidx.lifecycle.MutableLiveData;
import com.btsl.permit.data.ApiService;
import com.btsl.permit.data.RetrofitClient;
import com.btsl.permit.data.datamodel.PageInfo;
import com.btsl.permit.data.datamodel.PaginatedResponse;
import com.btsl.permit.data.datamodel.Post;
import com.btsl.permit.data.datamodel.User;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepository {

  private static final String TAG = "UserRepository";
  private ApiService apiService;

  public UserRepository() {
    apiService = RetrofitClient.getInstance().getApiService();
  }

  // Get all users
  public MutableLiveData<List<User>> getUsers() {
    MutableLiveData<List<User>> usersLiveData = new MutableLiveData<>();

    apiService.getUsers().enqueue(new Callback<List<User>>() {
      @Override
      public void onResponse(Call<List<User>> call, Response<List<User>> response) {
        if (response.isSuccessful() && response.body() != null) {
          usersLiveData.setValue(response.body());
          Log.d(TAG, "Users fetched successfully: " + response.body().size());
        } else {
          usersLiveData.setValue(null);
          Log.e(TAG, "Failed to fetch users: " + response.code());
        }
      }

      @Override
      public void onFailure(Call<List<User>> call, Throwable t) {
        usersLiveData.setValue(null);
        Log.e(TAG, "Error fetching users: " + t.getMessage());
      }
    });

    return usersLiveData;
  }

  // Get user by ID
  public MutableLiveData<User> getUserById(int userId) {
    MutableLiveData<User> userLiveData = new MutableLiveData<>();

    apiService.getUserById(userId).enqueue(new Callback<User>() {
      @Override
      public void onResponse(Call<User> call, Response<User> response) {
        if (response.isSuccessful() && response.body() != null) {
          userLiveData.setValue(response.body());
          Log.d(TAG, "User fetched: " + response.body().getName());
        } else {
          userLiveData.setValue(null);
          Log.e(TAG, "Failed to fetch user: " + response.code());
        }
      }

      @Override
      public void onFailure(Call<User> call, Throwable t) {
        userLiveData.setValue(null);
        Log.e(TAG, "Error fetching user: " + t.getMessage());
      }
    });

    return userLiveData;
  }

  // Get all posts
  public MutableLiveData<List<Post>> getAllPosts() {
    MutableLiveData<List<Post>> postsLiveData = new MutableLiveData<>();

    apiService.getAllPosts().enqueue(new Callback<List<Post>>() {
      @Override
      public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
        if (response.isSuccessful() && response.body() != null) {
          postsLiveData.setValue(response.body());
          Log.d(TAG, "Posts fetched successfully: " + response.body().size());
        } else {
          postsLiveData.setValue(null);
          Log.e(TAG, "Failed to fetch posts: " + response.code());
        }
      }

      @Override
      public void onFailure(Call<List<Post>> call, Throwable t) {
        postsLiveData.setValue(null);
        Log.e(TAG, "Error fetching posts: " + t.getMessage());
      }
    });

    return postsLiveData;
  }

  // Get posts by user ID
  public MutableLiveData<List<Post>> getPostsByUserId(int userId) {
    MutableLiveData<List<Post>> postsLiveData = new MutableLiveData<>();

    apiService.getPosts(userId).enqueue(new Callback<List<Post>>() {
      @Override
      public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
        if (response.isSuccessful() && response.body() != null) {
          postsLiveData.setValue(response.body());
          Log.d(TAG, "User posts fetched: " + response.body().size());
        } else {
          postsLiveData.setValue(null);
          Log.e(TAG, "Failed to fetch user posts: " + response.code());
        }
      }

      @Override
      public void onFailure(Call<List<Post>> call, Throwable t) {
        postsLiveData.setValue(null);
        Log.e(TAG, "Error fetching user posts: " + t.getMessage());
      }
    });

    return postsLiveData;
  }

  // ============== PAGINATION METHODS ==============

  // Get users with cursor-based pagination
  public MutableLiveData<PaginatedResponse<User>> getUsersPaginated(String cursor, int limit) {
    MutableLiveData<PaginatedResponse<User>> paginatedLiveData = new MutableLiveData<>();

    apiService.getUsersPaginated(cursor, limit).enqueue(new Callback<PaginatedResponse<User>>() {
      @Override
      public void onResponse(Call<PaginatedResponse<User>> call, Response<PaginatedResponse<User>> response) {
        if (response.isSuccessful() && response.body() != null) {
          paginatedLiveData.setValue(response.body());
          Log.d(TAG, "Users paginated fetched successfully: " + response.body().getData().size());
        } else {
          paginatedLiveData.setValue(null);
          Log.e(TAG, "Failed to fetch paginated users: " + response.code());
        }
      }

      @Override
      public void onFailure(Call<PaginatedResponse<User>> call, Throwable t) {
        paginatedLiveData.setValue(null);
        Log.e(TAG, "Error fetching paginated users: " + t.getMessage());
      }
    });

    return paginatedLiveData;
  }

  // Get posts with cursor-based pagination
  public MutableLiveData<PaginatedResponse<Post>> getPostsPaginated(String cursor, int limit) {
    MutableLiveData<PaginatedResponse<Post>> paginatedLiveData = new MutableLiveData<>();

    apiService.getPostsPaginated(cursor, limit).enqueue(new Callback<PaginatedResponse<Post>>() {
      @Override
      public void onResponse(Call<PaginatedResponse<Post>> call, Response<PaginatedResponse<Post>> response) {
        if (response.isSuccessful() && response.body() != null) {
          paginatedLiveData.setValue(response.body());
          Log.d(TAG, "Posts paginated fetched successfully: " + response.body().getData().size());
        } else {
          paginatedLiveData.setValue(null);
          Log.e(TAG, "Failed to fetch paginated posts: " + response.code());
        }
      }

      @Override
      public void onFailure(Call<PaginatedResponse<Post>> call, Throwable t) {
        paginatedLiveData.setValue(null);
        Log.e(TAG, "Error fetching paginated posts: " + t.getMessage());
      }
    });

    return paginatedLiveData;
  }

  // Get posts by user ID with cursor-based pagination
  public MutableLiveData<PaginatedResponse<Post>> getPostsByUserIdPaginated(int userId, String cursor, int limit) {
    MutableLiveData<PaginatedResponse<Post>> paginatedLiveData = new MutableLiveData<>();

    apiService.getPostsByUserPaginated(userId, cursor, limit).enqueue(new Callback<PaginatedResponse<Post>>() {
      @Override
      public void onResponse(Call<PaginatedResponse<Post>> call, Response<PaginatedResponse<Post>> response) {
        if (response.isSuccessful() && response.body() != null) {
          paginatedLiveData.setValue(response.body());
          Log.d(TAG, "User posts paginated fetched: " + response.body().getData().size());
        } else {
          paginatedLiveData.setValue(null);
          Log.e(TAG, "Failed to fetch paginated user posts: " + response.code());
        }
      }

      @Override
      public void onFailure(Call<PaginatedResponse<Post>> call, Throwable t) {
        paginatedLiveData.setValue(null);
        Log.e(TAG, "Error fetching paginated user posts: " + t.getMessage());
      }
    });

    return paginatedLiveData;
  }
}