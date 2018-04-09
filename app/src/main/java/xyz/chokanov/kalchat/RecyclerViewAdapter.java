package xyz.chokanov.kalchat;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.List;

/**
 * Custom recycle view adapter for storing chat messages to be displayed on screen
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{
    private static final String TAG = "RecyclerViewAdapter";
    private List<String[]> mMessages;
    private Context mContext;

    /**
     * Default constructor
     * @param context Activity using the recycle view
     * @param messages list of an array of strings [Message, UserName, TimeStamp, Avatar]
     */
    public RecyclerViewAdapter(Context context, List<String[]> messages) {
        mMessages = messages;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");

        holder.mMessage.setText(mMessages.get(position)[0]);
        holder.mTimeStamp.setText(mMessages.get(position)[1]);
        holder.mUserName.setText(mMessages.get(position)[2]);
        byte [] encodeByte= Base64.decode(mMessages.get(position)[3], Base64.DEFAULT);
        Bitmap avatar= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
        holder.mImageAvatar.setImageBitmap(avatar);
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO - add a profile view maybe?
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMessages.size();
    }


    /**
     * Custom viewholder for binding individual chat list items
     */
    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView mUserName;
        TextView mMessage;
        TextView mTimeStamp;
        ImageView mImageAvatar;
        RelativeLayout parentLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            mUserName = itemView.findViewById(R.id.txtUser);
            mMessage = itemView.findViewById(R.id.txtMsg);
            mTimeStamp = itemView.findViewById(R.id.txtTime);
            mImageAvatar = itemView.findViewById(R.id.imgAvatarList);
            parentLayout = itemView.findViewById(R.id.layoutItem);
        }
    }
}




