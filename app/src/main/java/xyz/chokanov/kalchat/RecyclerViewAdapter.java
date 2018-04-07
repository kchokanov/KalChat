package xyz.chokanov.kalchat;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    private static final String TAG = "RecyclerViewAdapter";
    private ArrayList<String> mMessages = new ArrayList<>();
    private ArrayList<String> mUsers = new ArrayList<>();
    private Context mContext;

    public RecyclerViewAdapter(ArrayList<String> mMessages, ArrayList<String> mUsers, Context mContext) {
        this.mMessages = mMessages;
        this.mUsers = mUsers;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: called.");
        holder.mUser.setText(mUsers.get(position));
        holder.mMessage.setText(mMessages.get(position));
    }

    @Override
    public int getItemCount() {
        return mMessages.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView mAvatar;
        TextView mMessage, mUser;
        RelativeLayout mParentLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            mAvatar = itemView.findViewById(R.id.imgAvatar);
            mUser = itemView.findViewById(R.id.txtUser);
            mParentLayout = itemView.findViewById(R.id.layoutItem);
        }
    }
}

