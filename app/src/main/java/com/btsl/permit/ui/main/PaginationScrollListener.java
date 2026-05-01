package com.btsl.permit.ui.main;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * RecyclerView scroll listener for infinite pagination
 * Detects when user reaches near the end of the list and triggers loading of next page
 */
public class PaginationScrollListener extends RecyclerView.OnScrollListener {

  private LinearLayoutManager layoutManager;
  private OnPaginationListener paginationListener;
  
  private static final int VISIBLE_THRESHOLD = 5; // Load next page when within 5 items from end
  private boolean isLoading = false;
  private boolean isLastPage = false;

  public PaginationScrollListener(LinearLayoutManager layoutManager) {
    this.layoutManager = layoutManager;
  }

  @Override
  public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
    super.onScrolled(recyclerView, dx, dy);

    int totalItemCount = layoutManager.getItemCount();
    int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
    
    // Check if we should load more
    if (!isLoading && !isLastPage) {
      if (lastVisibleItemPosition >= totalItemCount - VISIBLE_THRESHOLD) {
        if (paginationListener != null) {
          paginationListener.onLoadMore();
          isLoading = true;
        }
      }
    }
  }

  /**
   * Call this when loading has started
   */
  public void setLoading(boolean loading) {
    isLoading = loading;
  }

  /**
   * Call this when the last page has been loaded
   */
  public void setLastPage(boolean lastPage) {
    isLastPage = lastPage;
  }

  /**
   * Reset the pagination listener (useful for refresh)
   */
  public void resetPagination() {
    isLoading = false;
    isLastPage = false;
  }

  public void setPaginationListener(OnPaginationListener listener) {
    this.paginationListener = listener;
  }

  /**
   * Interface for pagination events
   */
  public interface OnPaginationListener {
    void onLoadMore();
  }
}
