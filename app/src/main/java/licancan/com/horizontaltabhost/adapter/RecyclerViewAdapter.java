package licancan.com.horizontaltabhost.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

import licancan.com.horizontaltabhost.DownLoadActivity;
import licancan.com.horizontaltabhost.R;
import licancan.com.horizontaltabhost.bean.Catogray;

/**
 * Created by robot on 2017/9/5.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    List<Catogray> list;
    private OnItemClickListener onItemClickListener;
    private  TextView name;
    private  CheckBox cb;
    public RecyclerViewAdapter(Context context, List<Catogray> list) {
        this.context=context;
        this.list=list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.catogray_item,null);

        MyViewHolder holder=new MyViewHolder(view);

        /*//实现自己的回调接口
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.OnItemClickListener((Integer) view.getTag(),view);
            }
        });*/
        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        name.setText(list.get(position).name);
        if(list.get(position).state==true)
        {
            cb.setChecked(true);
        }else{
            cb.setChecked(false);
        }
        /**
         * checkBox的点击事件
         */
       /* cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Catogray c=list.get(position);
                if(cb.isChecked())
                {
                    cb.setChecked(false);
                    c.state=true;
                }
                else{
                    cb.setChecked(true);
                    c.state=false;
                }

                //修改原有的list数据
                list.set(position,c);
            }
        });*/
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.OnItemClickListener(position,holder.itemView);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    /**
     * 自己定义的viewHolder  继承RecyclerView自带的ViewHolder
     */
    class MyViewHolder extends RecyclerView.ViewHolder{


        public MyViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            cb = itemView.findViewById(R.id.cb);

        }
    }

    /**
     * 供调用者调用的接口（所以声明为public）
     */
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    /**
     * 条目点击事件接口
     */
    public interface OnItemClickListener{
        void  OnItemClickListener(int pos,View view);
    }
}
