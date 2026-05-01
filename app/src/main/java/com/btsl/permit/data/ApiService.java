package com.btsl.permit.data;


import com.btsl.permit.data.datamodel.PaginatedResponse;
import com.btsl.permit.data.datamodel.Post;
import com.btsl.permit.data.datamodel.User;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/*
* Example of retrofit API interface
* */

public interface ApiService {

  // Get list of users (non-paginated - for backward compatibility)
  @GET("users")
  Call<List<User>> getUsers();

  // Get users with cursor-based pagination
  @GET("users")
  Call<PaginatedResponse<User>> getUsersPaginated(
      @Query("cursor") String cursor,
      @Query("limit") int limit
  );

  // Get single user by ID
  @GET("users/{id}")
  Call<User> getUserById(@Path("id") int userId);

  // Get posts with query parameters (non-paginated - for backward compatibility)
  @GET("posts")
  Call<List<Post>> getPosts(@Query("userId") int userId);

  // Get posts with cursor-based pagination
  @GET("posts")
  Call<PaginatedResponse<Post>> getPostsPaginated(
      @Query("cursor") String cursor,
      @Query("limit") int limit
  );

  // Get posts by user with cursor-based pagination
  @GET("posts")
  Call<PaginatedResponse<Post>> getPostsByUserPaginated(
      @Query("userId") int userId,
      @Query("cursor") String cursor,
      @Query("limit") int limit
  );

  // Get all posts (non-paginated - for backward compatibility)
  @GET("posts")
  Call<List<Post>> getAllPosts();

  // Get single post
  @GET("posts/{id}")
  Call<Post> getPostById(@Path("id") int postId);
}
