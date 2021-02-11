package cn.lliiooll.kinhdown.ui.file;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import cn.lliiooll.kinhdown.R;
import cn.lliiooll.kinhdown.baidu.data.BaiduCloudInfo;
import cn.lliiooll.kinhdown.fab.DragFloatActionButtonFab;
import cn.lliiooll.kinhdown.fab.DragFloatActionMenu;
import cn.lliiooll.kinhdown.baidu.bridge.FileAdapter;
import com.alibaba.android.arouter.launcher.ARouter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lliiooll
 */
public class FileFragment extends Fragment {

    public static final List<BaiduCloudInfo> shares = new ArrayList<>();
    private FileAdapter adapter;
    private Handler handler = new Handler();
    private Runnable task = new Runnable() {
        @Override
        public void run() {
            if (FileFragment.this.isVisible()) {
                adapter.notifyDataSetChanged();
            }
            handler.postDelayed(task, 1000L);
        }
    };


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_file, container, false);
        final ListView listView = root.findViewById(R.id.file_list);
        if (adapter == null) {
            adapter = new FileAdapter(getContext(), R.layout.view_share_sub, shares);
        }

        listView.setAdapter(adapter);
        // MultiFloatActionButtonLayout button = root.findViewById(R.id.view_float_button);
       /* button.setOnFabItemClickListener(new MultiFloatActionButtonLayout.OnFabItemClickListener() {
            @Override
            public void onFabItemClick(SubFloatLayout view, int pos) {
                switch (pos) {
                    case 2:
                        Toasts.showShort("这里是下载界面");
                        break;
                    default:
                        Toasts.showShort("阴间操作");
                }
            }
        });
        button.requestLayout();
        */
        DragFloatActionMenu button = root.findViewById(R.id.fab_menu);
        button.setClosedOnTouchOutside(true);
        DragFloatActionButtonFab fab_down = root.findViewById(R.id.fab_download);
        DragFloatActionButtonFab fab_add = root.findViewById(R.id.fab_add);
        fab_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button.close(true);
                //Toasts.showShort("这里是下载");
                ARouter.getInstance().build("/kinhdown/download").navigation();
            }
        });
        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button.close(true);
                //Toasts.showShort("这里是下载");
                ARouter.getInstance().build("/kinhdown/addlink").navigation();
            }
        });
        handler.postDelayed(task, 3000L);
        return root;
    }


}