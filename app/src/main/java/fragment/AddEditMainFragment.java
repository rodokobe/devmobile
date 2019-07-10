package fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.projeto.academicplanner.R;

public class AddEditMainFragment extends Fragment {

    private static final String TAG = "AddEditParametersActivity";

    public AddEditMainFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        final View addEditMain = inflater.inflate(R.layout.fragment_add_edit_main, container, false);







        return addEditMain;
    }

}
