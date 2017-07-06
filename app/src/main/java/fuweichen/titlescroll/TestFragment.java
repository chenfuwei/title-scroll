package fuweichen.titlescroll;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Administrator on 17-6-28.
 */

public class TestFragment extends Fragment{

    public static TestFragment newInstance(String title)
    {
        TestFragment testFragment = new TestFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        testFragment.setArguments(bundle);
        return testFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test_layout, null);
        TextView tvTitle = (TextView)view.findViewById(R.id.tvTitle);
        Bundle bundle = getArguments();
        if(null != bundle)
        {
            tvTitle.setText(bundle.getString("title"));
        }
        return view;
    }
}
