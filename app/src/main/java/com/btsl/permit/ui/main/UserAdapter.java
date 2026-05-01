package com.btsl.permit.ui.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.btsl.permit.R;
import com.btsl.permit.data.datamodel.User;
import java.util.ArrayList;
import java.util.List;

/**
 * RecyclerView Adapter for displaying paginated user list
 */
public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

  private List<User> users;
  private OnItemClickListener onItemClickListener;

  public UserAdapter() {
    this.users = new ArrayList<>();
  }

  public UserAdapter(List<User> users) {
    this.users = users != null ? new ArrayList<>(users) : new ArrayList<>();
  }

  @NonNull
  @Override
  public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext())
        .inflate(android.R.layout.simple_list_item_2, parent, false);
    return new UserViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
    User user = users.get(position);
    holder.bind(user);
  }

  @Override
  public int getItemCount() {
    return users.size();
  }

  /**
   * Update the list of users (replace all)
   */
  public void setUsers(List<User> newUsers) {
    this.users = newUsers != null ? new ArrayList<>(newUsers) : new ArrayList<>();
    notifyDataSetChanged();
  }

  /**
   * Add more users to the existing list (for pagination)
   */
  public void addUsers(List<User> newUsers) {
    if (newUsers != null) {
      int startPosition = this.users.size();
      this.users.addAll(newUsers);
      notifyItemRangeInserted(startPosition, newUsers.size());
    }
  }

  /**
   * Clear all users from the list
   */
  public void clear() {
    this.users.clear();
    notifyDataSetChanged();
  }

  /**
   * Get the current list of users
   */
  public List<User> getUsers() {
    return new ArrayList<>(users);
  }

  /**
   * ViewHolder for user items
   */
  public class UserViewHolder extends RecyclerView.ViewHolder {

    private TextView tvUserId;
    private TextView tvUserName;

    public UserViewHolder(@NonNull View itemView) {
      super(itemView);
      tvUserId = itemView.findViewById(android.R.id.text1);
      tvUserName = itemView.findViewById(android.R.id.text2);
      
      itemView.setOnClickListener(v -> {
        if (onItemClickListener != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
          onItemClickListener.onItemClick(users.get(getAdapterPosition()));
        }
      });
    }

    public void bind(User user) {
      tvUserId.setText("ID: " + user.getId());
      tvUserName.setText("Name: " + user.getName());
    }
  }

  /**
   * Interface for item click listener
   */
  public interface OnItemClickListener {
    void onItemClick(User user);
  }

  public void setOnItemClickListener(OnItemClickListener listener) {
    this.onItemClickListener = listener;
  }
}
