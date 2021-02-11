package cn.lliiooll.kinhdown.baidu.bridge;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import cn.lliiooll.kinhdown.R;
import cn.lliiooll.kinhdown.baidu.data.BaiduCloudInfo;
import cn.lliiooll.kinhdown.baidu.data.BaiduCloudShareInfo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class FileAdapter extends ArrayAdapter<BaiduCloudInfo> {

    private int resoureId;


    public FileAdapter(Context context, int textViewResourceId, List<BaiduCloudInfo> objects) {
        super(context, textViewResourceId, objects);
        resoureId = textViewResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        BaiduCloudInfo info = getItem(position);
        @SuppressLint("ViewHolder") View view = LayoutInflater.from(getContext()).inflate(resoureId, parent, false);
        ImageView icon = view.findViewById(R.id.share_icon);
        TextView name = view.findViewById(R.id.share_name);
        TextView time = view.findViewById(R.id.share_time);
        time.setText(new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒").format(new Date()));
        if (info.getOnClickListenr() != null) {
            view.setOnClickListener(info.getOnClickListenr());
            icon.setOnClickListener(info.getOnClickListenr());
            name.setOnClickListener(info.getOnClickListenr());
            time.setOnClickListener(info.getOnClickListenr());
            // parent.setOnClickListener(info.getOnClickListenr());
        }
        if (info.getMOnLongClick() != null) {
            view.setOnLongClickListener(info.getMOnLongClick());
            icon.setOnLongClickListener(info.getMOnLongClick());
            name.setOnLongClickListener(info.getMOnLongClick());
            time.setOnLongClickListener(info.getMOnLongClick());
            // parent.setOnClickListener(info.getOnClickListenr());
        }
        icon.setImageDrawable(info.isFolder() ? view.getResources().getDrawable(R.drawable.ic_share_folder_24) : view.getResources().getDrawable(R.drawable.ic_share_file_24));
        String fileName = info.getFileName();
        if (fileName.length() > 30) {
            name.setText(fileName.substring(0, 30) + "...");
        } else {
            name.setText(fileName);
        }
        //TODO: 解析信息
        return view;
    }
}
