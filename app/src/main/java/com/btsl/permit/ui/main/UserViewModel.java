package com.btsl.permit.ui.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.btsl.permit.data.datamodel.PageInfo;
import com.btsl.permit.data.datamodel.PaginatedResponse;
import com.btsl.permit.data.datamodel.Post;
import com.btsl.permit.data.datamodel.User;
import com.btsl.permit.domain.UserRepository;
import java.util.List;

public class UserViewModel extends ViewModel {

  private UserRepository repository;
  private MutableLiveData<List<User>> usersLiveData;
  private MutableLiveData<User> userLiveData;
  private MutableLiveData<List<Post>> postsLiveData;
  private MutableLiveData<Boolean> isLoading;
  private MutableLiveData<String> errorMessage;

  // ============== PAGINATION STATE ==============
  private MutableLiveData<List<User>> paginatedUsersLiveData;
  private MutableLiveData<List<Post>> paginatedPostsLiveData;
  private MutableLiveData<PageInfo> currentPageInfo;
  
  private String currentUsersCursor = null;
  private String currentPostsCursor = null;
  private static final int DEFAULT_PAGE_SIZE = 20;

  public UserViewModel() {
    repository = new UserRepository();
    usersLiveData = new MutableLiveData<>();
    userLiveData = new MutableLiveData<>();
    postsLiveData = new MutableLiveData<>();
    paginatedUsersLiveData = new MutableLiveData<>();
    paginatedPostsLiveData = new MutableLiveData<>();
    currentPageInfo = new MutableLiveData<>();
    isLoading = new MutableLiveData<>(false);
    errorMessage = new MutableLiveData<>();
  }

  // Get all users (non-paginated)
  public LiveData<List<User>> getUsers() {
    isLoading.setValue(true);
    usersLiveData = repository.getUsers();
    isLoading.setValue(false);
    return usersLiveData;
  }

  // Get user by ID
  public LiveData<User> getUserById(int userId) {
    isLoading.setValue(true);
    userLiveData = repository.getUserById(userId);
    isLoading.setValue(false);
    return userLiveData;
  }

  // Get all posts (non-paginated)
  public LiveData<List<Post>> getAllPosts() {
    isLoading.setValue(true);
    postsLiveData = repository.getAllPosts();
    isLoading.setValue(false);
    return postsLiveData;
  }

  // Get posts by user ID (non-paginated)
  public LiveData<List<Post>> getPostsByUserId(int userId) {
    isLoading.setValue(true);
    postsLiveData = repository.getPostsByUserId(userId);
    isLoading.setValue(false);
    return postsLiveData;
  }

  // ============== PAGINATION METHODS ==============

  /**
   * Load users with pagination (first page or refresh)
   */
  public void loadUsersPaginated() {
    isLoading.setValue(true);
    currentUsersCursor = null;
    MutableLiveData<PaginatedResponse<User>> response = 
        repository.getUsersPaginated(currentUsersCursor, DEFAULT_PAGE_SIZE);
    
    response.observeForever(paginatedResponse -> {
      if (paginatedResponse != null) {
        paginatedUsersLiveData.setValue(paginatedResponse.getData());
        currentPageInfo.setValue(paginatedResponse.getPageInfo());
        if (paginatedResponse.getPageInfo() != null) {
          currentUsersCursor = paginatedResponse.getPageInfo().getNextCursor();
        }
      } else {
        errorMessage.setValue("Failed to load users");
      }
      isLoading.setValue(false);
    });
  }

  /**
   * Load posts with pagination (first page or refresh)
   */
  public void loadPostsPaginated() {
    isLoading.setValue(true);
    currentPostsCursor = null;
    MutableLiveData<PaginatedResponse<Post>> response = 
        repository.getPostsPaginated(currentPostsCursor, DEFAULT_PAGE_SIZE);
    
    response.observeForever(paginatedResponse -> {
      if (paginatedResponse != null) {
        paginatedPostsLiveData.setValue(paginatedResponse.getData());
        currentPageInfo.setValue(paginatedResponse.getPageInfo());
        if (paginatedResponse.getPageInfo() != null) {
          currentPostsCursor = paginatedResponse.getPageInfo().getNextCursor();
        }
      } else {
        errorMessage.setValue("Failed to load posts");
      }
      isLoading.setValue(false);
    });
  }

  /**
   * Load next page of users
   */
  public void loadNextUsersPage() {
    PageInfo pageInfo = currentPageInfo.getValue();
    if (pageInfo != null && pageInfo.isHasNextPage()) {
      isLoading.setValue(true);
      MutableLiveData<PaginatedResponse<User>> response = 
          repository.getUsersPaginated(pageInfo.getNextCursor(), DEFAULT_PAGE_SIZE);
      
      response.observeForever(paginatedResponse -> {
        if (paginatedResponse != null) {
          List<User> currentUsers = paginatedUsersLiveData.getValue();
          if (currentUsers != null) {
            currentUsers.addAll(paginatedResponse.getData());
            paginatedUsersLiveData.setValue(currentUsers);
          } else {
            paginatedUsersLiveData.setValue(paginatedResponse.getData());
          }
          currentPageInfo.setValue(paginatedResponse.getPageInfo());
          if (paginatedResponse.getPageInfo() != null) {
            currentUsersCursor = paginatedResponse.getPageInfo().getNextCursor();
          }
        } else {
          errorMessage.setValue("Failed to load next users page");
        }
        isLoading.setValue(false);
      });
    }
  }

  /**
   * Load next page of posts
   */
  public void loadNextPostsPage() {
    PageInfo pageInfo = currentPageInfo.getValue();
    if (pageInfo != null && pageInfo.isHasNextPage()) {
      isLoading.setValue(true);
      MutableLiveData<PaginatedResponse<Post>> response = 
          repository.getPostsPaginated(pageInfo.getNextCursor(), DEFAULT_PAGE_SIZE);
      
      response.observeForever(paginatedResponse -> {
        if (paginatedResponse != null) {
          List<Post> currentPosts = paginatedPostsLiveData.getValue();
          if (currentPosts != null) {
            currentPosts.addAll(paginatedResponse.getData());
            paginatedPostsLiveData.setValue(currentPosts);
          } else {
            paginatedPostsLiveData.setValue(paginatedResponse.getData());
          }
          currentPageInfo.setValue(paginatedResponse.getPageInfo());
          if (paginatedResponse.getPageInfo() != null) {
            currentPostsCursor = paginatedResponse.getPageInfo().getNextCursor();
          }
        } else {
          errorMessage.setValue("Failed to load next posts page");
        }
        isLoading.setValue(false);
      });
    }
  }

  /**
   * Check if there's a next page
   */
  public boolean hasNextPage() {
    PageInfo pageInfo = currentPageInfo.getValue();
    return pageInfo != null && pageInfo.isHasNextPage();
  }

  // Getters for loading and error states
  public LiveData<Boolean> isLoading() {
    return isLoading;
  }

  public LiveData<String> getErrorMessage() {
    return errorMessage;
  }

  // Getters for paginated data
  public LiveData<List<User>> getPaginatedUsers() {
    return paginatedUsersLiveData;
  }

  public LiveData<List<Post>> getPaginatedPosts() {
    return paginatedPostsLiveData;
  }

  public LiveData<PageInfo> getPageInfo() {
    return currentPageInfo;
  }

  // Refresh data
  public void refreshUsers() {
    getUsers();
  }

  public void refreshPosts() {
    getAllPosts();
  }

  public void refreshPaginatedUsers() {
    loadUsersPaginated();
  }

  public void refreshPaginatedPosts() {
    loadPostsPaginated();
  }
}