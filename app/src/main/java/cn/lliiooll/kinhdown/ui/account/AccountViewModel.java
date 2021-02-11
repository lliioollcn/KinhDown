package cn.lliiooll.kinhdown.ui.account;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * @author lliiooll
 */
public class AccountViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public AccountViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("这里是账户");
    }

    public LiveData<String> getText() {
        return mText;
    }
}