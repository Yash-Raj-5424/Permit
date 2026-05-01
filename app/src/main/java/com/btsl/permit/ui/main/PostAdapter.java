package com.btsl.permit.ui.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.btsl.permit.data.datamodel.Post;
import java.util.ArrayList;
import java.util.List;

/**
 * RecyclerView Adapter for displaying paginated post list
 */
public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

  private List<Post> posts;
  private OnItemClickListener onItemClickListener;

  public PostAdapter() {
    this.posts = new ArrayList<>();
  }

  public PostAdapter(List<Post> posts) {
    this.posts = posts != null ? new ArrayList<>(posts) : new ArrayList<>();
  }

  @NonNull
  @Override
  public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext())
        .inflate(android.R.layout.simple_list_item_2, parent, false);
    return new PostViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
    Post post = posts.get(position);
    holder.bind(post);
  }

  @Override
  public int getItemCount() {
    return posts.size();
  }

  /**
   * Update the list of posts (replace all)
   */
  public void setPosts(List<Post> newPosts) {
    this.posts = newPosts != null ? new ArrayList<>(newPosts) : new ArrayList<>();
    notifyDataSetChanged();
  }

  /**
   * Add more posts to the existing list (for pagination)
   */
  public void addPosts(List<Post> newPosts) {
    if (newPosts != null) {
      int startPosition = this.posts.size();
      this.posts.addAll(newPosts);
      notifyItemRangeInserted(startPosition, newPosts.size());
    }
  }

  /**
   * Clear all posts from the list
   */
  public void clear() {
    this.posts.clear();
    notifyDataSetChanged();
  }

  /**
   * Get the current list of posts
   */
  public List<Post> getPosts() {
    return new ArrayList<>(posts);
  }

  /**
   * ViewHolder for post items
   */
  public class PostViewHolder extends RecyclerView.ViewHolder {

    private TextView tvPostId;
    private TextView tvPostTitle;

    public PostViewHolder(@NonNull View itemView) {
      super(itemView);
      tvPostId = itemView.findViewById(android.R.id.text1);
      tvPostTitle = itemView.findViewById(android.R.id.text2);
      
      itemView.setOnClickListener(v -> {
        if (onItemClickListener != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
          onItemClickListener.onItemClick(posts.get(getAdapterPosition()));
        }
      });
    }

    public void bind(Post post) {
      tvPostId.setText("ID: " + post.getId());
      tvPostTitle.setText("Title: " + (post.getTitle().length() > 50 
          ? post.getTitle().substring(0, 50) + "..." 
          : post.getTitle()));
    }
  }

  /**
   * Interface for item click listener
   */
  public interface OnItemClickListener {
    void onItemClick(Post post);
  }

  public void setOnItemClickListener(OnItemClickListener listener) {
    this.onItemClickListener = listener;
  }
}
