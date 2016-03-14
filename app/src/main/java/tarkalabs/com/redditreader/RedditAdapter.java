package tarkalabs.com.redditreader;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class RedditAdapter extends RecyclerView.Adapter<RedditAdapter.ViewHolder> {

    List<RedditEntry> entries = new ArrayList<>();
    private Context context;

    public RedditAdapter(Context context) {
        this.context = context;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(context).inflate(R.layout.redditlits_row, null);

        return new ViewHolder(rowView);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.displayEntry(entries.get(position));
    }

    @Override
    public int getItemCount() {
        return entries.size();
    }

    public void setEntries(List<RedditEntry> entries) {
        this.entries = entries;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView txtRedditTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            txtRedditTitle = (TextView) itemView.findViewById(R.id.reddittitle);

        }
        public void displayEntry(RedditEntry entry) {
            txtRedditTitle.setText(entry.getTitle());
        }
    }
}
