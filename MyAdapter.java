package com.example.aashna.mynotes;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int msgType=1;
    private static final int urlType=2;
    private static final int imageType=3;
   private final String urlT="URLTYPE", imageT="IMAGETYPE",msgT="MSGTYPE";
   private ArrayList<Data> dataset;
   private Context context;

    public MyAdapter(ArrayList<Data> dataset, Context context) {
        this.dataset = dataset;
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        Data data=dataset.get(position);
        if(data.getType().equals(msgT)){
            return msgType; }
            else if (data.getType().equals(urlT)){
            return urlType;}
            else if (data.getType().equals(imageT)){
            return imageType;}
            else
                return -1;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       if (viewType == msgType){
           View view=LayoutInflater.from(context).inflate(R.layout.msg_item,parent,false);
           return new Msg_itemAdapter(view);}

           else if (viewType== urlType){
           View view=LayoutInflater.from(context).inflate(R.layout.url_item,parent,false);
           return new Url_itemAdapter(view);}

           else if (viewType == imageType){
           View view=LayoutInflater.from(context).inflate(R.layout.image_item,parent,false);
           return new Image_itemAdapter(view); }
           else
               throw new RuntimeException("Not a right type...");
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

switch (holder.getItemViewType()){
    case msgType:{
        final Msg_itemAdapter msg_itemAdapter=(Msg_itemAdapter)holder ;
        //((TextView)holder.msgT.setText(dataset.get(position).getDescription()));
            msg_itemAdapter.msgT.setText(dataset.get(position).getDescription());
         break; }
    case urlType:{
        final Url_itemAdapter url_itemAdapter=(Url_itemAdapter)holder;
        url_itemAdapter.urlT.setText(dataset.get(position).getUrl());
        url_itemAdapter.urlDes.setText(dataset.get(position).getDescription());

            break;}
    case imageType:{
        final Image_itemAdapter image_itemAdapter=(Image_itemAdapter)holder;
        image_itemAdapter.imageD.setText(dataset.get(position).getDescription());
        GlideApp.with(context)
                .load(dataset.get(position).getUrl())
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .placeholder(android.R.drawable.ic_input_add)
                .error(R.drawable.ic_launcher_background)
                .into(image_itemAdapter.getImage);
        final Uri imgLink=Uri.parse(dataset.get(position).getUrl());
        image_itemAdapter.getImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(Intent.ACTION_VIEW,imgLink);
                try{
                    context.startActivity(intent);}
                catch (Exception e){
                    e.printStackTrace();}
            }
        });
    }
}
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public static class Msg_itemAdapter extends RecyclerView.ViewHolder{
        TextView msgT;
        public Msg_itemAdapter(View itemView) {
            super(itemView);
            msgT=itemView.findViewById(R.id.msgTxt);


        }
    }//end of msgItemAdapterClass

    public static class Url_itemAdapter extends RecyclerView.ViewHolder{

        TextView urlT,urlDes;
        Context context;
        public Url_itemAdapter(final View itemView) {
            super(itemView);
            context=itemView.getContext();
            urlT=itemView.findViewById(R.id.urlText);
            urlDes=itemView.findViewById(R.id.urlDescriptionText);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   // view.setBackgroundColor(Color.TRANSPARENT);
                    //view.invalidate();
                    Uri link= Uri.parse(urlT.getText().toString());
                    Intent intent=new Intent(Intent.ACTION_VIEW,link);
                    try{
                        context.startActivity(intent);}
                    catch (Exception e){
                        e.printStackTrace();}
                    //view.setBackgroundColor(Color.parseColor("#6e4f3a"));
                    //view.invalidate();
                    }
            });

        }
    }//end of Url_item class

    public static class Image_itemAdapter extends RecyclerView.ViewHolder{
        ImageView getImage;
        TextView imageD;
        public Image_itemAdapter(View itemView) {
            super(itemView);
            getImage=itemView.findViewById(R.id.imgView);
            imageD=itemView.findViewById(R.id.imgDescription);

        }//end of  public Image_itemAdapter(View itemView)
    }//end of class Image_itemAdapter
}