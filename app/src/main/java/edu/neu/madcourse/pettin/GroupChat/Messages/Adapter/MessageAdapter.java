//package edu.neu.madcourse.pettin.GroupChat.Messages.Adapter;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import java.util.ArrayList;
//
//import edu.neu.madcourse.pettin.Classes.Message;
//import edu.neu.madcourse.pettin.Classes.User;
//
//// class displays the messages in the message activity
//public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {
//
//    private Context context;
//    private ArrayList<Message> listOfMessages;
//    private String userImage;
//
//    public static final int MSG_TYPE_RECEIVER = 0;
//    public static final int MSG_TYPE_SENDER = 1;
//
//    public MessageAdapter(Context context, ArrayList<Message> listOfMessages, String userImage) {
//        this.context = context;
//        this.listOfMessages = listOfMessages;
//        this.userImage = userImage;
//    }
//
//    @NonNull
//    @Override
//    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
////        View view = LayoutInflater.from(context.inflate(R.layout.))
////        return new MessageAdapter.MessageViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return 0;
//    }
//
//    public class MessageViewHolder extends RecyclerView.ViewHolder {
//
////        private Text
//
//        public MessageViewHolder(@NonNull View itemView) {
//            super(itemView);
//        }
//    }
//
//}
