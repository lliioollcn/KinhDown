package cn.lliiooll.kinhdown.ui.download.bridge;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import com.arialyy.aria.core.download.DownloadEntity;

import java.util.ArrayList;
import java.util.List;

public class PageViewModel extends ViewModel {

    private MutableLiveData<Integer> mIndex = new MutableLiveData<>();
    private LiveData<List<DownloadEntity>> mText = Transformations.map(mIndex, new Function<Integer, List<DownloadEntity>>() {
        @Override
        public List<DownloadEntity> apply(Integer input) {
            List<DownloadEntity> list = new ArrayList<>();
            DownloadEntity entity = new DownloadEntity();
            if (input == 2) {
                entity.setFileName("downing");
            } else if (input == 3) {
                entity.setFileName("downed");
            } else if (input == 4) {
                entity.setFileName("failed");
            } else
                entity.setFileName("total");
            list.add(entity);
            return list;
        }
    });

    public void setIndex(int index) {
        mIndex.setValue(index);
    }

    public LiveData<List<DownloadEntity>> getText() {
        return mText;
    }
}