package com.example.ballot;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class ViewPollAdapter extends RecyclerView.Adapter<ViewPollAdapter.MyViewHolder> {

    private Context context;
    private Activity activity;
    private ArrayList pollTitle, pollQues;

    ViewPollAdapter(Activity activity, Context context, ArrayList pollTitle, ArrayList pollQues){
        this.activity = activity;
        this.context = context;
        this.pollTitle = pollTitle;
        this.pollQues = pollQues;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.activity_view_poll, parent, false);
        return new MyViewHolder(view);
    }

    //@RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.title.setText(String.valueOf(pollTitle.get(position)));

//        holder.voteBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Global.currentSelectedPoll = poll;
//                Intent intent = new Intent(context, VoteActivity.class);
//                context.startActivity(intent);
//            }
//        });
//        //Recyclerview onClickListener, view result
//        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(context, UpdateActivity.class);
//                intent.putExtra("id", String.valueOf(book_id.get(position)));
//                intent.putExtra("title", String.valueOf(book_title.get(position)));
//                intent.putExtra("author", String.valueOf(book_author.get(position)));
//                intent.putExtra("pages", String.valueOf(book_pages.get(position)));
//                activity.startActivityForResult(intent, 1);
//            }
//        });


    }

    @Override
    public int getItemCount() {
        return pollTitle.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        Button voteBtn;
        LinearLayout mainLayout;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.pollTitle);
            voteBtn = itemView.findViewById(R.id.vote);
            mainLayout = itemView.findViewById(R.id.layoutV);

            //Animate Recyclerview
            //Animation translate_anim = AnimationUtils.loadAnimation(context, R.anim.translate_anim);
            //mainLayout.setAnimation(translate_anim);
        }

    }

}
