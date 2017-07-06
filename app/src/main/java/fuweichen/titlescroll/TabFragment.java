package fuweichen.titlescroll;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;


public class TabFragment extends Fragment {
	private String titles;
	private LayoutInflater mInflater;
	private View f_view;
	private TextView textview;
	private Context mContext;
	
	public TabFragment() {
		// TODO Auto-generated constructor stub
	}
     public TabFragment(String titless) {
		super();
		this.titles = titless;
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			 ViewGroup container,  Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		mInflater = inflater;
		mContext = getActivity();
		f_view = inflater.inflate(R.layout.tab_farament, container, false);
		textview = (TextView) f_view.findViewById(R.id.textview);
		textview.setText(titles);
		return f_view;
	}
}
