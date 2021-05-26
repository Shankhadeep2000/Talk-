package com.sarkar.talk.Adapters;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.sarkar.talk.Models.MessageModel;
import com.sarkar.talk.R;


import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter {

    ArrayList<MessageModel> messageModels;
    Context context;
    String recId;

    int SENDER_VIEW_TYPE = 1;
    int RECEIVER_VIEW_TYPE = 2;

    public ChatAdapter(ArrayList<MessageModel> messageModels, Context context) {
        this.messageModels = messageModels;
        this.context = context;
    }

    public ChatAdapter(ArrayList<MessageModel> messageModels, Context context , String recId) {
        this.messageModels = messageModels;
        this.context = context;
        this.recId = recId;


    }

    @NonNull

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder (@NonNull ViewGroup parent,int viewType){
        if (viewType == SENDER_VIEW_TYPE)
        {
            View view = LayoutInflater.from(context).inflate(R.layout.sample_sender, parent, false);
            return new SenderViewHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.sample_reciever, parent, false);
            return new RecieverViewHolder(view);

        }


    }

    @Override
    public int getItemViewType ( int position){
//        if (messageModels.get(position).getuId().equals(FirebaseAuth.getInstance().getUid())) {
//            return SENDER_VIEW_TYPE;
//        } else {
//            return RECEIVER_VIEW_TYPE;
//        }
        return 0;

    }



    @Override
    public void onBindViewHolder (@NonNull RecyclerView.ViewHolder holder,int position){
        MessageModel messageModel = messageModels.get(position);

//    \* Funtion for message delete
//
//            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
//                @Override
//                public boolean onLongClick(View v) {
//
//                    new AlertDialog.Builder(context)
//                            .setTitle("Delete")
//                            .setMessage("Are You Sure To Delete This Message")
//                            .setPositiveButton("yes", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//
//                                    FirebaseDatabase database = FirebaseDatabase.getInstance();
//                                    String sender = FirebaseAuth.getInstance().getUid() + recId;
//                                  database.getReference().child("chats").child(sender)//have to use sender Room which is in the Chat Detail Activity.
//                                            .child(messageModel.getMessageId())
//                                            .setValue(null);
//
//
//                                }
//                            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.dismiss();
//                        }
//                    }).show();
//
//
//                    return false;
//                }
//            });*\

        if (holder.getClass() == SenderViewHolder.class) {
            ((SenderViewHolder)holder).senderMsg.setText(messageModel.getMessage());
        } else {
            ((RecieverViewHolder)holder).receiverMsg.setText(messageModel.getMessage());

        }

    }

    @Override
    public int getItemCount () {
        return messageModels.size();
    }

    public class RecieverViewHolder extends RecyclerView.ViewHolder {

        TextView receiverMsg, receiverTime;

        public RecieverViewHolder(@NonNull View itemView) {

            super(itemView);
            receiverMsg = itemView.findViewById(R.id.recieverText);
            receiverTime = itemView.findViewById(R.id.recieverTime);


        }
    }

    public class SenderViewHolder extends RecyclerView.ViewHolder {
        TextView senderMsg, senderTime;

        public SenderViewHolder(@NonNull View itemView) {
            super(itemView);

            senderMsg = itemView.findViewById(R.id.senderText);
            senderTime = itemView.findViewById(R.id.senderTime);


        }
    }


}

