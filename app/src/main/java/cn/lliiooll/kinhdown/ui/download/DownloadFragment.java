package cn.lliiooll.kinhdown.ui.download;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import cn.lliiooll.kinhdown.R;
import cn.lliiooll.kinhdown.services.KDownloadServices;
import cn.lliiooll.kinhdown.ui.download.bridge.DownloadAdapter;
import com.arialyy.aria.core.download.DownloadEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lliiooll
 */
public class DownloadFragment extends Fragment {



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_download_main, container, false);
        ListView listView = root.findViewById(R.id.list);
        //doRefsh();
      //  DownloadAdapter adapter = new DownloadAdapter(getContext(),R.layout.activity_download_list,totalList);
        //listView.setAdapter(adapter);
        /*SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(getContext(), getFragmentManager());
        ViewPager viewPager = root.findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        final TabLayout tabs = root.findViewById(R.id.tabs);
        tabs.setTabMode(TabLayout.MODE_FIXED);
        tabs.setupWithViewPager(viewPager);
        viewPager.setCurrentItem(0);
        FloatingActionButton fab = root.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "开始下载", Snackbar.LENGTH_LONG)
                        .setAction("Action", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toasts.showToast("开始下载", Toast.LENGTH_SHORT);
                                PlaceholderFragment.doTest();
                            }
                        }).show();
            }
        });
        PlaceholderFragment.doRefsh();
        PlaceholderFragment.doRefRoot();
         */
        return root;
    }

}