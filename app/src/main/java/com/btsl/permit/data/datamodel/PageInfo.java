package com.btsl.permit.data.datamodel;

import com.google.gson.annotations.SerializedName;

/**
 * Contains cursor and pagination metadata for paginated responses
 * Includes navigation information (next/previous cursors) and total count
 */
public class PageInfo {

  @SerializedName("nextCursor")
  private String nextCursor;

  @SerializedName("previousCursor")
  private String previousCursor;

  @SerializedName("totalCount")
  private int totalCount;

  @SerializedName("hasNextPage")
  private boolean hasNextPage;

  @SerializedName("hasPreviousPage")
  private boolean hasPreviousPage;

  @SerializedName("pageSize")
  private int pageSize;

  // Constructors
  public PageInfo() {
  }

  public PageInfo(String nextCursor, String previousCursor, int totalCount,
      boolean hasNextPage, boolean hasPreviousPage, int pageSize) {
    this.nextCursor = nextCursor;
    this.previousCursor = previousCursor;
    this.totalCount = totalCount;
    this.hasNextPage = hasNextPage;
    this.hasPreviousPage = hasPreviousPage;
    this.pageSize = pageSize;
  }

  // Getters and Setters
  public String getNextCursor() {
    return nextCursor;
  }

  public void setNextCursor(String nextCursor) {
    this.nextCursor = nextCursor;
  }

  public String getPreviousCursor() {
    return previousCursor;
  }

  public void setPreviousCursor(String previousCursor) {
    this.previousCursor = previousCursor;
  }

  public int getTotalCount() {
    return totalCount;
  }

  public void setTotalCount(int totalCount) {
    this.totalCount = totalCount;
  }

  public boolean isHasNextPage() {
    return hasNextPage;
  }

  public void setHasNextPage(boolean hasNextPage) {
    this.hasNextPage = hasNextPage;
  }

  public boolean isHasPreviousPage() {
    return hasPreviousPage;
  }

  public void setHasPreviousPage(boolean hasPreviousPage) {
    this.hasPreviousPage = hasPreviousPage;
  }

  public int getPageSize() {
    return pageSize;
  }

  public void setPageSize(int pageSize) {
    this.pageSize = pageSize;
  }

  @Override
  public String toString() {
    return "PageInfo{" +
        "nextCursor='" + nextCursor + '\'' +
        ", previousCursor='" + previousCursor + '\'' +
        ", totalCount=" + totalCount +
        ", hasNextPage=" + hasNextPage +
        ", hasPreviousPage=" + hasPreviousPage +
        ", pageSize=" + pageSize +
        '}';
  }
}
