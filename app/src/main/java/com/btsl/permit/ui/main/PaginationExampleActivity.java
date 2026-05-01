package com.btsl.permit.ui.main;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.btsl.permit.R;
import com.btsl.permit.data.datamodel.PageInfo;

/**
 * Example Activity demonstrating cursor-based pagination implementation
 */
public class PaginationExampleActivity extends AppCompatActivity {

  private static final String TAG = "PaginationExample";
  
  private RecyclerView rvUsers;
  private ProgressBar progressBar;
  private Button btnRefresh;
  private Button btnNextPage;
  
  private UserViewModel viewModel;
  private UserAdapter userAdapter;
  private PaginationScrollListener paginationScrollListener;
  private LinearLayoutManager layoutManager;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    EdgeToEdge.enable(this);
    setContentView(R.layout.activity_pagination_example);

    ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
      Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
      v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
      return insets;
    });

    // Initialize UI components
    rvUsers = findViewById(R.id.rvUsers);
    progressBar = findViewById(R.id.progressBar);
    btnRefresh = findViewById(R.id.btnRefresh);
    btnNextPage = findViewById(R.id.btnNextPage);

    // Initialize ViewModel
    viewModel = new ViewModelProvider(this).get(UserViewModel.class);

    // Setup RecyclerView
    setupRecyclerView();

    // Setup button listeners
    btnRefresh.setOnClickListener(v -> refreshUsersList());
    btnNextPage.setOnClickListener(v -> loadNextPage());

    // Load initial data
    loadInitialData();

    // Observe pagination state changes
    observePaginationState();
  }

  /**
   * Setup RecyclerView with LinearLayoutManager and pagination scroll listener
   */
  private void setupRecyclerView() {
    layoutManager = new LinearLayoutManager(this);
    rvUsers.setLayoutManager(layoutManager);

    // Initialize adapter
    userAdapter = new UserAdapter();
    rvUsers.setAdapter(userAdapter);

    // Setup pagination scroll listener
    paginationScrollListener = new PaginationScrollListener(layoutManager);
    paginationScrollListener.setPaginationListener(() -> {
      Log.d(TAG, "Pagination scroll listener triggered - loading next page");
      viewModel.loadNextUsersPage();
    });
    rvUsers.addOnScrollListener(paginationScrollListener);

    // Item click listener
    userAdapter.setOnItemClickListener(user -> 
        Toast.makeText(PaginationExampleActivity.this, 
            "Clicked: " + user.getName(), 
            Toast.LENGTH_SHORT).show()
    );
  }

  /**
   * Load the first page of users
   */
  private void loadInitialData() {
    Log.d(TAG, "Loading initial data");
    viewModel.loadUsersPaginated();
  }

  /**
   * Refresh the entire users list (reset to first page)
   */
  private void refreshUsersList() {
    Log.d(TAG, "Refreshing users list");
    userAdapter.clear();
    paginationScrollListener.resetPagination();
    viewModel.refreshPaginatedUsers();
    Toast.makeText(this, "Refreshing users list...", Toast.LENGTH_SHORT).show();
  }

  /**
   * Manually load next page
   */
  private void loadNextPage() {
    if (viewModel.hasNextPage()) {
      Log.d(TAG, "Loading next page");
      viewModel.loadNextUsersPage();
      Toast.makeText(this, "Loading next page...", Toast.LENGTH_SHORT).show();
    } else {
      Toast.makeText(this, "No more pages available", Toast.LENGTH_SHORT).show();
    }
  }

  /**
   * Observe pagination state changes from ViewModel
   */
  private void observePaginationState() {
    // Observe paginated users list
    viewModel.getPaginatedUsers().observe(this, users -> {
      if (users != null) {
        Log.d(TAG, "Users received: " + users.size());
        userAdapter.setUsers(users);
      }
    });

    // Observe page info (cursor, total count, has next page, etc.)
    viewModel.getPageInfo().observe(this, pageInfo -> {
      if (pageInfo != null) {
        Log.d(TAG, "Page info updated: " + pageInfo);
        updatePaginationUI(pageInfo);
      }
    });

    // Observe loading state
    viewModel.isLoading().observe(this, isLoading -> {
      progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
      if (isLoading) {
        paginationScrollListener.setLoading(true);
      }
    });

    // Observe error messages
    viewModel.getErrorMessage().observe(this, errorMessage -> {
      if (errorMessage != null && !errorMessage.isEmpty()) {
        Toast.makeText(this, "Error: " + errorMessage, Toast.LENGTH_SHORT).show();
        Log.e(TAG, "Error: " + errorMessage);
        paginationScrollListener.setLoading(false);
      }
    });
  }

  /**
   * Update UI based on pagination info
   */
  private void updatePaginationUI(PageInfo pageInfo) {
    // Update next page button state
    btnNextPage.setEnabled(pageInfo.isHasNextPage());
    
    String paginationInfo = String.format(
        "Total: %d | Page Size: %d | Has Next: %s",
        pageInfo.getTotalCount(),
        pageInfo.getPageSize(),
        pageInfo.isHasNextPage()
    );
    Log.d(TAG, paginationInfo);

    // Signal to scroll listener that we can load more
    if (!pageInfo.isHasNextPage()) {
      paginationScrollListener.setLastPage(true);
    } else {
      paginationScrollListener.setLoading(false);
    }
  }
}
