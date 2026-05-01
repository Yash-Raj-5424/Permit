package com.btsl.permit.data.datamodel;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Generic wrapper for paginated API responses
 * Holds the list of data items and pagination metadata
 * @param <T> Type of data items in the response
 */
public class PaginatedResponse<T> {

  @SerializedName("data")
  private List<T> data;

  @SerializedName("pageInfo")
  private PageInfo pageInfo;

  // Constructors
  public PaginatedResponse() {
  }

  public PaginatedResponse(List<T> data, PageInfo pageInfo) {
    this.data = data;
    this.pageInfo = pageInfo;
  }

  // Getters and Setters
  public List<T> getData() {
    return data;
  }

  public void setData(List<T> data) {
    this.data = data;
  }

  public PageInfo getPageInfo() {
    return pageInfo;
  }

  public void setPageInfo(PageInfo pageInfo) {
    this.pageInfo = pageInfo;
  }

  @Override
  public String toString() {
    return "PaginatedResponse{" +
        "data=" + data +
        ", pageInfo=" + pageInfo +
        '}';
  }
}
