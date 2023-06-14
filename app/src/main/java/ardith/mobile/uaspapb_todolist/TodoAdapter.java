package ardith.mobile.uaspapb_todolist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.ViewHolder> {
    private List<ModelTodoActivity> mTodoAdapter;

    public TodoAdapter(List<ModelTodoActivity> todoList) {
        mTodoAdapter = todoList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflate the layout for each item in the RecyclerView
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_activity, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // bind the data to the views in each item of the RecyclerView
        ModelTodoActivity todoObject = mTodoAdapter.get(position);
        holder.timeTextView.setText(todoObject.time);
        holder.todoTextView.setText(todoObject.todo);
    }

    @Override
    public int getItemCount() {
        return mTodoAdapter.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView timeTextView;
        TextView todoTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            timeTextView = (TextView) itemView.findViewById(R.id.time_text_view);
            todoTextView = (TextView) itemView.findViewById(R.id.todo_text_view);
        }
    }
}
