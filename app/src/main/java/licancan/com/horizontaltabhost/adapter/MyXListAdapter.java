package licancan.com.horizontaltabhost.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import licancan.com.horizontaltabhost.R;
import licancan.com.horizontaltabhost.bean.Bean;

/**
 * Created by robot on 2017/8/30.
 */

public class MyXListAdapter extends BaseAdapter {

    public static final int atype=0;//第一种布局
    public static final int btype=1;//第二种布局
    public static final int num_type=2;//共两种布局

    Context context;
    List<Bean.ResultBean.DataBean> list;
    public MyXListAdapter(Context context, List<Bean.ResultBean.DataBean> list) {
        this.context=context;
        this.list=list;
    }



    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getViewTypeCount() {
        return num_type;
    }

    /**
     * 判断布局类型
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        if(position%2==0)
        {
            return atype;
        }
        else{
            return  btype;
        }
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder1 holder1=null;
        ViewHolder2 holder2=null;
        int type=getItemViewType(i);
        if(view==null)
        {
            switch (type)
            {
                case atype:
                    holder1=new ViewHolder1();
                    view= LayoutInflater.from(context).inflate(R.layout.item1,null);
                    holder1.img1=view.findViewById(R.id.iv_img1);
                    holder1.title1=view.findViewById(R.id.tv_title1);
                    holder1.category1=view.findViewById(R.id.tv_category1);
                    holder1.date1=view.findViewById(R.id.tv_date1);

                    holder1.title1.setText(list.get(i).getTitle());
                    holder1.category1.setText(list.get(i).getCategory());
                    holder1.date1.setText(list.get(i).getDate());
                    ImageLoader.getInstance().displayImage(list.get(i).getThumbnail_pic_s(),holder1.img1);

                    view.setTag(holder1);
                    break;
                case btype:
                    holder2=new ViewHolder2();
                    view= LayoutInflater.from(context).inflate(R.layout.item2,null);
                    holder2.img2=view.findViewById(R.id.iv_img2);
                    holder2.title2=view.findViewById(R.id.tv_title2);
                    holder2.category2=view.findViewById(R.id.tv_category2);
                    holder2.date2=view.findViewById(R.id.tv_date2);

                    holder2.title2.setText(list.get(i).getTitle());
                    holder2.category2.setText(list.get(i).getCategory());
                    holder2.date2.setText(list.get(i).getDate());
                    ImageLoader.getInstance().displayImage(list.get(i).getThumbnail_pic_s(),holder2.img2);

                    view.setTag(holder2);
                    break;
            }
        }
        else{
            switch (type)
            {
                case atype:
                    holder1= (ViewHolder1) view.getTag();
                    holder1.title1.setText(list.get(i).getTitle());
                    holder1.category1.setText(list.get(i).getCategory());
                    holder1.date1.setText(list.get(i).getDate());
                    ImageLoader.getInstance().displayImage(list.get(i).getThumbnail_pic_s(),holder1.img1);
                    break;
                case btype:
                    holder2= (ViewHolder2) view.getTag();
                    holder2.title2.setText(list.get(i).getTitle());
                    holder2.category2.setText(list.get(i).getCategory());
                    holder2.date2.setText(list.get(i).getDate());
                    ImageLoader.getInstance().displayImage(list.get(i).getThumbnail_pic_s(),holder2.img2);
                    break;
            }
        }
        return view;
    }

    class ViewHolder1{
        ImageView img1;
        TextView title1,date1,category1;
    }
    class ViewHolder2{
        ImageView img2;
        TextView title2,date2,category2;
    }
}
